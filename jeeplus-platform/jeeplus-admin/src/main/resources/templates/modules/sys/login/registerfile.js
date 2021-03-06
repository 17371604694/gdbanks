<script>
$(document).ready(function() {
    $('#consignFileApprovalTable').bootstrapTable({

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
        url: "${ctx}/consignfileapproval/confileapproval/consignFileApproval/data2?stateStep=5",
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
                field: 'time',
                title: '日期',
                width:155,
                sortable: true,
                sortName: 'time'

            }
            // ,{
            //     field: 'applicantUnit',
            //     title: '申请单位',
            //
            //
            // }
            ,{
                field: 'applicantPersonName',
                title: '申请人',


            }
            // ,{
            //     field: 'goalOfWork',
            //     title: '工作目的',
            //     sortable: true,
            //     sortName: 'goalOfWork',
            //     formatter:function(value, row , index){
                    //这个是复选
                    /*var valueArray = value.split(",");
                    var labelArray = [];
                    for(var i =0 ; i<valueArray.length-1; i++){
                        labelArray[i] = jp.getDictLabel(${fn.toJson(fn.getDictList('workType'))}, valueArray[i], "-");
                    }
                    return labelArray.join(",");*/
                    //这个是单选
            //         return jp.getDictLabel(${fn.toJson(fn.getDictList('workType'))}, value, "-");
            //     }
            //
            // }
            // ,{
            //     field: 'withTheWay',
            //     title: '用  途',
            //     sortable: true,
            //     sortName: 'withTheWay'
            //
            // }
            ,{
                field: 'outboundContent',
                title: '出库内容',
            }
            // ,{
            //     field: 'quantity',
            //     title: '数 量',
            //     sortable: true,
            //     sortName: 'quantity'
            //
            // }
            // ,{
            //     field: 'returnTime',
            //     title: '预计归还时间',
            //     sortable: true,
            //     sortName: 'returnTime'
            //
            // }
            /*,{
                field: 'chargeApplicantPerson',
                title: '申请单位负责人',
                sortable: true,
                sortName: 'chargeApplicantPerson'

            }
            ,{
                field: 'officeHead',
                title: '办公室负责人',


            }*/
            ,{
                field: 'stateStep',
                title: '当前环节',
                formatter:function (value,row,index) {
                    if(value==1){
                        return "<span style='color: red'>单位负责人审核中</span>"
                    }
                    if(value==2){
                        return "<span style='color: red'>材料审核员审核中</span>"
                    }
                    if(value==3){
                        return "<span style='color: red'>办公室负责人审核中</span>"
                    }
                    if(value==4){
                        return "<span style='color: red'>办公室主管行领导审核中</span>"
                    }
                    if(value==5){
                        return "<span style='color: green'>审核已结束</span>"
                    }
                    if(value==6){
                        return "<span style='color: blue'>驳回 待发起</span>"
                    }
                }

            }
            ,{
                field: 'state',
                title: '审核状态',
                // sortable: true,
                // sortName: 'state',
                formatter:function(value, row , index){
                    if (value==1){
                        return "待审核";
                    }
                    if (value==2){
                        if(row.stateStep==5){
                            return "通过";
                        }
                        return "待审核";
                        // if (row.stateStep==2){
                        //     return "单位负责人审核通过";
                        // } else if (row.stateStep==3) {
                        //     return "办公室负责人审核通过 -- 完成";
                        // }else  if(row.stateStep==4){
                        //     return "办公室主管行领导审核通过 -- 完成";
                        // }
                    }
                    if (value==3){
                        return "驳回";
                        // if (row.stateStep==2){
                        //     return "单位负责人审核未通过";
                        // } else if (row.stateStep==3) {
                        //     return "办公室负责人审核未通过";
                        // }else  if(row.stateStep==4){
                        //     return "办公室主管行领导审核 -- 未通过";
                        // }
                    }
                }

            }
            /* ,{
                 field: 'remarks',
                 title: '备注信息',
                 sortable: true,
                 sortName: 'remarks'
                 ,formatter:function(value, row , index){
                 <% if(shiro.hasPermission("consignfileapproval:confileapproval:consignFileApproval:edit") ){ %>
                         if(!value){
                             return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
                         }else{
                             return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
                         }
                     <% }else if(shiro.hasPermission("consignfileapproval:confileapproval:consignFileApproval:view")){ %>
                         if(!value){
                             return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
                         }else{
                             return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                         }
                     <% }else{ %>
                         return value;
                     <% } %>
                 }

             }*/
            // ,{
            //     field: 'operate',
            //     title: '操作',
            //     align: 'center',
            //     class: 'text-nowrap',
            //     events: {
            //         'click .view': function (e, value, row, index) {
            //             view(row.id);
            //         },
            //         'click .edit': function (e, value, row, index) {
            //             edit(row.id)
            //         },
            //         'click .del': function (e, value, row, index) {
            //             del(row.id);
            //
            //         }
            //     },
            //     formatter:  function operateFormatter(value, row, index) {
            //         var u = JSON.parse(jp.getCookie("user"));
            //         var result  = [ '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
            //             /*<% if(shiro.hasPermission("consignfileapproval:confileapproval:consignFileApproval:view")){ %>
            //                '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
            //            <% } %>*/
            //             <% if(shiro.hasPermission("consignfileapproval:confileapproval:consignFileApproval:edit")){ %>
            //             '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
            //         <% } %>
            //     <% if(shiro.hasPermission("consignfileapproval:confileapproval:consignFileApproval:del")){ %>
            //             '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
            //             <% } %>];
            //         var result1 = ['<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>'];
            //         if(row.stateStep==1) {
            //             if (u.name == row.applicantPerson) {
            //                 return result.join('');
            //             } else {
            //                 return result1.join('');
            //             }
            //         }else {
            //             return result1.join('');
            //         }
            //     }
            // }
        ]

    });


    $('#consignFileApprovalTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#remove').prop('disabled', ! $('#consignFileApprovalTable').bootstrapTable('getSelections').length);
        $('#edit').prop('disabled', $('#consignFileApprovalTable').bootstrapTable('getSelections').length!=1);
    });

    $("#import").click(function(){//显示导入面板
        $("#search-collapse").hide();
        $("#import-collapse").fadeToggle()

    })

    $("#btnImportExcel").click(function(){//导入Excel
        var importForm = $('#importForm')[0];
        jp.block('#import-collapse',"文件上传中...");
        jp.uploadFile(importForm,"${ctx}/consignfileapproval/confileapproval/consignFileApproval/import",function (data) {
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
        jp.downloadFile('${ctx}/consignfileapproval/confileapproval/consignFileApproval/import/template');
    })

    $("#export").click(function(){//导出Excel文件
        var searchParam = $("#searchForm").serializeJSON();
        searchParam.pageNo = 1;
        searchParam.pageSize = -1;
        var sortName = $('#consignFileApprovalTable').bootstrapTable("getOptions", "none").sortName;
        var sortOrder = $('#consignFileApprovalTable').bootstrapTable("getOptions", "none").sortOrder;
        var values = "";
        for(var key in searchParam){
            values = values + key + "=" + searchParam[key] + "&";
        }
        if(sortName != undefined && sortOrder != undefined){
            values = values + "orderBy=" + sortName + " "+sortOrder;
        }

        jp.downloadFile('${ctx}/consignfileapproval/confileapproval/consignFileApproval/export?'+values);
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

    $('#time').datepicker({//日期控件初始化
        toggleActive: true,
        language:"zh-CN",
        format:"yyyy-mm-dd"
    });
    $('#returnTime').datepicker({//日期控件初始化
        toggleActive: true,
        language:"zh-CN",
        format:"yyyy-mm-dd"
    });

});

//获取选中行
function getIdSelections() {
    return $.map($("#consignFileApprovalTable").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

//删除
function del(ids){
    if(!ids){
        ids = getIdSelections();
    }
    jp.confirm('确认要删除该寄存档案出库记录吗？', function(){
        var index =jp.loading();
        jp.get("${ctx}/consignfileapproval/confileapproval/consignFileApproval/delete?ids=" + ids, function(data){
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


//刷新列表
function refresh() {
    $('#consignFileApprovalTable').bootstrapTable('refresh');
}

//新增表单页面
function add() {
    jp.openSaveDialog('新增寄存档案出库', "${ctx}/consignfileapproval/confileapproval/consignFileApproval/form/add",'1300px', '700px');
}
//编辑表单页面
function edit(id){
    if(!id){
        id = getIdSelections();
    }
    jp.openSaveDialog('编辑寄存档案出库', "${ctx}/consignfileapproval/confileapproval/consignFileApproval/form/edit?id="+id,'1300px', '700px');
}
//查看表单页面
function view(id) {
    if(!id){
        id = getIdSelections();
    }
    jp.openViewDialog('查看寄存档案出库', "${ctx}/consignfileapproval/confileapproval/consignFileApproval/form/view?id="+id,'1300px', '700px');
}
</script>