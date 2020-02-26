<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改个人信息</title>
		<link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">   
		<link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">   
		<!--引入css让登录界面居中-->
	    <link rel="stylesheet" type="text/css" href="css/login_aligncenter.css"  />
	
		<script type="text/javascript" src="easyUI/jquery.min.js"></script>   
		<script type="text/javascript" src="easyUI/jquery.easyui.min.js"></script>  
        <script type="text/javascript" src="easyUI/locale/easyui-lang-zh_CN.js"></script>  
	    
	    <script>
	    	//使用easyUI提供方法用jQuery方式创建label并且设置值和位置
			$(function(){
				//用户名
				$("#admin_username").textbox({
					required:true,//定义为必填字段。
					missingMessage:'账号不能为空!', //当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '账号:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				//密码
				$("#admin_password").textbox({
					required:true,//定义为必填字段。
					missingMessage:'密码不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '密码:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				//邮箱
				$("#email").textbox({
					required:true,//定义为必填字段。
					validType:'email',//定义字段验证类型
					missingMessage:'email不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: 'email:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				//手机号码
				$("#telephone").textbox({
					required:true,//定义为必填字段。
					missingMessage:'手机号码不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '手机号码:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				
				//当页面加载使用获取后台提供的json数据，填充到表单上，使用easyUI表单提供的方法进行,回显管理员个人信息，进行修改
				$('#editAdminInfoForm').form('load','${pageContext.request.contextPath }/selectAdminInfo?admin_id=${adminUser.id}');	// 读取表单的URL
				
			});
			
			
			
			//自定义一个规则匹配手机号，第一位必须是1，第二位是3-8之间任意一个数字，剩下9位是0-9之间任意一个数字
			//JavaScript自带一个方法test() 方法用于检测一个字符串是否匹配某个模式.
			$.extend($.fn.validatebox.defaults.rules, {
			    phoneNum: {    //验证手机号码
			        validator: function(value, param){   
			        	//第一个字符： ^ 匹配输入字符串的开始位置 -》  ^1 匹配输入第一个字符是1
			        	//第二个字符：使用 [a-z]代表字符范围 -》[3-8]代表3到8之间任意一个字符
			        	//还剩九个字符：\d 匹配一个数字字符。等价于 [0-9]就是取0到9之间任意一个数字
			        	//然后再\d{9} 匹配\d确定的 9 次
			        	//$  匹配输入字符串的结束位置
			        	//在正则表达式中两边加“/”表示这个范围内的就是正则表达式了，相当于提示分界的作用；
			            return /^1[3-8]\d{9}$/.test(value);  
			        },    
			        message: '请输入正确的手机号码!'   
			    }    
			});  
			
			//当用户点击提交，先进行表单效验，如果通过就使用easyUI提供的ajax方式提交表单，否则提示用填写完整数据
			function submitEditAdminInfo()
			{
				//使用easyUI提交的ajax表单方式，去提交表单
				$('#editAdminInfoForm').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/editAdminInfo",
					//在提交之前触发，返回false可以终止提交。提交表单前效验用户表单信息是否填写完整
					onSubmit: function(){
						//validate方法做表单字段验证，当所有字段都有效的时候返回true
						var isValid = $(this).form('validate');
	                    //如果效验表单存在不通过，怎提示用户检查数据
						if (!isValid){
							//如果存在效验没通过，提示用户！
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
						//1.判断返回data是否存在修改成功或失败信息，有就提示
						  //修改个人信息成功data.success=true
						if(data.success)
						{
						  //提示修改成功！
						  /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
						  $.messager.alert('提示信息',data.editAdminInfo,'info');
						  
						}
						  //修改个人信息失败data.success=false
						if(data.success==false)
						{
						  //提示修改失败
						  /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
						  $.messager.alert('提示信息',data.editAdminInfo,'error');
						}
                        	
					}
				});
			}
	    </script>
	</head>
	<body>
		
		<div class="center-in">
				<!-- 为了确保账号用户名唯一，这里不能修改用户名，将它设置为只读readonly:true -->
				<form id="editAdminInfoForm">
				        <!-- 这里需要搞个隐藏域，用来提交管理员id -->
				        <input type="hidden" name="id" value="${adminUser.id }">
				        
				        <!--用户名-->
						<div style="margin-bottom:20px;font-size: 17px;margin-top: 20px;">
							<input id="admin_username" name="admin_name"  class="easyui-textbox" type="text" style="width:300px;height:30px;" data-options="readonly:true">
						</div>
						<!--密码，使用easyUI提供的密码框-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="admin_password" name="admin_password" class="easyui-passwordbox"   style="width:300px;height:30px;">
						</div>
						
						<!--性别-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input type="radio"  name="sex" value="male"/>男&nbsp;&nbsp;&nbsp;&nbsp;
						    <input type="radio" name="sex" value="female" />女
						</div>	
						<!--邮箱-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="email" name="email" class="easyui-textbox" style="width:300px;height:30px;">
						</div>
						<!--手机号码-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="telephone" name="telephone" class="easyui-textbox" style="width:300px;height:30px;" validType='phoneNum'">
						</div>
						
						<div style="text-align: center;">
							<a href="javascript:void(0);" onclick="submitEditAdminInfo()" class="easyui-linkbutton" style="width:90px;height:30px">提交</a>
						</div>
				</form>
				
			
		</div>
	</body>
</html>
