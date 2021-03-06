<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>分类列表</title>
		<link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">   
		<link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">   
		
		<script type="text/javascript" src="easyUI/jquery.min.js"></script>   
		<script type="text/javascript" src="easyUI/jquery.easyui.min.js"></script>  
        <script type="text/javascript" src="easyUI/locale/easyui-lang-zh_CN.js"></script>  
	    <!--使用Javascript去创建DataGrid控件-->
		<!--可以通过data-options这个属性在标签里面设置easyUI组件的属性，也可以通过js设置-->
		<script type="text/javascript">
		    $(function(){
		      
	    	   //通过js为数据表格添加工具栏，参数easyUI自己自动添加带上去
			   $("#dg").datagrid({
				   
			   	    url:'${pageContext.request.contextPath }/selectCategoryListInfo',  //URL属性是一个URL从远程站点请求数据。
			   	   /*  pageList:[1,2,3,4],
			   	    pageSize:1, */
				    fit:true,  //当设置为true的时候面板大小将自适应父容器
				    border:false,  //定义是否显示数据表格边框
			   	    title:'分类列表',   //数据表格标题，继承panel面板title
			   	    iconCls: 'icon-tip',   //显示图标
			   	    //singleSelect:true,   //如果为true，则只允许选择一行。
			   	    // method:'get',   //该方法类型请求远程数据		默认是post   	    
			   	    rownumbers:true, //如果为true，则显示一个行号列。
			   	    //--------------------------------------------------------
			   	    pagination:true, //如果为true，则在DataGrid控件底部显示分页工具栏
			   	    //--------------------------------------------------------
			   	    collapsible:true,  //定义是否显示可折叠按钮。这个属性继承panel面板属性   	   
			   	    nowrap:true,   //如果为true，则在同一行中显示数据。	    
			   	    striped:true,  //是否显示斑马线效果。就是隔行换色
			   	    //通过js为数据表格添加工具栏,设置数据表格的工具栏数据，赋值一个json格式的数组
			   	    //顶部工具栏的DataGrid面板,'-'显示一条竖线
			   	    toolbar:'#tb',
			   	    	
					//设置冻结列，将复选框和主键冻结在左边，使用easyUI数据表格里面属性frozenColumns同列属性，但是这些列将会被冻结在左侧。
			       frozenColumns:[
					                [{field:'ck',checkbox:true}, 
					                {field:'cid',title:'分类id',width:100,align:'center'},]
					                ],
					//为数据表格添加列标题，field代表列名称和json数据名称对应/数据库字段名称对应
					columns:[[   
					    //显示数据库字段对应9列数据，其中主键列在上面冻结在左边显示  
					    {field:'cname',title:'分类名称',width:200,align:'center'},   
				    ]] 
			   });
		    });
			
		    
		    //当用户点击查找按钮进行查询操作,当用户点击每页显示条数、上一页、下一页等等easyUI自动带page，rows，还有搜索框输入值（如果指定了'param'，它将取代'queryParams'属性）
		    //我们只需要写好数据网格的URL，参数由easyUI框架自己带，不需要我们管
		    function doSearch(){
		    	//点击查找，调用数据网格带上参数从新加载数据返回后台，参数是json格式对象类型
		    	//访问的URL还是原来数据网格URL地址
		    	//如果指定了'param'，它将取代'queryParams'属性。通常可以通过传递一些参数执行一次查询，通过调用这个方法从服务器加载新数据
				$('#dg').datagrid('load',{
					// $('#cname').val()使用jQuery获取输入框的值作为查询条件
					cname: $('#cname').val()
				});
			}
		    
		    //新增分类，当用户点击新增按钮打开添加分类窗口
		    function addCategoryInfo()
		    {
		    	//打开添加分类窗口
		    	$('#addCategoryWindow').window('open');  
		    }
		    
		    //在打开添加分类窗口，点击取消，清空表单然后关闭window窗口，取消添加分类窗口
		    function CanceleAddCategory()
		    {
		    	//1.关闭添加分类窗口先清空表单
		    	$("#addCategoryInfoForm").form('clear');
		    	//2.关闭添加分类窗口
		    	$("#addCategoryWindow").window('close');  
		    }
			
			//在添加分类时候，检查分类名称是否存在，自定义一个规则检查输入的商品分类名称是否存在，(还有在对应标签上应用该规则validType="checkCategoryName")
			$.extend($.fn.validatebox.defaults.rules, {
			    checkCategoryName: {    //验证商品分类名称是否存在
			        validator: function(value, param){   
			        	//定义一个标记判断是否让规则通过
			    		var flag=false;//默认不让通过规则，就是提示红色的字
			    		//value代表输入框的值param代表规则的值写true
			    		//自定义效验规则，如果用户存在不通过规则返回false，否则返回true
			    		$.ajax({
			    			"async":false,//表示同步传输就是单线程意思
			    			"type": "POST",
			    			"url": "${pageContext.request.contextPath }/checkCategoryIsExist",//这里访问的URL地址和前台用户注册检查用户是否存在一样路径
			    			"data" : {"cname":value},//传输的参数名称也是和之前一样，前台注册检查用户书写一样
			    			"success" : function(data) { //成功回调函数
			    				flag=data;
			    			},
			    			"dataType":"json"
			    		});
			    		
			    		return !flag; 
			        },    
			        message: '分类名称已经存在!'   
			    }    
			});  
			
			//添加分类窗口点击确定按钮提交表单，当管理员提交确定按钮，添加分类，触发单击事件，使用ajax提交表单,效验用户表单数据的有效性，再选择是否提交或提示用户
			function submitAddCategory()
			{
		        //使用easyUI提交的ajax表单方式，去提交表单
				$('#addCategoryInfoForm').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/addCategoryInfo",
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
						//1.判断返回data是否存在添加分类成功或失败注册信息，有就提示
						  //添加分类成功data.success=true
						if(data.success)
						{
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
						    fn: 在窗口关闭的时候触发该回调函数。 
						  */
						 //提示添加分类成功！，然后关闭添加分类的window窗口，刷新数据表格datagrid
						  $.messager.alert('提示信息',data.addCategoryInfo,'info',function(){
							  //在关闭window窗口之前清空添加表单
							  $("#addCategoryInfoForm").form('clear');
							  //关闭添加分类的window窗口
							  $("#addCategoryWindow").window('close');  
							  //重新加载数据表格数据
							  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
						  });
						 
						}
						  //添加分类失败data.success=false
						if(data.success==false)
						{
						   //提示添加分类失败
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
							$.messager.alert('提示信息',data.addCategoryInfo,'error');
						}
                        						
					}
				});
			}
			
			//当用户点击编辑按钮，编辑类别
			/* 也许有人为了方便使用getRowIndex会在datagrid中设置idField属性，如果不注意这个属性，那么在调用getSelected或者getChecked方法时会引起更多莫名其妙的问题。 */
			function editCategoryInfo()
			{
				//首先判断用户是否选择数据表格里面的记录，如果没有提示选择，如果有判断是否选择一条，如果不是就提示用户只能选择一条
				//使用easyUI中getSelections这个方法进行判断返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
				var selectData=$("#dg").datagrid("getSelections");//返回是被选中的数据数组,是json对象
			    //判断该数组是否为空，如果为空提示用户选择要编辑的分类
				if(selectData.length==0)
				{
				   //表明数组为空，用户没有选择，提示用户选择
				   /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				   $.messager.alert('提示信息','请选择要编辑的分类！','warning');
				}else if(selectData.length>1)
				{
					//说明用户选择多条，提示用户只能选择一条
					/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				    $.messager.alert('提示信息','只能选择一条要编辑的分类！','warning');
				}else
				{
					//说明用户选择一条记录，获取该条记录信息提交给后台，查询弹出页面进行回显
					//easyUI返回选中数组selectData是一个json对象，由于编辑只能选中一条记录，所以数组只有一个元素，元素也是json对象
					//当用户点击编辑按钮，显示一个window窗口，而且表单中的数据通过加载后台查询数据显示，并且带上选中的用户的uid作为查询条件传输给后台
					/* alert(selectData[0].uid); */
					//用户点击编辑按钮打开编辑窗口
					$("#editCategoryWindow").window('open');  
					//获取选中类别的cid
					var cid=selectData[0].cid;
					//编辑窗口打开的同时表单也要向后台获取数据填充，通过用户uid进行查询选中的类别信息进行回显,这是使用form表单提供load方法进行回显
					$('#editCategoryInfoForm').form('load','${pageContext.request.contextPath }/selectCategoryInfo?cid='+cid);	// 读取表单的URL，向后台获取json数据填充表单，进行回显
				}
				
			}
			//编辑分类,点击确定提交表单，当用户点击确定按钮提交表单，通过ajax方式提交
			function submitEditCategory()
			{
		        //使用easyUI提交的ajax表单方式，去提交表单
				$('#editCategoryInfoForm').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/editCategoryInfo",
					//在提交之前触发，返回false可以终止提交。提交表单前效验分类表单信息是否填写完整
					onSubmit: function(){
						//validate方法做表单字段验证，当所有字段都有效的时候返回true,进行表单效验
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
						//1.判断返回data是否存在编辑类别成功或失败，有就提示
						  //编辑类别成功data.success=true
						if(data.success)
						{
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
						    fn: 在窗口关闭的时候触发该回调函数。 
						  */
						 //提示编辑类别成功！，然后关闭添加类别的window窗口，刷新数据表格datagrid
						  $.messager.alert('提示信息',data.editCategoryInfo,'info',function(){
							  //在关闭window窗口之前清空添加表单
							  $("#editCategoryInfoForm").form('clear');
							  //关闭添加分类的window窗口
							  $("#editCategoryWindow").window('close');  
							  //重新加载数据表格数据
							  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
						  });
						 
						}
						  //编辑类别失败data.success=false
						if(data.success==false)
						{
						   //提示编辑类别失败
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
							$.messager.alert('提示信息',data.editCategoryInfo,'error');
						}
                        						
					}
				});
			}
			
			//编辑分类，点击取消，清空表单然后关闭window窗口
		    function CanceleEditCategory()
		    {
		    	//1.关闭编辑类别窗口先清空表单
		    	$("#editCategoryInfoForm").form('clear');
		    	//2.关闭编辑类别窗口
		    	$("#editCategoryWindow").window('close');  
		    }
			//删除分类，选择要删除的分类信息点击删除按钮，获取选中分类的cid拼接，使用ajax方式提交到后台
			function deleteCategoryInfo()
			{
				//首先判断用户是否选择数据表格里面的记录，如果没有提示选择，
				//使用easyUI中getSelections这个方法进行判断返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
				var selectData=$("#dg").datagrid('getSelections');//返回是被选中的数据数组，我们只需要根据返回数组判断用户是否选择
				//判断该数组是否为空，如果为空提示用户选择要编辑的分类
				if(selectData.length==0)
				{
				   //表明数组为空，用户没有选择，提示用户选择
				   /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				   $.messager.alert('提示信息','请选择要删除的分类！','warning');
				}else
				{
					//说明用户选择一条或多条要删除的记录，弹个确认框提示用户是否删除，如果是，将该删除的用户信息提交个后台，进行删除
					/* title：在头部面板显示的标题文本。msg：显示的消息文本。fn(b): 当用户点击“确定”按钮的时侯将传递一个true值给回调函数，否则传递一个false值。 */
					$.messager.confirm('删除确认', '你确认要删除选中的分类？', function(r){
						if (r){
						   
						    //定义一个字符串进行拼接分类的cid
						    var strCid="";
						    //遍历选中数据中的分类cid，使用字符串进行拼接使用逗号进行隔开，然后传输到后台
						    for(var i=0;i<selectData.length;i++)
					    	{
						    	strCid+=selectData[i].cid+",";
					    	}
						    
						   //通过ajax提交删除的用户uid字符串
						   $.ajax({
				    			"async":true,
				    			"type": "POST",
				    			"url": "${pageContext.request.contextPath }/deleteCategoryInfo",
				    			"data" : {"strCid":strCid},
				    			"success" : function(data) {
				    				//data代表返回的json对象
				    				if(data.success)
									{
										/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
									    fn: 在窗口关闭的时候触发该回调函数。 
									  */
									 //提示删除分类成功！，刷新数据表格datagrid
									  $.messager.alert('提示信息',data.deleteCategoryInfo,'info',function(){
										  //重新加载数据表格数据
										  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
									  });
									 
									}
									  //删除分类失败data.success=false
									if(data.success==false)
									{
									   //提示删除分类失败
										/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
										$.messager.alert('提示信息',data.deleteCategoryInfo,'error');
									}
				    			},
				    			"dataType":"json"
			    		   });
						   
						}
					});


				}
				
			}
       </script>
	</head>
	<body>
		    
		<!--easyui中的datagrid数据表格  field为列字段名称。-->
		<table id="dg" ></table>
		
		<!-- 定义一个工具类，通过设置数据网格个工具栏属性，将该工具栏添加到网格中 -->
		<div id="tb" style="padding:3px">
            <!-- 新增分类 -->
            <a href="javascript:void(0);" onclick="addCategoryInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增分类</a>
            <!-- 编辑分类 -->
            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="editCategoryInfo()" data-options="iconCls:'icon-edit',plain:true">编辑分类</a>
            <!-- 删除分类-->
            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="deleteCategoryInfo()" data-options="iconCls:'icon-cancel',plain:true">删除分类</a>
     
	        <!-- 查找，通过用户名进行查找 --> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="cname" class="easyui-textbox" style="line-height:26px;border:1px solid #ccc" data-options="prompt:'请输入分类名称'">
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" onclick="doSearch()" data-options="iconCls:'icon-search'">查询</a>   
	    </div>
	    
	    <!-- 添加分类窗口window，默认是关闭，单击新增让该窗口打开 -->
	    <!--默认设置close为true定义是否可以关闭窗口。，就是默认window窗口是打开将属性close=true窗口就关闭，当点击修改密码时候，让该windows窗口打开open  modal="true"定义是否将窗体显示为模式化窗口。resizable 定义是否能够改变窗口大小。-->
	    <div id="addCategoryWindow" class="easyui-window" title="添加分类" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
	        maximizable="false" icon="icon-save"  style="width: 350px; height: 140px; padding: 5px;
	        background: #fafafa">
	        <div class="easyui-layout" fit="true">
	            <!-- 使用layout布局中的中部和南部 -->
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                <form id="addCategoryInfoForm" method="post">
	                    <!-- cellpadding 属性规定单元边沿与其内容之间的空白。为3像素 ，使用easyUI的ValidateBox（验证框）-->
		                <table cellpadding=3 align="center">
		                    <tr>
		                        <td>分类名称：</td>
		                        <td><input id="cname" name="cname" class="easyui-textbox"  data-options="required:true,missingMessage:'分类名称不能为空!'" validType="checkCategoryName"/></td>
		                    </tr>
		                </table>
	                </form>
	            </div>
	             <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <!-- 确定按钮 -->
                    <a id="btnConfirm" onclick="submitAddCategory()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                    <!-- 取消按钮 -->
                    <a id="btnCancel" onclick="CanceleAddCategory()" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
                </div>
	        </div>
	    </div>
	    
	    
	     <!-- 编辑分类窗口window，默认是关闭，单击编辑让该窗口打开 -->
	    <!--默认设置close为true定义是否可以关闭窗口。，就是默认window窗口是打开将属性close=true窗口就关闭，当点击修改密码时候，让该windows窗口打开open  modal="true"定义是否将窗体显示为模式化窗口。resizable 定义是否能够改变窗口大小。-->
	    <div id="editCategoryWindow" class="easyui-window" title="编辑分类" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
	        maximizable="false" icon="icon-save"  style="width: 350px; height: 140px; padding: 5px;
	        background: #fafafa">
	        <div class="easyui-layout" fit="true">
	            <!-- 使用layout布局中的中部和南部 -->
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                <!-- 表单进行回显时候name名称和json里面name一样才能正常回显，并且一一对应 -->
	                <form id="editCategoryInfoForm" method="post">
	                    <!-- 这里要弄个隐藏域，存储回显用户uid，在提交时候后台需要根据uid进行更新该用户的信息 -->
		                <input type="hidden" name="cid" >
		                
	                    <!-- cellpadding 属性规定单元边沿与其内容之间的空白。为3像素 ，使用easyUI的ValidateBox（验证框）-->
		                <table cellpadding=3 align="center">
		                    <tr>
		                        <td>分类名称：</td>
		                        <td><input id="cname" name="cname" class="easyui-textbox" data-options="required:true,missingMessage:'分类名称不能为空!'" /></td>
		                    </tr>
		                </table>
	                </form>
	            </div>
	             <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <!-- 确定按钮 -->
                    <a id="btnConfirm" onclick="submitEditCategory()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                    <!-- 取消按钮 -->
                    <a id="btnCancel" onclick="CanceleEditCategory()" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
                </div>
	        </div>
	    </div>
	    
	</body>
</html>
