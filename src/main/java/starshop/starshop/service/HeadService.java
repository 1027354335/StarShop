package starshop.starshop.service;

import starshop.starshop.domain.Head;

import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public interface HeadService {
    /**
     * 通过玩家名以及UUID获取到头颅信息列表
     * @param uniqueId uuid
     * @param name     玩家名
     * @return Head
     */
    Head isHaveHead(UUID uniqueId, String name);

    /**
     * 插入一条Head数据
     * @param uniqueId   uuid
     * @param name       玩家名
     * @param nbtData    nbt数据
     * @param updateTime 更新时间
     * @return Boolean
     */
    Boolean insertHeadInfo(UUID uniqueId, String name, String nbtData, String updateTime);

    /**
     * 更新Head数据
     * @param playId   uuid
     * @param playName 玩家名
     * @param nbtData  新的nbt数据
     * @return Boolean
     */
    Boolean updateHeadInfo(String playId, String playName, String nbtData);

}
