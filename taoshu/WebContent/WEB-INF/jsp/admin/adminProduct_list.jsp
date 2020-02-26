<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>商品列表</title>
		<link rel="stylesheet" type="text/css" href="easyUI/themes/default/easyui.css">   
		<link rel="stylesheet" type="text/css" href="easyUI/themes/icon.css">   
		
		<script type="text/javascript" src="easyUI/jquery.min.js"></script>   
		<script type="text/javascript" src="easyUI/jquery.easyui.min.js"></script>  
        <script type="text/javascript" src="easyUI/locale/easyui-lang-zh_CN.js"></script>  
	    <!--使用Javascript去创建DataGrid控件-->
		<!--可以通过data-options这个属性在标签里面设置easyUI组件的属性，也可以通过js设置-->
		<script type="text/javascript">
		    $(function(){
		      
	    	   //通过js为数据表格添加工具栏，参数easyUI自己自动添加带上去，formatter是一个属性
			   $("#dg").datagrid({
				   
			   	    url:'${pageContext.request.contextPath }/selectProductListInfo',  //URL属性是一个URL从远程站点请求数据。
			   	   /*  pageList:[1,2,3,4],
			   	    pageSize:1, */
				    fit:true,  //当设置为true的时候面板大小将自适应父容器
				    border:false,  //定义是否显示数据表格边框
			   	    title:'商品列表',   //数据表格标题，继承panel面板title
			   	    iconCls: 'icon-tip',   //显示图标
			   	    //singleSelect:true,   //如果为true，则只允许选择一行。
			   	    //method:'get',   //该方法类型请求远程数据	,默认是post	   	    
			   	    rownumbers:true, //如果为true，则显示一个行号列。
			   	    //--------------------------------------------------------
			   	    pagination:true, //如果为true，则在DataGrid控件底部显示分页工具栏
			   	    //--------------------------------------------------------
			   	    collapsible:true,  //定义是否显示可折叠按钮。这个属性继承panel面板属性   	   
			   	    nowrap:true,   //如果为true，则在同一行中显示数据。	    
			   	    striped:true,  //是否显示斑马线效果。就是隔行换色
			   	    // idField:'user_id',  //指明哪一个字段是标识字段。就是数据库中的主键，主要用作数据表数据唯一标识
			   	    //通过js为数据表格添加工具栏,设置数据表格的工具栏数据，赋值一个json格式的数组
			   	    //顶部工具栏的DataGrid面板,'-'显示一条竖线
			   	    toolbar:'#tb',
			   	    	
					//设置冻结列，将复选框和主键冻结在左边，使用easyUI数据表格里面属性frozenColumns同列属性，但是这些列将会被冻结在左侧。
			       frozenColumns:[
					                [{field:'ck',checkbox:true}, 
					                {field:'pid',title:'商品id',width:45,align:'center'},]
					                ],
					//为数据表格添加列标题，field代表列名称和json数据名称对应/数据库字段名称对应
					columns:[[   
					    //显示数据库字段对应9列数据，其中主键列在上面冻结在左边显示  
				        {field:'pname',title:'商品名称',width:130,align:'center'},    
				        {field:'market_price',title:'市场价格',width:70,align:'center'},
				        {field:'shop_price',title:'销售价格',width:70,align:'center'},    
				        {field:'pimage',title:'商品图片',width:80,align:'center',
				        	formatter: function(value,row,index){  //使用formatter格式化刷子  value：字段值。row：行记录数据。	index: 行索引
				        		//将该字段原来显示数据库商品图片路径img/product/sj/sj001.jpg，使用formatter格式化刷子添加HTML图片标签img显示图片
				        		return "<img src='${pageContext.request.contextPath }/"+value+"' width='35px' height='38px'/>";
							}	
				        
				        },  
				        {field:'pdate',title:'商品日期',width:80,align:'center'}, 
				        {field:'is_hot',title:'是否热门',width:60,align:'center',
				        	formatter: function(value,row,index){  //使用formatter格式化刷子 value：字段值。row：行记录数据。	index: 行索引
				        		//将该字段原来显示数据库1,0，使用formatter格式化刷子显示是、否
				        		if(value==0)
			        			{  //如果字段值是0，就将字段值设置为否
			        			   return value="否";
			        			}else
		        				{   //如果字段值是1，就将字段值设置为是
			        			   return value="是";
		        				}
				        	}	
				        
				        
				        },    
				        {field:'pdesc',title:'商品描述',width:300,align:'center'},    
				      /*   {field:'pcolor',title:'商品颜色',width:80,align:'center'},  */  
				        {field:'pflag',title:'上架/下架',width:70,align:'center',
				        	formatter: function(value,row,index){  //使用formatter格式化刷子 value：字段值。row：行记录数据。	index: 行索引
				        		//将该字段原来显示数据库pflag商品标记1（代表上架）,0（代表下架），使用formatter格式化刷子显示上架、下架
				        		if(value==0)
			        			{  //如果字段值是0，就将字段值设置为下架
			        			   return value="下架";
			        			}else
		        				{   //如果字段值是1，就将字段值设置为上架
			        			   return value="上架";
		        				}
				        	}	
				        
				        },   
				        {field:'cid',title:'分类id',width:80,align:'center',
				        	formatter: function(value,row,index){  //使用formatter格式化刷子 value：字段值。row：行记录数据。	index: 行索引
				        		//将pageBean对象转换成json格式给数据表格获取数据显示格式{'total':'','rows':[{},{},{}....]}
				                //而显示数据表格中的每一行数据就是rows集合里面每个{}product对象的属性值，{}里面是{'pname':'',...'category':{}}
				                //但是每个商品product对象最后一个cid使用category对象进行封装，我们需要获取category对象里面封装好的cid值
				                //这里row代表rows里面{}product对象是json对象，然后调用category属性获取cid值
				                
				                //设置当前行cid字段的值
				                 row.cid=row.category.cid;
				                //必须要有返回值，该返回值就是该字段单元格显示的数据
				        		return row.category.cid;//获取cid值返回到该字段field显示
				        	}	

				        },   
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
					// $('#pname').val()使用jQuery获取输入框的值作为查询条件
					pname: $('#pname').val()
				});
			}
		    
		    //新增商品,当管理员点击新增商品按钮打开新增窗口
		    function addProductInfo()
		    {
		    	//打开添加商品的窗口
		    	$('#addProductWindow').window('open');  
		    	//让商品所属分类回显的数据中第一项默认被选中,调用select方法选择指定项。value=1项被选中
		    	$("#addcid").combobox('select','1');
		    	
		    }
		    
		    //新增商品，点击取消按钮，清空表单然后关闭window窗口
		    function CanceleAddProduct()
		    {
		    	//1.关闭添加商品窗口先清空表单
		    	$("#addProductInfoForm").form('clear');
		    	//2.关闭添加商品窗口
		    	$("#addProductWindow").window('close');  
		    }
			
			//新增商品，点击确定按钮提交表单，触发单击事件，使用ajax提交表单,效验商品表单数据的有效性，再选择是否提交或提示用户
			function submitAddProduct()
			{
		        //使用easyUI提交的ajax表单方式，去提交表单
				$('#addProductInfoForm').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/addProductInfo",
					//在提交之前触发，返回false可以终止提交。提交表单前效验商品表单信息是否填写完整
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
						//1.判断返回data是否存在添加商品成功或失败注册信息，有就提示
						  //添加商品成功data.success=true
						if(data.success)
						{
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
						    fn: 在窗口关闭的时候触发该回调函数。 
						  */
						 //提示添加商品成功！，然后关闭添加商品的window窗口，刷新数据表格datagrid
						  $.messager.alert('提示信息',data.addProductInfo,'info',function(){
							  //在关闭window窗口之前清空添加表单
							  $("#addProductInfoForm").form('clear');
							  //关闭添加商品的window窗口
							  $("#addProductWindow").window('close');  
							  //重新加载数据表格数据
							  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
						  });
						 
						}
						  //添加商品失败data.success=false
						if(data.success==false)
						{
						   //提示添加商品失败
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
							$.messager.alert('提示信息',data.addProductInfo,'error');
						}
                        						
					}
				});
			}
			
			//编辑商品，当用户点击编辑按钮，判断用户是否选择，进行相应的提示
			/* 也许有人为了方便使用getRowIndex会在datagrid中设置idField属性，如果不注意这个属性，那么在调用getSelected或者getChecked方法时会引起更多莫名其妙的问题。 */
			function editProductInfo()
			{
				//首先判断用户是否选择数据表格里面的记录，如果没有提示选择，如果有判断是否选择一条，如果不是就提示用户只能选择一条
				//使用easyUI中getSelections这个方法进行判断返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
				var selectData=$("#dg").datagrid("getSelections");//返回是被选中的数据数组,是json对象
			    //判断该数组是否为空，如果为空提示用户选择要编辑的用户
				if(selectData.length==0)
				{
				   //表明数组为空，用户没有选择，提示用户选择
				   /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				   $.messager.alert('提示信息','请选择要编辑的商品！','warning');
				}else if(selectData.length>1)
				{
					//说明用户选择多条，提示用户只能选择一条
					/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				    $.messager.alert('提示信息','只能选择一条要编辑的商品！','warning');
				}else
				{
					//说明用户选择一条记录，获取该条记录信息提交给后台，查询弹出页面进行回显
					//easyUI返回选中数组selectData是一个json对象，由于编辑只能选中一条记录，所以数组只有一个元素，元素也是json对象
					//当用户点击编辑按钮，显示一个window窗口，而且表单中的数据通过加载后台查询数据显示，并且带上选中的用户的uid作为查询条件传输给后台
					/* alert(selectData[0].uid); */
					//用户点击编辑按钮打开编辑窗口
					$("#editProductWindow").window('open');  
					//获取选中商品的pid
					var pid=selectData[0].pid;
					//编辑窗口打开的同时表单也要向后台获取数据填充，通过用户pid进行查询选中的商品信息进行回显
					$('#editProductInfoForm').form('load','${pageContext.request.contextPath }/selectProductInfo?pid='+pid);	// 读取表单的URL，向后台获取json数据填充表单，进行回显
				    //在加载完表单数据之后，还要设置下拉框所属分类被选中的下拉项
				    //先获取被选中的商品分类cid,然后设置它被选中
				    var cid=selectData[0].cid;
				    $("#editcid").combobox('select',cid);
				    //显示该商品图片
				    var pimage=selectData[0].pimage;
				    content="<img src='${pageContext.request.contextPath }/"+pimage+"' width=25px height=25px />"  
				    $("#img").html(content);
				}
				
			}
			//编辑商品，当用户点击确定按钮提交表单，通过ajax方式提交
			function submitEditProduct()
			{
		        //使用easyUI提交的ajax表单方式，去提交表单
				$('#editProductInfoForm').form('submit', {
					//提交表单的URL地址
					url: "${pageContext.request.contextPath }/editProductInfo",
					//在提交之前触发，返回false可以终止提交。提交表单前效验商品表单信息是否填写完整
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
						//1.判断返回data是否存在编辑商品成功或失败信息，有就提示
						  //编辑商品成功data.success=true
						if(data.success)
						{
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
						    fn: 在窗口关闭的时候触发该回调函数。 
						  */
						 //提示编辑商品成功！，然后关闭添加商品的window窗口，刷新数据表格datagrid
						  $.messager.alert('提示信息',data.editProductInfo,'info',function(){
							  //在关闭window窗口之前清空添加表单
							  $("#editProductInfoForm").form('clear');
							  //关闭添加商品的window窗口
							  $("#editProductWindow").window('close');  
							  //重新加载数据表格数据
							  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
						  });
						 
						}
						  //编辑商品失败data.success=false
						if(data.success==false)
						{
						   //提示编辑商品失败
							/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
							$.messager.alert('提示信息',data.editProductInfo,'error');
						}
                        						
					}
				});
			}
			
			 //编辑商品，点击取消按钮，清空表单然后关闭window窗口
		    function CanceleEditProduct()
		    {
		    	//1.关闭编辑商品窗口先清空表单
		    	$("#editProductInfoForm").form('clear');
		    	//2.关闭编辑商品窗口
		    	$("#editProductWindow").window('close');  
		    }
			//删除商品
			function deleteProductInfo()
			{
				//首先判断用户是否选择数据表格里面的记录，如果没有提示选择，
				//使用easyUI中getSelections这个方法进行判断返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
				var selectData=$("#dg").datagrid('getSelections');//返回是被选中的数据数组，我们只需要根据返回数组判断用户是否选择
				//判断该数组是否为空，如果为空提示用户选择要编辑的商品
				if(selectData.length==0)
				{
				   //表明数组为空，用户没有选择，提示用户选择
				   /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				   $.messager.alert('提示信息','请选择要删除的商品！','warning');
				}else
				{
					//说明用户选择一条或多条要删除的记录，弹个确认框提示用户是否删除，如果是，将该删除的商品信息提交个后台，进行删除
					/* title：在头部面板显示的标题文本。msg：显示的消息文本。fn(b): 当用户点击“确定”按钮的时侯将传递一个true值给回调函数，否则传递一个false值。 */
					$.messager.confirm('删除确认', '你确认要删除选中的商品？', function(r){
						if (r){
						   
						    //定义一个字符串进行拼接商品的pid
						    var strPid="";
						    //遍历选中数据中的商品的pid，使用字符串进行拼接，然后传输到后台
						    for(var i=0;i<selectData.length;i++)
					    	{
						    	strPid+=selectData[i].pid+",";
					    	}
						    //将选中记录拼接好的商品pid传输到后台,通过js访问URL
						   // location.href="${pageContext.request.contextPath }/deleteUserInfo?strUid="+strUid;
						    
						   //通过ajax提交删除的商品pid字符串
						   $.ajax({
				    			"async":true,
				    			"type": "POST",
				    			"url": "${pageContext.request.contextPath }/deleteProductInfo",
				    			"data" : {"strPid":strPid},
				    			"success" : function(data) {
				    				//data代表返回的json对象
				    				if(data.success)
									{
										/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
									    fn: 在窗口关闭的时候触发该回调函数。 
									  */
									 //提示删除商品成功！
									  $.messager.alert('提示信息',data.deleteProductInfo,'info',function(){
										  //重新加载数据表格数据
										  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
									  });
									 
									}
									  //删除商品失败data.success=false
									if(data.success==false)
									{
									   //提示删除商品失败
										/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
										$.messager.alert('提示信息',data.deleteProductInfo,'error');
									}
				    			},
				    			"dataType":"json"
			    		   });
						   
						}
					});


				}
				
			}
			
			//商品上架
			function publishProduct()
			{
				//其实很删除过程很相似，先判断用户是否选择，没有就提示选择，可以单选或多选，将拼接好的商品pid传输到后台，进行更新商品pflag=1
				//首先判断用户是否选择数据表格里面的记录，如果没有提示选择，
				//使用easyUI中getSelections这个方法进行判断返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
				var selectData=$("#dg").datagrid('getSelections');//返回是被选中的数据数组，我们只需要根据返回数组判断用户是否选择
				//判断该数组是否为空，如果为空提示用户选择要编辑的商品
				if(selectData.length==0)
				{
				   //表明数组为空，用户没有选择，提示用户选择
				   /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				   $.messager.alert('提示信息','请选择要上架的商品！','warning');
				}else
				{
					//说明用户选择一条或多条要上架的记录，	   
				    //定义一个字符串进行拼接商品的pid
				    var strPid="";
				    //遍历选中数据中的商品的pid，使用字符串进行拼接，然后传输到后台
				    for(var i=0;i<selectData.length;i++)
			    	{
				    	strPid+=selectData[i].pid+",";
			    	}
				  
				   //通过ajax提交上架的商品pid字符串
				   $.ajax({
		    			"async":true,
		    			"type": "POST",
		    			"url": "${pageContext.request.contextPath }/publishProductInfo",
		    			"data" : {"strPid":strPid},
		    			"success" : function(data) {
		    				//data代表返回的json对象
		    				if(data.success)
							{
								/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
							    fn: 在窗口关闭的时候触发该回调函数。 
							  */
							 //提示上架商品成功！
							  $.messager.alert('提示信息',data.publishProductInfo,'info',function(){
								  //重新加载数据表格数据
								  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
							  });
							 
							}
							  //上架商品失败data.success=false
							if(data.success==false)
							{
							   //提示上架商品失败
								/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
								$.messager.alert('提示信息',data.publishProductInfo,'error');
							}
		    			},
		    			"dataType":"json"
	    		   });
						   
				}
					
			}
			
			
			//商品下架
			function unpublishProduct()
			{
				//其实和商品上架做法基本一样，先判断用户是否选择，没有就提示选择，可以单选或多选，将拼接好的商品pid传输到后台，进行更新商品pflag=0
				//首先判断用户是否选择数据表格里面的记录，如果没有提示选择，
				//使用easyUI中getSelections这个方法进行判断返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
				var selectData=$("#dg").datagrid('getSelections');//返回是被选中的数据数组，我们只需要根据返回数组判断用户是否选择
				//判断该数组是否为空，如果为空提示用户选择要编辑的商品
				if(selectData.length==0)
				{
				   //表明数组为空，用户没有选择，提示用户选择
				   /* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
				   $.messager.alert('提示信息','请选择要下架的商品！','warning');
				}else
				{
					//说明用户选择一条或多条要下架的记录，	   
				    //定义一个字符串进行拼接商品的pid
				    var strPid="";
				    //遍历选中数据中的商品的pid，使用字符串进行拼接，然后传输到后台
				    for(var i=0;i<selectData.length;i++)
			    	{
				    	strPid+=selectData[i].pid+",";
			    	}
				  
				   //通过ajax提交下架的商品pid字符串
				   $.ajax({
		    			"async":true,
		    			"type": "POST",
		    			"url": "${pageContext.request.contextPath }/unpublishProductInfo",
		    			"data" : {"strPid":strPid},
		    			"success" : function(data) {
		    				//data代表返回的json对象
		    				if(data.success)
							{
								/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。
							    fn: 在窗口关闭的时候触发该回调函数。 
							  */
							 //提示下架商品成功！
							  $.messager.alert('提示信息',data.unpublishProductInfo,'info',function(){
								  //重新加载数据表格数据
								  $('#dg').datagrid('reload');    // 重新载入当前页面数据  
							  });
							 
							}
							  //下架商品失败data.success=false
							if(data.success==false)
							{
							   //提示下架商品失败
								/* title：在头部面板显示的标题文本。msg：显示的消息文本。icon：显示的图标图像。可用值有：error,question,info,warning。 */
								$.messager.alert('提示信息',data.unpublishProductInfo,'error');
							}
		    			},
		    			"dataType":"json"
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
            <!-- 新增商品 -->
            <a href="javascript:void(0);" onclick="addProductInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增商品</a>
            <!-- 编辑商品 -->
            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="editProductInfo()" data-options="iconCls:'icon-edit',plain:true">编辑商品</a>
            <!-- 删除商品 -->
            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="deleteProductInfo()" data-options="iconCls:'icon-cancel',plain:true">删除商品</a>
            <!-- 商品上架  publish代表上架发行/发布-->
            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="publishProduct()" data-options="iconCls:'icon-remove',plain:true">上架</a>
            <!-- 商品下架 unpublish代表下架-->
            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="unpublishProduct()" data-options="iconCls:'icon-remove',plain:true">下架</a>
     
	        <!-- 查找，通过商品名称进行查找 --> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="pname" class="easyui-textbox" style="line-height:26px;border:1px solid #ccc" data-options="prompt:'请输入商品名称'">
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" onclick="doSearch()" data-options="iconCls:'icon-search'">查询</a>   
	    </div>
	    
	    <!-- 添加商品窗口window，默认是关闭，单击新增让该窗口打开 -->
	    <!--默认设置close为true定义是否可以关闭窗口。，就是默认window窗口是打开将属性close=true窗口就关闭，当点击修改密码时候，让该windows窗口打开open  modal="true"定义是否将窗体显示为模式化窗口。resizable 定义是否能够改变窗口大小。-->
	    <div id="addProductWindow" class="easyui-window" title="添加商品" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
	        maximizable="false" icon="icon-save"  style="width: 350px; height: 425px; padding: 5px;
	        background: #fafafa">
	        <div class="easyui-layout" fit="true">
	            <!-- 使用layout布局中的中部和南部 -->
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                <form id="addProductInfoForm" method="post" enctype="multipart/form-data">
	                    <!-- cellpadding 属性规定单元边沿与其内容之间的空白。为3像素 ，使用easyUI的ValidateBox（验证框）-->
		                <table cellpadding=3 align="center">
		                    <tr>
		                        <td>商品名称：</td>
		                        <td><input id="pname" name="pname" class="easyui-textbox"  data-options="required:true,missingMessage:'商品名称不能为空!'" /></td>
		                    </tr>
		                    <tr>
		                        <td>市场价格：</td>
		                        <td><!-- 创建数值输入框。min允许的最小值。precision在十进制分隔符之后显示的最大精度。（即小数点后的显示精度）小数位2 -->
		                          <input id="market_price" name="market_price" type="text" class="easyui-numberbox" data-options="min:0,precision:2,required:true,missingMessage:'市场价格不能为空!'"></input>  
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>销售价格：</td>
		                         <td><!-- 创建数值输入框。min允许的最小值。precision在十进制分隔符之后显示的最大精度。（即小数点后的显示精度）小数位2 -->
		                          <input id="shop_price" name="shop_price" type="text" class="easyui-numberbox" data-options="min:0,precision:2,required:true,missingMessage:'销售价格不能为空!'"></input>  
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>商品图片：</td>
		                        <td>
		                           <input id="pimage" name="file" class="easyui-filebox" style="width:150px" data-options="buttonText:'浏览',required:true,missingMessage:'请选择商品的图片!'">
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>是否热门：</td>
		                        <td> <!--  panelHeight 下拉面板高度。默认200 editable定义用户是否可以直接输入文本到字段中。这里设置false不让用户输入只能选择是或否 -->
		                            <select id="is_hot" name="is_hot" class="easyui-combobox" name="is_hot" style="width:50px;" data-options="panelHeight:50,required:true,missingMessage:'请选择其中一个!',editable:false">   
									    <option value="1">是</option>   
									    <option value="0">否</option>   	      
									</select>  
		                        </td>
		                    </tr>
		                   <!--  <tr>
		                        <td>商品颜色：</td>
		                        <td><input id="pcolor" name="pcolor" class="easyui-textbox"  data-options="required:true,missingMessage:'商品颜色不能为空!'"/></td>
		                    </tr> -->
		                    <tr>
		                        <td>所属分类：</td>
		                        <td>
		                          <!-- 使用easyUI提供的下拉列表，可以使用URL请求后台json数据进行 回显
		                           valueField基础数据值名称绑定到该下拉列表框。  相当于下拉框的value值
		                           textField 基础数据字段名称绑定到该下拉列表框。相当于下拉框显示的text内容
		                                                                      通过<input>标签创建下拉列表框。
		                           -->
		                        <input id="addcid"  name="cid" class="easyui-combobox" data-options="required:true,valueField:'cid',textField:'cname',url:'${pageContext.request.contextPath}/selectAllCategoryInfo',editable:false" />  
		                        
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>商品描述：</td>
		                        <td>
		                           
		                           <textarea name="pdesc" rows="3" cols="20" class="easyui-validatebox" data-options="required:true,missingMessage:'商品描述不能为空!'"></textarea>
		                        </td>
		                    </tr>
		                   
		                </table>
	                </form>
	            </div>
	             <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <!-- 确定按钮 -->
                    <a id="btnConfirm" onclick="submitAddProduct()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                    <!-- 取消按钮 -->
                    <a id="btnCancel" onclick="CanceleAddProduct()" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
                </div>
	        </div>
	    </div>
	    
	    
	     <!--编辑商品窗口window，默认是关闭，单击编辑让该窗口打开 -->
	    <!--默认设置close为true定义是否可以关闭窗口。，就是默认window窗口是打开将属性close=true窗口就关闭，当点击修改密码时候，让该windows窗口打开open  modal="true"定义是否将窗体显示为模式化窗口。resizable 定义是否能够改变窗口大小。-->
	    <div id="editProductWindow" class="easyui-window" title="编辑商品" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
	        maximizable="false" icon="icon-save"  style="width: 350px; height: 440px; padding: 5px;
	        background: #fafafa">
	        <div class="easyui-layout" fit="true">
	            <!-- 使用layout布局中的中部和南部 -->
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                <!-- 表单进行回显时候name名称和json里面name一样才能正常回显，并且一一对应 -->
	                <form id="editProductInfoForm" method="post" enctype="multipart/form-data">
	                    <!-- 这里要弄个隐藏域，存储回显用户uid，在提交时候后台需要根据uid进行更新该用户的信息 -->
		                <input type="hidden" name="pid" >
		                
	                    <!-- cellpadding 属性规定单元边沿与其内容之间的空白。为3像素 ，使用easyUI的ValidateBox（验证框）-->
		                 <table cellpadding=3 align="center">
		                    <tr>
		                        <td>商品名称：</td>
		                        <td><input id="pname" name="pname" class="easyui-textbox"  data-options="required:true,missingMessage:'商品名称不能为空!'" /></td>
		                    </tr>
		                    <tr>
		                        <td>市场价格：</td>
		                        <td><!-- 创建数值输入框。min允许的最小值。precision在十进制分隔符之后显示的最大精度。（即小数点后的显示精度）小数位2 -->
		                          <input id="market_price" name="market_price" type="text" class="easyui-numberbox" data-options="min:0,precision:2,required:true,missingMessage:'市场价格不能为空!'"></input>  
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>销售价格：</td>
		                         <td><!-- 创建数值输入框。min允许的最小值。precision在十进制分隔符之后显示的最大精度。（即小数点后的显示精度）小数位2 -->
		                          <input id="shop_price" name="shop_price" type="text" class="easyui-numberbox" data-options="min:0,precision:2,required:true,missingMessage:'销售价格不能为空!'"></input>  
		                        </td>
		                    </tr>
		                    <!-- 在点击编辑商品时候，编辑页面显示一下该商品图片 -->
		                    <tr>
		                       <td>
		                         <div id="img"></div>
		                       </td>
		                    </tr>
		                    <tr>
		                        <td>商品图片：</td>
		                        <td>  
		                             
		                             <input id="pimage" name="file" class="easyui-filebox" style="width:150px" data-options="buttonText:'浏览',">
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>是否热门：</td>
		                        <td> <!--  panelHeight 下拉面板高度。默认200 editable定义用户是否可以直接输入文本到字段中。这里设置false不让用户输入只能选择是或否 -->
		                            <select id="is_hot" name="is_hot" class="easyui-combobox" name="is_hot" style="width:50px;" data-options="panelHeight:50,required:true,missingMessage:'请选择其中一个!',editable:false">   
									    <option value="1">是</option>   
									    <option value="0">否</option>   	      
									</select>  
		                        </td>
		                    </tr>
		                    <!-- <tr>
		                        <td>商品颜色：</td>
		                        <td><input id="pcolor" name="pcolor" class="easyui-textbox"  data-options="required:true,missingMessage:'商品颜色不能为空!'"/></td>
		                    </tr> -->
		                    <tr>
		                        <td>所属分类：</td>
		                        <td>
		                          <!-- 使用easyUI提供的下拉列表，可以使用URL请求后台json数据进行 回显
		                           valueField基础数据值名称绑定到该下拉列表框。  相当于下拉框的value值
		                           textField 基础数据字段名称绑定到该下拉列表框。相当于下拉框显示的text内容
		                           -->
		                           <input id="editcid"  name="cid" class="easyui-combobox" data-options="required:true,valueField:'cid',textField:'cname',url:'${pageContext.request.contextPath}/selectAllCategoryInfo',editable:false" />  
		                      
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>商品描述：</td>
		                        <td>
		                           <textarea id="pdesc" name="pdesc" rows="2" cols="20" class="easyui-validatebox" data-options="required:true,missingMessage:'商品描述不能为空!'"></textarea>
		                        </td>
		                    </tr>
		                   
		                </table>
	                </form>
	            </div>
	             <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                    <!-- 确定按钮 -->
                    <a id="btnConfirm" onclick="submitEditProduct()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                    <!-- 取消按钮 -->
                    <a id="btnCancel" onclick="CanceleEditProduct()" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
                </div>
	        </div>
	    </div>
	    
	</body>
</html>
