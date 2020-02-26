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
<title>订单成功</title>

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
	   
        <!-- 如果用户是货到付款 -->
        <c:if test="${order.payment_type==1 }">
           <div class="container" >
                <div class="row" style="margin-top:50px;margin-bottom:140px;" >
                    <div class="col-md-8">
                        <img src="img/icon.png"><span style="color:#7ABD54;font-size:18px;">感谢您，订单提交成功！</span>我们会尽快为您发货~ 订单号：${order.oid }
                    </div>
                    <div class="col-md-1" style="margin-top:10px;">
                        <a href="${pageContext.request.contextPath }/index">继续购物</a>
                    </div>
                    <div  class="col-md-3" style="margin-top:7px;">
		                                      货到付款: <span style="font-size:18px;color:#e4393c">￥${order.total }元</span>
	                </div>
                </div>
           </div>
        </c:if>
       
       <!-- 如果用户是在线支付 -->
       <c:if test="${order.payment_type==2 }">
           <div class="container" style="margin-top:10px;">
               <div class="row" style="margin-top:30px;">
                   <div class="col-md-8">
                      <img src="img/icon.png"><span style="color:#7ABD54;font-size:18px;">订单提交成功，请尽快付款！</span><span>订单号： ${order.oid } </span>
                   </div>
                   <div  class="col-md-4" style="text-align:right;">
		                               应付金额: <span style="font-size:20px;color:#e4393c">￥${order.total }元</span>
	                </div>
               </div>
               <div class="row" style="margin-top:10px;margin-left:10px;">
                    <div>
                        <span class="pay_word2">请您在提交订单后 <em style="color:red;">24小时</em> 内完成支付，超时订单会自动取消。</span>
                    </div>
                   
               </div>
               
               <!-- 支付选择 -->
               <div class="row" style="text-align:center;margin-top:30px;">
                    
                        <div>
                            <!-- 支付宝支付 -->
                            <label class="radio-inline">
						       <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="支付宝" checked> <img alt="" src="img/pay/pay0.jpg">
						    </label>
                        </div>
                        <div style="margin-top:8px;">
                            <!-- 微信支付 -->
							<label class="radio-inline">
							  <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="微信"><img alt="" src="img/pay/pay1.jpg">
							</label>
                        </div>
	                    
	                    
	                    <!-- 支付按钮 -->
		                <div style="text-align:center;margin-top:40px;margin-bottom:20px;">	  
					      <input id="payOrder" type="button" width="100" value="立即支付"  style="background:#e4393c ;height:35px;width:100px;color:white;">	
			            </div>
                  
		              <script type="text/javascript">
		                  $(function(){
		               	   $("#payOrder").click(function(){
		               		   //获取用户选择的支付方式
		               		   var checkedPayName=$('input[name="inlineRadioOptions"]:checked').val();
		               		   //暂时还没开发支付
		               		   if(checkedPayName=="支付宝")
	               			   {
		               		      $('#myModal1').modal('show')
	               			   }
		               		   if(checkedPayName=="微信")
	               			   {
		               		      $('#myModal2').modal('show')
	               			   }
// 		               		   alert(checkedPayName+"支付待开发!")
		               	   });
		                  });
		               </script>
                    
                 </div>
                 
           </div>
       
       </c:if>
       
       
       <!-- 支付宝支付模态框显示二维码 -->
       <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="myModalLabel">
							支付宝支付（扫一扫下方二维码完成支付）  应付金额: <span style="font-size:18px;color:#e4393c">￥${order.total }元</span>
						</h4>
					</div>
					<div class="modal-body" style="text-align:center;">
						<img src="img/pay/zhifubao.png">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">关闭
						</button>
<!-- 						<button type="button" class="btn btn-primary"> -->
<!-- 							提交更改 -->
<!-- 						</button> -->
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
       </div>
       
       <!-- 微信支付模态框显示二维码 -->
       <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="myModalLabel">
							微信支付（扫一扫下方二维码完成支付）  应付金额: <span style="font-size:18px;color:#e4393c">￥${order.total }元</span>
						</h4>
					</div>
					<div class="modal-body" style="text-align:center;">
						<img src="img/pay/weixin.png">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">关闭
						</button>
<!-- 						<button type="button" class="btn btn-primary"> -->
<!-- 							提交更改 -->
<!-- 						</button> -->
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
       </div>
       <!-- 引入footer.jsp -->
	   <jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>
</html>