package starshop.starshop.listeners.menu;

import cn.hutool.core.util.ObjectUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import starshop.starshop.domain.Shop;
import starshop.starshop.ui.menu.CreateShopMenu;
import starshop.starshop.ui.menu.JoinShopMenu;
import starshop.starshop.ui.menu.ShowAllShopMenu;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/8
 */
public class CreateOrJoinEven extends MenuEvent {
    @Override
    public void execute(InventoryClickEvent event, Player player) {
        ItemStack currentItem = event.getCurrentItem();
        if (ObjectUtil.isEmpty(currentItem) || currentItem.getType() == Material.AIR || currentItem.getType() == Material.STAINED_GLASS_PANE || currentItem.getType() == Material.SKULL_ITEM) {
            return;
        }
        if (event.getRawSlot() == 8) {
            player.closeInventory();
            player.openInventory(ShowAllShopMenu.getInstance().getMenu());
        } else if (event.getRawSlot() == 21) {
            //如果是创建商店指令-》CreateShopMenu
            player.closeInventory();
            CreateShopMenu.createShopInfoMap.put(player.getUniqueId(), new Shop());
            player.openInventory(CreateShopMenu.getInstance().getMenu());
        } else if (event.getRawSlot() == 23) {
            //如果是加入商店选项-》JoinShopMenu
            player.closeInventory();
            JoinShopMenu instance = JoinShopMenu.getInstance();
            instance.refreshShopList(1, 28);
            player.openInventory(instance.getMenu());
        }
    }
}
