package top.shanbing.service;

import top.shanbing.domain.entity.IpBlock;

public interface BlockService {

    IpBlock getIpBlockById(String ip);

    /**查询DB是否IP黑名单，并加入Redis*/
    void queryIpBlock(String ip);


    /**加入临时黑名单,minute 分钟 null 不过期,返回黑名单过期时间(分钟)*/
    Long addIpBlock(String ip,Long minute );

    /**加入黑名单,返回黑名单过期时间(分钟)*/
    Long addIpBlock(String ip);

    /**加入黑名单，当天有效,返回黑名单过期时间(分钟)*/
    Long addIpBlockDay(String ip);
}
