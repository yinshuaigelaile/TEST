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
 * ���ﳵ��ʵ��
 * @author admin
 *
 */
@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	/**
	 *  �����Ʒ�����ﳵ,�����ﳵ�洢���������˵�session��
	 */
	@RequestMapping("/addCart")
	@ResponseBody
	public boolean addCart(HttpServletRequest request,int buyNum,int pid,HttpServletResponse response)
	{
		/*��ӹ��ﳵ��˼·���ǽ�ǰ̨���ݷ�װ����������Ʒ����������ɫ����ƷС�ƣ�Ȼ��洢�����ﳵ�����ٴ洢��session��*/
		
		//ͨ������getsession������ȡsession����÷������ж���session����ͻ�ȡû�оʹ���һ��session
		HttpSession session = request.getSession();
		
		//1.�������ﳵ����󣬷�װ�����Ʒ����Ϣ,������Ʒ�Ķ�����������Ʒ�ļ�ǮС��
		CartItem cartItem=new CartItem();
		//2.������Ʒ��pid��ѯ��Ʒ����Ϣ�����Ҵ洢�����ﳵ��CartItem�������������
		Product productInfo = productService.selectProductInfoById(pid);
		//����Ʒ�Ķ����װ�����ﳵ����
		cartItem.setProduct(productInfo);
		//3.��װ��Ʒ������
		cartItem.setBuyNum(buyNum);
		//4.���������Ʒ��С�Ƽ�Ǯ������һ����Ʒ�򼸼���Ҫ�ܼ�Ǯ
		double subTotal=buyNum*productInfo.getShop_price();
		//��С�Ʒ�װ�����ﳵ�������
		cartItem.setSubTotal(subTotal);
		/*�����ǽ���ӹ��ﳵ���ݷ�װ���ˣ������û������ӵ����ﳵ����Ҫʹ�ü��ϴ洢��
		 * ������ֱ�Ӵ洢����Ȼ�ٴ���ӹ��ﳵsession����������ͬ�Ḳ�ǣ���Ҫ�û��ٴ���һ���������м������ԣ�
		 * �����ﳵ��洢�����ﳵ���漯���У������û�����Ҫ�鿴��Ҫ������洢��session����*/
		//5.�����ﳵ����󣬴洢�����ﳵ�ļ���������,��Ҫ�������ﳵ�����ȡ���漯������
		/*Cart cart=new Cart();����������������ͬһ���û�ÿ����ӹ��ﳵ���ᴴ���µĹ��ﳵ*/
		//Ӧ�ý����ﳵ�洢��session�У�����û��ǵ�һ����ӹ��ﳵ��ȡΪnull���û��͸ô���һ�����ﳵ���󣬷���ֱ�ӻ�ȡ���ﳵ
		//6.��ù��ﳵ----�жϹ��ﳵ�Ƿ��Ѿ����ڹ��ﳵ
		Cart cart = (Cart) session.getAttribute("cart");
		//���session�в����ڹ��ﳵ�������Ĭ��ֵΪnull
		if(cart==null)
		{
			//û�й��ﳵ�ʹ���һ��
			 cart = new Cart();
		}
		//���Ͼ��õ����ﳵ�������ǽ����ﳵ���װ�����ﳵ���������map������
		Map<Integer, CartItem> cartMap = cart.getCartMap();//��һ������string�洢����Ʒpid,��ʱ�������ﳵɾ����Ʒ��Ψһʶ��
		//�����ﳵ��洢�����ﳵ����ļ�������,key���Ǹ���Ʒ��pid
		//ע���ˣ��ٽ���Ʒ�洢�����ﳵʱ��Ӧ���жϹ��ﳵ�����Ƿ��Ѿ���ӹ�����Ʒ�������ֱ�Ӽӻ���ͬ��key����ɸ���ԭ����Ʒ��
		//����Ӧ���ж�һ��,��һ�´����������Ʒ������С�ƴ��¼��㣬�������Ǹ���,�ٸ���ǰ��ĳЩ���ݴ��¼���һ��
		//hashmap��������ж��Ƿ�����ͬkey����public boolean containsKey(Object key)
		if(cartMap.containsKey(pid))
		{
			//�����true����map���������и���Ʒ����ʱ��Ҫ������Ʒ���������㻹��С�����������ݷ����仯
			//1.��ȡԭ�����ﳵ������Ʒ������,ͨ��key������Ʒ��pid��ȡ�ü����������Ʒ�Ĺ��ﳵ�����
			CartItem oldcartItem = cartMap.get(pid);
			//�õ��ɵ���Ʒ������
			int oldBuyNum = oldcartItem.getBuyNum();
			//����õ���Ϣ����Ʒ������,buyNumΪ���������Ʒ��������oldBuyNumΪ���ﳵ�洢����Ʒ��������newBuyNum�¼������������Ʒ����
			int newBuyNum=oldBuyNum+buyNum;
			//���¼�������Ʒ�������ٴη�װ�����ﳵ��
			cartItem.setBuyNum(newBuyNum);
			//2.����С��,��ȡ����Ʒ��С��oldSubTotal������������Ʒ��С�ƣ��õ��ܸ���Ʒ��С��
			double oldSubTotal = oldcartItem.getSubTotal();
			double newSubTotal=oldSubTotal+subTotal;
			//����Ʒ��С�ƴ洢��������
			cartItem.setSubTotal(newSubTotal);
			
		}
		
		//���㹺�ﳵ������Ʒ���ܼ�Ǯ,��ȡ���ﳵ����Ʒ�ܼ۸���������Ʒ��С��
		double total=cart.getTotal()+subTotal;
		//���۸�洢�����ﳵ��total������
		cart.setTotal(total);
		
		cartMap.put(pid, cartItem);
		//�ٽ����ﳵ�洢��session����
		session.setAttribute("cart", cart);
		

		return true;
	}
	
	
	
	/**
	 * ɾ���������ﳵ��
	 */
	@RequestMapping("/deleteCartItem")
	public String deleteCartItem(int pid,HttpServletRequest request)
	{
		/*˼·��ȡsession�й��ﳵ��ֱ�Ӹ���pid����map�����е�keyͨ����������ɾ����
		 * �����޸�һ��������Ʒ���ܼ۸�
		 * �ٽ����ﳵ����洢��session��*/
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart!=null)
		{
			//��ȡ���ﳵ��map���ϣ�����洢�ǹ��������
			Map<Integer, CartItem> cartMap = cart.getCartMap();
			
			//�����޸�������Ʒ���ܼ۸񣬹��ﳵ�ܼ۸�,����ԭ�м۸�-ɾ������Ʒ��С�ƣ�ɾ��֮ǰ���޸��ܼ۸�
			cart.setTotal(cart.getTotal()-cartMap.get(pid).getSubTotal());
			
			//����map�д��������keyɾ��
			cartMap.remove(pid);
			//��ɾ����ļ�������Ϊ���ﳵmap���Ե�ֵ
			cart.setCartMap(cartMap);
			
		}
		
		//��ɾ����Ʒ��Ĺ��ﳵ��ӵ�session��
		session.setAttribute("cart", cart);
		//�ض��򵽹��ﳵҳ��
		return "redirect:cart";
	}
	
	//ɾ��ѡ�е���Ʒ�����Ե�ѡ���ѡ��ȫѡ��
	@RequestMapping("/deleteCheckedProduct")
	public String deleteCheckedProduct(String strPid,HttpServletRequest request)
	{
		//���ַ������ն��Ž��зָ�,�õ����е�pid�洢���ַ�������
		String[] arrpid = strPid.split(",");
		//��ȡsession����session�л�ȡ���ﳵ
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		//������ﳵ��Ϊ�ղ����ַ�������洢���ݲ�Ϊ��
		if(cart!=null && arrpid.length>0)
		{
			//��ȡ���ﳵ�е�map����
			Map<Integer, CartItem> cartMap = cart.getCartMap();
			//��������
			for(int i=0;i<arrpid.length;i++)
			{
				//��ȡ��Ʒ��pid,���ַ���ת��������int����
				int pid=Integer.parseInt(arrpid[i]);
				//����Ʒɾ��֮ǰ�޸���Ʒ���ܼ۸�
				cart.setTotal(cart.getTotal()-cartMap.get(pid).getSubTotal());
				//�ȵ�ÿ����Ʒ��pid=>arrpid[i],���ݻ�ȡ��Ʒ��pid��key��ɾ��cartItem����(value)
				cartMap.remove(pid);	
			}
			
			//���޸ĺ��map���϶����ٴ���ӵ����ﳵcart������
			cart.setCartMap(cartMap);
			
		}
		
		//�����ﳵ����洢��session����
		session.setAttribute("cart", cart);
		//�ض��򵽹��ﳵҳ��
		return "redirect:cart";
	}
	
	
	/**
	 * ��ʾ���ﳵҳ��
	 */
	@RequestMapping("/cart")
	public String showCart()
	{
		return "cart";
	}
	

}
