package starshop.starshop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import starshop.starshop.domain.Shop;
import starshop.starshop.domain.vo.AllShopVo;
import starshop.starshop.service.ShopService;
import starshop.starshop.utils.SqlUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public class ShopServiceImpl implements ShopService {

    @Override
    public Boolean insertShopInfo(String shopName, String teamId, String describe, String createTime, String updateTime) {
        int insert = SqlUtil.insert("insert into shop(shop_name,team_id,describe,create_time,update_time) values (?,?,?,?,?)",
                shopName, teamId, describe, createTime, updateTime);
        return insert != 0;
    }

    @Override
    public Boolean isSameShopName(String shopName) {
        Shop shop = SqlUtil.queryForObject("select * from shop where shop_name=?", Shop.class, shopName);
        return ObjectUtil.isNotEmpty(shop);
    }

    @Override
    public List<AllShopVo> getAllShopInfoPage(int pageNo, int pageSize) {
        return SqlUtil.queryForPage("select * from shop s left join team_play t on s.team_id=t.team_id left join head h on h.play_id=t.play_id where t.identity=0", AllShopVo.class, pageNo, pageSize);
    }

    @Override
    public Shop getShopNameByPlayId(UUID uniqueId) {
        return SqlUtil.queryForObject("select s.* from shop s left join team_play t on t.team_id=s.team_id where t.play_id=?", Shop.class, uniqueId);
    }

    @Override
    public Shop getShopInfoByShopName(String shopName) {
        return SqlUtil.queryForObject("select * from shop where shop_name=?", Shop.class, shopName);
    }
}
