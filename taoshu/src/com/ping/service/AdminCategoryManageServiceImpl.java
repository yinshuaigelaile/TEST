package com.ping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ping.common.utils.AdminPageBean;
import com.ping.mapper.AdminCategoryManageMapper;
import com.ping.pojo.Category;
import com.ping.pojo.User;

@Service
public class AdminCategoryManageServiceImpl implements AdminCategoryManageService {

	@Autowired
	private AdminCategoryManageMapper adminCategoryManageMapper;
	
	//ͨ���������ƽ���ģ����ѯ��ҳ��ʾ�����û�����������Ͳ�ѯ���з�����Ϣ
	@Override
	public AdminPageBean getCategoryListPageBean(int currentPage, int pageSize, String cname) {
		//��Ҫ�κ��Ǵ�����ҳ���󲢷�װ��������,������ҳ������Ҫȷ�����͵�����
		AdminPageBean<Category> pageBean=new AdminPageBean<Category>();
		//1.��ѯ��ҳ��������total�ܼ�¼������װ
		int total=adminCategoryManageMapper.selectCategoryTotal(cname);
		//��װ����ҳ����
		pageBean.setTotal(total);
		//2.��ѯ��ҳ��������rows��ǰҳ�ĵ����ݲ���װ
		//��ҳ��ѯmysql���ݿ�sql�����Ҫȷ��limit ������������������һ����ǰҳ��ʼλ�ã���Ҫ���㣩���ڶ���������ÿҳ��ʾ������pageSize
		int currentStart=(currentPage-1)*pageSize;//��ʾ��ǰҳ��ʼλ������
		//����dao��ѯ��ǰҳ����
		List<Category> categoryList=adminCategoryManageMapper.selectCategoryListInfo(currentStart,pageSize,cname);
		//��װ��ҳ����ǰҳ����
		pageBean.setRows(categoryList);
		//3.���ط�ҳ����
		return pageBean;
	}

	//����ӷ���ʱ����÷��������Ƿ����
	@Override
	public boolean checkCategoryIsExist(String cname) {
		//ͨ���������Ʋ�ѯ�÷����Ƿ����
		Long checkUserIsExsit=adminCategoryManageMapper.checkCategoryIsExist(cname);
		//checkUserIsExsit>0����������ƴ��ڣ����򲻴���
		return checkUserIsExsit>0?true:false;
	}
    
	//��ӷ�����Ϣ
	@Override
	public int addCategoryInfo(Category category) {
		//��������Ϣ���䵽dao�㽫����Ϣ���뵽���ݿ�
		int row=adminCategoryManageMapper.addCategoryInfo(category);
		return row;

	}
	//�༭�û���ͨ����ȡ����cid��ѯ�÷������Ϣ�����л���
	@Override
	public Category selectCategoryInfoByCid(int cid) {
		//����dao�㷽����ͨ��cid��ѯ�÷������Ϣ
		Category category=adminCategoryManageMapper.selectCategoryInfoByCid(cid);
		return category;
	}
    //�༭������Ϣ��ͨ��cid��������Ϣ���µ����ݿ�
	@Override
	public int updateCategoryInfo(Category category) {
		//mybatis�������ݿ�����ɹ��᷵��һ��int���͵�ֵ��ʹ��row�������row=1>0��ʾ���³ɹ����������ʧ��
		int row=adminCategoryManageMapper.updateCategoryInfo(category);
		return row;
	}
    //ɾ�����࣬ͨ��cid��������ɾ��
	@Override
	public int deleteCategoryInfo(String[] arrayCid) {
		int row=0;
	  	//���������ȡ������ÿ��cid
		for(int i=0;i<arrayCid.length;i++)
		{
			//�õ�ÿ���û���cid������dao�����ɾ��
			String strCid=arrayCid[i];
			//���ַ�������cidת����int����
			int cid = Integer.parseInt(strCid);
			//��cid���䵽dao��
			row=adminCategoryManageMapper.deleteCategoryInfo(cid);
			
		}
		//���ɾ���ɹ�row��ʵΪ1
		return row;
		
	}
	

}
