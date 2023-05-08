package starshop.starshop.listeners.menu;

import cn.hutool.core.util.ObjectUtil;
import de.tr7zw.nbtapi.NBTItem;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.ui.menu.BuyInfoMenu;
import starshop.starshop.ui.menu.ShowAllShopMenu;
import starshop.starshop.utils.LogUtil;

import java.awt.*;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/8
 */
public class SingleShopEven extends MenuEvent {
    @Override
    @SneakyThrows
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
            LogUtil.info("{}",event);
            ItemStack cursor = event.getCursor();

            player.closeInventory();
            Inventory menu = ShowAllShopMenu.getInstance().getMenu();
            InventoryView inventoryView = player.openInventory(menu);
            inventoryView.setCursor(cursor);
        } else {
            //点击获取物品 跳转到购买数量界面（在这里写查询余额 购买物品 更新余额 更新商品数据）
            BuyInfoMenu buyInfoMenu=new BuyInfoMenu(currentItem);
            player.closeInventory();
            player.openInventory(buyInfoMenu.getMenu());
        }
    }
}
