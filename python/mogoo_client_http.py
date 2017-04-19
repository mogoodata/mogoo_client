# -*- coding: utf-8 -*-

import requests
import json
from Crypto.Cipher import PKCS1_v1_5 as Cipher_pkcs1_v1_5
from Crypto.PublicKey import RSA
import base64
from hashlib import md5

import sys
reload(sys)
sys.setdefaultencoding("utf-8")

developer_id = 'ea8ea38459539e13015962f8982a0001'
solt_key = 'U3tfvBK3'
public_key = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCOEoQbDy6iX7b5u33UyGEkVHegoKBXanjaHqyBkN3O1Ur2S8aGyYC5sHc4G/dSjzvPCFN7X7wSt0ZiUvX84n90gaJUmjbtazzoeI/UcuYTGY0Q4gbc2ZU0phx8l4AnGYur+umX1HEIMNtN70eQgYtAQuwPFbK2T6q3BPZOGRFiuwIDAQAB'

name = u'中文姓名'
phone = '186****4656'
bank_id = '622576871138****'
id_num = '421127199007242****'


def rsa_encode(clear_text):
    """
    通过公钥对明文进行加密，返回加密之后的密文

    试了无数遍，最终还是妥协了
    实在是无法通过公钥的字符串直接转化成publicKey对象，
    必须只能是从一个文件读出来，再行转化！

    :param clear_text:
    :return: cipher_text
    """

    key_import_str = '''-----BEGIN PUBLIC KEY-----\n
    %s\n-----END PUBLIC KEY-----''' % public_key

    print "RSA 加密前明文为： %s" % clear_text
    pubkey = RSA.importKey(key_import_str)
    cipher = Cipher_pkcs1_v1_5.new(pubkey)
    print len(clear_text)
    cipher_text = base64.b64encode(cipher.encrypt(clear_text))
    print "RSA 加密后密文为： %s" % cipher_text
    return cipher_text


def md5_encode(condition_str):
    """
    通过MD5加密算法对指定的查询条件格式进行加密，返回加密之后的密文
    这里面尤其要注意，传入进来的字符串格式！！！

    :param condition_str:
    :return: md5_str
    """
    print "MD5 加密前明文为： %s" % condition_str
    md5_instance = md5()
    md5_instance.update(condition_str)
    print "MD5 加密后密文为： %s" % md5_instance.hexdigest()
    return md5_instance.hexdigest()


def send_http_request(dev_id, cipher, md5_str):
    body = {
        'developerId': dev_id,
        'ciphertext': cipher,
        'md5': md5_str
    }
    headers = {'content-type': 'application/json'}
    url = "http://139.224.81.95:8080/weishu/api/query"
    print "http请求参数Body为:"
    print str(body)
    result = requests.post(url=url,
                           data=json.dumps(body),
                           headers=headers)
    print "返回结果为："
    print str(result.text)
    return result.text


def main():
    """
    入口函数,主要包含三个步骤:
        第一步：生成查询条件密文;
        第二步：MD5加密查询条件，生成防篡改标识密文（MD5）;
        第三步：组装RequestBody，并发送POST请求到服务，取得返回结果;
    :return: query_result_json
    """

    # 第一步：生成查询条件密文;
    clear_text_json = '''{
    "bankId":"%s",
    "name": "%s",
    "phone":"%s",
    "idNum":"%s"}''' % (bank_id, name, phone, id_num)

    clear_text = str(clear_text_json)
    cipher_text = rsa_encode(clear_text)

    # 第二步：MD5加密查询条件，生成防篡改标识密文（MD5）;
    condition_str = "%s name=%s, bankId=%s, idNum=%s, phone=%s" % (solt_key,
                                                                   name,
                                                                   bank_id,
                                                                   id_num,
                                                                   phone)
    md5_str = md5_encode(condition_str)

    # 第三步：组装RequestBody，并发送POST请求到服务，取得返回结果;
    print "发送POST请求到服务端"
    return send_http_request(developer_id, cipher_text, md5_str)


if __name__ == "__main__":
    main()
