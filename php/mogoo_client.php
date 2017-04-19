<?php

// 公钥字符串
#$public_key = '-----BEGIN PUBLIC KEY-----
#MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbrbo/JaPJTJLl+6hfZm7uuLIr
#t/hivaLfot32wq/nSzoSsYkoNk27Yy+n10ODoZ75/91Y8QoJKeoWe0Ik1H1DmMuw
#Ef3eBoBCFn+eNjgZq6SIVBCNEnUaS0STmWqGPFKRFJ1Ujd4rJQ1tGFG3z3v9Cw2b
#Kq41AAYMD7ZqLv2zfQIDAQAB
#-----END PUBLIC KEY-----';



$public_key = '-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGSUAM6H3/802jQEEyP+krK0Rd
6X943wCDjdp5qFfanojOEhn0BYnuhhC3hsUVGYymMw0IswNZiAnRsLF328XyTRG0
OJQAIHCmg7C5abO7c52J9EnyqaDcXF89LQzI1HKOl05nAlz6Zpgz5JlAtL9hMmsU
MajcRec8x6IbcQ0BiQIDAQAB
-----END PUBLIC KEY-----';



$pu_key = openssl_pkey_get_public($public_key);


#$original_json_str = "{\"bankId\":\"6225768711381796\"}";      // 原始数据样例
$original_json_str = "{\"bankId\":\"62170032123131312\",\"phone\":\"18124203156\",\"name\":\"\u5c0f\u8d56\",\"idNum\":\"440981100025541232\"}";


echo "------------------------公钥加密----------------------------\n";
$encrypted = "";
openssl_public_encrypt($original_json_str, $encrypted, $pu_key);        // 公钥加密
$encrypted = base64_encode($encrypted);
echo $encrypted;                // 加密结果字符串
echo "\n";


echo "--------------------------MD5加密---------------------------\n";

$str = "ua1mZh+8:$original_json_str";
echo "字符串：$str\n";
//echo "TRUE - 原始 16 字符二进制格式：".md5($str, TRUE)."<br>";
echo "FALSE - 32 字符十六进制格式：".md5($str)."<br>";
echo "\n";
"test_php_encode.php" [dos] 43L, 1494C