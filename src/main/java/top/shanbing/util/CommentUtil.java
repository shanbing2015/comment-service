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
        BlockService blockService = SpringApplicationContext.context.getBean(BlockService.class);
        IpBlock ipBlock = blockService.getIpBlockById(ip);
        if(ipBlock != null ){
            throw new BizException(ErrorCodeEnum.IP_BLACK_ERROR);
        }
    }


}
