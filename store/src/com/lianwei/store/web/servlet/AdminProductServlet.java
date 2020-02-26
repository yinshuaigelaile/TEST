package com.lianwei.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.lianwei.store.domain.Category;
import com.lianwei.store.domain.PageModel;
import com.lianwei.store.domain.Product;
import com.lianwei.store.service.AdminCategoryService;
import com.lianwei.store.service.AdminProductService;
import com.lianwei.store.service.serviceImpl.AdminCategoryServiceImpl;
import com.lianwei.store.service.serviceImpl.AdminProductServiceImpl;
import com.lianwei.store.utils.MyBeanUtils;
import com.lianwei.store.utils.UUIDUtils;
import com.lianwei.store.utils.UploadUtils;
import com.lianwei.store.web.base.BaseServlet;


@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {

	public String findAllProducts(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		int num = Integer.parseInt(req.getParameter("num"));
		AdminProductService adminProductService = new AdminProductServiceImpl();
		PageModel page = adminProductService.findAllProducts(num);
		req.setAttribute("page", page);
		return "/admin/product/list.jsp";
	}
	
	public String findAllPushDownProducts(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		int num = Integer.parseInt(req.getParameter("num"));
		AdminProductService adminProductService = new AdminProductServiceImpl();
		PageModel page = adminProductService.findAllPushDownProducts(num);
		req.setAttribute("page", page);
		return "/admin/product/pushDown_list.jsp";
	}
	/**
	 * @method addProductUI
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addProductUI(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		//创建AdminCategoryService
		AdminCategoryService adminCategoryService = new AdminCategoryServiceImpl();
		//查询出所有的分类
		List<Category> list = adminCategoryService.findAllCats();
		//将list保存到req中
		req.setAttribute("list", list);
		return "/admin/product/add.jsp";
	}
	/**
	 * @method addProduct
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addProduct(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		try {
			//简单三行语句
			DiskFileItemFactory disk = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(disk);
			List<FileItem> fileItems = upload.parseRequest(req);
			for (FileItem fileItem : fileItems) {
				//判断是上传项还是普通项 是普通项按普通项进行处理  
				if (fileItem.isFormField()) {
					map.put(fileItem.getFieldName(),fileItem.getString("utf-8"));
				}else {
					//如果是上传项
					//获取文件名称   123333.doc
					String fileName = fileItem.getName();
					//获取到文件流
					InputStream is = fileItem.getInputStream();
					//需要将获取的文件名称通过一个函数转换为用uuid来表示 保证其不重复   fddd566ffftt.jpg
					String newFileName = UploadUtils.getUUIDName(fileName);
					//获取需要上传文件到存储的一个位置的文件名称创建八层目录   products/3/
					String dir = getServletContext().getRealPath("/products/3/");   //D:ff/fgg/products/3
					String newDir = UploadUtils.getDir(fileName);   // a/7/8/b/8/9/6
					String path = dir+newDir;
					//内存中声明一个目录
					File file = new File(path);
					//创建目录
					if (!file.exists()) {
						file.mkdirs();
					}
					//再该目录下创建对应的一个文件
					File finalFile = new File(file, newFileName);
					if (!finalFile.exists()) {
						finalFile.createNewFile();
					}
			
					//新建文件 新建输出流   将文件输入流输出到输出流中  关闭流
					OutputStream os = new FileOutputStream(finalFile);
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					//将对应的文件路径保存到map中
					map.put("pimage", "products/3/"+newDir+"/"+newFileName);
				}
				
			}
			
			//将map作为参数传入product中
			Product product = new Product();
			BeanUtils.populate(product, map);
			product.setPdate(new Date());
			product.setPid(UUIDUtils.getCode());
			product.setPflag(0);
			AdminProductService adminProductService = new AdminProductServiceImpl();
			adminProductService.addProduct(product);
			//重定向到查询页面
			resp.sendRedirect("/store/AdminProductServlet?method=findAllProducts&num=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
