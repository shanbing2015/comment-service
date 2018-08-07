package top.shanbing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.shanbing.common.redis.IRedisManager;
import top.shanbing.common.redis.RedisKeys;
import top.shanbing.domain.entity.IpBlock;
import top.shanbing.domain.mapper.BlockMapping;
import top.shanbing.service.BlockService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapping blockMapping;

    @Autowired
    private IRedisManager redisManager;

    @Override
    public IpBlock getIpBlockById(String ip) {
        return blockMapping.selectIpBlockByIp(ip);
    }

    @Override
    public void addIpBlock(String ip) {
        addIpBlock(ip,null);
    }

    @Override
    public void addIpBlockDay(String ip) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);//当天零点
        Duration duration = java.time.Duration.between(nowTime,today_end);
        this.addIpBlock(ip,duration.toMinutes());
    }

    @Override
    public void addIpBlock(String ip, Long minute) {
        String key = String.format(RedisKeys.IP_BLOCK,ip);
        if(minute != null){
            redisManager.incr(key,minute*60);
        }else {
            redisManager.incr(key,null);
        }
    }
}
