<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="js.jsp"></jsp:include>
	<title>Administrator</title>
</head>
<body class="easyui-layout">
		<!-- 页头 -->
		<div data-options="region:'north'" style="height:50px">快速开发平台</div>
		<!-- 页尾 -->
		<div data-options="region:'south',split:true"  id="console" style="height:70px;"></div>
		<!-- 树形菜单 -->
		<div data-options="region:'west',split:true" title="East" style="width:180px;">
			<ul class="easyui-tree"   id="tt" data-options="url:'create/get.do',method:'get',animate:true,dnd:true,lines:true,
																			onContextMenu: function(e,node){
																				e.preventDefault();
																				$(this).tree('select',node.target);
																				$('#mm').menu('show',{
																					left: e.pageX,
																					top: e.pageY
																				});},
																			onDblClick:function(node){
																								openFile();
																			}">
			</ul>
			
		</div>
			<div id="mm" class="easyui-menu" style="width:120px;">
			<div  data-options="iconCls:'icon-add'">新建</div>
			<div class="menu-sep"></div>
			<div onclick="createProject()">项目</div>
			<div onclick="createFile()">文件</div>
			<div class="menu-sep"></div>
			<div onclick="openFile()" data-options="iconCls:'icon-add'">打开</div>
			<div onclick="saveFile()" data-options="iconCls:'icon-save'">保存</div>
			<div onclick="removeFile()" data-options="iconCls:'icon-cancel'">删除</div>
			<div onclick="reload()" data-options="iconCls:'icon-reload'">刷新</div>
			<div class="menu-sep"></div>
			<div onclick="expand()">展开</div>
			<div onclick="collapse()">收缩</div>
			<div onclick="collapse()">属性</div>
		</div>
		<!-- 主菜单 -->
		<div data-options="region:'east',split:true" title="West" style="width:100px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="Title1" style="padding:10px;">
					content1
				</div>
				<div title="Title2" data-options="selected:true" style="padding:10px;">
					content2
				</div>
				<div title="Title3" style="padding:10px">
					content3
				</div>
			</div>
		</div>
		<!-- 内容 -->
		<div data-options="region:'center',title:'Main Title',iconCls:'icon-ok'">
			<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<!-- 数据网格 -->
					<!-- 工作区 -->
					<div title="Workspace" id="workspace" style="padding:5px" >
						<textarea id="editor" name="editor"  ></textarea>
					</div>
					
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
					<script type="text/javascript">
						var myTextarea = document.getElementById('editor');
						var CodeMirrorEditor = CodeMirror.fromTextArea(myTextarea, {
							matchBrackets: true,  
						    mode: "text/x-java",
							lineNumbers: true,
							tabMode: "indent",
							theme:"eclipse",
				            autoMatchParens: true,
				            textWrapping: true
						});
						
						
						function addToConsole(message){
							 $("#console").append(message+"<br/>");
							 $("#console").animate({scrollTop:$("#console")[0].scrollHeight},'1000');
						}
						
						
						function reload(){  
			                  $('#tt').tree('reload');  
			            }  
						
						function getCurrentPath(){
							var node = $('#tt').tree('getSelected');
							if (node){
								var s = node.text;
								if (node.attributes){
									s += ","+node.attributes.p1+","+node.attributes.p2;
								}
								var father = $("#tt").tree('getParent',node.target);
								var path=s;
								while(null != father){
									path = father.text +'/'+ path;
									father = $("#tt").tree('getParent',father.target);
								}
								return path;
							}else{
								return null;
							}
						}
						
						
						function openFile(){
							var path =	getCurrentPath();
							if(null != path){
								$.ajax({
									  url: "${pageContext.request.contextPath }/create/open.do",
									  data: {path:path},
									  success:  function(data,status){
										  					addToConsole(data.message);
										  					if(data.success==1){
											  					CodeMirrorEditor.setOption("mode",data.editorMode);
															    CodeMirrorEditor.setValue(data.content);
										  					}else{
										  						CodeMirrorEditor.setValue("\t");
										  					}
													  },
									  dataType:"json"
									});
							}
						}
						
						function saveFile(){
							 var code = CodeMirrorEditor.getValue();
							 alert(code);
							var path = getCurrentPath();
							 if(null != path){
									$.ajax({
										  url: "${pageContext.request.contextPath }/create/save.do",
										  data: {path:path,code:code},
										  success:  function(data,status){
															    addToConsole(data.message);
														  },
										  dataType:"json"
										});
								}
						}
						
						function createFile(){
							openDialog("createItemFile");
						}
						
						function sendCreateRequset(){
							closeDialog("createItemFile");
							var fileName = $("#createItemFileName").val();
							var path = getCurrentPath();
							 if(null != path){
									$.ajax({
										  url: "${pageContext.request.contextPath }/create/create.do",
										  data: {path:path,fileName:fileName},
										  success:  function(data,status){
															    addToConsole(data.message);
															    if(data.success==1){
															    	reload();
															    	CodeMirrorEditor.setOption("mode",data.editorMode);
																    CodeMirrorEditor.setValue(data.content);
															    }
														  },
										  dataType:"json"
										});
								}
						}
						
					    function	createProject(){
					    	openDialog("createItem");
					    }
					    
					    function sendCreateProjectRequset(){
							closeDialog("createItem");
							var projectName = $("#createItemName").val();
							var path = getCurrentPath();
							 if(null != path){
									$.ajax({
										  url: "${pageContext.request.contextPath }/create/build.do",
										  data: {path:path,projectName:projectName},
										  success:  function(data,status){
															    addToConsole(data.message);
															    if(data.success==1){
															    	reload();
															    }
														  },
										  dataType:"json"
										});
								}
						}
						
						function removeFile(){
							var path = getCurrentPath();
							 if(null != path){
									$.ajax({
										  url: "${pageContext.request.contextPath }/create/remove.do",
										  data: {path:path},
										  success:  function(data,status){
															    addToConsole(data.message);
															    if(data.success==1){
															    	reload();
															    	CodeMirrorEditor.setValue("\t");
															    }
														  },
										  dataType:"json"
										});
								}
						}
					</script>
					<!-- 网格体 -->
					<div title="DataGrid" id="dataGrid" style="padding:5px" >
						<table class="easyui-datagrid"   id="dg" title="Users" style="width:550px;height:250px"
								toolbar="#toolbar" idField="id" rownumbers="true" fitColumns="true"  fit="true"
								pageSize="20"   rownumbers="true" pagination="true"
								data-options="url:'vagueSelect.do',method:'get'">
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
				   <!-- about -->
				   <div title="About" data-options="href:'_content.html'" style="padding:10px"></div>
			</div>
		</div>
		<div id="createFile">
			<!-- 创建文件的模态窗口 -->		
			<div id="createItemFile" class="easyui-dialog" title="创建文件" data-options="modal:true,closed:true,iconCls:'icon-save'" buttons="#createItemFiledlg" style="width:300px;height:130px;padding:10px;">
			    <form method="post"  id="createItemFileForm" action="save.do">
			    	<table class="dv-table" >
			    		<tr>
			    			<td>Name</td>
			    			<td><input name="createItemFileName"  id="createItemFileName" class="easyui-validatebox" required="true"></input></td>
			    		</tr>						   
			    	</table>
			    </form>
			</div>
		    <div id="createItemFiledlg">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="sendCreateRequset()">Ok</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog('createItemFile')">Cancel</a>
		    </div>
		</div>
		
		<div id="createProject">
			<!-- 创建项目的模态窗口 -->		
			<div id="createItem" class="easyui-dialog" title="创建项目" data-options="modal:true,closed:true,iconCls:'icon-save'" buttons="#createItemdlg" style="width:300px;height:130px;padding:10px;">
			    	<table class="dv-table" >
			    		<tr>
			    			<td>Name</td>
			    			<td><input name="createItemName"  id="createItemName" class="easyui-validatebox" required="true"></input></td>
			    		</tr>						   
			    	</table>
			</div>
		    <div id="createItemdlg">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="sendCreateProjectRequset()">Ok</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog('createItem')">Cancel</a>
		    </div>
		</div>
		
		
		<div id="add">
			<!-- 添加的模态窗口 -->		
			<div id="w" class="easyui-dialog" title="模态窗口" data-options="modal:true,closed:true,iconCls:'icon-save'" buttons="#dlg-buttons" style="width:500px;height:200px;padding:10px;">
			    <form method="post"  id="addUser" action="save.do">
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
				    <form method="post"  id="form" action="edit.do">
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
		  deleteNoteById('dg','delete.do');
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