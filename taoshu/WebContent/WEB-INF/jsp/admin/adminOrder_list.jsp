<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>订单列表</title>
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
				   
			   	    url:'${pageContext.request.contextPath }/selectOrderListInfo',  //URL属性是一个URL从远程站点请求数据。
			   	   /*  pageList:[1,2,3,4],
			   	    pageSize:1, */
				    fit:true,  //当设置为true的时候面板大小将自适应父容器
				    border:false,  //定义是否显示数据表格边框
			   	    title:'订单列表',   //数据表格标题，继承panel面板title
			   	    iconCls: 'icon-tip',   //显示图标
			   	    //singleSelect:true,   //如果为true，则只允许选择一行。
			   	    //method:'get',   //该方法类型请求远程数据	默认是post	   	    
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
					                {field:'oid',title:'订单id',width:260,align:'center'},]
					                ],
					//为数据表格添加列标题，field代表列名称和json数据名称对应/数据库字段名称对应
					columns:[[   
					    //显示数据库字段对应9列数据，其中主键列在上面冻结在左边显示  
				        {field:'ordertime',title:'订单时间',width:130,align:'center'},    
				        {field:'total',title:'总金额',width:80,align:'center'},
				        {field:'payment_type',title:'支付类型',width:80,align:'center',
				        	//值为1代表货到付款，2代表在线支付
				        	formatter: function(value,row,index){
								if (row.payment_type==1){
									return "货到付款";
								} else {
									return "在线支付";
								}
							}
	
				        },    
				        {field:'status',title:'订单状态',width:80,align:'center',
				           //0代表未付款，1代表已付款,2代表已发货 ,3	代表已完成
				        	formatter: function(value,row,index){
								if (row.status==0){
									return "未付款";
								} else if(row.status==1) {
									return "已付款";
								}
							}
				        },    
				        {field:'receiver_name',title:'收货人姓名',width:80,align:'center'}, 
				        {field:'receiver_phone',title:'收货人电话',width:90,align:'center'},    
				        {field:'receiver_address',title:'收货人地址',width:200,align:'center'},    
				        {field:'uid',title:'订单所属用户id',width:100,align:'center',
				        	 formatter: function(value,row,index){
				        	    return row.user.uid;
				        	 } 
				        	
				        },   
				        {field:'orderinfo',title:'订单详情',width:80,align:'center',
				        	
				        	formatter: function(value,row,index){
				        		
								return "<button onclick='orderItemInfo(this)' value='"+row.oid+"'>订单详情</button>";
							
				        	}
				        
				        },   
				    ]] 
			   });
	    	   
			 //显示订单详情的数据表格
			   $("#dg2").datagrid({
				   
			   	    url:'${pageContext.request.contextPath }/selectOrderItemListInfo',  //URL属性是一个URL从远程站点请求数据。
			   	    pageList:[1,2,3,4,5],
			   	    pageSize:3,
				    fit:true,  //当设置为true的时候面板大小将自适应父容器
				    border:false,  //定义是否显示数据表格边框
			   	    //singleSelect:true,   //如果为true，则只允许选择一行。
			   	    //method:'get',   //该方法类型请求远程数据	默认是post	   	    
			   	    rownumbers:true, //如果为true，则显示一个行号列。
			   	    //--------------------------------------------------------
			   	    pagination:true, //如果为true，则在DataGrid控件底部显示分页工具栏
			   	    //--------------------------------------------------------  	   
			   	    nowrap:true,   //如果为true，则在同一行中显示数据。	
			   	    
			   	    toolbar:'#tb2',
					//为数据表格添加列标题，field代表列名称和json数据名称对应/数据库字段名称对应
					columns:[[   
					    //显示数据库字段对应9列数据，其中主键列在上面冻结在左边显示  
				        {field:'pimage',title:'商品图片',width:80,align:'center',
                              formatter: function(value,row,index){
                            	//将该字段原来显示数据库商品图片路径img/product/sj/sj001.jpg，使用formatter格式化刷子添加HTML图片标签img显示图片
  				        		return "<img src='${pageContext.request.contextPath }/"+row.product.pimage+"' width='35px' height='38px'/>";
							
				        	}
				        },    
				        {field:'pname',title:'商品名称',width:250,align:'center',
				        	 formatter: function(value,row,index){
					        		
								return row.product.pname;
							
				        	}	
				        },   
				        /* {field:'pcolor',title:'商品颜色',width:80,align:'center',
				        	formatter: function(value,row,index){
				        		
								return row.product.pcolor;
							
				        	}		
				        }, */    
				        {field:'receiver_name',title:'商品价格',width:80,align:'center',
				        	formatter: function(value,row,index){
				        		
								return row.product.shop_price;
							
				        	}		
				        }, 
				        {field:'count',title:'数量',width:80,align:'center'},    
				        {field:'subtotal',title:'小计',width:80,align:'center'},    
				        
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
					// $('#oid').val()使用jQuery获取输入框的值作为查询条件
					oid: $('#oid').val()
				});
			}
		    
		  
		    //订单详情
		    function orderItemInfo(obj)
		    {
		 
		    	//用户点击订单详情按钮
				$("#orderInfoWindow").window('open'); 
		    	//显示订单号
		    	$('#orderid').text($(obj).val());
		    	//再次加载数据表格，并带上订单号作为查询条件
		    	$('#dg2').datagrid('load',{
		    		// $(obj).val()获取订单号作为数据表格的查询参数
					oid: $(obj).val()
				});
		    	//清除所有勾选的行
		    	$('#dg').datagrid('clearChecked');
		    	
				
		    }
       </script>
	</head>
	<body>
		    
		<!--easyui中的datagrid数据表格  field为列字段名称。-->
		<table id="dg" ></table>
		
		<!-- 定义一个工具类，通过设置数据网格个工具栏属性，将该工具栏添加到网格中 -->
		<div id="tb" style="padding:3px">
	        <!-- 查找，通过订单号进行查找 --> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="oid" class="easyui-textbox" style="line-height:26px;border:1px solid #ccc" data-options="prompt:'请输入订单号'">
			<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" onclick="doSearch()" data-options="iconCls:'icon-search'">查询</a>   
	    </div>
	    
	    
	     <!--订单详情 -->
	    <!--默认设置close为true定义是否可以关闭窗口。，就是默认window窗口是打开将属性close=true窗口就关闭，当点击修改密码时候，让该windows窗口打开open  modal="true"定义是否将窗体显示为模式化窗口。resizable 定义是否能够改变窗口大小。-->
	    <div id="orderInfoWindow" class="easyui-window" title="订单详情" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
	        maximizable="false" icon="icon-save"  style="width: 750px; height: 350px; padding: 5px;
	        background: #fafafa">
	        
	        <div class="easyui-layout" fit="true">
	            <div >订单号</div>
	            <!-- 使用layout布局中的中部和北部 -->
	            <div region="north" border="false" style="height: 30px; line-height: 30px;">
	                <!-- 显示订单号 -->
                                                 订单号:<span id="orderid"></span>
                </div>
	            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
	                  
	                     <!--easyui中的datagrid数据表格  field为列字段名称。-->
		                 <table id="dg2" ></table>
	            </div>
	            
	        </div>
	    </div>
	</body>
</html>
