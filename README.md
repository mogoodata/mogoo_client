# mogoo-client
蘑菇云平台Client样例代码    
本文当也等同于《微数云平台接口使用帮助文档（Jar版）》
# 1.使用说明
本文旨在向第三方合作应用介绍如何访问蘑菇平台开放服务并通过交互取得数据。
接口协议为标准HTTP协议，并采用POST请求。
文中将会介绍Java调用该接口的示范程序。
# 规范适用对象说明
本规范仅适用于由第三方使用客户端发起调用请求、POST提交数据以及请求文本数据结果的API。


# 2.名词解释
*开发者ID：*
```
注册API合作时由蘑菇平台生成的用来标识第三方的开发人员权限的唯一的32位字符串，用于平台与第三方之间通信时标识第三方的ID
```

*开发者公钥：*
```
注册API合作时由蘑菇平台动态生成的，用于做数据加密和身份识别的一串216位字符串，俗称公钥
```

*校验码：*
```
注册API合作时由蘑菇平台动态生成的，用于做数据防篡改安全机制的校验码，为8位字符串
```


# 3.接口请求规范
## 3.1系统级参数
以下参数是由蘑菇平台 API平台系统定义的，提供给第三方平台做规范约束。微数平台采用应用授权认证接口方式，合作初始由第三方向微数平台申请和注册账户，随即得到开发者ID，公钥和校验码等信息。

| 系统参数名	      | 参数类型     | 参数是否必需    | 参数含义    |
| ------------- |:-------------:| :-----:| :------------ |
| developerId | String | 是 | 开发者ID |
| publicKey | String | 是 | 开发者私钥 |
| solt | String | 是 | 校验码 |

## 3.2 业务级参数
第三方接入调用蘑菇云平台时需遵守蘑菇云平台定义API规范中业务级参数的约定进行配置。
具体内容可转至《》

## 3.3 通过Jar包调用接口示范程序
依照下述示范程序，将设置API相应系统级参数和业务级参数，同时需引用相关mogoo.Client的jar包，具体由蘑菇云提供。（JDK:1.8）    
样例代码为针对“查询银联卡消费数据”接口为例子，更换成其他接口主要的变化就在于【业务级参数】的不同设值内容。
```java
import mogoo.MogooClient;
import net.sf.json.JSONObject;


public class TestClient {

	public static void main(String[] args) {

		MogooClient client = new MogooClient ();   // 初始化Client对象

		client.setDevelopId("XXXX开发者IDXXXX");      // 系统参数设置
		client.setPublicKey("XXXX公钥XXXX");          // 系统参数设置
		client.setSolt("XXXX校验码XXXX");             // 系统参数设置
				
		JSONObject bussinessCondition = new JSONObject();  // 业务参数JSON对象
		// 业务参数填充，不同的接口对应不同的业务参数要求，本代码以“查询银联卡消费数据”接口为例子
		bussinessCondition.put("bankId", "银行卡号")       // 必选
		bussinessCondition.put("name", "姓名")             // 可选
		bussinessCondition.put("phone", "手机号")          // 可选
		bussinessCondition.put("idNum", "身份证号")        // 可选
		
		// 调用服务接口
		JSONObject result = client.getDataFromPlatform (bussinessCondition);
		System.out.println(result.toString());
	}
}	
```

# 4.接口数据返回格式Json
## 4.1	返回格式Json数据结构
```json
{	
    "code": 000,
    "data":{},
    "message":"",
    "status":""
}
```
解释说明：

| 名称| 说明|
| :-------------: |:-----------------------------------|
|code| 请求响应码，由微数定义，具体说明解释见下文。
|data| 请求返回的实际数据，如报错，此数据为空。
|message| 请求具体消息，一般出错的时候该数据会告知具体出错信息
|status| 请求状态，“success”或者“failed”,标明请求成功或者失败

## 4.2 请求响应码对照表
| 响应码 | 含义 |
| :-------------: |:-----------------------------------|
|0|	成功|
|101|	请求参数不完整，有缺省|
|102|	DeveloperID不合法，平台无此记录|
|103|	请求IP不合法，不在用户白名单范围之内|
|104|	解密失败，用户公钥私钥不匹配|
|105|	解密成功，但是业务参数JSON格式不正确|
|106|	防篡改验证失败，该请求可能被篡改|
|107|	业务参数校验失败（手机号，身份证号，银行卡号）|
|108|   用户未设置套餐，请联系管理员进行设置|
|109|	用户余额不足|
|110|	获取数据为空，请修改成准确的参数之后再次提交|
|111|	数据源连接失败，请联系管理员确认！|
|其它|   其它错误 |

# 5.mogoo.MogooClient.jar包
请联系蘑菇云，由我司提供。

# 6.基于标准HTTP请求进行调用接口
请先转至并参照[《基于标准HTTP调用规范》](https://github.com/mogoodata/mogoo_client/wiki/%E8%98%91%E8%8F%87%E4%BA%91%E5%B9%B3%E5%8F%B0%E6%8E%A5%E5%8F%A3%E4%BD%BF%E7%94%A8%E5%B8%AE%E5%8A%A9%E6%96%87%E6%A1%A3%EF%BC%88http%E7%89%88%EF%BC%89)
