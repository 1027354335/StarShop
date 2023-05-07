package starshop.starshop.Listeners.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import starshop.starshop.StarShop;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.ui.textgui.AnvilGUI;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/8
 */
public class BuyInfoEven extends MenuEvent {
    @Override
    public void execute(InventoryClickEvent event, Player player) {
        //禁止玩家操作
        if (event.getRawSlot() < Constant.MENU_SIZE || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getClick() == ClickType.MIDDLE) {
            event.setCancelled(true);
        }

        new AnvilGUI(StarShop.instance, player, "输入购买数量", (player1, reply) -> {
            return null;
        });
    }
}
