<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form method="post">
	<table class="dv-table" style="width:100%;background:#fafafa;padding:5px;margin-top:5px;">
		<tr>
			<td>First Name</td>
			<td><input name="firstname" class="easyui-validatebox" required="true"></input></td>
			<td>Last Name</td>
			<td><input name="lastname" class="easyui-validatebox" required="true"></input></td>
		</tr>
		<tr>
			<td>Phone</td>
			<td><input name="phone"></input></td>
			<td>Email</td>
			<td><input name="email" class="easyui-validatebox" validType="email"></input></td>
		</tr>
	</table>
	<div style="padding:5px 0;text-align:right;padding-right:30px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveItem(<?php echo $_REQUEST['index'];?>)">Save</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelItem(<?php echo $_REQUEST['index'];?>)">Cancel</a>
	</div>
</form>
</body>
</html>