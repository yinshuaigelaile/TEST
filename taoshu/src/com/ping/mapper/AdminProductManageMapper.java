package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Category;
import com.ping.pojo.Product;

/**
 * ��̨��Ʒ��������������Ʒ���༭��Ʒ��ɾ����Ʒ����ѯ��Ʒ���ϼܡ��¼�
 * @author admin
 *
 */
public interface AdminProductManageMapper {

	//��ѯ������Ʒ���ܼ�¼������û�������Ʒ���Ʋ�Ϊ�վ͸�����Ʒ����ģ����ѯ�������Ʒ������
	int selectProductTotal(@Param("pname") String pname);
    //������Ʒ���ƺ͵�ǰҳ��ѯ����Ʒ�����е�ǰҳ����Ʒ��Ϣ
	List<Product> selectProductListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("pname") String pname);
	
	//�������Ʒʱ���Ȳ�ѯ���з�����Ϣ���л��Թ��û�ѡ��
	List<Category> selectAllCategoryInfo();
	//�����Ʒ��Ϣ
	int addProductInfo(Product product);
	//�༭��Ʒ��ͨ��pid��ѯ������Ʒ��Ϣ�����л���
	Product selectProductInfoByPid(int pid);
	//ͨ����Ʒ��pid������Ʒ����Ϣ
	int editProductInfo(Product product);
	//ɾ����Ʒ��Ϣ��ͨ��pidɾ����Ʒ��Ϣ
	int deleteProductInfo(int pid);
	//��Ʒ�ϼܣ�ͨ��pid������Ʒ��Ϣ��pflag=1��ʾ��Ʒ�ϼ�
	int updatePublishProductInfoByPid(int pid);
	//��Ʒ�¼ܣ�ͨ��pid������Ʒ��Ϣ��pflag=0��ʾ��Ʒ�¼�
	int updateUnpublishProductInfoByPid(int pid);

}
