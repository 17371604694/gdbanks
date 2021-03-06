<script>
$(document).ready(function() {
	$('#bankConferenceRoomReservationTable').bootstrapTable({
		 
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
               url: "${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/notOpenData",
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
				   //     field: 'context',
				   //     title: '会议内容',
				   // // sortable: true,
				   // // sortName: 'conferenceContext',
				   // //     formatter:function(value, row , index){
				   // //     	return jp.unescapeHTML(value);
				   // //     }
                   //
				   // }
			,{
		        field: 'conferenceTheme',
		        title: '会议主题',
		        sortable: true,
		        sortName: 'conferenceTheme'
		        ,formatter:function(value, row , index){
		        	  <% if(shiro.hasPermission("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:edit") ){ %>
					   if(!value){
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>-</a>";
					   }else{
						  return "<a  href='#' onclick='edit(\""+row.id+"\")'>"+value+"</a>";
						}
                     <% }else if(shiro.hasPermission("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:view")){ %>
					   if(!value){
						  return "<a  href='#' onclick='view(\""+row.id+"\")'>-</a>";
                       }else{
                          return "<a  href='#' onclick='view(\""+row.id+"\")'>"+value+"</a>";
                       }
                     <% }else{ %>
					      return value;
					 <% } %>
		         }
		       
		    }
			,{
		        field: 'hostDeptName',
		        title: '主办部门',
		        // sortable: true,
		        // sortName: 'hostDept'
		       
		    }
			,{
		        field: 'conferenceContactPreson',
		        title: '会议联系人',
		        // sortable: true,
		        // sortName: 'conferenceContactPreson'
		       
		    }
			,{
		        field: 'createDate',
		        title: '申请时间',
		    }
			,{
		        field: 'conferenceRoom',
		        title: '会议室',
		        // formatter:function(value, row , index){
		        // 	return jp.getDictLabel(${fn.toJson(fn.getDictList(''))}, value, "-");
		        // }
		       
		    }
			,{
		        field: 'beginTime',
		        title: '开始时间',
		        sortable: true,
		        sortName: 'beginTime'
		       
		    }
			,{
		        field: 'endTime',
		        title: '结束时间',
		        sortable: true,
		        sortName: 'endTime'
		       
		    }
			,{
		        field: 'conferenceType',
		        title: '会议类型',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fn.toJson(fn.getDictList('meeting_type'))}, value, "-");
		        }
		       
		    }
			// ,{
		    //     field: 'conferencePreson',
		    //     title: '参会人员',
		    // }
			,{
		        field: 'accessory',
		        title: '附件',
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
		    }

			// ,{
		    //     field: 'conferenceDemand',
		    //     title: '会议需求',
		        // sortable: true,
		        // sortName: 'conferenceDemand'
		    // }
			// ,{
		    //     field: 'applicantId',
		    //     title: '申请人',
		    //     sortable: true,
		    //     sortName: 'applicantId'
			//
		    // }
			// ,{
		    //     field: 'deptId',
		    //     title: '申请人部门',
		    //     sortable: true,
		    //     sortName: 'deptId'
		    //
		    // }
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
					<% if(shiro.hasPermission("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5"> <i class="fa fa-search"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5"> <i class="fa fa-pencil"></i></a>',
				   <% } %>
				   <% if(shiro.hasPermission("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs"> <i class="fa fa-trash-o"></a>'
				   <% } %>
				   ].join('');
			   }
		   }
		     ]
		
		});
		

	  $('#bankConferenceRoomReservationTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#bankConferenceRoomReservationTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#bankConferenceRoomReservationTable').bootstrapTable('getSelections').length!=1);
        });

	 $("#import").click(function(){//显示导入面板
            $("#search-collapse").hide();
            $("#import-collapse").fadeToggle()

      })

	 $("#btnImportExcel").click(function(){//导入Excel
		 var importForm = $('#importForm')[0];
		 jp.block('#import-collapse',"文件上传中...");
		 jp.uploadFile(importForm,"${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/import",function (data) {
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
            jp.downloadFile('${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/import/template');
		})

	$("#export").click(function(){//导出Excel文件
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#bankConferenceRoomReservationTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#bankConferenceRoomReservationTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/export?'+values);
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
        return $.map($("#bankConferenceRoomReservationTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //删除
  function del(ids){
     if(!ids){
          ids = getIdSelections();
     }
	 jp.confirm('确认要取消该会议室预定记录吗？取消之后将不再保留！', function(){
		var index =jp.loading();
		jp.get("${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/delete?ids=" + ids, function(data){
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
      $('#bankConferenceRoomReservationTable').bootstrapTable('refresh');
  }

   //新增表单页面
 function add() {
     jp.openSaveDialog('新增会议室预定', "${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/form/add",'1200px', '600px');
 }
  //编辑表单页面
  function edit(id){
      if(!id){
          id = getIdSelections();
      }
	  jp.openSaveDialog('编辑会议室预定', "${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/form/edit?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      if(!id){
          id = getIdSelections();
      }
      jp.openViewDialog('查看会议室预定', "${ctx}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation/form/view?id="+id,'800px', '500px');
  }
</script>