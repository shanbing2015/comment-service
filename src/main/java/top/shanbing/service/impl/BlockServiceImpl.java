package top.shanbing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.shanbing.domain.entity.IpBlock;
import top.shanbing.domain.mapper.BlockMapping;
import top.shanbing.service.BlockService;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapping blockMapping;

    @Override
    public IpBlock getIpBlockById(String ip) {
        return blockMapping.selectIpBlockByIp(ip);
    }
}
