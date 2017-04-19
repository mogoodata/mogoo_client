package weishu;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.sf.json.JSONObject;

import weishu.HttpRequest;

public class WeishuClient {

	private String publicKey;
	private String developerId;
	private String solt;
	private String url;
		
	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}

	public String getSolt() {
		return solt;
	}

	public void setSolt(String solt) {
		this.solt = solt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject getDataFromPlatform(String bankId, String name, String phone, String idNum){
		// 请求明文
		String clearText = "{"
				+ "\"bankId\":\"" + bankId + "\","
				+ "\"name\":\"" + name + "\","
				+ "\"phone\":\"" + phone + "\","	
				+ "\"idNum\":\"" + idNum + "\""
				+ "}";
		String ciphertext = encodeByPublicKey(clearText, getPublicKey());
		
		// md5加密
		String conditionStr = "name=" + name + ", bankId=" + bankId + ", idNum=" + idNum + ", phone=" + phone + "\"" ;
		String md5 = encodeConditionToMD5(conditionStr);
		return doPostRequestToGetData(getDeveloperId(), ciphertext, md5);
	}
	
	private static String encodeByPublicKey(String clearText, String key){	
		try {
			byte[] keyBytes = Base64.getDecoder().decode(key);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key publicKey = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			byte[] encodeData = cipher.doFinal(clearText.getBytes("UTF-8"));
			String ciphertext = Base64.getEncoder().encodeToString(encodeData);

			System.out.println(ciphertext);	
			return ciphertext;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException 
				| InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("通过公钥队请求进行加密：失败！");
			return null;
		}
		
	} 

	private String encodeConditionToMD5(String conditionStr){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] newstr = Base64.getEncoder().encode(md5.digest((conditionStr).getBytes("ISO-8859-1")));
			System.out.println(new String(newstr,"ISO-8859-1"));
			return new String(newstr,"ISO-8859-1");			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private JSONObject doPostRequestToGetData(String developerId, String ciphertext, String md5){
		String requestBody = "{"
						   + "\"developerId\":\"" + developerId + "\","	
						   + "\"ciphertext\":\"" + ciphertext + "\","	
						   + "\"md5\":\"" + md5 + "\"}"	;			
		//String url = "http://106.14.35.4:8080/weishu/api/query";
		HttpRequest request = HttpRequest.post(getUrl()).contentType("application/json").send(requestBody);
		String result = request.body();
		return JSONObject.fromObject(result);		
	}
	
}
