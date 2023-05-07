package starshop.starshop.service.impl;

import starshop.starshop.domain.Product;
import starshop.starshop.service.ProductService;
import starshop.starshop.utils.SqlUtil;

import java.util.List;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/4
 */
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getProductPageByShopId(Integer shopId, Integer pageNo, Integer pageSize) {
        return SqlUtil.queryForPage("select * from product p where p.shop_id=?", Product.class, pageNo, pageSize, shopId);
    }
}
