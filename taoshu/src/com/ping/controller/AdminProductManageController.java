package com.ping.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.Category;
import com.ping.pojo.Product;
import com.ping.service.AdminProductManageService;

/**
 * ��̨��Ʒ��������������Ʒ���༭��Ʒ��ɾ����Ʒ����ѯ��Ʒ���ϼܡ��¼�
 * @author admin
 *
 */
@Controller
public class AdminProductManageController {

	@Autowired
	private AdminProductManageService adminProductManageService;
	/**
	 * ��ѯ��Ʒ�б�ͨ��������Ʒ����ģ����ѯ����ҳ��ʾ��Ʒ��Ϣ
	 */
	//ͨ����Ʒ����ģ����ѯ������Ʒ��Ϣ,���ҽ��з�ҳ��ʾ�����û�������ѯ������Ʒ��Ϣ���������Ա��������Ʒ�б�
	@RequestMapping("/selectProductListInfo")
	@ResponseBody   //��AdminPageBean��ҳ����ת����json��ʽ���ݷ���ҳ�棬easyUI���ݱ���Զ��������
	//pageΪeasyUIǰ̨�ύ�ĵ�ǰҳ��rowsΪÿҳ��ʾ����������������easyUI��̶ܹ�
	public AdminPageBean  getSelectProductListInfo(int page,int rows,String pname)
	{
		
		int currentPage=page;//��ȡǰ̨�ĵ�ǰҳ
		int pageSize=rows;//ÿҳ��ʾ������
		//�������ݴ��䵽service��,��÷�װ�õ���Ʒ�б��ҳ����
		AdminPageBean pageBean=adminProductManageService.getProductListPageBean(currentPage,pageSize,pname);
		return pageBean;
	}
	
	
	/**
	 * ����Ա�����Ʒʱ��Ҫ����ҳ����Ʒ������Ϣ���ԣ����û�ѡ��
	 */
	@RequestMapping("/selectAllCategoryInfo")
	@ResponseBody
	public List<Category> selectAllCategoryInfo()
	{
		//��ѯ������Ʒ�ķ�����Ϣ��ת����json��ʽ���ظ�ǰ̨easyUI�����б���л��ԣ����û�ѡ��
		List<Category> categoryList=adminProductManageService.selectAllCategoryInfo();
		//�������з�����Ϣ
		return categoryList;
	}
	
	/**
	 * ����Ա�����Ʒ��Ϣ,���ϴ�ͼƬ���棬Ȼ���װ������Ʒ��Ϣ��������Ʒ��Ϣ�������ݿ�
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/addProductInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody  //����springmvc��ͼ������������json���ݶ�����ҳ��
	public String getAddProductInfo(@RequestParam("file") MultipartFile file,HttpServletRequest request,Product product,int cid)
	{
		/**
		 * �����Ʒ���޷ǽ������Ʒ�����ݻ�ȡ��װ���ʹ������ϴ�ͼƬ����װ��Ʒ����������Ϣ��Ȼ��������ݿ�
		 */
		//�����Ʒ����Ҫ�ǽ���Ʒ�Ķ����������ݷ�װ�ã���Ҫ�����ϴ�ͼƬ,��ͼƬ���浽��������Ŀ�е��ļ�Ŀ¼��
		//��product����Ҫ�����Ʒ��Ϣ��װ����,product��11�����ԣ������Զ����ɾ�ʣ4��������Ҫ�����ֶ���װ
	
		//�ϴ�ͼƬ�������ǿ��ж�
		if (!file.isEmpty()) 
		{
			//1.�����ļ��������·����Ϊ��ǰ����Ŀ¼�µ�../../img/uploadpicture
	        ServletContext sc = request.getSession().getServletContext();
	        String realPath = sc.getRealPath("/img/uploadpicture");
	        //2.�ļ�ԭʼ����,ͨ��Դ�ļ����������ļ���
	        String fileName=file.getOriginalFilename();
	        //3.�µ�ͼƬ����(ʹ��UUID������ɲ���ͬ��Ϊ�´洢ͼƬ�����ƣ���ֹ�ϴ�ͼƬ������ͬ���Ƹ������)
	        //ͼƬ����=UUID�������+ͼƬ��չ��(����.jpg)  ����fileName=aa.jpgȡ�ļ����е�.jpg�������һ��.��ʼȡ���ַ���
	        String newFileName=UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
	        //4.�����ļ��������洢��ȡ�ϴ��ļ�������
	        File newfile=new File(realPath,newFileName);
	        //5.�жϴ洢Ŀ¼�Ƿ���ڣ���������ھʹ���
	        if(!newfile.getParentFile().exists())
	        {
	        	newfile.getParentFile().mkdirs();
	        }
	        //6.���ϴ��ļ����浽newfile�ļ�����
	        try {
				file.transferTo(newfile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				return "{'success':false,'addProductInfo':'�ϴ�ͼƬʧ��'}";
			}
	        
	        //���ϴ�����ϴ�ͼƬ����������װ������Ʒproduct���󣬳���ǰ̨�ύ���ݣ����������Զ����ɣ���ʣ4��������Ҫ�Լ��ֶ���װ
	        //1.��Ʒ���ڣ�private String pdate;ʹ�õ�ǰ������Ϊ��Ʒ���� 
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String pdate = dateFormat.format(new Date());
			product.setPdate(pdate);
			//2.private Integer pflag; ��Ʒ����Ƿ����¼ܣ�0�����¼ܣ�1�����ϼܣ������ƷĬ�����ϼ�����Ϊ1
			product.setPflag(1);
			//3.private Category category;    //���������ʹ������������д洢
			Category category=new Category(); //�����������
			category.setCid(cid);//��cid�洢��������
			product.setCategory(category); //�ٽ��������洢����Ʒ��������
			//4.private String pimage;        //��ƷͼƬ��������Ҫ��װ�ϴ�ͼƬ�ڷ��������·������ img/uploadpicture/aa.jpg
	        product.setPimage("img/uploadpicture/"+newFileName);
	        
	        //���Ϸ�װ�������Ʒ���󣬲��Ҵ������ϴ�ͼƬ����product���䵽service�㣬�������ݿ�,
	        //ͨ��insert���ݿ�mybatis�᷵��һ��int����ֵ�������ֵΪ1>0��ʾ����ɹ�������ʧ��
	        int row=adminProductManageService.addProductInfo(product);
	        if(row>0)
	        {
	        	//����json��ʽ�����ַ�������ʾ�û������Ʒ�ɹ�
	        	return "{'success':true,'addProductInfo':'��ӳɹ�!'}";	
	        }else
	        {
	        	return "{'success':false,'addProductInfo':'���ʧ��!'}";	
	        }
	        
		}else
		{
			return "{'success':false,'addProductInfo':'�ϴ�ͼƬ����Ϊ��!'}";
		}
			
	}
	
	
	/**
	 * ����Ա�༭��Ʒ��ͨ��ǰ̨�����pid��ѯ����Ʒ��Ϣ�����ҷ���json����ǰ̨��������
	 */
	@RequestMapping("/selectProductInfo")
	@ResponseBody   //��product����ת����json��ʽ�Ķ���
	public Product selectProductInfoByPid(int pid)
	{
		//ͨ��pid��ѯ����Ʒ����Ϣ
		Product product=adminProductManageService.selectProductInfoByPid(pid);
		//���ط������ͨ��ע��@ResponseBodyת����json��ʽ�ַ�������ǰ̨
		return product;
	}
	
	/**
	 * �༭��Ʒ������Ʒ��Ϣ�����ݿ�
	 * produces={"text/html;charset=UTF-8;","application/json;"}��ֹ���ص�json���ĳ�������
	 */
	@RequestMapping(value="/editProductInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditProductInfo(@RequestParam("file") MultipartFile file,HttpServletRequest request,Product product,int cid)
	{
		//�༭��Ʒ��Ϣ�����ж��û��Ƿ�ѡ���ϴ���Ƭ�����û�оͲ�������Ƭ���оͷ�װ����,��Ҫ��װ�����������ԣ����������Ʒ����һ��������
		//��Ʒһ��11�����ԣ�ǰ̨�ύ8�����Ե�ֵ������4��������Ҫ�Լ��ֶ���װ�ֱ�Ϊpdate,pflag,cid,pimage(Ҫ��ǰ̨�Ƿ��ϴ�ͼƬ���оͷ�װû�оͲ���)
		//�ϴ�ͼƬ�������ǿ��ж�
		if (!file.isEmpty()) 
		{   //����˵���ϴ�ͼƬ��Ҫ�ȱ���ͼƬ��Ȼ���װproduct��pimage����
			//1.�����ļ��������·����Ϊ��ǰ����Ŀ¼�µ�../../img/uploadpicture
	        ServletContext sc = request.getSession().getServletContext();
	        String realPath = sc.getRealPath("/img/uploadpicture");
	        //2.�ļ�ԭʼ����,ͨ��Դ�ļ����������ļ���
	        String fileName=file.getOriginalFilename();
	        //3.�µ�ͼƬ����(ʹ��UUID������ɲ���ͬ��Ϊ�´洢ͼƬ�����ƣ���ֹ�ϴ�ͼƬ������ͬ���Ƹ������)
	        //ͼƬ����=UUID�������+ͼƬ��չ��(����.jpg)  ����fileName=aa.jpgȡ�ļ����е�.jpg�������һ��.��ʼȡ���ַ���
	        String newFileName=UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
	        //4.�����ļ��������洢��ȡ�ϴ��ļ�������
	        File newfile=new File(realPath,newFileName);
	        //5.�жϴ洢Ŀ¼�Ƿ���ڣ���������ھʹ���
	        if(!newfile.getParentFile().exists())
	        {
	        	newfile.getParentFile().mkdirs();
	        }
	        //6.���ϴ��ļ����浽newfile�ļ�����
	        try {
				file.transferTo(newfile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				return "{'success':false,'addProductInfo':'�ϴ�ͼƬʧ��'}";
			}
	     
	        //���汣�����ƷͼƬ����Ҫ��ͼƬ·����װ��product����pimage��
	       //4.private String pimage;        //��ƷͼƬ��������Ҫ��װ�ϴ�ͼƬ�ڷ��������·������ img/uploadpicture/aa.jpg
	       product.setPimage("img/uploadpicture/"+newFileName);
	        
		}
		//�ֶ���װproduct��pdate,pflag,cid����
		//1.��Ʒ���ڣ�private String pdate;ʹ�õ�ǰ������Ϊ��Ʒ���� 
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String pdate = dateFormat.format(new Date());
		product.setPdate(pdate);
		//2.private Integer pflag; ��Ʒ����Ƿ����¼ܣ�0�����¼ܣ�1�����ϼܣ������ƷĬ�����ϼ�����Ϊ1
		product.setPflag(1);
		//3.private Category category;    //���������ʹ������������д洢
		Category category=new Category(); //�����������
		category.setCid(cid);//��cid�洢��������
		product.setCategory(category); //�ٽ��������洢����Ʒ��������
        //���Ϸ�װ�������Ʒ���󣬲��Ҵ������ϴ�ͼƬ����product���䵽service�㣬�������ݿ�,
        //ͨ��insert���ݿ�mybatis�᷵��һ��int����ֵ�������ֵΪ1>0��ʾ����ɹ�������ʧ��
        int row=adminProductManageService.editProductInfo(product);
        if(row>0)
        {
        	//����json��ʽ�����ַ�������ʾ�û��޸���Ʒ�ɹ�
        	return "{'success':true,'editProductInfo':'�޸ĳɹ�!'}";	
        }else
        {
        	return "{'success':false,'editProductInfo':'�޸�ʧ��!'}";	
        }
        			
	}
	
	/**
	 * ɾ����Ʒ��Ϣ����ѡ�е���Ʒ�����ݿ���ɾ��
	 */
	@RequestMapping(value="/deleteProductInfo")
	@ResponseBody
	public String getDeleteProductInfo(String strPid)
	{
		//���ַ������ն��Ž��зָ�,�ȵ�һ��pid����
		String[] arrayPid = strPid.split(",");
		//�����鴫�䵽service��
		int row=adminProductManageService.deleteProductInfo(arrayPid);
        //���row>0��ʾɾ���ɹ�������ʧ��
		if(row>0)
		{   //��дjson��ʽ�Ķ���jQuery����ʶ������,���ֶ��˫����ʹ��ת���ַ�\�������easyUI���ajax�ص��Ͳ��������ӣ�����ʶ������
			return "{\"success\":true,\"deleteProductInfo\":\"ɾ���ɹ���\"}";
		}else
		{
			return "{\"success\":false,\"deleteProductInfo\":\"ɾ��ʧ�ܣ�\"}";
		}
		
	}
	
	/**
	 * ��Ʒ�ϼܣ�ͨ����Ʒ��pid������Ʒ��Ϣ����pflag=1��ʾ��Ʒ�ϼ�
	 */
	@RequestMapping("/publishProductInfo")
	@ResponseBody
	public String getPublishProductInfo(String strPid)
	{
		//���ַ������ն��Ž��зָ�,�ȵ�һ��pid����
	    String[] arrayPid = strPid.split(",");
	    //�����鴫�䵽service��
	  	int row=adminProductManageService.updatePublishProductInfoByPid(arrayPid);
	    //���row>0��ʾɾ���ɹ�������ʧ��
  		if(row>0)
  		{   //��дjson��ʽ�Ķ���jQuery����ʶ������,���ֶ��˫����ʹ��ת���ַ�\�������easyUI���ajax�ص��Ͳ��������ӣ�����ʶ������
  			return "{\"success\":true,\"publishProductInfo\":\"�ϼܳɹ���\"}";
  		}else
  		{
  			return "{\"success\":false,\"publishProductInfo\":\"�ϼ�ʧ�ܣ�\"}";
  		}
	}
	
	/**
	 * ��Ʒ�¼ܣ�ͨ����Ʒ��pid������Ʒ��Ϣ����pflag=0��ʾ��Ʒ�¼�
	 */
	@RequestMapping("/unpublishProductInfo")
	@ResponseBody
	public String getUnpublishProductInfo(String strPid)
	{
		//���ַ������ն��Ž��зָ�,�ȵ�һ��pid����
	    String[] arrayPid = strPid.split(",");
	    //�����鴫�䵽service��
	  	int row=adminProductManageService.updateUnpublishProductInfoByPid(arrayPid);
	    //���row>0��ʾ��Ʒ�¼ܳɹ�������ʧ��
  		if(row>0)
  		{   //��дjson��ʽ�Ķ���jQuery����ʶ������,���ֶ��˫����ʹ��ת���ַ�\�������easyUI���ajax�ص��Ͳ��������ӣ�����ʶ������
  			return "{\"success\":true,\"unpublishProductInfo\":\"�¼ܳɹ���\"}";
  		}else
  		{
  			return "{\"success\":false,\"unpublishProductInfo\":\"�¼�ʧ�ܣ�\"}";
  		}
	}
	
}
