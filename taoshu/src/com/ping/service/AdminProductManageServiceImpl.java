package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminProductManageMapper;
import com.ping.pojo.Category;
import com.ping.pojo.Product;
/**
 * ��̨��Ʒ��������������Ʒ���༭��Ʒ��ɾ����Ʒ����ѯ��Ʒ���ϼܡ��¼�
 * @author admin
 *
 */
@Service
public class AdminProductManageServiceImpl implements AdminProductManageService {
	
	@Autowired
	private AdminProductManageMapper adminProductManageMapper;
	
	//ͨ����Ʒ���ƽ���ģ����ѯ��ҳ��ʾ�����û�����������Ͳ�ѯ������Ʒ��Ϣ���ҷ�ҳ��ʾ
	@Override
	public AdminPageBean getProductListPageBean(int currentPage, int pageSize, String pname) {
		//��Ҫ�κ��Ǵ�����ҳ���󲢷�װ��������,������ҳ������Ҫȷ�����͵�����
		AdminPageBean<Product> pageBean=new AdminPageBean<Product>();
		//1.��ѯ��ҳ��������total�ܼ�¼������װ
		int total=adminProductManageMapper.selectProductTotal(pname);
		//��װ����ҳ����
		pageBean.setTotal(total);
		//2.��ѯ��ҳ��������rows��ǰҳ�ĵ����ݲ���װ
		//��ҳ��ѯmysql���ݿ�sql�����Ҫȷ��limit ������������������һ����ǰҳ��ʼλ�ã���Ҫ���㣩���ڶ���������ÿҳ��ʾ������pageSize
		int currentStart=(currentPage-1)*pageSize;//��ʾ��ǰҳ��ʼλ������
		//����dao��ѯ��ǰҳ����
		List<Product> productList=adminProductManageMapper.selectProductListInfo(currentStart,pageSize,pname);
		//��װ��ҳ����ǰҳ����
		pageBean.setRows(productList);
		//3.���ط�ҳ����
		return pageBean;
		
	}
    //�������Ʒ��Ϣʱ���Ȳ�ѯ���з�����Ϣ���л��Թ��û�ѡ��
	@Override
	public List<Category> selectAllCategoryInfo() {
		//��ѯ���з�����Ϣ
		List<Category> categoryList=adminProductManageMapper.selectAllCategoryInfo();
		return categoryList;
	}
	//�����Ʒ��Ϣ
	@Override
	public int addProductInfo(Product product) {
		int row=adminProductManageMapper.addProductInfo(product);
		return row;
	}
	//�༭��Ʒ��ͨ����Ʒ��pid���в�ѯ��Ʒ��Ϣ���л���
	@Override
	public Product selectProductInfoByPid(int pid) {
		//ͨ����Ʒ��pid��ѯ������Ʒ����Ϣ
		Product product=adminProductManageMapper.selectProductInfoByPid(pid);
		//���ط�װ�õ���Ʒ����
		return product;
	}
	
	//�༭��Ʒ������װ�õ���Ʒ��Ϣ���µ����ݿ�
	@Override
	public int editProductInfo(Product product) {
		int row=adminProductManageMapper.editProductInfo(product);
		return row;
	}
	
	//ɾ����Ʒ��Ϣ��ͨ����Ʒ��pid��������ɾ����Ʒ��Ϣ
	@Override
	public int deleteProductInfo(String[] arrayPid) {
		int row=0;
	  	//���������ȡ������ÿ��pid
		for(int i=0;i<arrayPid.length;i++)
		{
			//�õ�ÿ����Ʒ��pid������dao�����ɾ��
			String strPid=arrayPid[i];
			//���ַ�������pidת����int����
			int pid = Integer.parseInt(strPid);
			//��pid���䵽dao��,����ɾ����Ʒ��Ϣ
			row=adminProductManageMapper.deleteProductInfo(pid);
			
		}
		//���ɾ���ɹ�row��ʵΪ1
		return row;
		
	}
	//��Ʒ�ϼܣ�ͨ��pid�����������£�������Ʒ��pflag=1��ʾ�ϼ�
	@Override
	public int updatePublishProductInfoByPid(String[] arrayPid) {
		int row=0;
	  	//���������ȡ������ÿ��pid
		for(int i=0;i<arrayPid.length;i++)
		{
			//�õ�ÿ����Ʒ��pid������dao����и�����pflag=1
			String strPid=arrayPid[i];
			//���ַ�������pidת����int����
			int pid = Integer.parseInt(strPid);
			//��pid���䵽dao��,���и�����Ʒ��Ϣ
			row=adminProductManageMapper.updatePublishProductInfoByPid(pid);
			
		}
		//������³ɹ�row��ʵΪ1
		return row;
	}
	
	//��Ʒ�¼ܣ�ͨ��pid�����������£�������Ʒ��pflag=0��ʾ�¼�
	@Override
	public int updateUnpublishProductInfoByPid(String[] arrayPid) {
		int row=0;
	  	//���������ȡ������ÿ��pid
		for(int i=0;i<arrayPid.length;i++)
		{
			//�õ�ÿ����Ʒ��pid������dao����и�����pflag=0
			String strPid=arrayPid[i];
			//���ַ�������pidת����int����
			int pid = Integer.parseInt(strPid);
			//��pid���䵽dao��,���и�����Ʒ��Ϣ
			row=adminProductManageMapper.updateUnpublishProductInfoByPid(pid);
			
		}
		//������³ɹ�row��ʵΪ1
		return row;
	}
	
	
	

}
