package starshop.starshop.Listeners.menu;

import cn.hutool.core.util.ObjectUtil;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.ui.menu.ShowAllShopMenu;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/8
 */
public class SingleShopEven extends MenuEvent {
    @Override
    public void execute(InventoryClickEvent event, Player player) {
        //禁止玩家操作
        if (event.getRawSlot() < Constant.MENU_SIZE || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getClick() == ClickType.MIDDLE) {
            event.setCancelled(true);
        }
        //如果是群组商店界面
        ItemStack currentItem = event.getCurrentItem();
        if (ObjectUtil.isEmpty(currentItem) || currentItem.getType() == Material.AIR || currentItem.getType() == Material.STAINED_GLASS_PANE || currentItem.getType() == Material.SKULL_ITEM) {
            return;
        }
        if (event.getRawSlot() == 8) {

            player.closeInventory();
            player.openInventory(ShowAllShopMenu.getInstance().getMenu());
        } else {
            //点击获取物品 跳转到购买数量界面（在这里写查询余额 购买物品 更新余额 更新商品数据）

            NBTItem nbt = new NBTItem(currentItem);
            ItemStack item = nbt.getItem();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(null);
            item.setItemMeta(itemMeta);

            player.getInventory().addItem(item);
        }
    }
}
