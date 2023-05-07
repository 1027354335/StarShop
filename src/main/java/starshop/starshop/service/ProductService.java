package starshop.starshop.service;

import starshop.starshop.domain.Product;

import java.util.List;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/4
 */
public interface ProductService {

    /**
     * 通过商店ID 获取商品分页
     * @param shopId   shopId
     * @param pageNo   初始页码
     * @param pageSize 页面大小
     * @return List<Product>
     */
    List<Product> getProductPageByShopId(Integer shopId, Integer pageNo, Integer pageSize);

}
