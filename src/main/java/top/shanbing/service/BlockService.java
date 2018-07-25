package top.shanbing.service;

import top.shanbing.domain.entity.IpBlock;

public interface BlockService {

    IpBlock getIpBlockById(String ip);
}
