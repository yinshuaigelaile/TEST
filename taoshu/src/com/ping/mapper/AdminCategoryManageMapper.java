package com.ping.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ping.pojo.Category;


public interface AdminCategoryManageMapper {

	//����û��������������Ϊ��ѯ�������Ͳ�ѯ��Ӧ���������������ѯ���з���������
	int selectCategoryTotal(@Param("cname") String cname);
    //����û��������������Ϊ��ѯ����,ģ����ѯ������Ϣ�������ǵ�ǰҳ�������ѯ���з��൱ǰҳ��Ϣ
	List<Category> selectCategoryListInfo(@Param("currentStart")int currentStart,@Param("pageSize") int pageSize,@Param("cname") String cname);
	
	//��ӷ���ʱ������������Ƿ����
	Long checkCategoryIsExist(String cname);
	//��ӷ�����Ϣ�����뵽���ݿ�
	int addCategoryInfo(Category category);
	//�༭���࣬ͨ����ȡcid��ѯ������Ϣ���л���
	Category selectCategoryInfoByCid(int cid);
	//�༭������Ϣ��ͨ��cid��������Ϣ���µ����ݿ�
	int updateCategoryInfo(Category category);
	//ɾ�����࣬ͨ��cid��������ɾ��
	int deleteCategoryInfo(int cid);

}
