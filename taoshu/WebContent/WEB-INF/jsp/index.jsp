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
		<title>毕业设计网上书城首页</title>

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
		<!--轮播图-->
		<!-- <div class="container">
			<div class="row">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					Indicators
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
						<li data-target="#carousel-example-generic" data-slide-to="3"></li>

					</ol>

					Wrapper for slides  class="center-block"
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img  src="img/b1.jpg" alt="0" style="width:100%;">
						    
						</div>
						<div class="item">
							<img  src="img/b2.jpg" alt="1" style="width:100%;">
							
						</div>
						<div class="item">
							<img  src="img/b3.jpg" alt="2" style="width:100%;">
							
						</div>
						<div class="item">
							<img  src="img/b4.jpg" alt="3" style="width:100%;">
							
						</div>

					</div>

					Controls
					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>

		</div> -->

		<!--最新商品-->
		<div class="container" style="margin-top:10px ;">
			<div class="row" style="font-size: 20px;background: #eaeaea;">
				<p style="font-size:25px ;color:#333">新品首发 <span style="font-size: 15px;">每日为你精心挑选</span></p>
			</div>
			<div class="row">
				<div class="container">
					<div class="row" >
						
						<!-- 使用jstl遍历存储在request域中商品的集合 ,需要引入标签-->
						<c:forEach items="${newProductList }" var="newproduct">
						   
						   <div class="col-xs-5 col-sm-4 col-md-2 img" style="text-align: center;width:190px;height:230px;margin-left:4px">
							<a href="${pageContext.request.contextPath }/productInfo?pid=${newproduct.pid}"><img src="${pageContext.request.contextPath }/${newproduct.pimage }" style="width:160px;height:170px;" /></a>
							<p><a href="${pageContext.request.contextPath }/productInfo?pid=${newproduct.pid}">${newproduct.pname }</a></p>
							<p><span style="font-size:17px;color:#e33333">¥${newproduct.shop_price }</span>&nbsp;&nbsp;<span style="color:#999;font-size:13px"><del>¥${newproduct.market_price }</del></span></p>
						   </div>
						
						</c:forEach>
						
						
	
					</div>
				</div>
				
			</div>
		</div>
		<!--热门商品-->
		<div class="container" style="margin-top:10px ;">
			<div class="row" style="font-size: 20px;background: #eaeaea;">
				<p style="font-size:25px;color:#333">热门推荐 <span style="font-size: 15px;">每日为你精心挑选</span></p>
			</div>
			<div class="row">
				<div class="container">
					<div class="row">
					
						<!-- 使用jstl遍历存储在request域中商品的集合 ,需要引入标签-->
						<c:forEach items="${hotProductList }" var="hotproduct">
						   
						   <div class="col-xs-5 col-sm-4 col-md-2 img" style="text-align: center;width:190px;height:230px;margin-left:4px">
							<a href="${pageContext.request.contextPath }/productInfo?pid=${hotproduct.pid}"><img src="${pageContext.request.contextPath }/${hotproduct.pimage }" style="width:160px;height:170px;" /></a>
							<p><a href="${pageContext.request.contextPath }/productInfo?pid=${hotproduct.pid}">${hotproduct.pname }</a></p>
							<p><span style="font-size:17px;color:#e33333">¥${hotproduct.shop_price }</span>&nbsp;&nbsp;<span style="color:#999;font-size:13px"><del>¥${hotproduct.market_price }</del></span></p>
						   </div>
						
						</c:forEach>
					</div>
				</div>
				
				
			</div>
		</div>

	
      <!-- 引入footer.jsp -->
	 <jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

	</body>

</html>