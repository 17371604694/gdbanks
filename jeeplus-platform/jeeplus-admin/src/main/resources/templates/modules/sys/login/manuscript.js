<script>
$(document).ready(function() {
    // window.location.reload();

    $('#distributeContentTable').bootstrapTable({
        //请求方法
        method: 'post',
        //类型json
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
        //移动端自适应
        mobileResponsive: true,
        //允许列拖动大小
        resizable: true,
        //固定表头

        stickyHeader: true,
        stickyHeaderOffsetY: 0,
        //显示检索按钮
        showSearch: false,
        //显示刷新按钮
        showRefresh: false,
        //显示切换手机试图按钮
        showToggle: false,
        //显示 内容列下拉框
        showColumns: false,
        //显示到处按钮
        showExport: false,
        //显示切换分页按钮
        showPaginationSwitch: false,
        //最低显示2行
        minimumCountColumns: 2,
        //是否显示行间隔色
        striped: false,
        rightFixedColumns: true, //右侧冻结列
        rightFixedNumber: 1,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性(*)
        cache: false,
        //是否显示分页(*)
        pagination: true,
        //分页方式: client客户端分页，server服务端分页(*)
        sidePagination: "server",
        //排序方式
        sortOrder: "asc",
        //初始化加载第一页，默认第一页
        pageNumber:1,
        //每页的记录行数(*)
        pageSize: 5,
        //可供选择的每页的行数(*)
        pageList: [5, 10, 20, 50],
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: "${ctx}/programatcontent/programatcont/distributeContent/allData",
        //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
        //queryParamsType:'',
        ////查询参数,每次调用是会带上这个参数，可自定义
        queryParams : function(params) {
            var searchParam = $("#searchForm").serializeJSON();
            searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
            searchParam.pageSize = params.limit === undefined? -1 : params.limit;
            if(params.sort && params.order){
                searchParam.orderBy = params.sort+ " "+  params.order;
            }
            return searchParam;
        },
        onShowSearch: function () {
            $("#import-collapse").hide();
            $("#search-collapse").fadeToggle();
        },
        columns: [
            {
                field: 'programatIdName', //原programatId
                title: '栏目',
            }
            // ,{
            //     field: 'programatParentidName', //原programatParentid
            //     title: '副栏目',
            //
            // }
            ,{
                field: 'title',
                title: '标题',
            }

            // ,{
            //     field: 'stick',
            //     title: '是否固定',
            //     formatter:function(value, row , index){
            //         return jp.getDictLabel(${fn.toJson(fn.getDictList('fixation'))}, value, "-");
            //     }
            //
            // }
            // ,{
            //     field: 'contentType',
            //     title: '内容类型',
            //     formatter:function(value, row , index){
            //         return jp.getDictLabel(${fn.toJson(fn.getDictList('contentType'))}, value, "-");
            //     }
            //
            // }
            // ,{
            //     field: 'accessorys',
            //     sortName: 'accessorys',
            //     formatter:function(value, row , index){
            //         var valueArray = value.split("|");
            //         var labelArray = [];
            //         for(var i =0 ; i<valueArray.length; i++){
            //             if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(valueArray[i]))
            //             {
            //                 labelArray[i] = "<a href=\""+valueArray[i]+"\" url=\""+valueArray[i]+"\" target=\"_blank\">"+decodeURIComponent(valueArray[i].substring(valueArray[i].lastIndexOf("/")+1))+"</a>"
            //             }else{
            //                 labelArray[i] = '<img   onclick="jp.showPic(\''+valueArray[i]+'\')"'+' height="50px" src="'+valueArray[i]+'">';
            //             }
            //         }
            //         return labelArray.join(" ");
            //     }
            //
            // }
            // ,{
            //     field: 'content',
            //     title: '内容',
            //     formatter:function(value, row , index){
            //         return jp.unescapeHTML(value);
            //     }
            //
            // }
            ,{
                field: 'author',
                title: '作者',
            }
            ,{
                field: 'statepid',
                title: '栏目审核状态',
                formatter:function(value, row , index) {
                    if (value == 1) {
                        return "<span style='color:red;'>待审核</span>"
                    } else if (value == 2) {
                        if (row.stateparentid == 1) {
                            return "<span style='color:red;'>部门负责人待审核</span>";
                        } else if (row.stateparentid == 2) {
                            if(row.reservec){//有栏目审核人
                                return "<span style='color:red;'>栏目审核人待审核</span>";
                            }else {
                                return "<span style='color:red;'>副栏目审核人待审核</span>";
                            }
                        }else if (row.stateparentid == 3) {
                            return "<span style='color:red;'>副栏目审核人待审核</span>";
                        }
                        else if (row.stateparentid == 6) {
                            return "<span style='color:green;'>审核通过</span>";
                        }
                    } else if (value == 3) {
                        //return "驳回";
                        return "<span style='color:green;'>未通过</span>";
                    }
                }

            }
            ]

    });

    $('#distributeContentTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', ! $('#distributeContentTable').bootstrapTable('getSelections').length);
        $('#edit').prop('disabled', $('#distributeContentTable').bootstrapTable('getSelections').length!=1);
    });

    $("#import").click(function(){//显示导入面板
        $("#search-collapse").hide();
        $("#import-collapse").fadeToggle();

    });

    $("#btnImportExcel").click(function(){//导入Excel
        var importForm = $('#importForm')[0];
        jp.block('#import-collapse',"文件上传中...");
        jp.uploadFile(importForm,"${ctx}/programatcontent/programatcont/distributeContent/import",function (data) {
            if(data.success){
                jp.toastr_success(data.msg);
                refresh();
            }else{
                jp.toastr_error(data.msg);
            }
            jp.unblock('#import-collapse',200);
        })
    })

    $("#btnDownloadTpl").click(function(){//下载模板文件
        jp.downloadFile('${ctx}/programatcontent/programatcont/distributeContent/import/template');
    })

    $("#export").click(function(){//导出Excel文件
        var searchParam = $("#searchForm").serializeJSON();
        searchParam.pageNo = 1;
        searchParam.pageSize = -1;
        var sortName = $('#distributeContentTable').bootstrapTable("getOptions", "none").sortName;
        var sortOrder = $('#distributeContentTable').bootstrapTable("getOptions", "none").sortOrder;
        var values = "";
        for(var key in searchParam){
            values = values + key + "=" + searchParam[key] + "&";
        }
        if(sortName != undefined && sortOrder != undefined){
            values = values + "orderBy=" + sortName + " "+sortOrder;
        }

        jp.downloadFile('${ctx}/programatcontent/programatcont/distributeContent/export?'+values);
    })

    $("#search").click("click", function() {// 绑定查询按扭
        refresh();

    });
    $("#reset").click("click", function() { //绑定重置按钮
        $("#searchForm  input").val("");
        $("#searchForm  select").val("");
        $("#searchForm  .select-item").html("");
        refresh();
    });


});

//获取选中行
function getIdSelections() {
    return $.map($("#distributeContentTable").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

//删除
function del(ids){
    if(!ids){
        ids = getIdSelections();
    }
    jp.confirm('确认要删除该操作成功记录吗？', function(){
        var index =jp.loading();
        jp.get("${ctx}/programatcontent/programatcont/distributeContent/delete?ids=" + ids, function(data){
            if(data.success){
                refresh();
                jp.toastr_success(data.msg);
            }else{
                jp.toastr_error(data.msg);
            }
            jp.close(index);
        })
    })
}
var usertt = JSON.parse(jp.getCookie("user"));//用户信息
//通过
function tg(ids){
    if(!ids){
        ids = getIdSelections();
    }
    //  alert(ids);
    //  alert(usertt.id);
    jp.get("${ctx}/programatcontent/programatcont/distributeContent/passgo?ids="+ids+"&starte=2",function (obj) {
        refresh();
        layer.msg("通过")

    });

}
//驳回
function bh(ids){
    if(!ids){
        ids = getIdSelections();
    }
    jp.get("${ctx}/programatcontent/programatcont/distributeContent/passgo?ids="+ids+"&starte=3",function (obj) {
        refresh();
        layer.msg("驳回");

    });

}

//编辑表单页面
function edit(id){
    if(!id){
        id = getIdSelections();
    }
    jp.openSaveDialog('编辑操作成功', "${ctx}/programatcontent/programatcont/distributeContent/form/edit?id="+id,'1200px', '800px');
}

//刷新列表
function refresh() {
    $('#distributeContentTable').bootstrapTable('refresh');
}

//新增表单页面
function add() {
    jp.openSaveDialog('新增操作成功', "${ctx}/programatcontent/programatcont/distributeContent/form/add",'1200px', '800px');
}

//查看表单页面
function view(id) {
    if(!id){
        id = getIdSelections();
    }
    jp.openViewDialog('查看操作成功', "${ctx}/programatcontent/programatcont/distributeContent/form/view?id="+id,'800px', '500px');
}
</script>