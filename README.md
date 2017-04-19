# weishu-client
微数云平台Client样例代码
# 1.使用说明
本文旨在向第三方合作应用介绍如何访问微数平台开放服务并通过交互取得数据。
接口协议为标准HTTP协议，并采用POST请求。
文中将会介绍Java调用该接口的示范程序。
# 规范适用对象说明
本规范仅适用于由第三方使用客户端发起调用请求、POST提交数据以及请求文本数据结果的API。


# 2.名词解释
*开发者ID：*
```
注册API合作时由微数平台生成的用来标识第三方的开发人员权限的唯一的32位字符串，用于平台与第三方之间通信时标识第三方的ID
```

*开发者公钥：*
```
注册API合作时由微数平台动态生成的，用于做数据加密和身份识别的一串216位字符串，俗称公钥
```

*校验码：*
```
注册API合作时由微数平台动态生成的，用于做数据防篡改安全机制的校验码，为8位字符串
```


# 3.接口请求规范
## 3.1系统级参数
以下参数是由微数平台 API平台系统定义的，提供给第三方平台做规范约束。微数平台采用应用授权认证接口方式，合作初始由第三方向微数平台申请和注册账户，随即得到开发者ID，公钥和校验码等信息。

| 方法名	      | 参数类型     | 参数是否必需    | 参数含义    |
| ------------- |:-------------:| :-----:| :------------ |
| setDeveloperId | String | 是 | 开发者ID |
| setPublicKey | String | 是 | 开发者私钥 |
| setSolt | String | 是 | 校验码 |

## 3.2业务级参数
第三方使用客户端时需遵守微数平台 API规范中业务级参数的约定进行配置。      
 
方法名:```getDataFromPlatform```
参数类型及其参数含义：

| 参数顺序| 参数类型     | 参数是否必需    | 参数含义    |
| ------------- |:-------------:| :-----:| :------------ |
| 1 | String | 是 | 银行卡号|
| 2 | String | 是 | 姓名|
| 3 | String | 是 | 手机号|
| 4 | String | 是 | 身份证号|
```
String	银行卡号
String	姓名
String	身份证号
String	手机号
```
## 3.3 通过Jar包调用接口示范程序
依照下述示范程序，将设置API相应系统级参数和业务级参数，同时需引用相关weishu.Client的jar包，具体由微数提供。（JDK:1.7）
```java
import weishu.WeishuClient;
import net.sf.json.JSONObject;


public class TestClient {

	public static void main(String[] args) {

		WeishuClient weishuCliet = new WeishuClient ();

		weishuCliet.setDevelopId("XXXX开发者IDXXXX");
		weishuCliet.setPublicKey("XXXX公钥XXXX");
		weishuCliet.setSolt("XXXX校验码XXXX");
				
		JSONObject result = weishuCliet.getDataFromPlatform ("银行卡号", "姓名", "手机号", "身份证号");

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
|108| 用户未设置套餐，请联系管理员进行设置|
|109|	用户余额不足|
|110|	获取数据为空，请修改成准确的参数之后再次提交|
|111|	数据源连接失败，请联系管理员确认！|
|其它|	其它错误 |

# 5.weishu.WeishuClient.jar包
请联系微数金融，由我司提供。

# 6.基于标准HTTP请求进行调用接口
请先转至并参照《基于标准HTTP调用规范》
https://github.com/WeishuData/weishu-client/wiki/%E3%80%8A%E5%9F%BA%E4%BA%8E%E6%A0%87%E5%87%86HTTP%E8%AF%B7%E6%B1%82%E8%B0%83%E7%94%A8%E8%A7%84%E8%8C%83%E3%80%8B
