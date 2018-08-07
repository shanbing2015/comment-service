package top.shanbing.util;

import org.springframework.stereotype.Component;
import top.shanbing.common.SpringApplicationContext;
import top.shanbing.common.exception.BizException;
import top.shanbing.common.redis.IRedisManager;
import top.shanbing.common.redis.RedisKeys;
import top.shanbing.domain.entity.CommentSites;
import top.shanbing.domain.entity.IpBlock;
import top.shanbing.domain.enums.ErrorCodeEnum;
import top.shanbing.service.BlockService;

@Component
public class CommentUtil {

    /***
     * 站点是否黑名单
     * @param site
     * @throws BizException 业务异常
     */
    public static void isSiteBlack(CommentSites site) throws BizException{
        if(site.getType() == 2){
            throw new BizException(ErrorCodeEnum.SITE_BLACK_ERROR);
        }
    }

    /**查询是否ip黑名单，只查询redis缓存,未查到调用异步db查询*/
    public static void isIpBlack(String ip) throws BizException{
        IRedisManager redisManager = SpringApplicationContext.context.getBean(IRedisManager.class);
        String key = String.format(RedisKeys.IP_BLOCK,ip);
        Integer count = redisManager.get(key,Integer.class);
        if(count != null && count > 0){
            throw new BizException(ErrorCodeEnum.IP_BLACK_ERROR);
        }
        BlockService blockService = SpringApplicationContext.context.getBean(BlockService.class);
        blockService.queryIpBlock(ip);
//        IpBlock ipBlock = blockService.getIpBlockById(ip);
//        if(ipBlock != null ){
//            throw new BizException(ErrorCodeEnum.IP_BLACK_ERROR);
//        }
    }


}
