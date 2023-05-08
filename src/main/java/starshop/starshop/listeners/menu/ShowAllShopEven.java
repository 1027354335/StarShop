package starshop.starshop.listeners.menu;

import cn.hutool.core.util.ObjectUtil;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.domain.Shop;
import starshop.starshop.domain.vo.AllShopVo;
import starshop.starshop.service.ShopService;
import starshop.starshop.service.TeamPlayService;
import starshop.starshop.service.impl.ShopServiceImpl;
import starshop.starshop.service.impl.TeamPlayServiceImpl;
import starshop.starshop.ui.menu.CreateOrJoinMenu;
import starshop.starshop.ui.menu.ShowAllShopMenu;
import starshop.starshop.ui.menu.SingleShopMenu;
import starshop.starshop.utils.LogUtil;
import starshop.starshop.utils.MsgUtil;

import java.util.Map;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/8
 */
public class ShowAllShopEven extends MenuEvent {
    private final ShopService shopService = new ShopServiceImpl();
    private final TeamPlayService teamPlayService = new TeamPlayServiceImpl();

    @Override
    public void execute(InventoryClickEvent event, Player player) {
        //禁止玩家操作
        if (event.getRawSlot() < Constant.MENU_SIZE || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getClick() == ClickType.MIDDLE) {
            event.setCancelled(true);
        }
        //展示所有商店的界面  点击进入每一个商店的详细界面
        ItemStack currentItem = event.getCurrentItem();
        if (ObjectUtil.isEmpty(currentItem) || currentItem.getType() == Material.AIR || currentItem.getType() == Material.STAINED_GLASS_PANE) {
            return;
        }
        if (event.getRawSlot() == 2) {
            //点击 我的商店 界面
            player.closeInventory();
            UUID uniqueId = player.getUniqueId();
            //检测如果玩家在某个队伍里面则跳转 SingleStore
            Boolean is = teamPlayService.isTeamPlay(uniqueId);
            if (is) {
                Shop shop = shopService.getShopNameByPlayId(player.getUniqueId());
                MsgUtil.sendWarningMessage(player, "这里应该是管理界面，先用购买界面凑合");
                SingleShopMenu singleShopMenu = new SingleShopMenu(shop);
                player.closeInventory();
                player.openInventory(singleShopMenu.getMenu());
            } else {
                player.openInventory(CreateOrJoinMenu.getInstance().getMenu());
            }
        } else if (event.getRawSlot() == 8) {
            ItemStack cursor = player.getItemOnCursor();
            //关闭界面显示
            player.closeInventory();
            player.setItemOnCursor(cursor);
        } else {
            NBTItem nbtItem = new NBTItem(currentItem);
            LogUtil.info("物品信息:{}", nbtItem);
            //处理进入商城详细界面逻辑
            Map<Integer, AllShopVo> shopSlotMap = ShowAllShopMenu.getInstance().getShopSlotMap();
            AllShopVo allShopVo = shopSlotMap.get(event.getRawSlot());
            Shop shop = shopService.getShopInfoByShopName(allShopVo.getShopName());
            SingleShopMenu singleShopMenu = new SingleShopMenu(shop);
            player.closeInventory();
            player.openInventory(singleShopMenu.getMenu());
        }
    }
}
