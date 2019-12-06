package com.tensquare.encrypt.test;

import com.tensquare.encrypt.EncryptApplication;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description
 * @Author tangkai
 * @Date 10:37 2019/12/4
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EncryptApplication.class)
public class EncryptTest {

    @Autowired
    private RsaService rsaService;


    @Test
    // 加密
    public void fun01() throws Exception {
        String data = "{\"columnid\": \"1\", \"title\":\"java\"}";
        String rsaEncryptDataPEM = rsaService.rsaEncryptDataPEM(data, RsaKeys.getServerPubKey());
        System.out.println("加密后的数据: " + rsaEncryptDataPEM);
    }


    @Test
    //解密
    public void fun02() throws Exception {
        String data = "WcY4IEhOfB0DbV9bSzC2wjHRCLr1uOFXYObVjoGq5cbCc7sLT78q4lEflzsOsQlPG8Y6bYS5avBWv7qiNBxjR+Yoil0WBljW6PHPaoFPaCBaxmwP8EGNJ+t5zxgZniNrUK5RgFbcUQT99J7T8hNJAlibfctWbyktMcwGsfjTWAyoLoETtrRjUFsCEyJtHg7CGh1fhDyiFY7fQ0VTCIFcv9+HMBQ1nbebeCMmJB7ZBUCzhhUeKmzV5KL5bMo30c2g7PeR3szQySb86EP4M8Dp25hG9l06kyqnJzbktj85VpVp75AlBKsXt6tkjllUf+8zvKX4ECVL/9x7BK9WwrtoVA==";
        String rsaEncryptDataPEM = rsaService.rsaDecryptDataPEM(data, RsaKeys.getServerPrvKeyPkcs8());
        System.out.println("解密后的数据: " + rsaEncryptDataPEM);

    }
}
