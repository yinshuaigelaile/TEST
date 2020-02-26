package com.ping.service;

import java.util.List;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.Category;
import com.ping.pojo.Product;

/**
 * ��̨��Ʒ��������������Ʒ���༭��Ʒ��ɾ����Ʒ����ѯ��Ʒ���ϼܡ��¼�
 * @author admin
 *
 */
public interface AdminProductManageService {
	//ͨ����Ʒ���ƽ���ģ����ѯ��ҳ��ʾ�����û�����������Ͳ�ѯ������Ʒ��Ϣ
	AdminPageBean getProductListPageBean(int currentPage, int pageSize, String pname);
    //�������Ʒʱ���Ȳ�ѯ���з�����Ϣ���л��Թ��û�ѡ��
	List<Category> selectAllCategoryInfo();
	//�����Ʒ��Ϣ
	int addProductInfo(Product product);
	//�༭��Ʒ��ͨ��pid���в�ѯ��Ʒ��Ϣ����
	Product selectProductInfoByPid(int pid);
	//�༭��Ʒ������װ�õ���Ʒ��Ϣ���µ����ݿ�
	int editProductInfo(Product product);
	//ɾ����Ʒ��Ϣ��������Ʒ��pid��������ɾ����Ʒ��Ϣ
	int deleteProductInfo(String[] arrayPid);
	//��Ʒ�ϼܣ�ͨ����Ʒpid��������������Ʒ��Ϣ����pflag=1
	int updatePublishProductInfoByPid(String[] arrayPid);
	//��Ʒ�¼ܣ�ͨ����Ʒpid��������������Ʒ��Ϣ����pflag=0
	int updateUnpublishProductInfoByPid(String[] arrayPid);

}
