<script>
$(document).ready(function() {

	$('#bankVoteTable').bootstrapTable({
		 
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
               url: "${ctx}/vote/vote/bankVote/bankSelectListData?id=${bankVote.id!}",
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
			// ,{
		    //     field: 'remarks',
		    //     title: '备注信息',
		    //     sortable: true,
		    //     sortName: 'remarks'
		    //     ,formatter:function(value, row , index){
		    //     	  <% if(shiro.hasPermission("vote:vote:bankVote:edit") ){ %>
			// 		   if(!value){
			// 			  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
			// 		   }else{
			// 			  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
			// 			}
            //          <% }else if(shiro.hasPermission("vote:vote:bankVote:view")){ %>
			// 		   if(!value){
			// 			  return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
            //            }else{
            //               return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
            //            }
            //          <% }else{ %>
			// 		      return value;
			// 		 <% } %>
		    //      }
		    //
		    // }
			,{
		        field: 'themeName',
		        title: '主题名',
		        // sortable: true,
		        // sortName: 'themeName'
		       
		    }
			,{
		        field: 'describe',
		        title: '描述',
		        sortable: true,
		        sortName: 'describe'
		        // formatter:function(value, row , index){
		        // 	var valueArray = value.split(",");
		        // 	var labelArray = [];
		        // 	for(var i =0 ; i<valueArray.length-1; i++){
		        // 		labelArray[i] = jp.getDictLabel(${fn.toJson(fn.getDictList(''))}, valueArray[i], "-");
		        // 	}
		        // 	return labelArray.join(",");
		        // }

		    }
			,{
		        field: 'content',
		        title: '正文',
		        sortable: true,
		        sortName: 'content'

		    }
			// ,{
		    //     field: 'endTimes',
		    //     title: '投票截止时间',
		    //     sortable: true,
		    //     sortName: 'endTimes'
		    //
		    // }
			// ,{
		    //     field: 'selectResult',
		    //     title: '选出结果',
		    //     sortable: true,
		    //     sortName: 'selectResult'
		    //
		    // }
			,{
			   field: 'operate',
			   title: '操作',
			   align: 'center',
			   class: 'text-nowrap',
			   events: {
				   'click .view': function (e, value, row, index) {
					   view(row.id)
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

				   <% if(shiro.hasPermission("vote:vote:bankVote:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5">编辑选项</i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("vote:vote:bankVote:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs m-r-5">删除选项</a>',
				   <% } %>

				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#bankVoteTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#bankVoteTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#bankVoteTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/vote/vote/bankVote/import",function (data) {
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
            jp.downloadFile('${ctx}/vote/vote/bankVote/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#bankVoteTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#bankVoteTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/vote/vote/bankVote/export?'+values);
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

	 $('#beginTimes').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
	 $('#endTimes').datepicker({//日期控件初始化
			toggleActive: true,
			language:"zh-CN",
    			format:"yyyy-mm-dd"
		});
		
	});
//主题id
  var id = '${bankVote.id!}';
	//获取选中行
  function getIdSelections() {
        return $.map($("#bankVoteTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要删除该投票记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/vote/vote/bankVote/deleteselect?ids=" + ids, function(data){
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
      $('#bankVoteTable').bootstrapTable('refresh');
  }


  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑选项', "${ctx}/vote/vote/bankVote/editlist?id="+id,'800px', '500px');
  }
  //添加选项表单页面
  function view() {
      jp.openSaveDialog('添加选项', "${ctx}/vote/vote/bankVote/voteselect?id="+id,'800px', '500px');
  }


</script>