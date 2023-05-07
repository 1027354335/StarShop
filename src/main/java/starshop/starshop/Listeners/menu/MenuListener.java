package starshop.starshop.Listeners.menu;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import starshop.starshop.common.constants.MenuTypeTitle;
import starshop.starshop.ui.menu.CreateOrJoinMenu;


/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/4/25
 */
public class MenuListener implements Listener {


    @EventHandler
    @SneakyThrows
    public void onMenuClick(InventoryClickEvent e) {
        MenuEvent menuEvent = null;
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        if (ObjectUtil.isEmpty(inventory) || StrUtil.isEmpty(inventory.getTitle()) || ObjectUtil.isEmpty(e.getCurrentItem())) {
            return;
        }
        //分别检测多种菜单实现职能划分
        switch (inventory.getTitle()) {
            case MenuTypeTitle.CREATE_OR_JOIN: {
                menuEvent = new CreateOrJoinEven();
                break;
            }
            case MenuTypeTitle.SINGLE_SHOP: {
                menuEvent = new SingleShopEven();
                break;
            }
            case MenuTypeTitle.CREATE_SHOP: {
                menuEvent = new CreateShopEven();
                break;
            }
            case MenuTypeTitle.SHOW_ALL_SHOP: {
                menuEvent = new ShowAllShopEven();
                break;
            }
            case MenuTypeTitle.BUY_INFO:
                menuEvent = new BuyInfoEven();
                break;
            default:
                break;
        }
        if (ObjectUtil.isNotEmpty(menuEvent)) {
            assert menuEvent != null;
            menuEvent.execute(e, player);
        }
    }

    @EventHandler
    public void onMenuDrag(InventoryDragEvent e) {
        Inventory menu = CreateOrJoinMenu.getInstance().getMenu();
        //检测玩家拖动的物品是否在规定范围内
        for (int slot : e.getRawSlots()) {
            if (slot < menu.getSize()) {
                e.setCancelled(true);
                return;
            }
        }
    }
}
