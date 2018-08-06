package top.shanbing.util;

import org.springframework.stereotype.Component;
import top.shanbing.common.SpringApplicationContext;
import top.shanbing.common.exception.BizException;
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

    public static void isIpBlack(String ip) throws BizException{

        /**
         * todo
         *  1、本地限时黑名单缓存
         *  2、本地当天黑名单缓存
         *  3、本地永久黑名单缓存
         *
         *  4、redis限时黑名单缓存
         *  5、redis当前黑名单缓存
         *  6、redis永久黑名单缓存
         *
         *  7、MySQL永久黑名单库
         */

        BlockService blockService = SpringApplicationContext.context.getBean(BlockService.class);
        IpBlock ipBlock = blockService.getIpBlockById(ip);
        if(ipBlock != null ){
            throw new BizException(ErrorCodeEnum.IP_BLACK_ERROR);
        }
    }


}
