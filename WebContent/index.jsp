<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<input type="button" id="sendout" value="submit"
		style="font-size: 10pt; width: 50px; height: 25px;">
		<br>
	<label id="output"></label>

	<script type="text/javascript">
		$("#sendout").click(function() {
			$.post("/testDW/TestServlet?date=" + "10000", {}, function(postRtn) {
				$("#output").html(postRtn);
			});
		});
	</script>
</body>
</html>