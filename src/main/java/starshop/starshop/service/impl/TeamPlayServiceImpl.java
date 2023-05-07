package starshop.starshop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import starshop.starshop.domain.TeamPlay;
import starshop.starshop.service.TeamPlayService;
import starshop.starshop.utils.SqlUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public class TeamPlayServiceImpl implements TeamPlayService {
    @Override
    public Boolean insertTeamPlay(String teamId, UUID uniqueId, int identity, int scale) {
        int insert = SqlUtil.insert("insert into team_play(team_id,play_id,identity,scale) values (?,?,?,?)", teamId, uniqueId, identity, scale);
        return insert != 0;
    }

    @Override
    public List<TeamPlay> getByTeamIdAndIdentity(String teamId) {
        return SqlUtil.queryForList("select * from team_play t where t.team_id=? and identity=1", TeamPlay.class, teamId);
    }

    @Override
    public TeamPlay getTeamOwner(String teamId) {
        return SqlUtil.queryForObject("select * from team_play t left join head h on t.play_id=h.play_id where t.team_id=? and identity=0", TeamPlay.class, teamId);
    }

    @Override
    public Boolean isTeamPlay(UUID uniqueId) {
        TeamPlay teamPlay = SqlUtil.queryForObject("select * from team_play t where t.play_id=?", TeamPlay.class, uniqueId);
        return ObjectUtil.isNotEmpty(teamPlay);
    }

    @Override
    public List<TeamPlay> getTeamPlayListByShopName(String shopName) {
        return SqlUtil.queryForList("select t.* from shop s left join team_play t on t.team_id=s.team_id where s.shop_name=?", TeamPlay.class, shopName);
    }
}
