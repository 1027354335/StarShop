package starshop.starshop.listeners.menu;

import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import starshop.starshop.StarShop;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.domain.Shop;
import starshop.starshop.service.ShopService;
import starshop.starshop.service.TeamPlayService;
import starshop.starshop.service.impl.ShopServiceImpl;
import starshop.starshop.service.impl.TeamPlayServiceImpl;
import starshop.starshop.ui.menu.CreateOrJoinMenu;
import starshop.starshop.ui.menu.CreateShopMenu;
import starshop.starshop.ui.menu.SingleShopMenu;
import starshop.starshop.ui.textgui.AnvilGUI;
import starshop.starshop.utils.DateUtil;
import starshop.starshop.utils.MsgUtil;
import starshop.starshop.utils.SqlUtil;
import starshop.starshop.utils.UUIDUtil;

import java.util.Date;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/8
 */
public class CreateShopEven extends MenuEvent {
    private final ShopService shopService = new ShopServiceImpl();
    private final TeamPlayService teamPlayService = new TeamPlayServiceImpl();

    @Override
    @SneakyThrows
    public void execute(InventoryClickEvent event, Player player) {
        //禁止玩家操作
        if (event.getRawSlot() < Constant.MENU_SIZE || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getClick() == ClickType.MIDDLE) {
            event.setCancelled(true);
        }
        Shop shop = CreateShopMenu.createShopInfoMap.get(player.getUniqueId());
        //禁止玩家拿菜单物品
        if (event.getRawSlot() < Constant.MENU_SIZE || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            event.setCancelled(true);
        }
        if (event.getRawSlot() == 21) {
            new AnvilGUI(StarShop.instance, player, "输入商店名", (player1, reply) -> {
                shop.setShopName(reply);
                return null;
            });
        } else if (event.getRawSlot() == 23) {
            //商店队伍的描述
            new AnvilGUI(StarShop.instance, player, "输入商店队伍简介", (player1, reply) -> {
                shop.setDescribe(reply);
                return null;
            });
        } else if (event.getRawSlot() == 49) {
            //判断当前商店名/描述是否为空
            if (StringUtils.isEmpty(shop.getShopName()) || StringUtils.isEmpty(shop.getDescribe())) {
                MsgUtil.sendErrorMessage(player, "没有填写商店名以及商店描述");
                return;
            }
            //判断是否有相同的商店名
            Boolean isHaveSample = shopService.isSameShopName(shop.getShopName());
            if (isHaveSample) {
                MsgUtil.sendErrorMessage(player, "该商店名已存在");
                return;
            }
            //假设上面已经输如了商店名 下面是创建队伍和商店的操作
            //首先创建团队
            //创建事务
            SqlUtil.beginTransaction();
            String teamId = UUIDUtil.getContinuouslyUUID();
            Boolean isInsertTeam = teamPlayService.insertTeamPlay(teamId, player.getUniqueId(), 0, 100);

            String createTime = DateUtil.dateToString(new Date(), DateUtil.DATE_FORMAT_DAY);
            shop.setTeamId(teamId);
            shop.setCreateTime(createTime);
            //其次创建商店
            Boolean isInsertShop = shopService.insertShopInfo(shop.getShopName(), teamId, shop.getDescribe(), createTime, createTime);
            //判断返回
            if (!isInsertShop && !isInsertTeam) {
                MsgUtil.sendErrorMessage(player, "创建商店失败，请联系管理员");
                SqlUtil.rollbackTransaction();
                player.closeInventory();
                player.openInventory(CreateOrJoinMenu.getInstance().getMenu());
            } else {
                MsgUtil.sendSuccessMessage(player, "创建商店成功");
                SqlUtil.commitTransaction();
                player.closeInventory();
                SingleShopMenu singleShopMenu = new SingleShopMenu(shop);
                player.openInventory(singleShopMenu.getMenu());
            }

        }
    }
}
