package starshop.starshop.ui.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.common.constants.MenuTypeTitle;
import starshop.starshop.domain.Shop;
import starshop.starshop.domain.TeamPlay;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -初始菜单单例
 * @date 2023/4/27
 */
public class CreateShopMenu {

    public static TeamPlay team = new TeamPlay();
    public static Map<UUID, Shop> createShopInfoMap = new HashMap<>();
    private volatile static CreateShopMenu instance;
    /**
     * 唯一菜单
     */
    private final Inventory menu;

    /**
     * 构造菜单
     */
    private CreateShopMenu() {
        menu = Bukkit.createInventory(null, Constant.MENU_SIZE, MenuTypeTitle.CREATE_SHOP);
        Integer[] slot = new Integer[]{1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 49, 50, 51, 52};
        for (Integer item : slot) {
            menu.setItem(item, new ItemStack(Material.STAINED_GLASS_PANE));
        }
        ItemStack ice = new ItemStack(Material.ICE);
        ItemMeta iceMeta = ice.getItemMeta();
        iceMeta.setDisplayName("§9 §l 返回上一级");
        ice.setItemMeta(iceMeta);
        menu.setItem(8, ice);

        ItemStack inputItem = new ItemStack(Material.NAME_TAG);
        ItemMeta inputMeta = inputItem.getItemMeta();
        inputMeta.setDisplayName("请输入商店名：");
        inputItem.setItemMeta(inputMeta);
        menu.setItem(21, inputItem);

        ItemStack inputItem2 = new ItemStack(Material.NAME_TAG);
        ItemMeta inputMeta2 = inputItem2.getItemMeta();
        inputMeta2.setDisplayName("请输入商店描述：");
        inputItem2.setItemMeta(inputMeta2);
        menu.setItem(23, inputItem2);

        ItemStack confirmItem = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmMeta = confirmItem.getItemMeta();
        confirmMeta.setDisplayName("确认");
        confirmItem.setItemMeta(confirmMeta);
        menu.setItem(49, confirmItem);

    }

    /**
     * 单例模式
     */
    public static CreateShopMenu getInstance() {
        if (instance == null) {
            synchronized (CreateShopMenu.class) {
                //  双重检测机制
                if (instance == null) {
                    instance = new CreateShopMenu();
                }
            }
        }
        return instance;
    }


    /**
     * 获取菜单
     */
    public Inventory getMenu() {
        return menu;
    }

}
