<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>注册成功</title>

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet">
<!--引用自己定义好的样式-->
<link href="css/yitao.css" rel="stylesheet">
<script src="js/jquery-3.2.1.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
	var i = 5;
	var intervalid;
	intervalid = setInterval("fun()", 1000);

	function fun() {
		if (i == 0) {
			window.location.href = "${pageContext.request.contextPath }/register";
			clearInterval(intervalid);
		}
		document.getElementById("mes").innerHTML = i;
		i--;
	}
</script>


</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<!--用户注册-->
    <div class="container" style="height:300px;">
      <div class="row">
          <p style="font-size: 30px;">注册失败!<span id="mes" style="color: red;font-size: 35px;">5</span>秒之后跳转注册界面，请从新注册.....</p>
      </div>
    </div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>

</html>
