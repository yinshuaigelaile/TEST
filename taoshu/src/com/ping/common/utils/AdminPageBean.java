package com.ping.common.utils;

import java.util.List;

/**
 * ��̨��ҳ�ֻ࣬��Ҫ��װeasyUI��Ҫ���ص�total��ҳ����rows��ǰҳ��ʾ������
 * ʹ��easyUI��Ϊǰ̨���֮��ǰ̨��ҳ�ύ�ĵ�ǰҳ��ÿҳ��ʾ�������������Ѿ������ֱ�Ϊ page,rows
 * ��̨��ѯ���ص�json���ݸ�ʽҲ�Ƕ���������total��ҳ����rows��ǰҳ��ʾ����������Ҳ�ǲ��ܱ�
 * @author admin
 *
 */
public class AdminPageBean<T> {
	
    //ʹ��easyUI���ֻ��Ҫ����total��rows
	private int total; //������  easyUI�Ѿ�ȷ���ñ�����Ϊ����ʹǰ̨������ݱ����������ñ��������ܸı�
	private List<T> rows;//��ǰҳ��ʾ������ easyUI�Ѿ�ȷ���ñ�����Ϊ����ʹǰ̨������ݱ����������ñ��������ܸı�
	//��ҳ������Ͳ����Լ����㣬ǰ̨easyUI��������
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	

}
