package com.ping.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Product;
import com.ping.service.ProductService;

/**
 * ��Ʒ�йص�controller��
 * @author admin
 *
 */
@Controller
public class ProductController {

	//ͨ��ע�ⷽʽ�Զ��������ͽ�service�����ע�뵽�ó�Ա����
	@Autowired
	private ProductService productService;
	
	//������Ʒ��pid��ѯ������Ʒ����Ϣ
	@RequestMapping("/productInfo")
	public String selectProductInfo(@RequestParam int pid,Model model)
	{

		//������Ʒid��ѯ��Ʒ��Ϣ
		Product productInfo = productService.selectProductInfoById(pid);
		//�����ݴ洢��model�У��Զ���洢��request��
		model.addAttribute("productInfo", productInfo);
		//ת����product_info.jspҳ�������ʾ
		return "product_info";
	}
	
	
	//��ʾ��Ʒ���б���Ϣ���ҷ�ҳ��ʾ
	@RequestMapping("/productListInfo")
	public String selectProductListInfo(@RequestParam int cid,@RequestParam(defaultValue="1") int currentPage,Model model)
	{
		//������Ʒ����ѯ��Ʒ��Ϣ���ҷ�ҳ��ʾ
		//int currentPage=1;//��ǰҳ
		int currentCount=12;//ÿҳ��ʾ������
		//����service�㣬���ж�ǰ̨��Ҫ�����ݽ��в�ѯ�ͷ�װ
		PageBean pageBean=productService.selectProductListByCid(cid,currentPage,currentCount);
		//����ҳ��Ҫ��ʾ�����ݴ洢��model���Զ��浽request����
		model.addAttribute("pageBean", pageBean);
		//����Ʒ����cid�洢��request����
		model.addAttribute("cid", cid);
        //ת������Ʒ�б���Ϣ��ʾ
		return "product_list";
	}
	
	/**
	 * վ������
	 */
	
    //����ǰ̨ajax����������ı�������ֵ��Ϊ��Ʒ���ƣ�����ģ����ѯ���ݿ⣬��ȡ��Ӧ��Ʒ�����Ʒ���
	@RequestMapping("/getProductNameByInput")
	@ResponseBody   //ͨ����ע����Զ������صĶ�����������ת����json��ʽ���ַ���
	public List<Product> getProductNameListByInput(String inputvalue)
	{
		//ͨ�������ֵ��Ϊ��Ʒ�����ƽ���ģ����ѯ���ݿ�,�����ݴ��䵽service���в�ѯ
		List<Product> productNameList=productService.getProductNameListByInput(inputvalue);
		//����һ�����϶���ͨ��ע���Զ�ת����json��ʽ�ַ��������ص�ǰ̨
		return productNameList;
	}
	
	//���û����������ť����ȡ�û��������Ϣ���������ݿ���Ʒ�����Ƶ�ģ����ҳ��ѯ������list��������
	@RequestMapping("/selectProductInfoListByInput")
	public String selectProductInfoListByInput(String searchContent,@RequestParam(defaultValue="1") int currentPage,Model model )
	{
		
		//���һЩget�ύ���������  UTF-8����->UTF-8(iso-8859-1)����->iso-8859-1����->UTF-8����
		String searchContent1=null;
		try {
			searchContent1 = new String(searchContent.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int currentCount=12;//ÿҳ��ʾ������
		//��ȡǰ̨�ύ�����������ݣ�����service�㷽������ģ����ҳ��ѯ,���ط�װ�õķ�ҳ����
		PageBean pageBeanProduct=productService.selectProductInfoListByInput(searchContent1,currentPage,currentCount);
		//����ҳ����洢��model������Զ��洢��request���У�springmvc����ŵ�
		model.addAttribute("pageBeanProduct",pageBeanProduct );
		//ͬʱ��Ҫ�洢������Ʒ����Ϣ������ҳ�浽ʱ���ҳ���ĳһ��ҳ�棬��Ҫ�������������Ϣ����ģ����ѯ
		model.addAttribute("searchContent", searchContent1);
		//ת������ѯ��Ʒ�б�ҳ�������ʾ
		return "search_productList";
	}
	
}
