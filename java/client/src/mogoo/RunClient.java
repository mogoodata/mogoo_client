package mogoo;

import mogoo.MogooClient;
import net.sf.json.JSONObject;

public class RunClient {

	public static void main(String[] args) {
		
		MogooClient client = new MogooClient();

		// ϵͳ������
		client.setDeveloperId("*********������ID********");
		client.setPublicKey("**********************************�ǳ����Ĺ�Կ�ַ���********************************************");
		client.setSolt("**ʶ����**");
		
		// ҵ�񼶱����
		JSONObject bussinessCondistions = new JSONObject();
		bussinessCondistions.put("bankId", "���п���");
		bussinessCondistions.put("phone", "�ֻ���");
		bussinessCondistions.put("name", "����");
		bussinessCondistions.put("idNum", "���֤��");
		
//		JSONObject json = client.getDataFromPlatform(bussinessCondistions);
//
//		System.out.println("������������������Ų�ѯ�����ؽ��Ϊ��");
		System.out.println(bussinessCondistions.toString());
	}

}
