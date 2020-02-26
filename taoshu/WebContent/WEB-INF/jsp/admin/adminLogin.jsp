<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>易淘商城后台管理登录界面</title>
		<link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">   
		<link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">   
		<!--引入css让登录界面居中-->
	    <link rel="stylesheet" type="text/css" href="css/login_aligncenter.css"  />
	
		<!-- <script type="text/javascript" src="easyUI/jquery.min.js"></script>   --> 
        <script src="js/jquery-3.2.1.js"></script>
		<script type="text/javascript" src="easyUI/jquery.easyui.min.js"></script>  
        <script type="text/javascript" src="easyUI/locale/easyui-lang-zh_CN.js"></script>        
        <!-- 引入jQuery.cookie.js插件 -->
        <script src="js/jquery.cookie.js"></script> 
	    <style>
	       /*去掉超链接下面的下划线*/
	    	a{ 
	    		text-decoration: none;
	    		}
	    	
	    	/* 对easyUI的panel面板头部css样式进行重写 */
	    	.HeaderCSS div{
	        font-size:18px;
	        height:20px;
	      }
	    </style>
	   
	</head>
	<body>
		<script>
		    //使用easyUI提供方法用jQuery方式创建label并且设置值和位置
			$(function(){
				
				$("#admin_username").textbox({
					labelWidth:100,
					label: '管理员账号:',
					labelPosition: 'before',
				});
				$("#admin_password").textbox({
					labelWidth:100,
					label: '管理员密码:',
					labelPosition: 'before',
				});
				//验证码
				$("#checkimg").textbox({
					required:true,//定义为必填字段。
					missingMessage:'验证码不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:100,//标签宽度
					label: '验证码:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				
				 //为登录面板添加一个工具栏
				 $('#loginPanel').panel({    
						 
					     width:480,
					     height:380,
			             title:'易淘商城后台登录界面',
			             headerCls:'HeaderCSS'
					}); 
				 
				//使用cookie.js插件获取存储在cookie中管理员的账号和密码，显示在登录界面 
				/* 获取cookie的值 ,使用这个插件会自动解码*/
				var adminUserName = $.cookie('adminUserName');
				var adminUserPassword = $.cookie('adminUserPassword');
                //使用easyUI框架之后不能使用js为easyUI的textbox文本框赋值，需要使用easyUI提供的方法进行设置val
				$('#admin_username').textbox('setValue',adminUserName);	 
				$('#admin_password').passwordbox('setValue',adminUserPassword);	 
				//判断是否为空，让记住用户名和密码复选框选中
				if (adminUserName != null && adminUserPassword != null && adminUserName != ""
						&& adminUserPassword != "") {
					$("#remember_me").attr('checked', true);
				}

				 
			});
			
			
			 //每次请求后台生成验证码
			function changeImg(obj) {
				obj.src = "${pageContext.request.contextPath }/checkImg?time="+ new Date().getTime();
			}
			 
			 //当点击登录按钮，使用单击事件并用ajax异步提交表单
			function submitForm()
			{
				 //使用easyUI提交的ajax表单方式，去提交表单
				$('#loginForm').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/adminUserLogin",
					//在提交之前触发，返回false可以终止提交。提交表单前效验用户表单信息是否填写完整
					onSubmit: function(){
						//validate方法做表单字段验证，当所有字段都有效的时候返回true
						var isValid = $(this).form('validate');
	                    //如果效验表单存在不通过，怎提示用户检查数据
						if (!isValid){
							//如果存在效验没通过，提示用户！，使用easyUI消息框
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
							$.messager.alert('提示信息','请检查数据是否填写正确！','warning');
						}
						return isValid;	// 返回false终止表单提交，如果是true就提交表单
					},
					//提交表单成功，时候回调函数 data表示后台返回的数据对象，是个json格式的字符串，需要转换成object对象
					success: function(data){
						//将json格式的字符串data转换成JavaScript中的object对象（json对象),参照easyUI文档例子
					    var data = eval('(' + data + ')');  
						/* 对后台返回的数据进行判断，如果存在提示相关信息 */
						//1.判断返回data是否存在验证码信息，有就提示用户
						if(data.checkCode)
					    {
							//在指定的位置提示验证码错误，使用jQuery实现写HTML标签
							var content='<span style="color:red;font-size:15px;">'+data.checkCodeInfo+'</span>';
							//说明验证码不正确，使用jQuery在指定位置提示用户
							$("#checkCodeTip").html(content);
					    }
						
						//2.判断返回data是否存在登录成功或失败信息，有就提示
						  //登录成功data.success=true
						if(data.success)
						{
						  //alert(data.loginInfo);
						  /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
						    fn: 在窗口关闭的时候触发该回调函数。 
						  */
						  $.messager.alert('提示信息',data.loginInfo,'info',function(){
							  //当用户点击确定时候关闭该消息窗口调用回调函数，跳转到首页
							  location.href="${pageContext.request.contextPath }/adminIndex";
						  });
						  
						}
						  //登录失败data.success=false，提示用户账号或密码错误
						if(data.success==false)
						{
							//登录失败
							//在指定的位置提示用户名或密码错误，使用jQuery实现写HTML标签，（这里和验证码提示位置一样）
							var content='<span style="color:red;font-size:15px;">'+data.loginInfo+'</span>';
							//说明用户名或密码不正确，使用jQuery在指定位置提示用户
							$("#checkCodeTip").html(content);
						}
                        	
					}
				});
			}

			
		</script>
		<!--易淘商城后台登录界面-->
	     <div class="center-in">
	        <!-- 由于面板title样式easyUI写好，现在需要设置headerCls属性对头部css进行重写 -->
			<div class="easyui-panel" id="loginPanel" style="padding:30px 70px 20px 70px;" >
				<!-- 使用easyUI提交的ajax提交表单方式提交表单 -->
			    <form id="loginForm"  method="post">
			      
			    	<!--用户名    missingMessage当文本框未填写时出现的提示信息。-->
					<div style="margin-bottom:20px;margin-top: 30px;font-size: 17px;">
						<input id="admin_username" name="admin_name" class="easyui-textbox" style="width:300px;height:40px;padding:12px" data-options="prompt:'Username',iconCls:'icon-man',iconWidth:38,required:true,missingMessage:'管理员账号不能为空!'">
					</div>
					<!--用户密码,使用easyUI提供的密码框easyui-passwordbox-->
					<div style="margin-bottom:20px;font-size: 17px;">
						<input  id="admin_password"  name="admin_password" class="easyui-passwordbox" style="width:300px;;height:40px;padding:12px" data-options="prompt:'Password',iconWidth:38,required:true,missingMessage:'管理员密码不能为空!'">
					</div>
		
					<!-- 验证码 -->
					<div style="margin-bottom:20px;font-size: 17px;">
					  <!-- 使用float浮动使两个div在同一行显示 -->
					   <div style="float:left;">
					      <input id="checkimg" name="checkImg" class="easyui-textbox" style="width:220px;height:30px;">
					   </div>
					   <div style="float:left;">
					       &nbsp;<img src="${pageContext.request.contextPath }/checkImg" onclick="changeImg(this)">
					   </div>
					</div>
					<!-- 弄个空div拉开间距 -->
				    <div style="margin-bottom:20px;height:25px;" >
				    </div>
				    <!-- 默认复选框不选中 -->
					<div style="margin-bottom:20px;text-align: center;">
					  
	                        <input type="checkbox" id="remember_me" name="remember_me" value="remember_me">记住用户名密码 
						
					</div>
					<!-- 提示用户验证码信息 -->
					<div id="checkCodeTip" style="margin-bottom:10px;">
					  
					</div>
					<div style="text-align: center;">
					
						<a href="javascript:void(0);" onclick="submitForm()" class="easyui-linkbutton"  style="padding:5px 0px;width:80px;">
							<span style="font-size:14px;">登录</span>
						</a>
						<a href="${pageContext.request.contextPath }/adminRegister" class="easyui-linkbutton"  style="padding:5px 0px;width:80px;margin-left: 15px;">
							<span style="font-size:14px;">注册</span>
						</a>
						
					</div>
			    	
			    </form>
				
			</div>
	     </div>
	</body>
</html>
