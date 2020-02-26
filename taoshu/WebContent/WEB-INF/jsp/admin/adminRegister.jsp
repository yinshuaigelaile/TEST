<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>注册管理员账号</title>
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
				$("#admin_username").textbox({
					required:true,//定义为必填字段。
					missingMessage:'账号不能为空!', //当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '账号:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				$("#admin_password").textbox({
					required:true,//定义为必填字段。
					missingMessage:'密码不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '密码:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				$("#admin_repassword").textbox({
					required:true,//定义为必填字段。
					missingMessage:'确认密码不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '确认密码:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				$("#email").textbox({
					required:true,//定义为必填字段。
					validType:'email',//定义字段验证类型
					missingMessage:'email不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: 'email:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				$("#telephone").textbox({
					required:true,//定义为必填字段。
					missingMessage:'手机号码不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '手机号码:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				//验证码
				$("#checkimg").textbox({
					required:true,//定义为必填字段。
					missingMessage:'验证码不能为空!',//当文本框未填写时出现的提示信息。
					labelWidth:80,//标签宽度
					label: '验证码:',//标签名称
					labelPosition: 'before',//标签位置。
				});
				
				
				 //为注册面板添加一个工具栏
				 $('#registerPanel').panel({    
							      
			             title:'注册管理员账号',
						 tools: [{    
						    iconCls:'icon-back',    
						    handler:function(){
						    	//当用户点击时候，跳转到注册页面
						    	location.href ="${pageContext.request.contextPath }/adminLogin";
						    }    
						  }]  
				 
					});   
				 
			});
			
			//自定义规则验证两次输入的密码是否一致
			//自定义验证规则，需要重写$.fn.validatebox.defaults.rules中定义的验证器函数和无效消息
			$.extend($.fn.validatebox.defaults.rules, {    
			    eqPassword : {  
			    	//验证器函数，第一个参数代表现在输入的值，第二个参数代表validType []里面的参数param[0]代表括号里面第1个参数
			        validator: function(value, param){ 
			        	//返回一个Boolean值，true或false 这里param[0]='#admin_password'
			        	//通过使用jQuery获取原密码框值和现在密码框值比较，相同就通过规则
			            return value == $(param[0]).val();       
			        },   
			        //无效消息
			        message: '两次输入的密码不一致!'   
			    }    
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
			
			//自定义一个规则检查输入的用户名是否存在，(还有在对应标签上应用该规则validType="checkAdminName")
			$.extend($.fn.validatebox.defaults.rules, {
			    checkAdminName: {    //验证用户名是否存在
			        validator: function(value, param){   
			        	//定义一个标记判断是否让规则通过
			    		var flag=false;//默认不让通过规则，就是提示红色的字
			    		//value代表输入框的值param代表规则的值写true
			    		//自定义效验规则，如果用户存在不通过规则返回false，否则返回true
			    		$.ajax({
			    			"async":false,
			    			"type": "POST",
			    			"url": "${pageContext.request.contextPath }/checkAdminUserName",
			    			"data" : {"admin_username":value},
			    			"success" : function(data) {
			    				flag=data;
			    			},
			    			"dataType":"json"
			    		});
			    		
			    		return !flag; 
			        },    
			        message: '账号已经存在!'   
			    }    
			});  
			
			
			 //每次请求后台生成验证码
			function changeImg(obj) {
				obj.src = "${pageContext.request.contextPath }/checkImg?time="+ new Date().getTime();
			}
			 
			 
			//当用户单击注册，触发单击事件，使用ajax提交表单,效验用户表单数据的有效性，再选择是否提交或提示用户
			function submitForm()
			{
		        //使用easyUI提交的ajax表单方式，去提交表单
				$('#registerform').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/adminUserRegister",
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
						//1.判断返回data是否存在验证码信息，有就提示用户
						if(data.checkCode)
					    {
							//在指定的位置提示验证码错误，使用jQuery实现写HTML标签
							var content='<span style="color:red;font-size:15px;">'+data.checkCodeInfo+'</span>';
							//说明验证码不正确，使用jQuery在指定位置提示用户
							$("#checkCodeTip").html(content);
					    }
						
						//2.判断返回data是否存在注册成功或失败注册信息，有就提示
						  //注册成功data.success=true
						if(data.success)
						{
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
						    fn: 在窗口关闭的时候触发该回调函数。 
						  */
						  $.messager.alert('提示信息',data.registerInfo,'info',function(){
							  //当用户点击确定时候关闭该消息窗口调用回调函数，跳转访问登录界面，显示给用户
							  location.href="${pageContext.request.contextPath }/adminLogin";
						  });
						 
						}
						  //注册失败data.success=false
						if(data.success==false)
						{
						   /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
						   $.messager.alert('提示信息',data.registerInfo,'error');
						}
                        
						
						
					}
				});


			}
			
			//当点击重置按钮，调用easyUI里面textbox提供方法清空所有textbox内容
			function clearForm()
			{
				
				//获取表单调用easyUI中表单form提供的方法清空表单数据
				$('#registerform').form('clear');
				
			}
			
			//easyUI没有提供radio单选按钮的效验，自己扩展自定义一个,对性别单选按钮进行效验
			$.extend($.fn.validatebox.defaults.rules, {
				//判断用户是否选择性别单选按钮 ，进行效验
		        requireRadio: {  
		            validator: function(value, param){  
		            //param[0]=input[name=sex],下面进行获取单选按钮选中的值，如果选中就有值，如果没有选中就是undefined，返回false
		                return $(param[0] + ':checked').val() != undefined;
		            },  
		            message: '请选择性别！'  
		        }  
		    });
	    </script>
	    <style type="text/css">
	      /* 对easyUI的panel面板头部css样式进行重写 */
	    	.HeaderCSS div{
	        font-size:18px;
	        height:20px;
	      }
	    </style>
	</head>
	<body>
		
		<div class="center-in">
		    <!-- 由于面板title样式easyUI写好，现在需要设置headerCls属性对头部css进行重写 -->
			<div class="easyui-panel" id="registerPanel" title="Register" style="width:480px;height:500px;padding:30px 60px;" data-options="headerCls:'HeaderCSS'" >
				
				<!-- 加个表单 -->
				<form id="registerform" method="post">
				        <!--用户名-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="admin_username" name="admin_name"  class="easyui-textbox" type="text" style="width:300px;height:30px;" validType="checkAdminName">
						</div>
						<!--密码，使用easyUI提供的密码框-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="admin_password" name="admin_password"  class="easyui-passwordbox"   style="width:300px;height:30px;">
						</div>
						<!--确认密码-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="admin_repassword" class="easyui-passwordbox"  style="width:300px;height:30px;" validType="eqPassword['#admin_password']" >
						</div>
						<!--性别-->
						<div style="margin-bottom:20px;font-size: 17px;">
						
						    <input class="easyui-validatebox" type="radio" name="sex" value="male" >男                                                              
                            <input class="easyui-validatebox" type="radio" name="sex" value="female" validType="requireRadio['input[name=sex]']">女
	          
						</div>	
						<!--邮箱-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="email" name="email" class="easyui-textbox" style="width:300px;height:30px;">
						</div>
						<!--手机号码-->
						<div style="margin-bottom:20px;font-size: 17px;">
							<input id="telephone" name="telephone" class="easyui-textbox" style="width:300px;height:30px;" validType='phoneNum'">
						</div>
						
						<!-- 验证码 -->
						<div style="margin-bottom:20px;font-size: 17px;">
						  <!-- 使用float浮动使两个div在同一行显示,这里输入验证码 -->
						   <div style="float:left;">
						      <input id="checkimg" name="checkImg" class="easyui-textbox" style="width:200px;height:30px;">
						   </div>
						   <div style="float:left;">
						       &nbsp;<img src="${pageContext.request.contextPath }/checkImg" onclick="changeImg(this)">
						   </div>
						</div>
						<!-- 弄个空div拉开间距-->
						<div style="margin-bottom:20px;height:30px;" >
						    
						</div>
						<!-- 提示用户验证码信息 -->
						<div id="checkCodeTip" style="margin-bottom:20px;">
						  
						</div>
						 
						<!-- 注册 ,使用easyUI提供的ajax提交方式,给按钮添加单击事件-->
						<div style="text-align: center;">
							<!-- 注册按钮 -->
							<a href="javascript:void(0);" onclick="submitForm()" class="easyui-linkbutton"  style="padding:5px 0px;width:80px;">
							   <span style="font-size:14px;">提交</span>
						    </a>
					
						    <!-- 重置按钮 -->
						    <a href="javascript:void(0);" onclick="clearForm()" class="easyui-linkbutton"  style="padding:5px 0px;width:80px;margin-left: 15px;">
							   <span style="font-size:14px;">重置</span>
						    </a>
						</div>
				</form>
				
			</div>
		</div>
	</body>
</html>
