package top.shanbing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
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

@EnableAsync
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
    public Long addIpBlock(String ip) {
        return addIpBlock(ip,null);
    }

    @Override
    public Long addIpBlockDay(String ip) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);//当天零点
        Duration duration = java.time.Duration.between(nowTime,today_end);
        return this.addIpBlock(ip,duration.toMinutes());
    }

    @Override
    public Long addIpBlock(String ip, Long minute) {
        String key = String.format(RedisKeys.IP_BLOCK,ip);

        Long tt = redisManager.pttl(key);
        System.out.println("当前过期时间(毫秒):"+tt);
        if(tt == -1){
            redisManager.incr(key,null);
            return -1L;
        }
        if(minute == null){
            if(tt >= 0) redisManager.delete(key);
            redisManager.incr(key,null);
            return -1L;
        }
        if(tt < minute*60*1000) {
            redisManager.incr(key,minute * 60);
            return minute;
        }
        return tt/1000/60;
    }

    @Async
    @Override
    public void queryIpBlock(String ip) {
        IpBlock ipBlock = this.getIpBlockById(ip);
        if(ipBlock != null && ipBlock.getIp() != null){
            this.addIpBlock(ipBlock.getIp());
        }
    }
}
