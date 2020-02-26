<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>淘书吧后台管理系统首页</title>
		<link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">   
		<link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">   
		
		<script type="text/javascript" src="easyUI/jquery.min.js"></script>   
		<script type="text/javascript" src="easyUI/jquery.easyui.min.js"></script>  
        <script type="text/javascript" src="easyUI/locale/easyui-lang-zh_CN.js"></script>  
	    <style>
	       /*去掉超链接下面的下划线*/
	    	a{ 
	    		text-decoration: none;
	    		}
	    	
	    	/*解决出现两个垂直滚动条,并且在iframe设置scrolling="auto" ,将tab自己那个div出现的滚动条隐藏*/
	        #tab .tabs-panels>.panel>.panel-body {  
		       overflow: hidden;  
		    } 
	    </style>
	    <script type="text/javascript">
	        //当页面初始化时候显示欢迎框
	        $(function(){
	        	//页面初始化时候显示欢迎框，5秒之后滑动消失
	        	$.messager.show({
	        		title:'消息提示', //在头部面板显示的标题文本。
	        		msg:'欢迎登录，${adminUser.admin_name }管理员',  //显示的消息文本。
	        		timeout:5000,   //如果定义为0，消息窗体将不会自动关闭，除非用户关闭他。如果定义成非0的树，消息窗体将在超时后自动关闭。默认：4秒。 
	        		showType:'slide'  //定义将如何显示该消息。可用值有：null,slide,fade,show。默认：slide
	        	});

	        });
	    
	    
		    //当用户单击左边列表，使用js动态添加选项卡到中间的tab中
		    function addTab(obj,url)
		    {	
		    	
		      //获取用户点击的按钮的值,例如用户点击用户列表，这里获取就是用户列表四个字
		      var value=$(obj).text();
		      //定义一个内联框架iframe ，在框架里面显示一个页面
		      //<iframe height="100%" width="100%" frameborder="0" src=""></iframe>
			  //选项卡中的内容,显示一个页面，使用frame框架  src=/edit_adminInfo,因为jsp页面放在WEB-INF不能直接方法，通过映射才访问到
			  var content='<iframe scrolling="auto" height="100%" width="100%" frameborder="0" src="'+url+'"></iframe>';
					   
		      // 通过jQuery动态添加选项卡
		      //在添加选项卡先判断该选项卡是否存在/就是是否打开状态，使用easyUI的tab组件提供的方法exist，通过title名称进行判断
		      //'which'参数可以是选项卡面板的标题或索引。  方法的返回值是boolean类型
		       var existTab= $("#tab").tabs('exists',value);
		       //如果该选项卡不存在，就添加一个选项卡，否则将打开选项卡选中
		       if(existTab)
		       {
		       	    //如果该选项卡存在就让该选项卡被选中
		       	    $("#tab").tabs('select',value);
		       }else
		       {
			       	//选项卡不存在，就在tab中打开一个
			       	$('#tab').tabs('add',{    
			       		//选项卡的标题
					    title:value,
					    //定义一个内联框架iframe ，在框架里面显示一个页面
		                //<iframe height="100%" width="100%" frameborder="0" src=""></iframe>
					    //选项卡中的内容,显示一个页面，使用frame框架
					    content:content,//在上面拼接URL变量
					    //显示一个叉号可以关闭选项卡
					    closable:true,  
					         
					});
		       }
				
		    }
			  
		    
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
		  
		  //当用户点击确定按钮，修改密码，当用户点击确定时候，先进行表单效验，然后使用ajax提交表单
		  function editNewPassword()
		  {
			  //使用easyUI提交的ajax表单方式，去提交表单
				$('#editNewPasswordForm').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/updateAdminPassword",
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
						//1.判断返回data是否存在修改密码成功或失败注册信息，有就提示
						  //修改密码成功data.success=true
						if(data.success)
						{
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
						    fn: 在窗口关闭的时候触发该回调函数。 
						  */
						  $.messager.alert('提示信息',data.updateAdminPasswordInfo,'info',function(){
							  //在关闭修改密码窗口之前，将验证框中输入的内容清空，因为ajax提交表单不会刷新页面，内容还在清空表单,使用easyUI提供清空表单方法
							  $("#editNewPasswordForm").form('clear');
							  //修改密码成功，关闭修改密码Windows窗口
							  $("#editPwdWindow").window('close');
						  }); 
						}
						  //2.修改密码失败data.success=false
						if(data.success==false)
						{
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
							$.messager.alert('提示信息',data.updateAdminPasswordInfo,'error');
						}	
					}
				});	
		  }
		  
		  //当用户点击取消按钮，取消修改密码关闭修改密码窗口
		  function CanceleditNewPassword()
		  {
			  //点击取消按钮，窗口关闭之前应该清空一下表单，不然这些验证框会存储输入的内容没有清空
			   $("#editNewPasswordForm").form('clear');
			  //关闭修改密码窗口
			  $("#editPwdWindow").window('close');
		  }
		</script>
	</head>
	
	<!--easyui-layout 使用完整页面创建布局-->
	<body class="easyui-layout">
		
			
		<!--北部-->
		<div data-options="region:'north',border:false" style="height:90px;padding:10px;background: url(img/houtai/houtai_background1.jpg);background-size:100%;">
			<div style="text-align: center;font-size: 25px;margin-top: 10px;">
				<strong><em>淘&nbsp;&nbsp;书&nbsp;&nbsp;吧&nbsp;&nbsp;后&nbsp;&nbsp;台&nbsp;&nbsp;管&nbsp;&nbsp;理&nbsp;&nbsp;系&nbsp;&nbsp;统</em></strong>
			</div>
			<div style="text-align: right;font-size: 15px;">
				<span>欢迎，<strong>${adminUser.admin_name }</strong></span>&nbsp;&nbsp;
				<a id="btn" href="javascript:void(0);" class="easyui-linkbutton" onclick="$('#editPwdWindow').window('open')" >修改密码</a> 
				<a id="btn" href="${pageContext.request.contextPath }/deleteAdminUser" class="easyui-linkbutton" >安全退出</a> 
			</div>
			
		</div>
		<!--西部-->
		<div data-options="region:'west',split:true" title="菜单导航" style="width:220px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<!--用户管理-->
				<div title="用户管理"  style="padding:10px;">
					<!--用户列表-->
					<div style="margin-top: 10px;">
						<a id="btn" href="javascript:void(0);" onclick="addTab(this,'${pageContext.request.contextPath }/adminUser_list')" class="easyui-linkbutton" style="width: 100%;">用户列表</a>  
					</div>
					
				</div>
				<!--商品管理-->
				<div title="商品管理"  style="padding:10px;">
					<!--商品列表-->
					<div style="margin-top: 10px;">
						<a id="btn" href="javascript:void(0);" onclick="addTab(this,'${pageContext.request.contextPath }/adminProduct_list')" class="easyui-linkbutton" style="width: 100%;">商品列表</a>  
					</div>
					
				</div>
				<!--订单管理-->
				<div title="订单管理"  style="padding:10px">
					<!--订单列表-->
					<div style="margin-top: 10px;">
						<a id="btn" href="javascript:void(0);" onclick="addTab(this,'${pageContext.request.contextPath }/adminOrder_list')" class="easyui-linkbutton" style="width: 100%;">订单列表</a>  
					</div>
					
				</div>
				<!--分类管理-->
				<div title="分类管理"  style="padding:10px">
					<!--分类列表-->
					<div style="margin-top: 10px;">
						<a id="btn" href="javascript:void(0);" onclick="addTab(this,'${pageContext.request.contextPath }/adminCategory_list')" class="easyui-linkbutton" style="width: 100%;">分类列表</a>  
					</div>
					
				</div>
				<!--个人信息管理-->
				<div title="个人信息管理"  style="padding:10px">
					<!--个人信息列表-->
					<div style="margin-top: 10px;">
						<a id="btn" href="javascript:void(0);" onclick="addTab(this,'${pageContext.request.contextPath }/edit_adminInfo')" class="easyui-linkbutton" style="width: 100%;">修改个人信息</a>  
					</div>
					
				</div>
			</div>
		</div>
		
		<!--南部-->
		<div data-options="region:'south',border:false" style="height:35px;background:#A9FACD;padding:10px;background: url(img/houtai/houtai_background2.jpg);background-size:100%;"></div>
		<!--中间-->
		<div  data-options="region:'center'">
		   <!--tab选项卡div，使用js动态添加选项卡-->
		   <div id="tab" class="easyui-tabs"  data-options="fit:true" >   
			   
           </div> 
           
		</div>
	    <!--修改密码窗口,默认设置close为true定义是否可以关闭窗口。，就是默认window窗口是打开将属性close=true窗口就关闭，当点击修改密码时候，让该windows窗口打开open  modal="true"定义是否将窗体显示为模式化窗口。resizable 定义是否能够改变窗口大小。-->
	    <div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
	       draggable="false" maximizable="false" icon="icon-save"  style="width: 300px; height: 160px; padding: 5px;
	        background: #fafafa">
	        <div class="easyui-layout" fit="true">
	            <!-- 使用layout布局中的中部和南部 -->
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                <form id="editNewPasswordForm">
	                    <!-- cellpadding 属性规定单元边沿与其内容之间的空白。为3像素 ，使用easyUI的ValidateBox（验证框）-->
		                <table cellpadding=3>
		                    <tr>
		                        <td>新密码：</td>
		                        <td><input id="NewPassword" name="newAdminPassword" class="easyui-validatebox" type="Password" data-options="required:true,missingMessage:'新密码不能为空!'" /></td>
		                    </tr>
		                    <tr>
		                        <td>确认密码：</td>
		                        <td><input id="ReNewPassword" class="easyui-validatebox" type="Password" validType="eqPassword['#NewPassword']" data-options="required:true,missingMessage:'确认密码不能为空!'"/></td>
		                    </tr>
		                </table>
	                </form>
	            </div>
	            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
	                <!-- 确定按钮 -->
	                <a id="btnConfirm" onclick="editNewPassword()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
	                <!-- 取消按钮 -->
	                <a id="btnCancel" onclick="CanceleditNewPassword()" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
	            </div>
	        </div>
	    </div>
	    
	    
	</body>
	
</html>
