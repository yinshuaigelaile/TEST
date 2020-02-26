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
<title>商品信息</title>

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

	<div class="container">
		<div class="row">
			<div
				style="border: 1px solid #e4e4e4;margin-bottom: 10px; margin: 0 auto; padding: 10px; margin-bottom: 10px;">
				<a href="${pageContext.request.contextPath }/index">网站首页&nbsp;&nbsp;</a> 
				
				<c:if test="${!empty productInfo}">
				  &gt;<a href="${pageContext.request.contextPath }/productListInfo?cid=${productInfo.category.cid}">${productInfo.category.cname }&nbsp;&nbsp;</a>
				  &gt;<a href="${pageContext.request.contextPath }/productInfo?pid=${productInfo.pid}"> ${productInfo.pname }</a>
				</c:if>
				
			</div>

			<div style="margin: 0 auto;">
			    
				<div class="col-md-3 ">
					<!-- 最好加上图片的所在工程的名称，就是写绝对路径，通过el表达式获取四大域对象的值 
					width: 400px; height: 350px;
					-->
					<img style="opacity: 1;width: 250px; height: 250px; " title=""
						class="medium"
						src="${pageContext.request.contextPath }/${productInfo.pimage }">
				</div>
                <div class="col-md-1 hidden-xs">
			    
			    </div>
				<div class="col-md-8" style="height: 350px;">
					<div>
						<strong>${productInfo.pdesc }</strong>
					</div>
					<div
						style="border-bottom: 1px dotted #dddddd; width: 250px; margin: 10px 0 10px 0;">
						<div>编号：${productInfo.pid }</div>
					</div>

					<div style="margin: 10px 0 10px 0;">
						价格: <strong style="color: #e4393c;font-size:20px">￥:${productInfo.shop_price }元</strong>
						&nbsp;&nbsp;
						<del>￥${productInfo.market_price }元</del>
					</div>

					<div style="margin: 10px 0 10px 0;">
						促销: <a target="_blank" title="限时抢购 "
							style="color:#f71919;">限时抢购</a>
					</div>
					
						

					<div style="padding: 10px; width: 250px; margin: 15px 0 10px 0; ">
	                     <!-- 给加减号添加单击事件，使用jQuery -->
	                    <script type="text/javascript">
	                        $(function(){
	                        	//给减号添加点击事件，调用匿名方法，主要是获取文本框中的值-1,调用val()可以设置或者获取
	                        	$("#subtract").click(function(){
	                        		//获取文本框的值，默认val方法返回时字符串，parseInt将字符串转换成整数类型
	                        		var num=parseInt($("#buyNum").val());
	                        		//判断获取文本框的值是否大于1
	                        		  if(num>1)
                        			  {
	                        			//将文本框值减1
	                        			$("#buyNum").val(num-1);
                        			  }else
                       				  {
                        				//将文本框值设置为1
  	                        			$("#buyNum").val(1);
                       				  }
	                        	});
	                        	
	                        	//给加号按钮添加单击事件
	                        	$("#add").click(function(){
	                        		//获取文本框的值,默认val方法返回时字符串，parseInt将字符串转换成整数类型
	                        		var num=parseInt($("#buyNum").val());
	                        		//将文本框的值加1
	                        		$("#buyNum").val(num+1);
	                        		  
	                        			
	                        	});
	                        });
	                    </script>
						<div style=" margin-top: 20px; padding-left: 10px;">
							购买数量: 
							<span><input id="subtract" type="button" value="-" style="width:35px;" ></span>
							<input id="buyNum" name="buyNum" value="1" maxlength="4" size="3" type="text">
						    <span><input id="add" type="button" value="+" style="width:35px;"></span>
						</div>
                       
                       <!-- 当用户点击添加购物车，将商品获取页面数据提交给后台 -->
                       <script type="text/javascript">
                         $(function(){
                        	 //当用户单击加入购物车，触发点击事件
                        	 $("#addcart").click(function(){
                        		 //获取商品的数量
                        		 var buyNum=$("#buyNum").val();
                        		 //通过ajax请求后台controller并且带上商品的参数
                        		 $.ajax({
                        			   url: "${pageContext.request.contextPath }/addCart?pid=${productInfo.pid}",
                        			   data: {'buyNum':buyNum},//传输到服务器的数据，也是json格式的字符串
                        			   success:function(data)//请求成功的回调函数
                        			   {
                        				   if(data)
                       					   {
                       					      alert("添加购物车成功！");
                       					   }else
                   						   {
                       						  alert("添加购物车失败！");
                   						   }
                        			   },
                        			   type:"POST",
                        			   dataType:"json",//返回值类型是json格式字符串
                        			   async:false//true代表异步请求，false代表同步请求
                        			 });
                        	 });
                         });
                       </script>
                       <!-- 由于该页面显示商品的详细信息，并是不表单，只能通过js连接访问后台，不能直接使用超链接，因为还需要使用js获取参数提交给后台 -->
						<div style="margin-top:20px;margin-left:15px; ">
							
							<input id="addcart" style="background-color:#df3033; height: 36px; width: 127px;color:#ffffff;" value="加入购物车" type="button">
							
						</div>
					</div>

				</div>
			</div>
			<div class="clear"></div>
			<div style=" margin: 0 auto;">
				
				<div style="margin-top: 10px; ">
				   
					<table class="table table-bordered">
						<tbody>
						    <tr class="active">
						      <th colspan="2">商品介绍</th>
						    </tr>
							<tr >
								<th colspan="2">商品基本参数</th>
							</tr>
							<tr>
								<th width="10%">商品的名称</th>
								<td width="30%">${productInfo.pname }</td>
							</tr>
							<tr>
								<th width="10%">商品的编号</th>
								<td>${productInfo.pid }</td>
							</tr>
							<tr>
								<th width="10%">商品的日期</th>
								<td>${productInfo.pdate }</td>
							</tr>
							<tr>
								<th width="10%">商品是否热门</th>
								<c:if test="${productInfo.is_hot==1 }">
								  <td>是</td>
								</c:if>
								<c:if test="${productInfo.is_hot==0 }">
								  <td>否</td>
								</c:if>
								
							</tr>
							
						</tbody>
					</table>
				</div>

			</div>

		</div>
	</div>


	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

</body>

</html>