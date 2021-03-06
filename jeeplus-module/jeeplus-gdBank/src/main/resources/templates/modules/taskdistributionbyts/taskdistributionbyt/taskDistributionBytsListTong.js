<script>
$(document).ready(function() {
	$('#taskDistributionBytsTable').bootstrapTable({
		 
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
               url: "${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/data2",
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
		        field: 'publisherId',
		        title: '任务下发人id',
		        sortable: true,
		        sortName: 'publisherId'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:view")){ %>
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
		        field: 'publisherName',
		        title: '下发人名称',
		        sortable: true,
		        sortName: 'publisherName'
		       
		    }
			,{
		        field: 'startTime',
		        title: '发起时间',
		        sortable: true,
		        sortName: 'startTime'
		       
		    }

			,{
		        field: 'taskName',
		        title: '任务名称',
					   width:100,
		        sortable: true,
		        sortName: 'taskName',
					   cellStyle:formatTableUnit,
					   formatter:paramsMatter
		       
		    }
			,{
		        field: 'content',
		        title: '内容',
					   width:100,
		        sortable: true,
		        sortName: 'content',
					   cellStyle:formatTableUnit,
					   formatter:paramsMatter
		       
		    }
		    ,{
		        field: 'file',
		        title: '模板文件',
		        sortable: true,
		        sortName: 'file',
		        formatter:function(value, row , index){
		        	if(value!=''&&value!=undefined){
                        var valueArray = value.split("|");
                        var labelArray = [];
                        for(var i =0 ; i<valueArray.length; i++){
                            if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(valueArray[i]))
                            {
                                labelArray[i] = "<a href=\""+valueArray[i]+"\" url=\""+valueArray[i]+"\" target=\"_blank\">"+decodeURIComponent(valueArray[i].substring(valueArray[i].lastIndexOf("/")+1))+"</a>"
                            }else{
                                labelArray[i] = '<a href="'+valueArray[i]+'"><img   onclick="jp.showPic(\''+valueArray[i]+'\')"'+' height="50px" src="'+valueArray[i]+'"></a>';
                            }
                        }
                        return labelArray.join(" ");
					}else{
                        return value;
                    }

		        }
		       
		    }
			,{
		        field: 'state',
		        title: '状态',
		        sortable: true,
		        sortName: 'state',
                       formatter:function (value, row,index) {

		        	          if(value=="-1"){
		        	          	 return "完成"
							  }else {
		        	          	 return "执行中("+value+"/"+row.taskIds.split(",").length+")"
							  }



                       }
		       
		    }
			,{
		        field: '',
		        title: '执行列表',
		        sortable: true,
		        sortName: '',
                formatter:function (value, row,index) {
                    console.log(row)
					var id="\""+row.id+"\"";
		        	return "<button style='background-color: #3FDD86;color: white;padding: 5px;border: 0px' onclick='taskList("+id+")'>执行列表</button>"


                }
		    }
                   ,{
                       field: 'endTime',
                       title: '结束时间',
                       sortable: true,
                       sortName: 'endTime'

                   }
		   /* ,{
		        field: '',
		        title: '执行人id',
		        sortable: true,
		        sortName: ''

		    }*/
			,{
			   field: 'operate',
			   title: '操作',
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
				   return [
                       '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
					/*<% if(shiro.hasPermission("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>*/
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#taskDistributionBytsTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#taskDistributionBytsTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#taskDistributionBytsTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/import",function (data) {
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
            jp.downloadFile('${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#taskDistributionBytsTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#taskDistributionBytsTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/export?'+values);
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

	 $('#startTime').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
	 $('#endTime').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});


  function taskList(id) {
     // http://localhost:8080/a/taskdistributionbyts/taskdistributionbytsfits/taskDistributionBytsFit
      jp.openViewDialog('查看执行人', "${ctx}/taskdistributionbyts/taskdistributionbytsfits/taskDistributionBytsFit/getList?tid="+id,'1200px', '500px');
  }



	//获取选中行
  function getIdSelections() {
        return $.map($("#taskDistributionBytsTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该任务下发记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/delete?ids=" + ids, function(data){
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
      $('#taskDistributionBytsTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增任务下发', "${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/form/add",'800px', '500px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑任务下发', "${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/form/edit?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看任务下发', "${ctx}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts/form/view?id="+id,'800px', '500px');
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
</script>