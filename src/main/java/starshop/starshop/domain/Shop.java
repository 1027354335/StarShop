package starshop.starshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxh
 * @version 1.0
 * @description -一个商店的实体
 * @date 2023/4/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    /**
     * 商店id
     */
    private Integer shopId;
    /**
     * 商店名称
     */
    private String shopName;
    /**
     * 商店组id
     */
    private String teamId;
    /**
     * 商店状态
     */
    private Integer shopStatus;
    /**
     * 累计售出金额
     */
    private Double amount;
    /**
     * 商店描述
     */
    private String describe;
    /**
     * 位置
     */
    private String location;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}
