<script>
$(document).ready(function() {
	$('#advertisingAuditTable').bootstrapTable({
		 
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
               url: "${ctx}/advertisingaudit/advertising/advertisingAudit/data2",
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
			/*,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("advertisingaudit:advertising:advertisingAudit:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("advertisingaudit:advertising:advertisingAudit:view")){ %>
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
			,{
		        field: 'time',
		        title: '时间',
                       width:155,
		        sortable: true,
		        sortName: 'time'
		       
		    }
			,{
		        field: 'applicantUnit',
		        title: '申请单位',
		        // sortable: true,
		        // sortName: 'applicantUnit'
		       
		    }
			,{
		        field: 'applicantPersonName',
		        title: '申请人',
		        // sortable: true,
		        // sortName: 'applicantPerson'
		       
		    }
		    // ,{
		    //     field: 'advertisingCentent',
		    //     title: '广告内容',
            //            width:155,
		    //     formatter:function(value, row , index){
		    //     	return jp.unescapeHTML(value);
		    //     }
            //
		    // }
		  /*  ,{
		        field: 'advertisingFile',
		        title: '文件',

		        formatter:function(value, row , index){
		        	var valueArray = value.split("|");
		        	var labelArray = [];
		        	for(var i =0 ; i<valueArray.length; i++){
		        		if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(valueArray[i]))
		        		{
		        			labelArray[i] = "<a href=\""+valueArray[i]+"\" url=\""+valueArray[i]+"\" target=\"_blank\">"+decodeURIComponent(valueArray[i].substring(valueArray[i].lastIndexOf("/")+1))+"</a>"
		        		}else{
		        			labelArray[i] = '<img   onclick="jp.showPic(\''+valueArray[i]+'\')"'+' height="50px" src="'+valueArray[i]+'">';
		        		}
		        	}
		        	return labelArray.join(" ");
		        }
		       
		    }*/
			/*,{
		        field: '',
		        title: '办公室负责人',
		    }
			,{
		        field: '',
		        title: '办公室主管行领导',
		    }*/

                   ,{
                       field: 'unitPrincipalName',
                       title: '单位负责人',
                       // sortable: true,
                       // sortName: 'offerPrincipalName'
                       /*formatter:function (value, row, index) {
                           if(value){
                                return value;
                           }else {
                               return "未设置"
                           }
                       }*/
                   }
			,{
		        field: 'offerPrincipalName',
		        title: '办公室负责人',
		        // sortable: true,
		        // sortName: 'offerPrincipalName'
		       
		    }
			,{
		        field: 'satrapPrincipalName',
		        title: '行领导',
                formatter:  function (value, row, index) {
                    if(value){
                        return value;
                    }else {
                        return "未设置"
                    }
                }

		    }
				   ,{
					   field: 'stateStep',
					   title: '当前环节',
                       width:155,
					   formatter:  function (value, row, index) {
                           if(row.stateStep==1){
                               return "<span style='color: red'>单位负责人审核中</span>"
                           }else if(row.stateStep==2){
                               return "<span style='color: red'>广告审核员</span>"
                           }else if(row.stateStep==3){
                               return "<span style='color: red'>相关部门审核中</span>"
                           }else if(row.stateStep==4){
                               return "<span style='color: red'>办公室负责人审核</span>"
                           }else if(row.stateStep==5){
                               return "<span style='color: red'>行领导</span>"
                           }else if(row.stateStep==6){
                               return "<span style='color: green'>审核已结束</span>"
                           }else if(row.stateStep===7){
                               return "<span style='color: blue'>驳回 待发起</span>"
                           }
					   }

				   }
            ,{
                field: 'state',
                title: '状态',
                       width:155,
                // sortable: true,
                // sortName: 'state',
					   formatter:function(value, row , index) {
                           if (value==1){
                               return "待审核";
                           }
                           if (value==2){
                               if(row.stateStep==6){
                                   return "通过";
                               }
                               return "待审核";
                           }
                           if (value==3){
                               return "驳回";
                           }
					   }

            }
			,{
			   field: 'operate',
			   title: '操作',
                       width:200,
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

				   }
			   },
			   formatter:  function operateFormatter(value, row, index) {

                   var u = JSON.parse(jp.getCookie("user"));

                   var result  = [
                       '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>'

                   ];

                   if(row.state==1){
                       result.push('<a class="del btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-trash-o"></i></a>')
                   }
                   if(u.id=="1" || u.id=="c6d9c07543f64a21b4454c23c8dedde2"){
                       if(row.applicantPerson==u.id){
                           if(row.stateStep==1) {
                               result.push('<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>')
                           }
                           if(row.stateStep==7){
                               result.push('<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5">重新发起</a>')
                           }
                       }
                       // result.push('<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>')
                   }else{
                       if(row.applicantPerson==u.id){

                           if(row.stateStep==1) {
                               result.push('<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>')
                           }
                           if(row.stateStep==7){
                               result.push('<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5">重新发起</a>')
                           }
                           // if(row.stateStep!=6){
                           //     result.push('<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>')
                           // }

                       }
                   }





                   return result.join('');



			   }
		   }
		     ]
		
		});
		

	  $('#advertisingAuditTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#advertisingAuditTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#advertisingAuditTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/advertisingaudit/advertising/advertisingAudit/import",function (data) {
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
            jp.downloadFile('${ctx}/advertisingaudit/advertising/advertisingAudit/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#advertisingAuditTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#advertisingAuditTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/advertisingaudit/advertising/advertisingAudit/export?'+values);
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
		
	});

	//获取选中行
  function getIdSelections() {
        return $.map($("#advertisingAuditTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该广告审核记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/advertisingaudit/advertising/advertisingAudit/delete?ids=" + ids, function(data){
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
      $('#advertisingAuditTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增广告审核', "${ctx}/advertisingaudit/advertising/advertisingAudit/form/add",'90%', '90%');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑广告审核', "${ctx}/advertisingaudit/advertising/advertisingAudit/form/edit?id="+id,'90%', '90%');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看广告审核', "${ctx}/advertisingaudit/advertising/advertisingAudit/form/view?id="+id,'90%', '90%');
  }
</script>