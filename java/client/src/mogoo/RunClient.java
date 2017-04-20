package mogoo;

import mogoo.MogooClient;
import net.sf.json.JSONObject;

public class RunClient {

	public static void main(String[] args) {
		
		MogooClient client = new MogooClient();

		// 系统级参数
		client.setDeveloperId("*********开发者ID********");
		client.setPublicKey("**********************************非常长的公钥字符串********************************************");
		client.setSolt("**识别码**");
		
		// 业务级别参数
		JSONObject bussinessCondistions = new JSONObject();
		bussinessCondistions.put("bankId", "银行卡号");
		bussinessCondistions.put("phone", "手机号");
		bussinessCondistions.put("name", "姓名");
		bussinessCondistions.put("idNum", "身份证号");
		
//		JSONObject json = client.getDataFromPlatform(bussinessCondistions);
//
//		System.out.println("根据输入参数进行征信查询，返回结果为：");
		System.out.println(bussinessCondistions.toString());
	}

}
