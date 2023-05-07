package starshop.starshop.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllShopVo {
    /**
     * 商店名称
     */
    private String shopName;
    /**
     * 商店描述
     */
    private String describe;
    /**
     * 商店状态
     */
    private Integer shopStatus;
    /**
     * 累计售出金额
     */
    private Double amount;
    /**
     * 创始人ID
     */
    private String playId;
    /**
     * 头像data
     */
    private String headData;
}
