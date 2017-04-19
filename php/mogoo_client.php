<?php

# 公钥字符串
$public_key = '-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGSUAM6H3/802jQEEyP+krK0Rd
********************请更换成真实的公钥字符串***********************
OJQAIHCmg7C5abO7c52J9EnyqaDcXF89LQzI1HKOl05nAlz6Zpgz5JlAtL9hMmsU
MajcRec8x6IbcQ0BiQIDAQAB
-----END PUBLIC KEY-----';

# 字符串转成公钥对象，并验证公钥字符串是否正确
$pu_key = openssl_pkey_get_public($public_key);

# 业务级参数字符串（样例）
$clear_text = "{\"bankId\":\"6225********\",\"phone\":\"181********\",\"name\":\"张三",\"idNum\":\"421127************\"}";


# ------------公钥加密：把clear_text加密生成chip_text--------------
$cipher_text = "";
openssl_public_encrypt($clear_text, $cipher_text, $pu_key);        // 公钥加密
$cipher_text = base64_encode($cipher_text);                        // 加密之后进行序列化成字符串结果密文
echo "\n

echo "----------------------------RSA加密结果--------------------------\n"
echo $encrypted;            
echo "\n";

# -------------------------MD5加密---------------------------\n";
$str = "ua1mZh+8:$clear_text";
$md5_str = .md5($str);
echo "\n";
echo $md5_str
  
  
# 经过上文中的步骤之后，需要的系统参数：developeID， cipherText， md5 就已经全部获取成功。
# 后面的步骤就是把这三个系统参数放到RequestBody里面，通过发送HTTP的POST请求到服务端接口地址即可完成整个查询接口的步骤
  
API_url = "http://139.224.81.95:8080/mogoo/api/query
