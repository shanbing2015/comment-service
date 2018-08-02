package top.shanbing.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.shanbing.domain.entity.IpBlock;

/**
 * Created by shanbing.top on 2018/8/2.
 */
@Repository
@Mapper
public interface BlockMapping {

    IpBlock selectIpBlockByIp(@Param("ip") String ip );
}
