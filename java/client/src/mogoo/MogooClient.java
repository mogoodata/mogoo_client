package mogoo;

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

public class MogooClient {
	
	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

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

	public JSONObject getDataFromPlatform(JSONObject bussinessConditions){
		// 请求明文
		String ciphertext = encodeByPublicKey(bussinessConditions.toString(), getPublicKey());
		// MD5加密
		String md5 = encodeConditionToMD5(bussinessConditions.toString());
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
	        MessageDigest md5 = MessageDigest.getInstance("md5");
	        String originStr = solt + ":" + conditionStr ;
	        md5.update(originStr.getBytes());
	        byte[] resultByte = md5.digest();
	        StringBuilder sb = new StringBuilder(resultByte.length * 2);
	        for (int i = 0; i < resultByte.length; i++) {
	            sb.append(hexChar[(resultByte[i] & 0xf0) >>> 4]);
	            sb.append(hexChar[resultByte[i] & 0x0f]);
	        }
	        return sb.toString();
	        
		} catch (NoSuchAlgorithmException e) {
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
		//String url = "http://localhost:8080/weishu/api/query";
		String url = "http://139.224.81.95:8080/mogoo/api/query";
		System.out.println(requestBody);
		HttpRequest request = HttpRequest.post(url).contentType("application/json").send(requestBody);
		String result = request.body();
		System.out.println(result);
		return JSONObject.fromObject(result);		
	}
	
}

