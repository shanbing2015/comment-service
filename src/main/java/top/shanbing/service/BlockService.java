package top.shanbing.service;

import top.shanbing.domain.entity.IpBlock;

public interface BlockService {

    IpBlock getIpBlockById(String ip);

    void addIpBlock(String ip);

    /**加入临时黑名单,minute 分钟*/
    void addIpBlockTemp(String ip,int minute );

    /**加入临时黑名单,当天*/
    void addIpBlockTemp(String ip);
}
