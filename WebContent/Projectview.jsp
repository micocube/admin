<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="./js.jsp"></jsp:include>
</head>
<body>
<!-- 数据网格 -->
	<div class="easyui-tabs" data-options="fit:true,border:false,plain:true"><!-- 正式使用时撤销该层 -->
		<!-- 网格工具栏 -->
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showAdd();">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="del();">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="showEdit();">编辑</a>
			<div id="tb" style="float: right;"> 
			 <form action=""> 
                   <input id="ss" class="easyui-searchbox"  
                        prompt="请输入项目名名"  searcher="doSearch"
                       style="width: 130px; vertical-align: middle;"></input>   
                  </form>
               </div>  
		</div>
		<!-- 网格体 -->
		<div title="DataGrid" id="datagrid" style="padding:5px" >
			<table class="easyui-datagrid"   id="dg" title="Projects" style="width:550px;height:250px"
					toolbar="#toolbar"  rownumbers="true" fitColumns="true"  fit="true"
					pageSize="20"   rownumbers="true" pagination="true"
					data-options="url:'${pageContext.request.contextPath}/project/vagueSelect.do',method:'get'">
				<thead>
					<tr>
					     <th field="ck" checkbox="true"></th>
					      						 <th data-options="field:'createTime'" width="100">项目创建时间 </th>
						  						 <th data-options="field:'name'" width="100">项目名 </th>
						  						 <th data-options="field:'path'" width="100">项目路径 </th>
						  					</tr>
				</thead>
			</table>
		</div>
	</div>
		<div id="add">
			<!-- 添加的模态窗口 -->		
			<div id="w" class="easyui-dialog" title="添加" data-options="modal:true,closed:true,iconCls:'icon-save'" buttons="#dlg-buttons" style="width:500px;height:250px;padding:10px 30px;">
			    <form method="post"  id="addProject" action="${pageContext.request.contextPath}/project/save.do">
			    	<table class="dv-table" >
			    		  			    		  <tr>
			    		  		<td>项目创建时间</td>
			    			    <td><input name="createTime" class="easyui-validatebox" required="true"></input></td>
			    		  </tr>
						  			    		  <tr>
			    		  		<td>项目名</td>
			    			    <td><input name="name" class="easyui-validatebox" required="true"></input></td>
			    		  </tr>
						  			    		  <tr>
			    		  		<td>项目路径</td>
			    			    <td><input name="path" class="easyui-validatebox" required="true"></input></td>
			    		  </tr>
						  			    	</table>
			    </form>
			</div>
		    <div id="dlg-buttons">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#addProject').submit();">Ok</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog('w')">Cancel</a>
		    </div>
		</div>
		
	    <div id="edit">
		    <!--编辑的模态窗口 -->		
			<div id="win" class="easyui-dialog" title="编辑" data-options="closed:true,iconCls:'icon-save'" buttons="#dlg-buttons" style="width:500px;height:250px;padding:10px 30px;">
				    <form method="post"  id="form" action="${pageContext.request.contextPath}/project/edit.do">
				        <input type="hidden" name="id"  id="id"  class="easyui-validatebox" required="true"></input>
				    	<table class="dv-table" >
				    	
				    		  				    		  <tr>
				    		  		<td>项目创建时间</td>
				    			    <td><input name="createTime" id="createTime" required="true"></input></td>
				    		  </tr>
							  				    		  <tr>
				    		  		<td>项目名</td>
				    			    <td><input name="name" id="name" required="true"></input></td>
				    		  </tr>
							  				    		  <tr>
				    		  		<td>项目路径</td>
				    			    <td><input name="path" id="path" required="true"></input></td>
				    		  </tr>
							  				    	</table>
				    </form>
				</div>
			    <div id="dlg-buttons">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#form').submit();">Ok</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog('win')">Cancel</a>
			    </div>
	    </div>
</body>
<script type="text/javascript">
	  function del(){
		  deleteNoteById('dg','${pageContext.request.contextPath}/project/delete.do');
		  flashTable("dg");
	  }
	  function showAdd(){
		  openDialog("w");
	  }
	  function showEdit(){
		  var row = getSingleSelectRow("dg");
		  if(row != null){
	  		  					  $("#createTime").val(row.createTime);
			  					  $("#name").val(row.name);
			  					  $("#path").val(row.path);
			  			  openDialog("win");
			  
		  }
	  }
	  $('#addProject').form({
			success:function(data){
				closeDialog("w");
				$.messager.alert('Info', data, 'info');
				flashTable("dg");
			}
		});
	  $('#form').form({
			success:function(data){
				closeDialog("win");
				$.messager.alert('Info', data, 'info');
				flashTable("dg");
			}
		});
	  
	  function doSearch(value,name){ //用户输入用户名,点击搜素,触发此函数  
	        $("#dg").datagrid({  
	            title:'searchBox',  
	            iconCls:'icon-ok',  
	            width:600,  
	            pageSize:10,  
	            pageList:[5,10,15,20],  
	            nowrap:true,  
	            striped:true,  
	            checkbox:true,
	            collapsible:true,  
	            toolbar:"#toolbar",  
	            url:'${pageContext.request.contextPath}/project/vagueSelect.do?keyword='+value, //触发此action,带上参数searcValue  
	            loadMsg:'数据加载中......',  
	            fitColumns:true,//允许表格自动缩放,以适应父容器  
	            remoteSort:false,  
	            columns : [ [ {  
	                field : 'ck',
	                checkbox:true,
	                width : 150,  
	                title : 'ID'  
	            },
	              						  {     field : 'createTime',  
				                width : 100,  
				                title : '项目创建时间'  
				            },
				   						  {     field : 'name',  
				                width : 100,  
				                title : '项目名'  
				            },
				   						  {     field : 'path',  
				                width : 100,  
				                title : '项目路径'  
				            },
				   	            ] ],  
	            pagination : true,  
	            rownumbers : true  
	        })  
	    }  
</script>		
</html>