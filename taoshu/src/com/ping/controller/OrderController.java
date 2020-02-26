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
 * ������Ϣ�йصĴ���
 * @author admin
 *
 */
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	/**
	 * ���û�������㰴ť����ʾ������Ϣҳ��
	 */
	@RequestMapping("/orderInfo")
	public String showOrderInfo(String strPid,HttpServletRequest request)
	{
		
		/*��Ҫ��˼���ǣ���ȡǰ̨�û��ύ�Ĺ��ﳵ����map���ϵ�key����ȡ���ϸ���key��ȡÿ�������
		����ص���Ϣ��װ��������ٽ��������װ�����������У�����������洢��session�У����ض��򵽶�����Ϣҳ��*/
		HttpSession session = request.getSession();
		
		//�����ж��û��Ƿ��¼����û�û�е�¼�ض��򵽵�¼����
		if(session.getAttribute("user")==null)
		{
			//�û�û�е�¼���ض��򵽵�¼���棬������벻ִ��
			return "redirect:login";
		}
		//�ѵ���Ƿ�װ������Ϣ�������û���������ύ����Ʒ��Ϣ�����з�װ������Ϣ
		//����һ����������
		Order order=new Order();//�ͷ�װ������3.�ܽ�����9.������������ύ�����ٷ�װ
		//1.private String oid;           ����id  ʹ��UUID���ɲ�����ȵ��ַ�����Ϊ������ ��ʱ������Ϊ,���ύ����������
		//2.private Date ordertime;    �����µ�ʱ��   ��ʱ������Ϊ,���ύ����������
		//3.private double total;      �������ܼ۸�,����������з�װ
		//4.private int payment_type;   ֧�����ͣ�1���������2������֧��
		//5.private int status;         ֧��״̬ 1�����Ѹ��0����δ���� �ύ��������û��������Ϊ0���ȸ������޸�״̬
		//6.private String receiver_name; �ջ��˵�����  ��ʱ���������ύ����������
		//7.private String receiver_phone; �ջ��˵ĵ绰 ��ʱ���������ύ����������
		//8.private String receiver_address; �ջ��˵ĵ�ַ ��ʱ���������ύ����������
		//����������Ӧ��ʹ����������˼�룬�����Ժ����ѯ���з�װ,ֻʹ��һ��ʵ������У��û���Ͷ�������һ�Զ��ϵ��
		//ʹ����������˼�룬���ֶ�����������һ��ʵ�壬ֱ�������ʵ�壬���������õ�������uid������������Ϣ
		//9.private User user;               �ö��������ĸ��û�
		
		//�������ж��ٸ�������,��װ�����Ҫ���ڴ������ݣ�ֻ��Ҫ����order�������ڲ���װ��Ӧ�Ķ�����
		//10.List<OrderItem> orderItems=new ArrayList<OrderItem>();
		//��ȡsession�еĹ��ﳵ
		Cart cart = (Cart) session.getAttribute("cart");
		//�жϹ��ﳵ�Ƿ�Ϊ��
		if(cart!=null)
		{
			//��ȡ���ﳵ��map����
			Map<Integer, CartItem> cartMap = cart.getCartMap();
			//��ǰ̨��ȡ��key�ַ�������װ���Ž��зָ�õ��ַ�������
			String[] pidKey = strPid.split(",");
			//���������ȡÿ��keyֵ
			for(int i=0;i<pidKey.length;i++)
			{
				//����key��integer���ͣ����ַ���ת����int����
				int keyValue = Integer.parseInt(pidKey[i]);
				//����key��ȡ����cartMap��Ӧ��cartItem������
				CartItem cartItem = cartMap.get(keyValue);
				//���ù��ﳵ������ֵ��װ����������,�ȴ���һ�����������
				OrderItem orderItem=new OrderItem();
				//�����ﳵ�����Ʒ����������Ϊ��������Ʒ����
				orderItem.setCount(cartItem.getBuyNum());
				//�����ﳵ�����Ʒ��С������Ϊ��������ƷС��
				orderItem.setSubtotal(cartItem.getSubTotal());
				//�����ﳵ�����Ʒ����Ϣ����Ϊ��������Ʒ��Ϣ
				orderItem.setProduct(cartItem.getProduct());
				//��ȡ�������涩����ϣ����÷�װ�õĶ���������װ��ȥ
				order.getOrderItems().add(orderItem);
				////3.private double total;      �������ܼ۸�,�޸Ķ���������ܼ۸�
				order.setTotal(order.getTotal()+orderItem.getSubtotal());
			}
			
			
		}
		//���Ͻ����������װ��
		//����װ�õĶ�������ŵ�session��
		session.setAttribute("order", order);
		
	/*----------------------------------------------------------*/
		//���ﻹ��Ҫ�洢�û�ѡ����Ʒ��pid�ַ������ں����ύ��������չ��ﳵ�õ�strPid��cart���ﳵ����map���ϵ�key��ɵ��ַ�������ʵҲ����Ʒpid�洢��
		session.setAttribute("strPid", strPid);
    /*----------------------------------------------------------*/
		
		//�Ӷ��򵽶�����Ϣ���������ʾ�û���д���˶Զ�����Ϣ
		return "redirect:showOrderInfo";
	}
	
	/**
	 * ���û�����ύ������ť���ͻ�ȡsession�еĶ������󣬷�װʣ�µ����ݣ���������Ϣ�ֱ��򶩵���Ͷ����������ϸ������
	 */
	@RequestMapping("/submitOrder")
	public String showOrderResultInfo(HttpServletRequest request,Order order)
	{
		
		/*˼·���ύ����˼·���Ƿ�װ�ö������󣬻�ȡ�û��ύ���ݣ�����֮ǰ��װ������Ʒ��Ϣ�������Լ������װ��
		���������service�����dao����ɶ�����Ͷ������������ݲ���*/
		
		HttpSession session = request.getSession();
		//��ȡsession�洢�Ķ������󣬶�û�з�װ�����Խ������·�װ
		Order oldOrder = (Order) session.getAttribute("order");
		//������ô����ȡ���������ж϶����Ƿ�Ϊ��
		if(oldOrder!=null)
		{
			//3.private double total; ��֮ǰ��װ�õĶ����ܼ۸��ȡ�����з�װ 
			//��֮ǰ��װ�õĶ����ܼ۸��ȡ�ٽ��з�װ
			order.setTotal(oldOrder.getTotal());
			//10.List<OrderItem> orderItems=new ArrayList<OrderItem>();
			//��֮ǰ��װ�õĶ���������ȡ���ٷ�װ����������
			order.setOrderItems(oldOrder.getOrderItems());
			
		}
	
		//һ.��ȡsession�е�order���з�װ�����û��ύ�����ݷ�װ�ö�������
		//1.private String oid;           ����id  ʹ��UUID���ɲ�����ȵ��ַ�����Ϊ������ ��ʱ������Ϊ,���ύ����������
		String oid = UUID.randomUUID().toString();
		order.setOid(oid);
		//2.private Date ordertime;    �����µ�ʱ��   ��ʱ������Ϊ,���ύ����������
		//���ﻹҪ����һ�£���ȡ��������Ķ���ʱ����и�ʽ��һ��
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ordertime = dateFormat.format(new Date());
		order.setOrdertime(ordertime);
		     
		//5.private int status;         ֧��״̬ 1�����Ѹ��0����δ���� �ύ��������û��������Ϊ0���ȸ������޸�״̬
		order.setStatus(0);//���ﲻ���Ƿ����߻��ǻ��������û��֧����֧���ں����ٽ���
		//4.private int payment_type;   ֧�����ͣ�1���������2������֧��
		//6.private String receiver_name; �ջ��˵�����  ��ʱ���������ύ����������
		//7.private String receiver_phone; �ջ��˵ĵ绰 ��ʱ���������ύ����������
		//8.private String receiver_address; �ջ��˵ĵ�ַ ��ʱ���������ύ����������
		
		//4.6.7.8���û��ύ����̨����springmvc����name�Զ���װ������order��
		
		//����������Ӧ��ʹ����������˼�룬�����Ժ����ѯ���з�װ,ֻʹ��һ��ʵ������У��û���Ͷ�������һ�Զ��ϵ��
		//ʹ����������˼�룬���ֶ�����������һ��ʵ�壬ֱ�������ʵ�壬���������õ�������uid������������Ϣ
		//9.private User user;               �ö��������ĸ��û�
	     //��ȡsession�е��û�
		User user = (User) session.getAttribute("user");
		order.setUser(user);
		//���Ͻ�order�����װ����
				
	    //��.�����������䵽service�����в��붩��
		orderService.insertOrder(order);
		//��.���ݷ���ֵ�ж϶����Ƿ����ɹ���ת���������ɹ�ҳ����ʾ�û��ύ�����ɹ�/����ʧ��
		//��������ض��򣬲�Ȼ�û�ˢ�¾ͻ����ύһ��
		session.setAttribute("order", order);
		/**
		 *      �ύ����ǰ����չ��ﳵ��Ӧ����Ʒ
		 * �����ύ���������Ҳ��뵽���ݿ⣬���洢������Ϣ��session����
		 * ���ض���ҳ����ʾ֮ǰ����չ��ﳵ����ղ��ύ�Ķ����������Ʒ���ڵ�����㰴ť����ʾ������Ϣʱ��ͽ���ص�ѡ�е���ƷpidҲ�ǹ��ﳵ����map����key
		 * ���Ҵ洢��session���У�������Ҫ�ǻ�ȡ���ﳵ���󣬺�ѡ����Ʒ�Ĵ洢��map���϶�Ӧkey�ַ���strPid,����keyɾ�����ﳵ����map��������Ĺ��������
		 * �ٽ�ɾ�����map�������´洢�����ﳵCart����map��������
		 */
	/*----------------------------------------------------------*/
		//���ض���ҳ��֮ǰ����չ��ﳵ�����û�ѡ���ύ��������Ʒ
		String strPid = (String) session.getAttribute("strPid");
		String[] pids = strPid.split(",");
		//����pid�͹��ﳵ����map���ϴ洢��keyһ����ն�Ӧ�Ĺ��������
		Cart cart = (Cart) session.getAttribute("cart");
		//�ӹ��ﳵ��ȡmap����
		Map<Integer, CartItem> cartMap = cart.getCartMap();
		//�������ݻ�ȡÿһ��pidֵ
		for(int i=0;i<pids.length;i++)
		{
			//���ַ������͵�pidת����int����
			int pid=Integer.parseInt(pids[i]);
			
			if(cart!=null)
			{
				//ɾ�����ﳵ��Ʒ�����޸��ܼ۸񣬲�Ȼ���ֿ�ָ���쳣
				//ͬʱ��Ҫ�޸Ĺ��ﳵ�ܼ۸�=ԭ��-��Ʒ��С��
				cart.setTotal(cart.getTotal()-cartMap.get(pid).getSubTotal());
				
				//����pid��key����ȵ����map����Ԫ��
				cartMap.remove(pid);
				
				
			}
			
			
		}
		//��cartMap���ϴ洢��cart���ﳵ�����������������
		cart.setCartMap(cartMap);
	/*----------------------------------------------------------*/
		
		//�ύ�����ɹ����ض��򵽶����ɹ�ҳ��
		return "redirect:orderSuccess";
	}
	
	/**
	 * ��ʾ�û�������㰴ť���ύ���ﳵ��ѡ�����Ʒ�ģ���ʾ������Ϣ
	 */
	@RequestMapping("/showOrderInfo")
	public String showOrderInfo()
	{
		return "order_info";
	}
	
	
	/**
	 * ��ʾ�ύ�����ɹ�ҳ��
	 */
	@RequestMapping("/orderSuccess")
	public String showOrderSuccess()
	{
		return "orderSuccess";
	}
    
	/**
	 * ���û�����ҵĶ�������ʾ���û��Ķ����б���Ϣ��Ҫ���з�ҳ��ѯ����ʱ�併��
	 */
	@RequestMapping("/orderListInfo")
	public String showOrderListInfo(HttpServletRequest request,Model model,@RequestParam(defaultValue="1") int currentPage)
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		//��ǰҳ
		//int currentPage=1;
		//ÿҳ��ʾ������,ÿҳ��ʾ����������Ϣ
		int currentCount=2;
		//���û�����ҵĶ���ʱ�������ж��û��Ƿ��¼������û�û�е�¼�����ص�¼������е�¼
		if(user==null)
		{
			//��session��û�л��user����˵���û�û�е�¼���ض��򵽵�¼����
			return "redirect:login";	
		}

		//������ִ��˵���û���Ϊ�գ������û��Ѿ���¼
		//�����û�uid�����е�ǰҳ��ÿҳ��ʾ������ҳ��ѯ��ҳ������Ϣ
		PageBean pageBeanOrder=orderService.selectOrderListByUidAndCurrentPage(user,currentPage,currentCount);
		//����ѯ�����Ķ������ϴ洢��model������Զ��洢��request����
		model.addAttribute("pageBeanOrder", pageBeanOrder);
		
	
		//������ת���������б�ҳ�������ʾ
		return "order_list";
	}
	
	/**
	 * ���ҵĶ���ҳ�棬���û����ɾ����ɾ���ö�����¼
	 */
	@RequestMapping("/deleteThisOrder")
	public String deleteThisOrder(String oid)
	{
		//���ݶ�����ɾ���ö���
		orderService.deleteThisOrderByOid(oid);
		//ɾ���ö������ض��򵽶����б������ʾ������Ϣ���������ݿ��ѯȻ�������ʾ����
		return "redirect:orderListInfo";
	}
	
	
}
