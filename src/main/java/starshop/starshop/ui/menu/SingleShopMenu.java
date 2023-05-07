package starshop.starshop.ui.menu;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.common.constants.MenuTypeTitle;
import starshop.starshop.domain.Head;
import starshop.starshop.domain.Product;
import starshop.starshop.domain.Shop;
import starshop.starshop.domain.TeamPlay;
import starshop.starshop.service.HeadService;
import starshop.starshop.service.ProductService;
import starshop.starshop.service.TeamPlayService;
import starshop.starshop.service.impl.HeadServiceImpl;
import starshop.starshop.service.impl.ProductServiceImpl;
import starshop.starshop.service.impl.TeamPlayServiceImpl;
import starshop.starshop.utils.HeadUtil;
import starshop.starshop.utils.SpigotUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -已创建单体商店菜单
 * @date 2023/4/27
 */
@Data
public class SingleShopMenu {
    private final HeadService headService;
    private final TeamPlayService teamPlayService;
    private final ProductService productService;
    private Inventory menu;
    private Integer pageNo = 1;
    private Integer pageSize = 28;

    @SneakyThrows
    public SingleShopMenu(Shop shop) {
        headService = new HeadServiceImpl();
        teamPlayService = new TeamPlayServiceImpl();
        productService = new ProductServiceImpl();
        setMenu(shop);
        initHeadInfo(shop);
        initProduct(shop, pageNo, pageSize);
    }

    public Inventory getMenu() {
        return menu;
    }

    public void setMenu(Shop shop) {
        menu = Bukkit.createInventory(null, Constant.MENU_SIZE, MenuTypeTitle.SINGLE_SHOP);
        Integer[] slot = new Integer[]{1, 2, 3, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 49, 50, 51, 52};
        for (Integer item : slot) {
            menu.setItem(item, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14));
        }

        ItemStack ice = new ItemStack(Material.ICE);
        ItemMeta iceMeta = ice.getItemMeta();
        iceMeta.setDisplayName("返回上一级");
        ice.setItemMeta(iceMeta);
        menu.setItem(8, ice);
    }

    /**
     * 初始化商店界面队伍介绍
     */
    private void initHeadInfo(Shop shop) {
        //通过shopName 获取到商店队伍从而获取商店队伍的list
        List<TeamPlay> teamPlayList = teamPlayService.getTeamPlayListByShopName(shop.getShopName());
        //循环增加头颅材质
        ItemStack skull = null;
        for (TeamPlay teamPlay : teamPlayList) {
            if (teamPlay.getIdentity() == 0) {
                //从数据库获取用户NBT数据
                Head head = headService.isHaveHead(UUID.fromString(teamPlay.getPlayId()), SpigotUtil.getNameById(UUID.fromString(teamPlay.getPlayId())));
                if (ObjectUtil.isNotEmpty(head)) {
                    List<String> nbtDataList = HeadUtil.getNbtDataList(head.getHeadData());
                    //,SpigotUtil.getNameById(UUID.fromString(head.getPlayId())),head.getPlayId()
                    skull = HeadUtil.getHeadItem(nbtDataList);
                } else {
                    //把默认的头放上去
                    skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                }
            }
        }
        //循环增加头颅lore数据
        assert skull != null;
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + shop.getShopName());
        List<String> lore = new ArrayList<>();
        teamPlayList.sort(Comparator.comparingInt(TeamPlay::getIdentity));
        for (TeamPlay teamPlay : teamPlayList) {
            if (teamPlay.getIdentity() == 0) {
                lore.add(ChatColor.GOLD + "商店创始人: " + SpigotUtil.getNameById(UUID.fromString(teamPlay.getPlayId())));
            } else {
                lore.add(ChatColor.GRAY + "商店成员: " + SpigotUtil.getNameById(UUID.fromString(teamPlay.getPlayId())));
            }
        }
        lore.add(ChatColor.GRAY + "累销售金额: " + (shop.getAmount() == null ? "0.0" : shop.getAmount()));
        lore.add(ChatColor.GRAY + "商店创建时间: " + shop.getCreateTime());
        itemMeta.setLore(lore);
        skull.setItemMeta(itemMeta);
        menu.setItem(4, skull);
    }


    /**
     * 初始化该商店内商品
     */
    private void initProduct(Shop shop, Integer pageNo, Integer pageSize) {
        Integer[] slot = new Integer[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
        //获取该商品所有物品数据
        List<Product> productList = productService.getProductPageByShopId(shop.getShopId(), pageNo, pageSize);
        for (int i = 0; i < productList.size(); i++) {
            Product item = productList.get(i);
            ItemStack productItem = SpigotUtil.getProductItem(item.getData());
            ItemMeta itemMeta = productItem.getItemMeta();
            if (itemMeta != null) {
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "商品价格: " + item.getPrice());
                lore.add(ChatColor.GRAY + "商品剩余数量: " + item.getCount());
                itemMeta.setLore(lore);
                productItem.setItemMeta(itemMeta);
                menu.setItem(slot[i], productItem);
            }
        }
    }
}
