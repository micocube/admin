<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/easyui/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/static/SyntaxHighlighter/styles/shCore.css"></link>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/static/SyntaxHighlighter/styles/shCoreDefault.css"></link>
	<script language="javascript" src="${pageContext.request.contextPath }/static/SyntaxHighlighter/scripts/shCore.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath }/static/SyntaxHighlighter/scripts/shAutoloader.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath }/static/SyntaxHighlighter/scripts/shBrushJava.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath }/static/SyntaxHighlighter/scripts/shBrushJScript.js"></script>
	<script type="text/javascript">
	function path()
	{
	    var args = arguments,
	    result = [];
	    for(var i = 0; i < args.length; i++)
	        result.push(args[i].replace('@', '${pageContext.request.contextPath }/static/SyntaxHighlighter/scripts/'));
	    return result
	};
	$(document).ready(function(){
	    SyntaxHighlighter.autoloader.apply(null, path(
	    'applescript            @shBrushAppleScript.js',
	    'actionscript3 as3      @shBrushAS3.js', 
	    'bash shell             @shBrushBash.js', 
	    'coldfusion cf          @shBrushColdFusion.js',
	    'cpp c                  @shBrushCpp.js',
	    'c# c-sharp csharp      @shBrushCSharp.js', 
	    'css                    @shBrushCss.js', 
	    'delphi pascal          @shBrushDelphi.js', 
	    'diff patch pas         @shBrushDiff.js', 
	    'erl erlang             @shBrushErlang.js',
	    'groovy                 @shBrushGroovy.js', 
	    'java                   @shBrushJava.js',
	    'jfx javafx             @shBrushJavaFX.js', 
	    'js jscript javascript  @shBrushJScript.js',
	    'perl pl                @shBrushPerl.js',
	    'php                    @shBrushPhp.js', 
	    'text plain             @shBrushPlain.js', 
	    'py python              @shBrushPython.js',
	    'ruby rails ror rb      @shBrushRuby.js',
	    'sass scss              @shBrushSass.js',
	    'scala                  @shBrushScala.js',
	    'sql                    @shBrushSql.js', 
	    'vb vbnet               @shBrushVb.js',
	    'xml xhtml xslt html    @shBrushXml.js'
	    ));
	    SyntaxHighlighter.defaults['smart-tabs'] = true;
	    SyntaxHighlighter.defaults['tab-size'] = 4;
	    SyntaxHighlighter.config.tagName="textArea";
	    SyntaxHighlighter.all();
	});
	</script>
<title>Insert title here</title>
</head>
<body>
<div title="Workspace" id="workspace" style="padding:5px" >
		<textarea id="code" name="code" class="java"></textarea>
</div>
					

<form method="post" action="${pageContext.request.contextPath }/create/build.do" >
		项目名：<input name="projectName" /><br>
		<button type="submit">提交</button>
</form>
</body>
</html>