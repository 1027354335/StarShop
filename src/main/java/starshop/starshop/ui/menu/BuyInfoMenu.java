package starshop.starshop.ui.menu;

import lombok.Data;
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
@Data
public class BuyInfoMenu {


    /**
     * 唯一菜单
     */
    private Inventory menu;
    /**
     * 点击的物品
     */
    private ItemStack itemStack;

    /**
     * 构造菜单
     */
    private BuyInfoMenu() {
        setMenu();
    }

    public void setMenu() {
        menu = Bukkit.createInventory(null, Constant.MENU_SIZE, MenuTypeTitle.CREATE_OR_JOIN);
        Integer[] slot = new Integer[]{1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 50, 51, 52};
        for (Integer item : slot) {
            menu.setItem(item, new ItemStack(Material.STAINED_GLASS_PANE));
        }
        ItemStack ice = new ItemStack(Material.ICE);
        ItemMeta iceMeta = ice.getItemMeta();
        iceMeta.setDisplayName("§9 §l 返回上一级");
        ice.setItemMeta(iceMeta);
        menu.setItem(8, ice);

        ItemStack stone = new ItemStack(Material.CHEST);
        ItemMeta stoneMeta = stone.getItemMeta();
        stoneMeta.setDisplayName("&9&l点击输入购买数量");
        stone.setItemMeta(stoneMeta);
        stoneMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        menu.setItem(22, stone);

        ItemStack confirmItem = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmMeta = confirmItem.getItemMeta();
        confirmMeta.setDisplayName("确认");
        confirmItem.setItemMeta(confirmMeta);
        menu.setItem(49, confirmItem);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
