package starshop.starshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 商店id
     */
    private Integer shopId;
    /**
     * 商品的NBT数据
     */
    private String data;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品的数量
     */
    private String count;
}
