package com.ping.service;

import java.util.List;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Product;

/**
 * ��Ʒ��Ϣ��service��
 * @author admin
 *
 */
public interface ProductService {

	//��ѯ������Ʒ��Ϣ�����ǲ�ѯ��Ʒ��ʱ����н���ѡ��ǰ����
	List<Product> selectNewProductList();
    //��ѯ������Ʒ��Ϣ
	List<Product> selectHotProductList();

	//������Ʒid��ѯ��Ʒ��Ϣ
	Product selectProductInfoById(int pid);
	
	//������Ʒ�ķ���id���в�ѯ������Ʒ���ҽ��з�ҳ��ʾ
	PageBean selectProductListByCid(int cid, int currentPage, int currentCount);
	
	//վ�������������������Ʒ���ƽ���ģ����ѯ���ݿ�
	List<Product> getProductNameListByInput(String inputvalue);
	//վ�������������û��������Ϣ���з�ҳ��ѯ��Ʒ��Ϣ
	PageBean selectProductInfoListByInput(String searchContent, int currentPage, int currentCount);
}
