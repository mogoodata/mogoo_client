package weishu;

import weishu.WeishuClient;
import net.sf.json.JSONObject;

public class RunClient {

	public static void main(String[] args) {
		if (args.length < 4) {
			System.err.println("参数缺少！");
			System.exit(0);
		}

		System.out.println("输入查询参数为:");
		System.out.println("姓名：" + args[0]);
		System.out.println("手机号：" + args[1]);
		System.out.println("身份证号：" + args[2]);
		System.out.println("银行卡号" + args[3]);

		WeishuClient client = new WeishuClient();

		client.setDeveloperId("ea8ea38458d89eb70158f19928500000");
		client.setPublicKey(
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSU3TdtRd2qjfgUDrGzf/7v+pp3dGHLaQPASVDdwZl4Ty3gM3BIvrxI6vQmcuZ1X/zqhfPku/He+MsCHVlP1gNNzsIqSzG0bsWR9FDTgb8Sgg7nZ4UJ0bn7FjLuj4+Be7ZC/1fOQ8o/0EF7nu8BHmGwby5Y8TIs+kpzUavWS4RqQIDAQAB");
		client.setSolt("F8+S78d7");
		client.setUrl("http://106.14.35.4:8080/weishu/api/query");

		JSONObject json = client.getDataFromPlatform(args[3], args[0], args[1], args[2]);

		System.out.println("根据输入参数进行征信查询，返回结果为：");
		System.out.println(json.toString());

	}

}
