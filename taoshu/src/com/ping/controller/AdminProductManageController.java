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
 * 后台商品管理，包括新增商品，编辑商品，删除商品，查询商品，上架、下架
 * @author admin
 *
 */
@Controller
public class AdminProductManageController {

	@Autowired
	private AdminProductManageService adminProductManageService;
	/**
	 * 查询商品列表，通过输入商品名称模糊查询并分页显示商品信息
	 */
	//通过商品名称模糊查询所有商品信息,并且进行分页显示（如果没有输入查询所有商品信息，例如管理员点击左侧商品列表）
	@RequestMapping("/selectProductListInfo")
	@ResponseBody   //将AdminPageBean分页对象转换成json格式数据返回页面，easyUI数据表格自动填充数据
	//page为easyUI前台提交的当前页，rows为每页显示的条数，变量名是easyUI框架固定
	public AdminPageBean  getSelectProductListInfo(int page,int rows,String pname)
	{
		
		int currentPage=page;//获取前台的当前页
		int pageSize=rows;//每页显示的条数
		//将该数据传输到service层,获得封装好的商品列表分页对象
		AdminPageBean pageBean=adminProductManageService.getProductListPageBean(currentPage,pageSize,pname);
		return pageBean;
	}
	
	
	/**
	 * 管理员添加商品时候，要进行页面商品分类信息回显，供用户选择
	 */
	@RequestMapping("/selectAllCategoryInfo")
	@ResponseBody
	public List<Category> selectAllCategoryInfo()
	{
		//查询所有商品的分类信息，转换成json格式返回给前台easyUI下拉列表进行回显，供用户选择
		List<Category> categoryList=adminProductManageService.selectAllCategoryInfo();
		//返回所有分类信息
		return categoryList;
	}
	
	/**
	 * 管理员添加商品信息,将上传图片保存，然后封装完整商品信息，将该商品信息插入数据库
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/addProductInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody  //告诉springmvc视图解析器返回是json数据而不是页面
	public String getAddProductInfo(@RequestParam("file") MultipartFile file,HttpServletRequest request,Product product,int cid)
	{
		/**
		 * 添加商品，无非将添加商品的数据获取封装，和处理保存上传图片，封装商品对象完整信息，然后插入数据库
		 */
		//添加商品，主要是将商品的对象属性数据封装好，还要处理上传图片,将图片保存到服务器项目中的文件目录下
		//将product对象将要添加商品信息封装完整,product中11个属性，主键自动生成就剩4个属性需要我们手动封装
	
		//上传图片，先做非空判断
		if (!file.isEmpty()) 
		{
			//1.设置文件存放物理路径，为当前工程目录下的../../img/uploadpicture
	        ServletContext sc = request.getSession().getServletContext();
	        String realPath = sc.getRealPath("/img/uploadpicture");
	        //2.文件原始名称,通过源文件名创建新文件名
	        String fileName=file.getOriginalFilename();
	        //3.新的图片名称(使用UUID随机生成不相同作为新存储图片的名称，防止上传图片出现相同名称覆盖情况)
	        //图片名称=UUID生成随机+图片扩展名(例如.jpg)  例如fileName=aa.jpg取文件名中的.jpg，从最后一个.开始取子字符串
	        String newFileName=UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
	        //4.创建文件对象来存储获取上传文件的内容
	        File newfile=new File(realPath,newFileName);
	        //5.判断存储目录是否存在，如果不存在就创建
	        if(!newfile.getParentFile().exists())
	        {
	        	newfile.getParentFile().mkdirs();
	        }
	        //6.将上传文件保存到newfile文件里面
	        try {
				file.transferTo(newfile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				return "{'success':false,'addProductInfo':'上传图片失败'}";
			}
	        
	        //以上处理好上传图片，接下来封装完整商品product对象，除了前台提交数据，数据主键自动生成，就剩4个属性需要自己手动封装
	        //1.商品日期，private String pdate;使用当前日期作为商品日期 
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String pdate = dateFormat.format(new Date());
			product.setPdate(pdate);
			//2.private Integer pflag; 商品标记是否上下架，0代表下架，1代表上架，添加商品默认是上架设置为1
			product.setPflag(1);
			//3.private Category category;    //这是外键，使用外键表对象进行存储
			Category category=new Category(); //创建分类对象
			category.setCid(cid);//将cid存储到对象中
			product.setCategory(category); //再将分类对象存储到商品对象属性
			//4.private String pimage;        //商品图片，这里需要封装上传图片在服务器相对路径例如 img/uploadpicture/aa.jpg
	        product.setPimage("img/uploadpicture/"+newFileName);
	        
	        //以上封装好添加商品对象，并且处理保存上传图片，将product传输到service层，插入数据库,
	        //通过insert数据库mybatis会返回一个int类型值，如果该值为1>0表示插入成功，否则失败
	        int row=adminProductManageService.addProductInfo(product);
	        if(row>0)
	        {
	        	//返回json格式对象字符串，提示用户添加商品成功
	        	return "{'success':true,'addProductInfo':'添加成功!'}";	
	        }else
	        {
	        	return "{'success':false,'addProductInfo':'添加失败!'}";	
	        }
	        
		}else
		{
			return "{'success':false,'addProductInfo':'上传图片不能为空!'}";
		}
			
	}
	
	
	/**
	 * 管理员编辑商品，通过前台传输的pid查询该商品信息，并且返回json对象供前台回显数据
	 */
	@RequestMapping("/selectProductInfo")
	@ResponseBody   //将product对象转换成json格式的对象
	public Product selectProductInfoByPid(int pid)
	{
		//通过pid查询该商品的信息
		Product product=adminProductManageService.selectProductInfoByPid(pid);
		//返回分类对象，通过注解@ResponseBody转换成json格式字符串传到前台
		return product;
	}
	
	/**
	 * 编辑商品，将商品信息更数据库
	 * produces={"text/html;charset=UTF-8;","application/json;"}防止返回的json中文出现乱码
	 */
	@RequestMapping(value="/editProductInfo",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getEditProductInfo(@RequestParam("file") MultipartFile file,HttpServletRequest request,Product product,int cid)
	{
		//编辑商品信息，先判断用户是否选择上传照片，如果没有就不更新照片，有就封装更新,还要封装完整其他属性，（和添加商品基本一样做法）
		//商品一个11个属性，前台提交8个属性的值，还有4个属性需要自己手动封装分别为pdate,pflag,cid,pimage(要看前台是否上传图片，有就封装没有就不用)
		//上传图片，先做非空判断
		if (!file.isEmpty()) 
		{   //这里说明上传图片，要先保存图片，然后封装product的pimage属性
			//1.设置文件存放物理路径，为当前工程目录下的../../img/uploadpicture
	        ServletContext sc = request.getSession().getServletContext();
	        String realPath = sc.getRealPath("/img/uploadpicture");
	        //2.文件原始名称,通过源文件名创建新文件名
	        String fileName=file.getOriginalFilename();
	        //3.新的图片名称(使用UUID随机生成不相同作为新存储图片的名称，防止上传图片出现相同名称覆盖情况)
	        //图片名称=UUID生成随机+图片扩展名(例如.jpg)  例如fileName=aa.jpg取文件名中的.jpg，从最后一个.开始取子字符串
	        String newFileName=UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
	        //4.创建文件对象来存储获取上传文件的内容
	        File newfile=new File(realPath,newFileName);
	        //5.判断存储目录是否存在，如果不存在就创建
	        if(!newfile.getParentFile().exists())
	        {
	        	newfile.getParentFile().mkdirs();
	        }
	        //6.将上传文件保存到newfile文件里面
	        try {
				file.transferTo(newfile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				return "{'success':false,'addProductInfo':'上传图片失败'}";
			}
	     
	        //上面保存好商品图片，还要将图片路径封装到product属性pimage中
	       //4.private String pimage;        //商品图片，这里需要封装上传图片在服务器相对路径例如 img/uploadpicture/aa.jpg
	       product.setPimage("img/uploadpicture/"+newFileName);
	        
		}
		//手动封装product的pdate,pflag,cid属性
		//1.商品日期，private String pdate;使用当前日期作为商品日期 
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String pdate = dateFormat.format(new Date());
		product.setPdate(pdate);
		//2.private Integer pflag; 商品标记是否上下架，0代表下架，1代表上架，添加商品默认是上架设置为1
		product.setPflag(1);
		//3.private Category category;    //这是外键，使用外键表对象进行存储
		Category category=new Category(); //创建分类对象
		category.setCid(cid);//将cid存储到对象中
		product.setCategory(category); //再将分类对象存储到商品对象属性
        //以上封装好添加商品对象，并且处理保存上传图片，将product传输到service层，插入数据库,
        //通过insert数据库mybatis会返回一个int类型值，如果该值为1>0表示插入成功，否则失败
        int row=adminProductManageService.editProductInfo(product);
        if(row>0)
        {
        	//返回json格式对象字符串，提示用户修改商品成功
        	return "{'success':true,'editProductInfo':'修改成功!'}";	
        }else
        {
        	return "{'success':false,'editProductInfo':'修改失败!'}";	
        }
        			
	}
	
	/**
	 * 删除商品信息，将选中的商品从数据库中删除
	 */
	@RequestMapping(value="/deleteProductInfo")
	@ResponseBody
	public String getDeleteProductInfo(String strPid)
	{
		//将字符串按照逗号进行分割,等到一个pid数组
		String[] arrayPid = strPid.split(",");
		//将数组传输到service层
		int row=adminProductManageService.deleteProductInfo(arrayPid);
        //如果row>0表示删除成功，否则失败
		if(row>0)
		{   //书写json格式的对象jQuery不认识单引号,出现多个双引号使用转义字符\，如果是easyUI框架ajax回调就不用这样子，它能识别单引号
			return "{\"success\":true,\"deleteProductInfo\":\"删除成功！\"}";
		}else
		{
			return "{\"success\":false,\"deleteProductInfo\":\"删除失败！\"}";
		}
		
	}
	
	/**
	 * 商品上架，通过商品的pid更新商品信息设置pflag=1表示商品上架
	 */
	@RequestMapping("/publishProductInfo")
	@ResponseBody
	public String getPublishProductInfo(String strPid)
	{
		//将字符串按照逗号进行分割,等到一个pid数组
	    String[] arrayPid = strPid.split(",");
	    //将数组传输到service层
	  	int row=adminProductManageService.updatePublishProductInfoByPid(arrayPid);
	    //如果row>0表示删除成功，否则失败
  		if(row>0)
  		{   //书写json格式的对象jQuery不认识单引号,出现多个双引号使用转义字符\，如果是easyUI框架ajax回调就不用这样子，它能识别单引号
  			return "{\"success\":true,\"publishProductInfo\":\"上架成功！\"}";
  		}else
  		{
  			return "{\"success\":false,\"publishProductInfo\":\"上架失败！\"}";
  		}
	}
	
	/**
	 * 商品下架，通过商品的pid更新商品信息设置pflag=0表示商品下架
	 */
	@RequestMapping("/unpublishProductInfo")
	@ResponseBody
	public String getUnpublishProductInfo(String strPid)
	{
		//将字符串按照逗号进行分割,等到一个pid数组
	    String[] arrayPid = strPid.split(",");
	    //将数组传输到service层
	  	int row=adminProductManageService.updateUnpublishProductInfoByPid(arrayPid);
	    //如果row>0表示商品下架成功，否则失败
  		if(row>0)
  		{   //书写json格式的对象jQuery不认识单引号,出现多个双引号使用转义字符\，如果是easyUI框架ajax回调就不用这样子，它能识别单引号
  			return "{\"success\":true,\"unpublishProductInfo\":\"下架成功！\"}";
  		}else
  		{
  			return "{\"success\":false,\"unpublishProductInfo\":\"下架失败！\"}";
  		}
	}
	
}
