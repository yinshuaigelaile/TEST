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
<title>订单列表</title>

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

    <div  class="container">
       <div  class="row">
          <div style="padding-top:10px;padding-left:15px;background-color:#f5f5f5;margin-bottom:10px;height:50px;" >
			         
			<p><strong style="font-size:20px;">我的订单</strong>
			
	      </div>
       </div>
    </div>
	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px;">
			     
				
				   <!-- 当用户点击删除订单处理单击事件绑定的函数 -->
				   <script type="text/javascript">
				      function deleteThisOrder(oid)
				      {
				    	 
				    	 if(confirm("你确定要删除该订单？")) 
			    		 {
				    		 //将订单号oid传输到后台删除该订单
				    		  
			    		      location.href="${pageContext.request.contextPath }/deleteThisOrder?oid="+oid;
			    		 }
				      }
				   </script>
				   <c:forEach items="${pageBeanOrder.list }" var="order">
					  <table class="table table-bordered">
						<tbody>
							<tr class="active">
							    <th colspan="1"><img src="img/order-time.png">${order.ordertime }&nbsp;&nbsp;淘书吧<img src="img/order-icons.png"></th>
								<th colspan="2">订单编号:&nbsp;&nbsp;${order.oid }</th>
								<!-- 订单点击的删除图片,将删除的订单号作为单击事件绑定函数的参数 -->
								<th colspan="1" style="text-align:right;"><img src="img/delete.png" onclick="deleteThisOrder('${order.oid}')"></th>
							</tr>
							<tr class="active">
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							
							</tr>

							<c:forEach items="${order.orderItems }" var="orderItem">
								<tr>
									<td width="30%">
									    <a href="${pageContext.request.contextPath }/productInfo?pid=${orderItem.product.pid}" target="_blank"><img src="${orderItem.product.pimage}" width="70" height="60"></a>
									    <a href="${pageContext.request.contextPath }/productInfo?pid=${orderItem.product.pid}" target="_blank">	${orderItem.product.pname}</a>
									    
									    
									</td>
									<td width="20%" style="vertical-align:middle">￥${orderItem.product.shop_price}</td>
									<td width="10%" style="vertical-align:middle">${orderItem.count}</td>
									<td width="15%" style="vertical-align:middle"><span class="subtotal">￥${orderItem.subtotal}</span></td>
									
								    
								</tr>
								
							</c:forEach>
                             <!-- 显示该订单所有商品的总金额 -->
                             <tr >
								
                                  <td colspan="4" width="15%" style="vertical-align:middle;text-align:right;" >
                                                                                                        总金额:&nbsp;&nbsp;<span>￥${order.total}</span>
                                        
                                          
                                          <!-- 显示支付方式  payment_type=1为货到付款，=2为在线支付 --> 
                                          <c:if test="${order.payment_type==1}">
                                            <p>货到付款
                                          </c:if>   
                                          <c:if test="${order.payment_type==2}">
                                            <p>在线支付
                                            <span>
                                                <c:if test="${order.status==0 }">
                                                   (未付款)
                                                </c:if>
                                                <c:if test="${order.status==1 }">
                                                   (已付款)
                                                </c:if>
                                            </span>
                                          </c:if>                                                       
                                  
                                  </td>
                                 
								
							 </tr>
						 </tbody>
                      </table>
					</c:forEach>


				
			</div>
		</div>
   
		
	   <!-- 显示分页信息 ，如果订单对象集合没有元素后台没有封装，属性默认为null-->
	   <c:if test="${pageBeanOrder.list!=null }">
	       <div class="row">
	       <div style="text-align: center;">
			<ul class="pagination">
			     
			     <!-- 首页 -->
			     <li><a href="${pageContext.request.contextPath }/orderListInfo?currentPage=1">首页</a></li>
			     <!-- 上一页，后台传输过来的当前页currentPage-1,还要判断是否到第一页，如果是第一页不让用户可以点击 -->
				 
				 <!-- 如果当前页是第一页 -->
				 <c:if test="${pageBeanOrder.currentPage==1 }">
				     <!-- 如果当前页是第一页，选它disable不能点击状态 -->
				     <li class="disabled">
				        <a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">上一页</span></a>
				     </li>
				 </c:if>
				 <!-- 如果不是第一页 -->
				  <c:if test="${pageBeanOrder.currentPage!=1 }">
				     <!-- 如果当前页不是第一页，选它可以点击-->
				     <li>
				        <a href="${pageContext.request.contextPath }/orderListInfo?currentPage=${pageBeanOrder.currentPage-1}" aria-label="Previous"><span aria-hidden="true">上一页</span></a>
				     </li>
				 </c:if>
				 
				<!-- 根据后台的提供分页数据信息获取进行动态分页 -->
			     <c:forEach begin="1" end="${pageBeanOrder.totalPage }" var="page">
			        
			        <!-- 判断一下如果用户的点击某一页让它被选中，主要是后台接收当前页数据来判断用户点击哪一页，让它选中状态 -->
			        <c:if test="${page==pageBeanOrder.currentPage }">
			            <!-- 后台接收到用户点击的当前页返回页面，与页面一样的页让它被选中 -->
			            <li class="active"><a href="${pageContext.request.contextPath }/orderListInfo?currentPage=${page }"> ${page } </a></li>
			        </c:if>
			        
			        <!-- 其他不是当前页,不被选中状态 -->
			        <c:if test="${page!=pageBeanOrder.currentPage }">
					
					    <li><a href="${pageContext.request.contextPath }/orderListInfo?currentPage=${page }"> ${page } </a></li>
			        
			        </c:if>
					
			     </c:forEach>
					
				<!-- 下一页  后台传输过来的当前页currentPage+1，同时还要判断是否到最后一页，如果到最后一页，让用户不可以点击状态-->	
				<c:if test="${pageBeanOrder.currentPage==pageBeanOrder.totalPage }">
				    <!-- 如果当前页是最后一页，让它disable -->
				    <li class="disabled">
				      <a  href="javascript:void(0);" aria-label="Next"> <span aria-hidden="true">下一页</span></a>
				    </li>
				</c:if>
				<c:if test="${pageBeanOrder.currentPage!=pageBeanOrder.totalPage }">
				    <!-- 如果当前页不是最后一页，让它可以点-->
				    <li>
				      <a href="${pageContext.request.contextPath }/orderListInfo?currentPage=${pageBeanOrder.currentPage+1}" aria-label="Next"> <span aria-hidden="true">下一页</span></a>
				    </li>
				</c:if>
				
				<!-- 尾页 -->
			    <li><a href="${pageContext.request.contextPath }/orderListInfo?currentPage=${pageBeanOrder.totalPage }">尾页</a></li>
			
			    <!-- 显示一下总页数 -->
			    <li class="disabled"><a href="javascript:void(0);"> 共${pageBeanOrder.totalPage }页 </a></li>
			 
			    <!-- 显示一下总记录数 -->
			    <li class="disabled"><a href="javascript:void(0);"> 共${pageBeanOrder.totalCount }条 </a></li>
			 </ul>
		   </div>
	   
	    </div>
	  </c:if>
	  
	  <!-- 如果没有订单友好提示一下 -->
	  <c:if test="${pageBeanOrder.list==null }">
	     
         <div class="row" style="text-align:center;margin-bottom:95px;margin-top:20px;">
            
             <h4>您还没有下过订单哦~</h4>
             <a href="${pageContext.request.contextPath }/index">去首页看看</a>
         </div>
	    
	  </c:if>
	   
  </div>		
		
		


	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>

</body>

</html>