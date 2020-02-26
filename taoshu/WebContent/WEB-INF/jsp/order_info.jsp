<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>订单信息</title>

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

    
    <!-- 订单信息 -->
	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px;">
				<div style="margin: 0 auto; padding: 10px;margin-bottom:20px; ">
				<p style="font-size:20px;">填写并核对订单信息</p>
			    </div>
				
				<table class="table table-bordered">
					<tbody>
					    <tr>
							 <th colspan="4" style="font-size:18px;">商品清单</th>
						</tr>
						<tr class="active">
							<th>商品</th>
							<th>单价</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<c:forEach items="${order.orderItems }" var="pro">
							<tr >
								<td width="30%">
								     <a href="${pageContext.request.contextPath }/productInfo?pid=${pro.product.pid}" target="_blank"> <img src="${pro.product.pimage }" width="70" height="60"></a> 
								     <a href="${pageContext.request.contextPath }/productInfo?pid=${pro.product.pid}" target="_blank"> ${pro.product.pname }</a>
								     
								    
								    
								</td>
								<td width="20%" style="vertical-align:middle">￥${pro.product.shop_price }</td>
								<td width="10%" style="vertical-align:middle">${pro.count }</td>
								<td width="15%" style="vertical-align:middle"><span class="subtotal">￥${pro.subtotal }</span></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>

            <div  style="text-align:right;">
			   总商品金额: <span style="font-size:20px;">￥${order.total }元</span>
		    </div>
			

		</div>
    </div>
     <!-- 收货人信息 和支付方式-->
        <div class="container" style="margin-top:20px;border:1px solid #ddd;" >
              
            <div style="margin-top:15px;">
				   <strong style="font-size:18px;">收货人信息</strong>
		    </div>
            <hr />   <!-- hr显示一条水平线 -->
           <!-- post方式提交不要在action传参数，get提交可以，post提交方式参数封装在请求头中，get提交封装在请求行中 -->
			<form id="orderFrom" class="form-horizontal" action="${pageContext.request.contextPath }/submitOrder" method="post" style="margin-top: 5px;">
	            <!-- 因为在这里用户已经登录存储在session中，从中获取回显一下，用户可以修改，也可以保持默认，友好一点 -->
				<div class="form-group">
					<label for="username" class="col-sm-1 control-label">收货地址</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="address"
							name="receiver_address" value="${user.address }" placeholder="请输入收货地址">
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-1 control-label">收货人</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="inputPassword3"
							name="receiver_name" value="${user.name }" placeholder="请输收货人">
					</div>
				</div>
				<div class="form-group">
					<label for="confirmpwd" class="col-sm-1 control-label">电话</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="confirmpwd"
							name="receiver_phone" value="${user.telephone }" placeholder="请输入联系方式">
					</div>
				</div>


				<hr />
				  <div class="row">
				     <div class="col-md-2">
				         <strong style="font-size:18px;">支付方式</strong>
				     </div>
				    
				     <div class="col-md-2">
				       <select name="payment_type" class="form-control">
						  <option  value="1">货到付款</option>
						  <option  value="2">在线支付</option>
					   </select>
				     </div>
				     
				     <div class="col-md-8">
				     
				     </div>
				   
				  </div>
                <hr />
				
				  <div  style="text-align:right;">
		                               应付总额: <span style="font-size:20px;color:#e4393c">￥${order.total }元</span>
	              </div>
	               <div style="text-align:right;margin-top:10px;margin-bottom:10px;">	  
					  <input type="submit" width="100" value="提交订单"  style="background:#e4393c ;height:35px;width:100px;color:white;">	
			       </div>
			</form>
            
        </div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

</body>

</html>