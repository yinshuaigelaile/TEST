<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>用户登录</title>

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet">
<!--引用自己定义好的样式-->
<link href="css/yitao.css" rel="stylesheet">
<script src="js/jquery-3.2.1.js"></script>
<script src="js/bootstrap.js"></script>
<!-- 引入jQuery表单校验插件js -->
<script src="js/jquery.validate.min.js"></script>
<!-- 引入jQuery.cookie.js插件 -->
<script src="js/jquery.cookie.js"></script>
<style type="text/css">
a {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}
/*将表单校验字体颜色变成红色*/
.error {
	color: red;
}
</style>
<script type="text/javascript">
	//点击验证码图片更换验证码
	function changeImg(obj) {
		obj.src = "${pageContext.request.contextPath }/checkImg?time="
				+ new Date().getTime();
	}
	//点击看不清楚，换一张更换验证码
	function change() {
		var img = document.getElementById("codeimg");
		img.src = img.src + "?";
	}

	//使用jQuery进行注册表单效验,使用默认规则
	$(function() {

		/* 获取cookie的值 ,使用这个插件会自动解码*/
		var username1 = $.cookie('username1');
		var password1 = $.cookie('password1');

		$('#username').val(username1);
		$('#password').val(password1);

		//alert("aaaa")
		//判断是否为空
		if (username1 != null && password1 != null && username1 != ""
				&& password1 != "") {
			$("#remember_me").attr('checked', true);
		}

		//登录表单js效验
		$('#myform1').validate({
			//效验规则,根据name
			rules : {
				username : {
					required : true

				},
				password : {
					required : true
				}

			},
			messages : {
				username : {
					required : "用户名不能为空！"

				},
				password : {
					required : "密码不能为空！"
				}

			}
		});
	});
</script>
</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<!--登录界面-->
	<div class="container"
		style="height: 460px; background: url('img/background.jpg') no-repeat;">
		<div class="row">
			<div class="col-md-7"></div>

			<div class="col-md-5 col-xs-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<p style="font-size: 20px; text-align: center">用户登录</p>
					<div>&nbsp;</div>
					<form id="myform1" class="form-horizontal" method="post"
						action="${pageContext.request.contextPath }/loginUser">
						<div class="form-group">
							<label for="username" class="col-sm-2 col-xs-2 control-label">用户名</label>
							<div class="col-sm-6 col-xs-6">
								<input type="text" class="form-control" id="username"
									name="username" placeholder="请输入用户名">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3"
								class="col-sm-2 col-xs-2 control-label">密码</label>
							<div class="col-sm-6 col-xs-6">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3"
								class="col-sm-2 col-xs-2 control-label">验证码</label>
							<div class="col-sm-3 col-sm-3">
								<input type="text" class="form-control" id="inputPassword3"
									name="validatecode" placeholder="请输入验证码">
							</div>
							<div class="col-sm-7 col-xs-7">
								<img id="codeimg"
									src="${pageContext.request.contextPath }/checkImg"
									onclick="changeImg(this)" /> <a href="javascript:change();" >看不清楚，换一张</a>
							</div>
						</div>
						<div>
							<label style="color: red;"> ${logininfo } </label>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10 col-xs-offset-2 col-xs-10">
								<div class="checkbox">
									<label> 
									   <input type="checkbox" name="autologin" id="autologin" value="autologin"> 自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<label> 
									   <input type="checkbox" id="remember_me" name="remember_me" value="remember_me"> 记住用户名密码
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10 col-xs-offset-2 col-xs-10">
								<input type="submit" width="100" value="登录" name="submit"
									style="height: 35px; width: 100px;background:#e4393c;color:white;">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>

</html>
