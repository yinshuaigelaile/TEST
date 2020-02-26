<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>购物车</title>

		<!-- Bootstrap -->
		<link href="css/bootstrap.css" rel="stylesheet">
		<!--引用自己定义好的样式-->
		<link href="css/yitao.css" rel="stylesheet">
		<script src="js/jquery-3.2.1.js"></script>
		<script src="js/bootstrap.js"></script>
		
	</head>

	<body>
		<!-- 引入header.jsp -->
		<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
     
        
        
        <!--判断用户是否登录，就是看看session是否存储用户， 购物车如果用户没有登录友好提示用户，否则不显示 -->
        <c:if test="${empty user }">
            <!-- 如果是true表示空用户没有登录提示用户登录 -->
           <div class="container">
	           <div class="row">
	              <div style="border: 1px solid khaki; margin-bottom: 10px; margin: 0 auto; padding: 10px; margin-bottom: 10px;">
					
					<span style="color:#ff6600"><img alt="" src="img/search.notice.png">您还没有登录！登录后购物车的商品将保存到您账号中</span>&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath }/login"><input type="button" value="立即登录" style="background:#e4393c;color:white;"></a>
					
				  </div>
	           </div>
           </div>
        </c:if>
        
        <!-- 显示购物车 -->
        <div class="container">
           <div class="row">
                <div style="text-align:center; margin: 0 auto; padding: 10px;">
				<strong style="font-size:30px;">购物车</strong>
			    </div>
           </div>
        </div>
        
        <!-- 通过判断购物车是否有商品，进行友好显示 -->
        <c:if test="${!empty cart.cartMap}">
            <div class="container" >
			<div class="row">
                
                <!-- 对全选复选框添加单击事件 -->
                <script type="text/javascript">
                   $(function(){
                        //使用jQuery实现点击复选框全选，让其他复选框全部选上，否则全部不选
                	   $("#checkboxall").click(function(){
                		   //$("#checkboxall").is(':checked') 通过方法is判断复选框是否被选中，就是获取checked属性值返回true、false
                		   if($("#checkboxall").is(':checked'))
               			   {
                			 $(".checkbox").prop("checked","checked");
                			// $('#id').attr('checked','checked') jquery版本是1.6以下;
                			//$('#id').prop('checked','checked') 1.6以上现在使用jQuery3.2.1版本;
               			   }else
           				   {
               				 $(".checkbox").prop("checked",false);
           				   }
                		   
                	   });
                   });
                   
                   //当用户点击删除按钮，删除购物车中的购物项
                   function delCartItem(pid)
                   {
                	  
                	   if(confirm("你确定要删除该商品？"))
               		   {
                		   location.href="${pageContext.request.contextPath }/deleteCartItem?pid="+pid;
               		   }
                	   

                   }
                   
                   //通过复选框选中要删除的商品
                   function deleteCheckedProduct()
                   {
                	 
                	   //通过name获取所有的复选框对象，返回数组
                	   var array=document.getElementsByName('checkboxname');
     
                	   //定义一个字符串来存储选中的复选框的字
                	   var str="";
                	   //遍历数组获取里面被选中的复选框的值
                	   for(var i=0;i<array.length;i++)
               		   {
               		      //判断该复选框是否被选中，如果选中获取该复选框的值，存储到字符串中
               		      if(array[i].checked)
           		    	  {
           		    	     //如果被选中，将该复选框的值添加到字符串中
           		    	     str+=array[i].value+",";
           		    	     
           		    	  }
               		   }
                	   //如果用户没有选任何复选框，根据str是否为空串判断，应该友好提示一下
                	   if(str=="")
               		   {
               		       //用户没有选中任何复选框，提示用户至上选择一种
               		       alert("至少选择一种商品！");
               		   }else
           			   {
               			   if(confirm("你确定要删除所选商品？"))
           				   {
               				  //如果用户有选中复选框，应该将数据传输到后台
                    		  //以上得到所有被选中商品的key或pid存储在str字符串以逗号隔开，将该字符串传输到后台
                          	  location.href="${pageContext.request.contextPath }/deleteCheckedProduct?strPid="+str;
           				   }
               			 
           			   }
                	   
               	      
                   }
                   
                   
                   //当用户点击结算按钮，进行判断用户是否选择商品，如果没有就提示用户，有就将商品购物项所在map集合的key值也就是商品的pid传输到后台
                    function submitAccount()
                   {
                	  
                	   //判断用户是否选择商品最少选择一种，
                       //通过name获取所有的复选框对象，返回数组
                 	   var array=document.getElementsByName('checkboxname');
                 	   //定义一个字符串来存储选中的复选框的字
                	   var str="";
                	   //遍历数组获取里面被选中的复选框的值
                	   for(var i=0;i<array.length;i++)
               		   {
               		      //判断该复选框是否被选中，如果选中获取该复选框的值，存储到字符串中
               		      if(array[i].checked)
           		    	  {
           		    	     //如果被选中，将该复选框的值添加到字符串中
           		    	     str+=array[i].value+",";
           		    	     
           		    	  }
               		   }
                	  //如果用户没有选任何复选框，根据str是否为空串判断，应该友好提示一下
                	   if(str=="")
               		   {
               		       //用户没有选中任何复选框，提示用户至上选择一种
               		       alert("至少选择一种商品！");
               		   }else
           			   {
               			  
               				  //如果用户有选中复选框，应该将数据传输到后台
                    		  //以上得到所有被选中商品的key或pid存储在str字符串以逗号隔开，将该字符串传输到后台
                          	  location.href="${pageContext.request.contextPath }/orderInfo?strPid="+str;
           				  
               			 
           			   }
                	   
                   }
                   
                   
                 </script>
               
               
               
				<div style="margin:0 auto; margin-top:10px;">
					<strong style="font-size:16px;margin:5px 0;">全部商品</strong>
					<table class="table table-bordered" >
						<tbody>
							<tr class="active">
								<th>
								<!-- 复选框不加checked属性默认不选中，加上该属性不管设置什么值都是被选中状态 -->
								<label class="checkbox-inline">
                                    <input type="checkbox" id="checkboxall"><strong>全选</strong>
                                </label>
                                </th>
								<th>商品</th>
								<th>单价</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							<!-- el表达式遍历map集合，拿到每一个cartItem对象，entry对象包括key，value(cartItem) -->
							<c:forEach items="${cart.cartMap}" var="entry">
							    <tr >
								<td width="70" width="40%">
									<label class="checkbox-inline">
									    <!-- 购物车用map集合的key就是购物车项里面的商品pid，创建集合就是这样设计pid值为key -->
                                        <input type="checkbox" class="checkbox" name="checkboxname" value="${entry.value.product.pid}">
                                    </label>
								</td>
								<td width="30%">
								    <!-- 显示商品包括商品图片，描述，颜色 -->
								  <a href="${pageContext.request.contextPath }/productInfo?pid=${entry.value.product.pid}" target="_blank"> <img src="${entry.value.product.pimage }" width="70" height="60"></a> 
								  <a href="${pageContext.request.contextPath }/productInfo?pid=${entry.value.product.pid}" target="_blank">${entry.value.product.pname }</a>
									
									
								</td>
								<td width="20%" style="vertical-align:middle">
								${entry.value.product.shop_price }
								</td>
								<td width="10%" style="vertical-align:middle">
									${entry.value.buyNum }
								</td>
								<td width="15%" style="vertical-align:middle">
									<span class="subtotal">${entry.value.subTotal }</span>
								</td>
								
								
								<td style="vertical-align:middle">
									<a href="javascript:;" class="delete" onclick="delCartItem('${entry.key }')">删除</a>
								</td>
							</tr>
							
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>  
		</div>
         
         <!-- 显示商品总价钱和结算按钮 -->
         <div class="container">
	           <div class="row" >
	               <!-- 删除选中商品的信息 -->
	               <div>
	                  <a href="javascript:void(0);" onclick="deleteCheckedProduct()">删除选中商品</a>
	               </div>
	              <div style="margin-bottom: 10px; margin: 0 auto; padding: 10px;">
	                    
	                    
						<!-- 购物车所有商品的总价格 -->
						<div  style="text-align:right;">
						    总价: <strong style="color:#e4393c;font-size:20px;">￥${cart.total }元</strong>
					    </div>
					    <div style="text-align:right;margin-top:10px;margin-bottom:10px;">
						    <!-- 点击结算按钮 -->
							<!-- 这里不需要传输购物车的数据，因为购物车是存储在session中，其他地方可以取数据 -->
							
						<input type="button" width="100" value="去结算"  onclick="submitAccount()" style="background:#e4393c ;height:35px;width:100px;color:white;">
							
				        </div>
				  </div>
	           </div>
             </div>  
        </c:if>
		
		<!-- 如果购物车的为空显示一张图片
		  不是cart为空，就是cart里面集合属性，map集合为空时候友好显示
		 -->
		<c:if test="${empty cart.cartMap}">
		<!-- 模仿淘宝购物车 -->
		<div class="container" style="margin-top:20px;margin-bottom:60px;"> 
		    <div class="row" align="center" >
		        <span><img alt="" src="${pageContext.request.contextPath}/img/cart.png"></span>
		        <span>您的购物车还是空的，赶紧行动吧！</span>
		        <span><a href="${pageContext.request.contextPath}/index">去购物></a></span> 
		    </div>
		</div>		
		   
		</c:if>
	   <!-- 引入footer.jsp -->
	   <jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

	</body>

</html>