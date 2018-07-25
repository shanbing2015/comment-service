package top.shanbing.service.impl;

import org.springframework.stereotype.Service;
import top.shanbing.common.exception.BizException;
import top.shanbing.domain.enums.ErrorCodeEnum;
import top.shanbing.service.BashService;

import java.io.IOException;

@Service
public class BashServiceImpl implements BashService {

    @Override
    public void test() {
        throw new IllegalArgumentException("测试异常");
    }

    @Override
    public void test2() {
        throw new BizException(ErrorCodeEnum.PARAM_VALID_ERROR);
    }

    @Override
    public void test3()throws Exception {
        throw new IOException("io测试异常");
    }
}
