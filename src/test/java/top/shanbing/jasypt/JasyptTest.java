package top.shanbing.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.shanbing.BaseTest;

/**
 * @author shanbing
 * @date 2018/8/6.
 * 明文密码加密
 */

public class JasyptTest extends BaseTest {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptTest() {
        String result = stringEncryptor.encrypt("");
        System.out.println(result);
    }

    //@Test
    public void decryptTest() {
        String result = "";
        result = stringEncryptor.decrypt(result);
        System.out.println(result);
    }
}
