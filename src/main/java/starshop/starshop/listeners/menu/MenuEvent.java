package starshop.starshop.listeners.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author lxh
 */
public abstract class MenuEvent {
    /**
     * 入口方法
     * @param event  事件
     * @param player 触发事件用户
     */
    public abstract void execute(InventoryClickEvent event, Player player);
}