package com.ping.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.pojo.Cart;
import com.ping.pojo.CartItem;
import com.ping.pojo.Product;
import com.ping.service.ProductService;

/**
 * 购物车的实现
 * @author admin
 *
 */
@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	/**
	 *  添加商品到购物车,将购物车存储到服务器端的session中
	 */
	@RequestMapping("/addCart")
	@ResponseBody
	public boolean addCart(HttpServletRequest request,int buyNum,int pid,HttpServletResponse response)
	{
		/*添加购物车的思路就是将前台数据封装起来包括商品的数量，颜色，商品小计，然后存储到购物车集合再存储到session中*/
		
		//通过调用getsession方法获取session对象该方法会判断有session对象就获取没有就创建一个session
		HttpSession session = request.getSession();
		
		//1.创建购物车项对象，封装相关商品的信息,包括商品的对象，数量，商品的价钱小计
		CartItem cartItem=new CartItem();
		//2.根据商品的pid查询商品的信息，并且存储到购物车项CartItem对象的属性里面
		Product productInfo = productService.selectProductInfoById(pid);
		//将商品的对象封装到购物车项中
		cartItem.setProduct(productInfo);
		//3.封装商品的数量
		cartItem.setBuyNum(buyNum);
		//4.计算该种商品的小计价钱，就是一种商品买几件需要总价钱
		double subTotal=buyNum*productInfo.getShop_price();
		//将小计封装到购物车项对象中
		cartItem.setSubTotal(subTotal);
		/*以上是将添加购物车数据封装好了，但是用户多次添加到购物车，需要使用集合存储，
		 * （不能直接存储，不然再次添加购物车session域中名称相同会覆盖）需要用户再创建一个类里面有集合属性，
		 * 将购物车项存储到购物车里面集合中，并且用户还需要查看还要将对象存储到session域中*/
		//5.将购物车项对象，存储到购物车的集合属性中,需要创建购物车对象获取里面集合属性
		/*Cart cart=new Cart();不能这样子做不能同一个用户每次添加购物车都会创建新的购物车*/
		//应该将购物车存储到session中，如果用户是第一次添加购物车获取为null，用户就该创建一个购物车对象，否则直接获取购物车
		//6.获得购物车----判断购物车是否已经存在购物车
		Cart cart = (Cart) session.getAttribute("cart");
		//如果session中不存在购物车，对象的默认值为null
		if(cart==null)
		{
			//没有购物车就创建一个
			 cart = new Cart();
		}
		//以上就拿到购物车对象，这是将购物车项封装到购物车对象的属性map集合中
		Map<Integer, CartItem> cartMap = cart.getCartMap();//第一个参数string存储是商品pid,到时候点击购物车删除商品能唯一识别
		//将购物车项存储到购物车里面的集合属性,key就是该商品的pid
		//注意了，再将商品存储到购物车时候应该判断购物车里面是否已经添加过该商品，如果有直接加会相同的key会造成覆盖原来商品，
		//所以应该判断一下,做一下处理，将相关商品数量，小计从新计算，反正都是覆盖,再覆盖前对某些数据从新计算一下
		//hashmap类有这个判断是否有相同key方法public boolean containsKey(Object key)
		if(cartMap.containsKey(pid))
		{
			//如果是true代表map集合里面有该商品，这时候要进行商品的数量计算还有小计这两个数据发生变化
			//1.获取原来购物车里面商品的数量,通过key就是商品的pid获取该集合里面该商品的购物车项对象
			CartItem oldcartItem = cartMap.get(pid);
			//得到旧的商品的数量
			int oldBuyNum = oldcartItem.getBuyNum();
			//计算得到信息的商品的数量,buyNum为本次添加商品的数量，oldBuyNum为购物车存储该商品的数量，newBuyNum新计算出来的总商品数量
			int newBuyNum=oldBuyNum+buyNum;
			//将新计算总商品的数量再次封装到购物车项
			cartItem.setBuyNum(newBuyNum);
			//2.计算小计,获取旧商品的小计oldSubTotal，加上现在商品的小计，得到总该商品的小计
			double oldSubTotal = oldcartItem.getSubTotal();
			double newSubTotal=oldSubTotal+subTotal;
			//将商品的小计存储到购物项
			cartItem.setSubTotal(newSubTotal);
			
		}
		
		//计算购物车所有商品的总价钱,获取购物车中商品总价格加上这次商品的小计
		double total=cart.getTotal()+subTotal;
		//将价格存储到购物车的total属性中
		cart.setTotal(total);
		
		cartMap.put(pid, cartItem);
		//再将购物车存储到session域中
		session.setAttribute("cart", cart);
		

		return true;
	}
	
	
	
	/**
	 * 删除单条购物车项
	 */
	@RequestMapping("/deleteCartItem")
	public String deleteCartItem(int pid,HttpServletRequest request)
	{
		/*思路获取session中购物车，直接根据pid就是map集合中的key通过方法将它删除，
		 * 还有修改一下所有商品的总价格
		 * 再将购物车对象存储到session中*/
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart!=null)
		{
			//获取购物车中map集合，里面存储是购物项对象
			Map<Integer, CartItem> cartMap = cart.getCartMap();
			
			//还有修改所有商品的总价格，购物车总价格,就是原有价格-删除该商品的小计，删除之前先修改总价格
			cart.setTotal(cart.getTotal()-cartMap.get(pid).getSubTotal());
			
			//根据map中传输过来的key删除
			cartMap.remove(pid);
			//将删除后的集合设置为购物车map属性的值
			cart.setCartMap(cartMap);
			
		}
		
		//将删除商品后的购物车添加到session中
		session.setAttribute("cart", cart);
		//重定向到购物车页面
		return "redirect:cart";
	}
	
	//删除选中的商品（可以单选或多选或全选）
	@RequestMapping("/deleteCheckedProduct")
	public String deleteCheckedProduct(String strPid,HttpServletRequest request)
	{
		//将字符串按照逗号进行分割,得到所有的pid存储到字符串数组
		String[] arrpid = strPid.split(",");
		//获取session，从session中获取购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		//如果购物车不为空并且字符串数组存储数据不为空
		if(cart!=null && arrpid.length>0)
		{
			//获取购物车中的map集合
			Map<Integer, CartItem> cartMap = cart.getCartMap();
			//遍历数组
			for(int i=0;i<arrpid.length;i++)
			{
				//获取商品的pid,将字符串转换成整数int类型
				int pid=Integer.parseInt(arrpid[i]);
				//在商品删除之前修改商品的总价格
				cart.setTotal(cart.getTotal()-cartMap.get(pid).getSubTotal());
				//等到每个商品的pid=>arrpid[i],根据获取商品的pid（key）删除cartItem对象(value)
				cartMap.remove(pid);	
			}
			
			//将修改后的map集合对象再次添加到购物车cart对象中
			cart.setCartMap(cartMap);
			
		}
		
		//将购物车对象存储到session对象
		session.setAttribute("cart", cart);
		//重定向到购物车页面
		return "redirect:cart";
	}
	
	
	/**
	 * 显示购物车页面
	 */
	@RequestMapping("/cart")
	public String showCart()
	{
		return "cart";
	}
	

}
