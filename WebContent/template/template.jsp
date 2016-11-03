<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../js.jsp"></jsp:include>
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
                        prompt="请输入用户名"  searcher="doSearch"
                       style="width: 130px; vertical-align: middle;"></input>   
                  </form>
               </div>  
		</div>
		<!-- 网格体 -->
		<div title="DataGrid" id="datagrid" style="padding:5px" >
			<table class="easyui-datagrid"   id="dg" title="Users" style="width:550px;height:250px"
					toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true"  fit="true"
					pageSize="20"   rownumbers="true" pagination="true"
					data-options="url:'${pageContext.request.contextPath }/vagueSelect.do',method:'get'">
				<thead>
					<tr>
					     <th field="ck" checkbox="true"></th>
						<th data-options="field:'id'" width="80">ID</th>
						<th data-options="field:'name'" width="100">name</th>
						<th data-options="field:'pwd',align:'right'" width="80">pwd</th>
						<th data-options="field:'age',align:'right'" width="80">age</th>
						<th data-options="field:'level'" width="150">level</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
		<div id="add">
			<!-- 添加的模态窗口 -->		
			<div id="w" class="easyui-dialog" title="模态窗口" data-options="modal:true,closed:true,iconCls:'icon-save'" buttons="#dlg-buttons" style="width:500px;height:200px;padding:10px;">
			    <form method="post"  id="addUser" action="${pageContext.request.contextPath }/save.do">
			    	<table class="dv-table" >
			    		<tr>
			    			<td>Name</td>
			    			<td><input name="name" class="easyui-validatebox" required="true"></input></td>
			    			<td>Password</td>
			    			<td><input name="pwd" class="easyui-validatebox" required="true"></input></td>
			    		</tr>
			    		<tr>
			    			<td>Age</td>
			    			<td><input name="age" class="easyui-validatebox" required="true"></input></td>
			    			<td>Level</td>
			    			<td><input name="level" class="easyui-validatebox" required="true"></input></td>
			    		</tr>
			    	</table>
			    </form>
			</div>
		    <div id="dlg-buttons">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="$('#addUser').submit();">Ok</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog('w')">Cancel</a>
		    </div>
		</div>
		
	    <div id="edit">
		    <!--编辑的模态窗口 -->		
			<div id="win" class="easyui-dialog" title="模态窗口" data-options="closed:true,iconCls:'icon-save'" buttons="#dlg-buttons" style="width:500px;height:200px;padding:10px;">
				    <form method="post"  id="form" action="${pageContext.request.contextPath }/edit.do">
				        <input type="hidden" name="id"  id="id"  class="easyui-validatebox" required="true"></input>
				    	<table class="dv-table" >
				    		<tr>
				    			<td>EditName</td>
				    			<td><input name="name"  id="name"  ></input></td>
				    			<td>Password</td>
				    			<td><input name="pwd"   id="pwd" ></input></td>
				    		</tr>
				    		<tr>
				    			<td>Age</td>
				    			<td><input name="age"  id="age" ></input></td>
				    			<td>Level</td>
				    			<td><input name="level"  id="level" ></input></td>
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
		  deleteNoteById('dg','${pageContext.request.contextPath }/delete.do');
		  flashTable("dg");
	  }
	  function showAdd(){
		  openDialog("w");
	  }
	  function showEdit(){
		  var row = getSingleSelectRow("dg");
		  if(row != null){
			  $("#name").val(row.name);
			  $("#id").val(row.id);
			  $("#pwd").val(row.pwd);
			  $("#age").val(row.age);
			  $("#level").val(row.level);
			  openDialog("win");
			  
		  }
	  }
	  $('#addUser').form({
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
	            url:'vagueSelect.do?keyword='+value, //触发此action,带上参数searcValue  
	            loadMsg:'数据加载中......',  
	            fitColumns:true,//允许表格自动缩放,以适应父容器  
	            sortName:'id',  
	            sortOrder:'asc',  
	            remoteSort:false,  
	            columns : [ [ {  
	                field : 'ck',
	                checkbox:true,
	                width : 150,  
	                title : '用户id'  
	            },{  
	                field : 'id',  
	                width : 150,  
	                title : '用户id'  
	            }, {  
	                field : 'name',  
	                width : 150,  
	                title : '用户名'  
	            }, {  
	                field : 'pwd',  
	                width : 230,  
	                align : 'right',  
	                title : '密码'  
	            } ,{  
	                field : 'age',  
	                width : 230,  
	                align : 'right',  
	                title : '年龄'  
	            } ,{  
	                field : 'level',  
	                width : 230,  
	                align : 'right',  
	                title : '等级'  
	            } 
	            ] ],  
	            pagination : true,  
	            rownumbers : true  
	        })  
	    }  
</script>		
</html>