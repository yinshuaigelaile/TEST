package com.ping.service;
/**
 * ��Ʒ��Ϣservice��
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.PageBean;
import com.ping.mapper.ProductMapper;
import com.ping.pojo.Product;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	//��ѯ������Ʒ��Ϣ�б�
	@Override
	public List<Product> selectNewProductList() {
		//����dao�㷽�����в�ѯ
		List<Product> newProductList = productMapper.selectNewProductList();
		return newProductList;
	}
	//��ѯ������Ʒ��Ϣ
	@Override
	public List<Product> selectHotProductList() {
		//����dao�㷽����ѯ������Ʒ��Ϣ
		List<Product> hotProductList=productMapper.selectHotProductList();
		return hotProductList;
	}
	//ͨ����Ʒid��ѯ��Ʒ��Ϣ
	@Override
	public Product selectProductInfoById(int pid) {
		//������Ʒid��ѯ��Ʒ��Ϣ
		Product productInfo = productMapper.selectProductInfoById(pid);
		return productInfo;
	}
	
	//������Ʒ�ķ���id��ѯ������Ʒ��Ϣ���ҽ��з�ҳ��ʾ
	@Override
	public PageBean selectProductListByCid(int cid, int currentPage, int currentCount) {
		//��ǰ̨��Ҫ�����ݽ��в�ѯ�����ҽ��з�װ,
		//Ӧ�÷�ҳ����ʹ�÷��ͣ��ڴ�������ʱ��ȷ������
		PageBean<Product> pageBean=new PageBean<Product>();
		//���õ�ǰҳ
		pageBean.setCurrentPage(currentPage);
		//����ÿҳ��ʾ������
		pageBean.setCurrentCount(currentCount);
		//�����ܼ�¼������Ҫ������Ʒ����cid�����ݿ��в�ѯ������Ʒ���ܼ�¼��
		int totalCount=productMapper.selectTotalCount(cid).intValue();
		pageBean.setTotalCount(totalCount);
		//������ҳ����ͨ��ÿҳ��ʾ�������������������������һ���ж���ҳ
		int totalPage=(totalCount+currentCount-1)/currentCount;
		pageBean.setTotalPage(totalPage);
		//�������ݿ�limit��һ������ֵ��ͨ����ǰҳ��ÿҳ��ʾ����
		/**
		 * ���� ��һҳ   ÿҳ��ʾ   limit��һ������
		 *     1     12      0(0-11���ݿ��0��ʼ��12��)  ͨ�����ɿ������ڣ���ǰҳ-1��*ÿҳ��ʾ����
		 *     2     12      12(12-23)
		 *     3     12      24(24-35)
		 */
		int currentStart=(currentPage-1)*currentCount;
		//��ѯ���ݿ��е�ǰҳ��ʾ����Ʒ����Ϣ
		List<Product> currentProductList=productMapper.selectCurrentPageProductList(cid,currentStart,currentCount);
		//����ѯ��������Ʒ��Ϣ��װ����ҳ�����У���ǰ̨ҳ����ʾ
		pageBean.setList(currentProductList);
		//����ҳ��ʾ�Ķ��󷵻�
		return pageBean;
	}
	
	
	//վ������������������Ʒ���ƽ���ģ����ѯ���ݿ�
	@Override
	public List<Product> getProductNameListByInput(String inputvalue) {
		List<Product> productNameList=productMapper.getProductNameListByInput(inputvalue);
		return productNameList;
	}
	
	
	//վ�����������û����������ť��������Ʒ���ҳģ����ѯ
	@Override
	public PageBean selectProductInfoListByInput(String searchContent, int currentPage, int currentCount) {

		//��ǰ̨��Ҫ�����ݽ��в�ѯ�����ҽ��з�װ,
		//Ӧ�÷�ҳ����ʹ�÷��ͣ��ڴ�������ʱ��ȷ������
		PageBean<Product> pageBean=new PageBean<Product>();
		//���õ�ǰҳ
		pageBean.setCurrentPage(currentPage);
		//����ÿҳ��ʾ������
		pageBean.setCurrentCount(currentCount);
		//�����ܼ�¼������Ҫ������Ʒ����ģ����ѯ�������ݿ��в�ѯ������Ʒ���ܼ�¼��
		int totalCount=productMapper.selectTotalCountByName(searchContent).intValue();
		pageBean.setTotalCount(totalCount);
		//������ҳ����ͨ��ÿҳ��ʾ�������������������������һ���ж���ҳ
		int totalPage=(totalCount+currentCount-1)/currentCount;
		pageBean.setTotalPage(totalPage);
		//�������ݿ�limit��һ������ֵ��ͨ����ǰҳ��ÿҳ��ʾ����
		/**
		 * ���� ��һҳ   ÿҳ��ʾ   limit��һ������
		 *     1     12      0(0-11���ݿ��0��ʼ��12��)  ͨ�����ɿ������ڣ���ǰҳ-1��*ÿҳ��ʾ����
		 *     2     12      12(12-23)
		 *     3     12      24(24-35)
		 */
		int currentStart=(currentPage-1)*currentCount;
		//��ѯ���ݿ��е�ǰҳ��ʾ����Ʒ����Ϣ,������Ʒ������ģ����ҳ��ѯ
		List<Product> currentProductList=productMapper.selectCurrentPageProductListByName(searchContent,currentStart,currentCount);
		//����ѯ��������Ʒ��Ϣ��װ����ҳ�����У���ǰ̨ҳ����ʾ
		pageBean.setList(currentProductList);
		//����ҳ��ʾ�Ķ��󷵻�
		return pageBean;
		
	}
	

}
