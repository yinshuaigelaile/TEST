<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>用户注册</title>

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet">
<!--引用自己定义好的样式-->
<link href="css/yitao.css" rel="stylesheet">
<script src="js/jquery-3.2.1.js"></script>
<script src="js/bootstrap.js"></script>
<!-- 引入jQuery表单校验插件js -->
<script src="js/jquery.validate.min.js"></script>
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
    //每次请求后台生成验证码
	function changeImg(obj) {
		obj.src = "${pageContext.request.contextPath }/checkImg?time="
				+ new Date().getTime();
	}
    //当用户点击看不清楚，请求后台生成新的验证码
	function change() {
    	//通过id获得图片验证码对象，然后获取该对象的src属性的值
		var img = document.getElementById("codeimg");
		img.src = img.src + "?";
	}

	//自定义校验规则，检查用户是否存在
	$.validator.addMethod(
	//规则名称
	"checkUserName",
	//效验函数
	function(value, param) {
		//定义一个标记判断是否让规则通过
		var flag=false;//默认不让通过规则，就是提示红色的字
		//value代表输入框的值param代表规则的值写true
		//自定义效验规则，如果用户存在不通过规则返回false，否则返回true
		$.ajax({
			"async":false,
			"type": "POST",
			"url": "${pageContext.request.contextPath }/checkUserName",
			"data" : {"username":value},
			"success" : function(data) {
				flag=data;
			},
			"dataType":"json"
		});
		
		return !flag;
		
	});
	
	//自定义效验规则检查手机号码
	$.validator.addMethod(
	//规则名称
	"checkTelephone",
	//效验函数
	function(value, param) {
		
		//第一个字符： ^ 匹配输入字符串的开始位置 -》  ^1 匹配输入第一个字符是1
    	//第二个字符：使用 [a-z]代表字符范围 -》[3-8]代表3到8之间任意一个字符
    	//还剩九个字符：\d 匹配一个数字字符。等价于 [0-9]就是取0到9之间任意一个数字
    	//然后再\d{9} 匹配\d确定的 9 次
    	//$  匹配输入字符串的结束位置
    	//在正则表达式中两边加“/”表示这个范围内的就是正则表达式了，相当于提示分界的作用；
        return /^1[3-8]\d{9}$/.test(value); 
		
		
	});
	
	//使用jQuery进行注册表单效验,使用默认规则
	$(function() {
		$('#myform2').validate({
			//效验规则,根据name
			rules : {
				username : {
					required : true,
					"checkUserName" : true
				},
				password : {
					required : true
				},
				repassword : {
					required : true,
					equalTo : "#password"
				},
				email : {
					required : true,
					email : true
				},
				name : {
					required : true
				},
				sex : {
					required : true
				},
				birthday : {
					required : true
				},
				telephone : {
					required : true,
					//使用自己定义手机号码规则
					"checkTelephone":true
				},
				address : {
					required : true,
				}
				
			},
			messages : {
				username : {
					required : "用户名不能为空！",
					"checkUserName" : "用户名已经存在"
				},
				password : {
					required : "密码不能为空！"
				},
				repassword : {
					required : "确认密码不能为空！",
					equalTo : "两次输入密码不一致！"
				},
				email : {
					required : "邮箱不能为空！",
					email : "邮箱格式不正确！"
				},
				name : {
					required : "姓名不能为空！",
				},
				sex : {
					required : "性别必须选择一个！"
				},
				birthday : {
					required : "出生日期不能为空！"
				},
				telephone : {
					required : "手机号码不能为空！",
					"checkTelephone" :"请输入正确的手机号码！"
				},
				address : {
					required : "家庭地址不能为空！"
				}

			}
		});
	});
</script>
</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<!--用户注册-->
	<div class="container" style="background: url('img/background.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<p style="font-size: 20px; text-align: center">用户注册</p>
				<form id="myform2" class="form-horizontal" action="${pageContext.request.contextPath }/registerUser" method="post" style="margin-top: 5px;">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"
								name="username" placeholder="请输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id=password
								name="password" placeholder="请输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd"
								name="repassword" placeholder="请输入确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="inputEmail3"
								name="email" placeholder="Email">
						</div>
					</div>

					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption"
								name="name" placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio1" value="male"> 男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio2" value="female"> 女
							</label> <label class="error" for="sex" style="display: none">您没有第三种选择</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday">
						</div>
					</div>
					<div class="form-group">
						<label for="telephone" class="col-sm-2 control-label">手机号码</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="telephone"
								name="telephone" placeholder="请输入手机号码">
						</div>
					</div>
					<div class="form-group">
						<label for="address" class="col-sm-2 control-label">家庭地址</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="address"
								name="address" placeholder="请输入家庭地址">
						</div>
					</div>
					
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input name="validatecode" type="text" class="form-control">

						</div>
						<div class="col-sm-6">
							<img id="codeimg"
								src="${pageContext.request.contextPath }/checkImg"
								onclick="changeImg(this)" /> <a href="javascript:change();">看不清楚，换一张</a>
						</div>
						
                        
					</div>
                    <div>
						<label class="error">${registerinfo }</label>
						</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit" style="height: 35px; width: 100px;background:#e4393c;color:white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>

</html>
