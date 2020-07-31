<script>
var $bankVerifierTrueTreeTable;
$(document).ready(function() {
	  $bankVerifierTrueTreeTable = $('#bankVerifierTrueTreeTable').bootstrapTreeTable({

		 	type: 'get',                   // 请求方式（*）
            url: "${ctx}/verifiertrue/verifiertrues/bankVerifierTrue/data",            // 请求后台的URL（*）
            ajaxParams : {},               // 请求数据的ajax的data属性
            toolbar: "#toolbar",      //顶部工具条
            expandColumn : 1,
               columns: [{
		        checkbox: true

		    },{
		        field: 'columnName',
		        title: '栏目名称',
		        sortable: true,
		        sortName: 'columnName'

		    }
			,{
		        field: 'remarks',
		        title: '审核人',
		        sortable: true,
		        sortName: 'remarks'

		    }
			,{
			   field: 'operate',
			   title: '操作',
			   align: 'center',
			   class: 'text-nowrap',
			   formatter:  function operateFormatter(value, row, index) {

			       if (row.columnName!='首页新闻' && row.columnName!='部室网栏' && row.columnName!="经营单位"){

                       return [
                           '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5" onclick="edit(\''+row.id+'\')">设置审核人</a>'
                       ].join('');
                   }else {
                       return [];
                   }

				  /* return [
					<% if(shiro.hasPermission("verifiertrue:verifiertrues:bankVerifierTrue:view")){ %>
					   '<a class="view btn btn-icon waves-effect waves-light btn-custom btn-xs m-r-5" onclick="view(\''+row.id+'\')">查看</a>',
				   <% } %>
				   <% if(shiro.hasPermission("verifiertrue:verifiertrues:bankVerifierTrue:edit")){ %>
					   '<a class="edit btn btn-icon waves-effect waves-light btn-success btn-xs m-r-5" onclick="edit(\''+row.id+'\')">编辑</a>',
				   <% } %>
				   <% if(shiro.hasPermission("verifiertrue:verifiertrues:bankVerifierTrue:del")){ %>
					   '<a class="del btn btn-icon waves-effect waves-light btn-danger btn-xs  m-r-5" onclick="del(\''+row.id+'\')">删除</a>',
				   <% } %>
				   <% if(shiro.hasPermission("verifiertrue:verifiertrues:bankVerifierTrue:add") ){ %>
					   '<a class="addChild btn btn-icon waves-effect waves-light btn-primary btn-xs" onclick="addChild(\''+row.id+'\')">添加下级审核栏目人</a>'
				   <% } %>
				   ].join('');*/
			   }

		   }
		     ]

		});
		

	});

	//获取选中行
  function getSelections() {
  
        return $("#bankVerifierTrueTreeTable").bootstrapTreeTable('getSelections');
    }

  //删除
  function del(id){
  
	 jp.confirm('确认要删除该审核栏目人记录吗？', function(){
		var index =jp.loading();
		jp.get("${ctx}/verifiertrue/verifiertrues/bankVerifierTrue/delete?id=" + id, function(data){
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

    //添加下级
 function addChild(id){//添加下级
    jp.openSaveDialog('添加下级审核栏目人', '${ctx}/verifiertrue/verifiertrues/bankVerifierTrue/form/add?parent.id='+id,'800px', '500px');
 }
    //刷新列表
  function refresh() {
      $('#bankVerifierTrueTreeTable').bootstrapTreeTable('refresh');
  }
  /*   //新增表单页面
 function add() {
     jp.openSaveDialog('新增审核栏目人', "${ctx}/verifiertrue/verifiertrues/bankVerifierTrue/form/add",'800px', '500px');
 }*/
  //编辑表单页面
  function edit(id){
	  jp.openSaveDialog('编辑审核栏目人', "${ctx}/verifiertrue/verifiertrues/bankVerifierTrue/form/mode?id="+id,'800px', '500px');
  }
  //查看表单页面
  function view(id) {
      jp.openViewDialog('查看审核栏目人', "${ctx}/verifiertrue/verifiertrues/bankVerifierTrue/form/view?id="+id,'800px', '500px');
  }

      var _expandFlag_all = false;
    $("#expandAllBtn").click(function(){
        if(_expandFlag_all){
            $bankVerifierTrueTreeTable.bootstrapTreeTable('expandAll');
        }else{
            $bankVerifierTrueTreeTable.bootstrapTreeTable('collapseAll');
        }
        _expandFlag_all = _expandFlag_all?false:true;
    })
	function refresh() {
		$bankVerifierTrueTreeTable.bootstrapTreeTable('refresh');
	}
</script>