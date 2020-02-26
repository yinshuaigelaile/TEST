package com.ping.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Cart;
import com.ping.pojo.CartItem;
import com.ping.pojo.Order;
import com.ping.pojo.OrderItem;
import com.ping.pojo.User;
import com.ping.service.OrderService;

/**
 * 订单信息有关的处理
 * @author admin
 *
 */
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	/**
	 * 当用户点击结算按钮，显示订单信息页面
	 */
	@RequestMapping("/orderInfo")
	public String showOrderInfo(String strPid,HttpServletRequest request)
	{
		
		/*主要的思想是，获取前台用户提交的购物车属性map集合的key，获取集合根据key获取每个购物项，
		将相关的信息封装到订单项，再将订单项封装到订单对象中，将订单对象存储到session中，从重定向到订单信息页面*/
		HttpSession session = request.getSession();
		
		//首先判断用户是否登录如果用户没有登录重定向到登录界面
		if(session.getAttribute("user")==null)
		{
			//用户没有登录，重定向到登录界面，下面代码不执行
			return "redirect:login";
		}
		//难点就是封装订单信息，根据用户点击结算提交的商品信息，进行封装订单信息
		//创建一个订单对象
		Order order=new Order();//就封装订单的3.总金额，还有9.订单项，其他在提交订单再封装
		//1.private String oid;           订单id  使用UUID生成不会相等的字符串作为订单号 暂时不设置为,等提交订单再设置
		//2.private Date ordertime;    订单下单时间   暂时不设置为,等提交订单再设置
		//3.private double total;      订单的总价格,放再下面进行封装
		//4.private int payment_type;   支付类型，1、货到付款，2、在线支付
		//5.private int status;         支付状态 1代表已付款，0代表未付款 提交订单都是没付款设置为0，等付款再修改状态
		//6.private String receiver_name; 收货人的姓名  暂时不设置留提交订单再设置
		//7.private String receiver_phone; 收货人的电话 暂时不设置留提交订单再设置
		//8.private String receiver_address; 收货人的地址 暂时不设置留提交订单再设置
		//如果是外键，应该使用面向对象的思想，方便以后多表查询进行封装,只使用一个实体类就行（用户表和订单表是一对多关系）
		//使用面向对象的思想，该字段是属于例外一个实体，直接引入该实体，这样可以拿到不单是uid，还有其他信息
		//9.private User user;               该订单属于哪个用户
		
		//订单中有多少个订单项,封装这个主要是在传输数据，只需要传输order对象，它内部封装对应的订单项
		//10.List<OrderItem> orderItems=new ArrayList<OrderItem>();
		//获取session中的购物车
		Cart cart = (Cart) session.getAttribute("cart");
		//判断购物车是否为空
		if(cart!=null)
		{
			//获取购物车的map集合
			Map<Integer, CartItem> cartMap = cart.getCartMap();
			//将前台获取的key字符串，安装逗号进行分割得到字符串数组
			String[] pidKey = strPid.split(",");
			//遍历数组获取每个key值
			for(int i=0;i<pidKey.length;i++)
			{
				//由于key是integer类型，将字符串转换成int类型
				int keyValue = Integer.parseInt(pidKey[i]);
				//根据key获取集合cartMap对应的cartItem购物项
				CartItem cartItem = cartMap.get(keyValue);
				//将该购物车项里面值封装到订单项中,先创建一个订单项对象
				OrderItem orderItem=new OrderItem();
				//将购物车项该商品的数量设置为订单项商品数量
				orderItem.setCount(cartItem.getBuyNum());
				//将购物车项该商品的小计设置为订单项商品小计
				orderItem.setSubtotal(cartItem.getSubTotal());
				//将购物车项该商品的信息设置为订单项商品信息
				orderItem.setProduct(cartItem.getProduct());
				//获取订单里面订单项集合，将该封装好的订单项对象封装进去
				order.getOrderItems().add(orderItem);
				////3.private double total;      订单的总价格,修改订单里面的总价格
				order.setTotal(order.getTotal()+orderItem.getSubtotal());
			}
			
			
		}
		//以上将订单对象封装好
		//将封装好的订单对象放到session中
		session.setAttribute("order", order);
		
	/*----------------------------------------------------------*/
		//这里还需要存储用户选择商品的pid字符串，在后面提交订单，清空购物车用到strPid是cart购物车里面map集合的key组成的字符串（其实也是商品pid存储）
		session.setAttribute("strPid", strPid);
    /*----------------------------------------------------------*/
		
		//从定向到订单信息界面进行显示用户填写并核对订单信息
		return "redirect:showOrderInfo";
	}
	
	/**
	 * 当用户点击提交订单按钮，就获取session中的订单对象，封装剩下的数据，将订单信息分别向订单表和订单项（订单明细表）插入
	 */
	@RequestMapping("/submitOrder")
	public String showOrderResultInfo(HttpServletRequest request,Order order)
	{
		
		/*思路：提交订单思路就是封装好订单对象，获取用户提交数据，还有之前封装订单商品信息，还有自己定义封装，
		将对象传输给service层调用dao层完成订单表和订单项表相关数据插入*/
		
		HttpSession session = request.getSession();
		//获取session存储的订单对象，对没有封装的属性进行重新封装
		Order oldOrder = (Order) session.getAttribute("order");
		//不管怎么样获取对象首先判断对象是否为空
		if(oldOrder!=null)
		{
			//3.private double total; 将之前封装好的订单总价格获取，进行封装 
			//将之前封装好的订单总价格获取再进行封装
			order.setTotal(oldOrder.getTotal());
			//10.List<OrderItem> orderItems=new ArrayList<OrderItem>();
			//将之前封装好的订单项对象获取，再封装到订单对象
			order.setOrderItems(oldOrder.getOrderItems());
			
		}
	
		//一.获取session中的order进行封装，将用户提交的数据封装好订单对象，
		//1.private String oid;           订单id  使用UUID生成不会相等的字符串作为订单号 暂时不设置为,等提交订单再设置
		String oid = UUID.randomUUID().toString();
		order.setOid(oid);
		//2.private Date ordertime;    订单下单时间   暂时不设置为,等提交订单再设置
		//这里还要处理一下，获取订单对象的订单时间进行格式化一下
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ordertime = dateFormat.format(new Date());
		order.setOrdertime(ordertime);
		     
		//5.private int status;         支付状态 1代表已付款，0代表未付款 提交订单都是没付款设置为0，等付款再修改状态
		order.setStatus(0);//这里不管是否在线还是货到付款都是没有支付，支付在后面再进行
		//4.private int payment_type;   支付类型，1、货到付款，2、在线支付
		//6.private String receiver_name; 收货人的姓名  暂时不设置留提交订单再设置
		//7.private String receiver_phone; 收货人的电话 暂时不设置留提交订单再设置
		//8.private String receiver_address; 收货人的地址 暂时不设置留提交订单再设置
		
		//4.6.7.8在用户提交给后台就由springmvc根据name自动封装到对象order中
		
		//如果是外键，应该使用面向对象的思想，方便以后多表查询进行封装,只使用一个实体类就行（用户表和订单表是一对多关系）
		//使用面向对象的思想，该字段是属于例外一个实体，直接引入该实体，这样可以拿到不单是uid，还有其他信息
		//9.private User user;               该订单属于哪个用户
	     //获取session中的用户
		User user = (User) session.getAttribute("user");
		order.setUser(user);
		//以上将order对象封装好了
				
	    //二.将订单对象传输到service，进行插入订单
		orderService.insertOrder(order);
		//三.根据返回值判断订单是否插入成功，转发到订单成功页面提示用户提交订单成功/或者失败
		//这里必须重定向，不然用户刷新就会又提交一次
		session.setAttribute("order", order);
		/**
		 *      提交订单前，清空购物车相应的商品
		 * 以上提交订单，并且插入到数据库，还存储订单信息到session域中
		 * 在重定向到页面显示之前，清空购物车里面刚才提交的订单里面的商品，在点击结算按钮，显示订单信息时候就将相关的选中的商品pid也是购物车里面map集合key
		 * 并且存储到session域中，这里主要是获取购物车对象，和选中商品的存储在map集合对应key字符串strPid,根据key删除购物车里面map集合里面的购物项对象
		 * 再将删除后的map对象，重新存储到购物车Cart集合map属性里面
		 */
	/*----------------------------------------------------------*/
		//在重定向页面之前，清空购物车里面用户选择提交订单的商品
		String strPid = (String) session.getAttribute("strPid");
		String[] pids = strPid.split(",");
		//根据pid和购物车里面map集合存储的key一样清空对应的购物项对象
		Cart cart = (Cart) session.getAttribute("cart");
		//从购物车中取map集合
		Map<Integer, CartItem> cartMap = cart.getCartMap();
		//遍历数据获取每一个pid值
		for(int i=0;i<pids.length;i++)
		{
			//将字符串类型的pid转换成int类型
			int pid=Integer.parseInt(pids[i]);
			
			if(cart!=null)
			{
				//删除购物车商品，先修改总价格，不然出现空指针异常
				//同时还要修改购物车总价格=原价-商品的小计
				cart.setTotal(cart.getTotal()-cartMap.get(pid).getSubTotal());
				
				//根据pid和key是相等的清空map集合元素
				cartMap.remove(pid);
				
				
			}
			
			
		}
		//将cartMap集合存储到cart购物车对象里面的属性里面
		cart.setCartMap(cartMap);
	/*----------------------------------------------------------*/
		
		//提交订单成功，重定向到订单成功页面
		return "redirect:orderSuccess";
	}
	
	/**
	 * 显示用户点击结算按钮，提交购物车所选择的商品的，显示订单信息
	 */
	@RequestMapping("/showOrderInfo")
	public String showOrderInfo()
	{
		return "order_info";
	}
	
	
	/**
	 * 显示提交订单成功页面
	 */
	@RequestMapping("/orderSuccess")
	public String showOrderSuccess()
	{
		return "orderSuccess";
	}
    
	/**
	 * 当用户点击我的订单，显示该用户的订单列表信息，要进行分页查询，按时间降序
	 */
	@RequestMapping("/orderListInfo")
	public String showOrderListInfo(HttpServletRequest request,Model model,@RequestParam(defaultValue="1") int currentPage)
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		//当前页
		//int currentPage=1;
		//每页显示的条数,每页显示两条订单信息
		int currentCount=2;
		//当用户点击我的订单时候首先判断用户是否登录，如果用户没有登录，返回登录界面进行登录
		if(user==null)
		{
			//从session中没有获得user对象，说明用户没有登录，重定向到登录界面
			return "redirect:login";	
		}

		//能向下执行说明用户不为空，就是用户已经登录
		//根据用户uid，还有当前页，每页显示条数分页查询该页订单信息
		PageBean pageBeanOrder=orderService.selectOrderListByUidAndCurrentPage(user,currentPage,currentCount);
		//将查询出来的订单集合存储到model对象会自动存储到request域中
		model.addAttribute("pageBeanOrder", pageBeanOrder);
		
	
		//将数据转发到订单列表页面进行显示
		return "order_list";
	}
	
	/**
	 * 在我的订单页面，当用户点击删除，删除该订单记录
	 */
	@RequestMapping("/deleteThisOrder")
	public String deleteThisOrder(String oid)
	{
		//根据订单号删除该订单
		orderService.deleteThisOrderByOid(oid);
		//删除该订单，重定向到订单列表进行显示订单信息，进行数据库查询然后从新显示数据
		return "redirect:orderListInfo";
	}
	
	
}
