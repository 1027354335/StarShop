package starshop.starshop.service;

import starshop.starshop.domain.TeamPlay;

import java.util.List;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public interface TeamPlayService {
    /**
     * 插入一条队伍信息
     * @param teamId   队伍id
     * @param uniqueId uuid
     * @param identity 身份标识
     * @param scale    分润比例
     * @return Boolean
     */
    Boolean insertTeamPlay(String teamId, UUID uniqueId, int identity, int scale);

    /**
     * 根据teamId获取队伍 普通队员身份列表 信息
     * @param teamId 队伍id
     * @return List<TeamPlay>
     */
    List<TeamPlay> getByTeamIdAndIdentity(String teamId);

    /**
     * 获取队伍拥有者的信息
     * @param teamId 队伍id
     * @return TeamPlay
     */
    TeamPlay getTeamOwner(String teamId);

    /**
     * 玩家uuid是否是某个队伍中的人
     * @param uniqueId uuid
     * @return Boolean
     */
    Boolean isTeamPlay(UUID uniqueId);

    /**
     * 通过shopName 获取到商店队伍从而获取商店队伍的list
     * @param shopName 商店名
     * @return List<TeamPlay>
     */
    List<TeamPlay> getTeamPlayListByShopName(String shopName);
}
