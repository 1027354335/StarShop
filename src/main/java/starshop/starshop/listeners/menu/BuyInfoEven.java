package starshop.starshop.listeners.menu;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import starshop.starshop.StarShop;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.ui.menu.BuyInfoMenu;
import starshop.starshop.ui.textgui.AnvilGUI;
import starshop.starshop.utils.MsgUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/8
 */
public class BuyInfoEven extends MenuEvent {
    @Override
    public void execute(InventoryClickEvent event, Player player) {
        Inventory inventory = event.getInventory();
        //禁止玩家操作
        if (event.getRawSlot() < Constant.MENU_SIZE || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getClick() == ClickType.MIDDLE) {
            event.setCancelled(true);
        }
        if(event.getRawSlot()==22){
            new AnvilGUI(StarShop.instance, player, "输入购买数量", (player1, reply) -> {
                Map<ItemStack,String> map= new HashMap<>();
                map.put(inventory.getItem(13),reply);
                BuyInfoMenu.buyItemCount.put(player.getUniqueId(), map);
                return "count";
            });
        }
        if (event.getRawSlot()==49){
            //确认购买触发购买逻辑
            NBTItem nbt = new NBTItem(inventory.getItem(13));
            ItemStack item = nbt.getItem();
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(null);
            item.setItemMeta(itemMeta);

            Map<ItemStack, String> itemStackStringMap = BuyInfoMenu.buyItemCount.get(player.getUniqueId());
            String s = itemStackStringMap.get(inventory.getItem(13));
            boolean number = NumberUtil.isNumber(s);
            if(number){
                //如果输入的是数字
                item.setAmount(Integer.parseInt(s));
                player.getInventory().addItem(item);
            }else {
                MsgUtil.sendErrorMessage(player,"请输入数字作为物品数量");
            }
        }


    }
}
