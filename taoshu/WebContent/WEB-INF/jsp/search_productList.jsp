<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入jstl包 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>商品列表</title>

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


    
	<!--商品列表-->
	<div class="container" style="margin-top: 10px;">

		<div class="row">
			<div class="container">
				<div class="row">

					<!-- 使用jstl遍历存储在request域中商品的集合 ,需要引入标签-->
					<c:forEach items="${pageBeanProduct.list }" var="product">

						<div class="col-xs-5 col-sm-4 col-md-2 img" style="text-align: center; width: 190px; height: 230px; margin-left: 4px">
							<!-- 商品的图片 -->
							<a href="${pageContext.request.contextPath }/productInfo?pid=${product.pid}">
							   <img src="${pageContext.request.contextPath }/${product.pimage }" style="width: 160px; height: 170px;" />
							</a>
							<!-- 商品的名称 -->
							<p>
				            	<a href="${pageContext.request.contextPath }/productInfo?pid=${product.pid}">${product.pname }</a>
							</p>
							<p>
							    <!-- 商品的销售价格 -->
								<span style="font-size:17px;color:#e33333">¥${product.shop_price }</span>&nbsp;&nbsp;
								<!-- 商品的市场价格 -->
								<span style="color: #999; font-size: 13px"><del>¥${product.market_price }</del></span>
							</p>
						</div>

					</c:forEach>



				</div>
			</div>

		</div>
	</div>
	
	<!-- 如果分页对象里面有商品集合存在就显示分页信息 -->
	<c:if test="${!empty pageBeanProduct.list}">
	<!--分页 -->
	<!-- 以上所有需要的的内容和数据后台已经转发给本页面 -->
	<div style=" margin: 0 auto; margin-top: 50px;text-align: center;">
	<ul class="pagination" style="margin-top: 10px;">
	    <!-- 首页 -->
           <li><a href="${pageContext.request.contextPath }/selectProductInfoListByInput?searchContent=${searchContent }&currentPage=1">首页</a></li>
		<!-- 上一页，就是后台存储当前页转发给jsp页面-1，还有进行判断是否到第一页 -->
		<c:if test="${pageBeanProduct.currentPage==1}">
		<li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">上一页</span></a></li>
		</c:if>
		<!-- 如果当前页不是第一页,用户可以点击 -->
		<c:if test="${pageBeanProduct.currentPage!=1}">
		<li><a href="${pageContext.request.contextPath }/selectProductInfoListByInput?searchContent=${searchContent }&currentPage=${pageBeanProduct.currentPage-1}" aria-label="Previous"><span aria-hidden="true">上一页</span></a></li>
		</c:if>
		
		
		<!-- 显示每一页，从数据库查询数据计算得来，遍历每一页 
		   如果点击当前页，给它 样式添加active，bootstrap让它选中状态
		-->
		<c:forEach begin="1" end="${pageBeanProduct.totalPage }" var="page"  >
		    <!-- 通过循环遍历每个页面，从1-总页数，后台默认当前页是1，如果用户没有点击循环从1开始，那么1就被选中
		         如果用户点击当前页，那么后台就会存储当前页，这时肯定相等判断是否点击就是是否相等，让它被选中
		     -->
		     <!-- 判断用户是否点击某一页，让它被选中 -->
		     <c:if test="${page==pageBeanProduct.currentPage }">
		        <li class="active"><a href="${pageContext.request.contextPath }/selectProductInfoListByInput?searchContent=${searchContent }&currentPage=${page}"> ${page } </a></li>
		     </c:if>
		     <!-- 没有点击的其他页肯定和后台存储的当前页不相等，没让它被选中 -->
		     <c:if test="${page!=pageBeanProduct.currentPage }">
		        <li><a href="${pageContext.request.contextPath }/selectProductInfoListByInput?searchContent=${searchContent }&currentPage=${page}"> ${page } </a></li>
		     </c:if>
		    
		</c:forEach>
	
		<!-- 下一页，就是后台存储当前页转发给jsp页面+1，还有判断是否到最后一页 -->
		<!-- 当点击下一页时候，判断是否到最后一页，如果是禁用不能点击 -->
		<c:if test="${pageBeanProduct.currentPage==pageBeanProduct.totalPage }">
		   <!-- 如果是最后一页，禁止用户点击 -->
		   <li class="disabled"><a href="javascript:void(0);" aria-label="Next"> <span aria-hidden="true">下一页</span></a></li>
		</c:if>
		<!-- 当点击下一页时候，如果不是最后一页 -->
		<c:if test="${pageBeanProduct.currentPage!=pageBeanProduct.totalPage }">
		   <li><a href="${pageContext.request.contextPath }/selectProductInfoListByInput?searchContent=${searchContent }&currentPage=${pageBeanProduct.currentPage+1}" aria-label="Next"> <span aria-hidden="true">下一页</span></a></li>
		</c:if>
		
           
        <!-- 尾页 -->
        <li><a href="${pageContext.request.contextPath }/selectProductInfoListByInput?searchContent=${searchContent }&currentPage=${pageBeanProduct.totalPage}">尾页</a></li>
	
	    <!-- 显示一下共有多少页数据 -->
	    <li class="disabled"> <span>共${pageBeanProduct.totalPage }页 </span></li>
	    
	     <!-- 显示一下共有多少页数据 -->
	    <li class="disabled"> <span>共${pageBeanProduct.totalCount }条 </span></li>
	  </ul>
    </div>
	<!-- 分页结束 -->
  </c:if>
    
    <!-- 如果分页对象里面没有商品集合，就提示用户没有搜索到该商品 -->
	<c:if test="${empty pageBeanProduct.list}">
	  <div class="container" style="margin-bottom:170px;">
	      <div class="row" style="text-align:center;margin-top:30px;">
	        <span><img  src="img/search.notice.png"></span><span style="color:#f60;font-size:16px;">抱歉，没有找到与“&nbsp;</span><em>${searchContent }</em><span style="color:#f60;font-size:16px;">&nbsp;”相关的商品</span>
	      </div>
	  </div>
	</c:if>
	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

</body>

</html>