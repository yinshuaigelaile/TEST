package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.common.utils.PageBean;
import com.ping.pojo.Product;

/**
 * ��Ʒ��ϢDao
 * @author admin
 *
 */
public interface ProductMapper {

	//��ѯ������Ʒ��Ϣ
	public List<Product> selectNewProductList();

	//��ѯ������Ʒ��Ϣ
	public List<Product> selectHotProductList();
	
	//ͨ����Ʒid��ѯ��Ʒ��Ϣ
	public Product selectProductInfoById(int pid);

	//������Ʒ����cid��ѯ������Ʒ���ܼ�¼��
	public Long selectTotalCount(int cid);
	
    //������Ʒ����cid,���е�ǰҳ��ÿҳ��ʾ����������ѯ������Ʒ����Ϣ
	public List<Product> selectCurrentPageProductList(@Param("cid") int cid,@Param("currentStart") int currentStart,@Param("currentCount") int currentCount);

	//վ�������������������Ʒ���Ʋ�ѯ���ݿ�
	public List<Product> getProductNameListByInput(String inputvalue);

	//վ��������������Ʒ����ģ����ѯ�ܼ�¼��
	public Long selectTotalCountByName(String searchContent);

	//������Ʒ����ģ����ҳ��ѯ,mybatis���������ʹ��@Param()ע��ָ��������
	public List<Product> selectCurrentPageProductListByName(@Param("searchContent") String searchContent,@Param("currentStart") int currentStart,@Param("currentCount") int currentCount);
}
