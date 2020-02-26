package com.ping.service;

import com.ping.common.utils.AdminPageBean;
import com.ping.pojo.Category;

public interface AdminCategoryManageService {

	//ͨ���������ƽ���ģ����ѯ��ҳ��ʾ�����û�����������Ͳ�ѯ���з�����Ϣ
	AdminPageBean getCategoryListPageBean(int currentPage, int pageSize, String cname);

	//����ӷ���ʱ�򣬼��÷��������Ƿ����
	boolean checkCategoryIsExist(String cname);
    //��ӷ�����Ϣ��������mybatis���뵽���ݿ�Ӱ�����ݿ�������ж��Ƿ����ɹ���ʧ��
	int addCategoryInfo(Category category);
    //�༭���࣬ͨ����ȡ����cid��ѯ�÷������Ϣ�����л���
	Category selectCategoryInfoByCid(int cid);
    //�༭���࣬ͨ��cid��������Ϣ���µ����ݿ�
	int updateCategoryInfo(Category category);
    //ɾ�����࣬ͨ��cid��������ɾ��
	int deleteCategoryInfo(String[] arrayCid);

}
