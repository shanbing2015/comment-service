package top.shanbing;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.shanbing.service.BlockService;

/**
 * @author shanbing
 * @date 2018/8/7.
 */
public class AddIpBlockTest extends BaseTest{

    @Autowired
    private BlockService service;

    @Test
    public void addIpBlockTest(){
        String ip = "192.168.1.2";
        service.addIpBlock(ip,-2L);
        //service.addIpBlock(ip,-2L);
    }
}
