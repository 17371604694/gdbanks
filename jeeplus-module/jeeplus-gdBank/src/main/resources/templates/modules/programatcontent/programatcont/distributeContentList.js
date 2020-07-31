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
        showSearch: true,
        //显示刷新按钮
        showRefresh: true,
        //显示切换手机试图按钮
        showToggle: true,
        //显示 内容列下拉框
        showColumns: true,
        //显示到处按钮
        showExport: true,
        //显示切换分页按钮
        showPaginationSwitch: true,
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
        pageSize: 10,
        //可供选择的每页的行数(*)
        pageList: [10, 25, 50, 100],
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: "${ctx}/programatcontent/programatcont/distributeContent/data?psnameId=${distributeContent.programatIdName}",
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
        columns: [{
            checkbox: true

        }
            ,{
                field: 'programatIdName', //原programatId
                title: '栏目',
                width:100,
                cellStyle:formatTableUnit,
                formatter:paramsMatter
            }
            ,{
                field: 'programatParentidNameList', //原programatParentid
                title: '副栏目',
                width:100,
                cellStyle:formatTableUnit,
                formatter:paramsMatter
            }
            ,{
                field: 'title',
                title: '标题',
                width:300,
                cellStyle:formatTableUnit,
                formatter:paramsMatter
            }
            ,{
                field: 'createDate',
                title: '时间',
                width:130,
                cellStyle:formatTableUnit,
                formatter:paramsMatter
            }

            ,{
                field: 'sorts',
                title: '是否置顶',
                width:90,
                formatter:function(value, row , index){
                    return jp.getDictLabel(${fn.toJson(fn.getDictList('fixation'))}, value, "未设置");
                }

            }
            // ,{
            //     field: 'contentType',
            //     title: '内容类型',
            //     formatter:function(value, row , index){
            //         return jp.getDictLabel(${fn.toJson(fn.getDictList('contentType'))}, value, "-");
            //     }
            // }
            // ,{
            //     field: 'accessorys',
            //     title: '附件',
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
            // }
            // ,{
            //     field: 'content',
            //     title: '内容',
            //     width:155,
            //     formatter:function(value, row , index){
            //         return jp.unescapeHTML(value);
            //     }
            //
            // }
            ,{
                field: 'author',
                title: '作者',
                width:90,
            }
            ,{
                field: 'statepid',
                title: '审核状态',
                width:90,
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
            /*  ,{
                  field: 'stateparentid',
                  title: '副栏目审核状态',
                  sortable: true,
                  sortName: 'stateparentid',
                  formatter:function(value, row , index){
                      if (value==1){
                          return "等待审核"
                      } else if (value==2) {
                          return "审核通过";
                      }else if (value==3) {
                          return "驳回";
                      }else if (value==5) {
                          return "发布";
                      }
                  }
              }*/
            ,{
                field: 'operate',
                title: '操作',
                width:300,
                align: 'center',
                class: 'text-nowrap',
                events: {
                    'click .view': function (e, value, row, index) {
                        view(row.id);
                    },
                    'click .edit': function (e, value, row, index) {
                        edit(row.id)
                    },
                    'click .del': function (e, value, row, index) {
                        del(row.id);

                    },
                    'click .tg': function (e, value, row, index) {
                        tg(row.id);

                    },
                    'click .bh': function (e, value, row, index) {
                        bh(row.id);

                    },
                    'click .zd': function (e, value, row, index) {
                        zd(row.id);

                    },
                    'click .cxfq': function (e, value, row, index) {
                        cxfq(row.id);

                    },
                    'click .yl': function (e, value, row, index) {
                        yl(row.id);

                    },
                },
                formatter:  function operateFormatter(value, row, index) {
                    var retun=[];
                    var user = JSON.parse(jp.getCookie("user"));//用户信息
                    //alert(JSON.stringify(row.reservec));
                   // var bushu=row.stepSum; //步数
                    var bushu=row.stateparentid; //拿当前步奏
                    var shs=[];
                    if (bushu==1){
                        if(row.departmentReviewer){
                            shs=(row.departmentReviewer).split(","); //部门审核人
                        }

                    } else if (bushu==2){
                        if(row.reservec){
                            shs=(row.reservec).split(","); //栏目审核人
                        }else {
                            if(row.remarks){
                                shs=(row.remarks).split(",");  //副栏目审核人
                            }
                        }
                    }else if (bushu==3){
                        if(row.remarks){
                            shs=(row.remarks).split(",");  //副栏目审核人
                        }

                    }
                    for (var i = 0; i <shs.length ; i++) {//添加审核按钮
                        if (shs[i]==user.id) {
                            retun=[
                                '<a class="tg btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5" id="test"> 通过</a>',
                                '<a class="bh btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> 驳回</a>',
                            ]
                        }else if(user.id=='1'||user.id=='c6d9c07543f64a21b4454c23c8dedde2'){
                            retun=[
                                '<a class="tg btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5" id="test"> 通过</a>',
                                '<a class="bh btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> 驳回</a>',
                            ]
                        }
                    }

                    if(user.id=='1'||user.id=='c6d9c07543f64a21b4454c23c8dedde2'){//添加置顶按钮
                        if(bushu==6&row.statepid==2){
                            retun.push(['<a class="zd btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> 置顶</a>']);
                        }
                    }

                    if(bushu==6&row.statepid==3){//审核没有通过，添加重新发起按钮
                        retun.push(['<a class="cxfq btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i ></i>重新发起</a>']);
                    }

                    if(bushu!=6){
                        retun.push(['<a class="edit btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> 修改</a>']);
                        retun.push(['<a class="del btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> 删除</a>']);
                    }
                    retun.push(['<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> 查看</a>']);
                    retun.push(['<a class="yl btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> 预览</a>']);
                    return retun.join('');
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
    $("#import-collapse").fadeToggle()

})

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
    //通过
    function tg(ids){
    if(!ids){
    ids = getIdSelections();
    }
    jp.get("${ctx}/programatcontent/programatcont/distributeContent/passgo?ids="+ids+"&starte=2",function (obj) {
    refresh();
    layer.msg("通过")

    });

    }

function paramsMatter(value,row,index) {
    var span=document.createElement('span');
    span.setAttribute('title',value);
    span.innerHTML = value;
    return span.outerHTML;
}

function formatTableUnit(value, row, index) {
    return {
        css: {
            "white-space": 'nowrap',
            "text-overflow": 'ellipsis',
            "overflow": 'hidden'
        }
    }
}

//预览
function yl(ids){
    if(!ids){
        ids = getIdSelections();
    }
    jp.openViewDialog('预览发稿', "${ctx}/logins/content?id="+ids,'90%', '90%');

}

//置顶发稿
function zd(id) {
    if (!id) {
        id = getIdSelections();
    }
    jp.openSaveDialog('置顶发稿', "${ctx}/programatcontent/programatcont/distributeContent/admintopform?id="+id,'600px', '400px');
}

function cxfq(id) {
    $.post("${ctx}/programatcontent/programatcont/distributeContent/again",{id:id},function (data) {
        layer.msg(data.msg);
    })
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
    jp.openSaveDialog('编辑发稿', "${ctx}/programatcontent/programatcont/distributeContent/form/edit?psnameId=${distributeContent.programatIdName}&id="+id,'90%', '90%');
}

    //刷新列表
    function refresh() {
    $('#distributeContentTable').bootstrapTable('refresh');
}

    //新增表单页面
    function add() {
    jp.openSaveDialog('新增发稿', "${ctx}/programatcontent/programatcont/distributeContent/form/add?psnameId=${distributeContent.programatIdName}",'90%', '90%');
}

    //查看表单页面
    function view(id) {
    if(!id){
    id = getIdSelections();
}
    jp.openViewDialog('查看发稿', "${ctx}/programatcontent/programatcont/distributeContent/form/view?psnameId=${distributeContent.programatIdName}&id="+id,'90%', '90%');
}
</script>