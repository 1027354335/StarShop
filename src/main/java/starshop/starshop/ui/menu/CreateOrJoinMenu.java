package starshop.starshop.ui.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.common.constants.MenuTypeTitle;

/**
 * @author lxh
 * @version 1.0
 * @description -初始菜单单例
 * @date 2023/4/27
 */
public class CreateOrJoinMenu {

    private volatile static CreateOrJoinMenu instance;

    /**
     * 唯一菜单
     */
    private final Inventory menu;

    /**
     * 构造菜单
     */
    private CreateOrJoinMenu() {
        menu = Bukkit.createInventory(null, Constant.MENU_SIZE, MenuTypeTitle.CREATE_OR_JOIN);
        Integer[] slot = new Integer[]{1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 49, 50, 51, 52};
        for (Integer item : slot) {
            menu.setItem(item, new ItemStack(Material.STAINED_GLASS_PANE));
        }
        ItemStack ice = new ItemStack(Material.ICE);
        ItemMeta iceMeta = ice.getItemMeta();
        iceMeta.setDisplayName("§9 §l 返回上一级");
        ice.setItemMeta(iceMeta);
        menu.setItem(8, ice);

        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = diamond.getItemMeta();
        diamondMeta.setDisplayName("§9 §l 点击创建一个商店");
        diamond.setItemMeta(diamondMeta);
        diamondMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        menu.setItem(21, diamond);


        ItemStack stone = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta stoneMeta = stone.getItemMeta();
        stoneMeta.setDisplayName("§9 §l 点击加入一个已有的");
        stone.setItemMeta(stoneMeta);
        stoneMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        menu.setItem(23, stone);
    }

    /**
     * 单例模式
     */
    public static CreateOrJoinMenu getInstance() {
        if (instance == null) {
            synchronized (CreateOrJoinMenu.class) {
                //  双重检测机制
                if (instance == null) {
                    instance = new CreateOrJoinMenu();
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
