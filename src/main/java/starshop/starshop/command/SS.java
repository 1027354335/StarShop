package starshop.starshop.command;


import cn.hutool.core.util.ObjectUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import starshop.starshop.StarShop;
import starshop.starshop.domain.Shop;
import starshop.starshop.service.ShopService;
import starshop.starshop.service.VaultService;
import starshop.starshop.service.impl.ShopServiceImpl;
import starshop.starshop.service.impl.VaultServiceImpl;
import starshop.starshop.ui.menu.ShowAllShopMenu;
import starshop.starshop.ui.menu.SingleShopMenu;
import starshop.starshop.utils.MsgUtil;
import starshop.starshop.utils.SpigotUtil;
import starshop.starshop.utils.SqlUtil;

import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/4/27
 */
public class SS implements CommandExecutor {
    private final ShopService shopService = new ShopServiceImpl();
    private final VaultService vaultService = new VaultServiceImpl();
    StarShop starShop;

    public SS(StarShop starShop) {
        this.starShop = starShop;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "openShop":
                    openShop(sender);
                    return true;
                case "testAdd":
                    testAdd(sender);
                    return true;
                case "deposit":
                    deposit(sender, args);
                    return true;
                case "withdraw":
                    withdraw(sender, args);
                    return true;
                case "transfer":
                    transfer(sender, args);
                    return true;
                case "get":
                    get(sender);
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    private void get(CommandSender sender) {
        Player player = (Player) sender;
        Double balance = vaultService.getBalance(player.getUniqueId());
        MsgUtil.sendSuccessMessage(player, "余额为:" + balance);
    }

    private void transfer(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        double amount = 0;
        if (args.length > 1) {
            amount = Double.parseDouble(args[1]);
        }
        boolean transfer = vaultService.transfer(UUID.fromString("461ce3aa-938e-32c3-a6a5-4ceca205040c"), player.getUniqueId(), amount);
        if (transfer) {
            MsgUtil.sendSuccessMessage(player, "转账" + amount + "成功");
        }
    }

    private void withdraw(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        double amount = 0;
        if (args.length > 1) {
            amount = Double.parseDouble(args[1]);
        }
        boolean withdraw = vaultService.withdraw(player.getUniqueId(), amount);
        if (withdraw) {
            MsgUtil.sendSuccessMessage(player, "打钱" + amount + "成功");
        }
    }

    private void deposit(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        double amount = 0;
        if (args.length > 1) {
            amount = Double.parseDouble(args[1]);
        }
        boolean deposit = vaultService.deposit(player.getUniqueId(), amount);
        if (deposit) {
            MsgUtil.sendSuccessMessage(player, "打钱" + amount + "成功");
        }
    }

    private void testAdd(CommandSender sender) {
        //检测手上的物品（获取物品信息然后上架
        Player player = (Player) sender;
        Shop shop = shopService.getShopNameByPlayId(player.getUniqueId());
        if (ObjectUtil.isEmpty(shop)) {
            MsgUtil.sendErrorMessage(player, "您没有商店呦~先去创建商店");
        } else {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) {
                return;
            }
            item.setAmount(1);
            String encodeProductData = SpigotUtil.getEncodeProductData(item);
            //保存物品到数据库
            SqlUtil.insert("insert into product(shop_id,data,price,count) values (?,?,?,?)", shop.getShopId(), encodeProductData, 233, 233);
            SingleShopMenu singleShopMenu = new SingleShopMenu(shop);
            player.openInventory(singleShopMenu.getMenu());
        }
    }

    private void openShop(CommandSender sender) {
        Player player = (Player) sender;
        //打开主菜单
        ShowAllShopMenu instance = ShowAllShopMenu.getInstance();
        instance.refreshShopList(1, 28);
        Inventory menu1 = instance.getMenu();

        player.openInventory(menu1);
    }
}
