package starshop.starshop.service.impl;

import starshop.starshop.domain.Head;
import starshop.starshop.service.HeadService;
import starshop.starshop.utils.SqlUtil;

import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public class HeadServiceImpl implements HeadService {
    @Override
    public Head isHaveHead(UUID uniqueId, String name) {
        return SqlUtil.queryForObject("select * from head where head.play_id=? and head.play_name=?", Head.class, uniqueId, name);
    }

    @Override
    public Boolean insertHeadInfo(UUID uniqueId, String name, String nbtData, String updateTime) {
        int insert = SqlUtil.insert("insert into head(play_id,play_name,head_data,update_time) values (?,?,?,?)", uniqueId, name, nbtData, updateTime);
        return insert != 0;
    }

    @Override
    public Boolean updateHeadInfo(String playId, String playName, String nbtData) {
        int update = SqlUtil.update("update head set head_data=? where play_id=? and play_name=?", nbtData, playId, playName);
        return update != 0;
    }
}
