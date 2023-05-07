package starshop.starshop.service;

import starshop.starshop.domain.Shop;
import starshop.starshop.domain.vo.AllShopVo;

import java.util.List;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public interface ShopService {

    /**
     * 新增商店信息
     * @param shopName   商店名
     * @param teamId     teamId
     * @param describe   描述
     * @param createTime 创建时间
     * @param updateTime 更新时间
     * @return Boolean
     */
    Boolean insertShopInfo(String shopName, String teamId, String describe, String createTime, String updateTime);

    /**
     * 根据商店名判断是否有相同的
     * @param shopName 商店名
     * @return Boolean
     */
    Boolean isSameShopName(String shopName);

    /**
     * 分页获取商店列表界面
     * @param pageNo   第n页
     * @param pageSize n条记录
     * @return List<AllShopVo>
     */
    List<AllShopVo> getAllShopInfoPage(int pageNo, int pageSize);

    /**
     * 通过UUID获取商店名
     * @param uniqueId uuid
     * @return 商店名
     */
    Shop getShopNameByPlayId(UUID uniqueId);

    /**
     * 根据商店名获取商店信息
     * @param shopName 商店名
     * @return Shop
     */
    Shop getShopInfoByShopName(String shopName);
}
