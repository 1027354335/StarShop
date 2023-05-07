package starshop.starshop.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author lxh
 */
public class MsgUtil {

    public static void sendErrorMessage(Player player, String message) {
        player.sendMessage("§l" + ChatColor.RED + "【错误】" + ChatColor.WHITE + " " + message);
    }

    public static void sendSuccessMessage(Player player, String message) {
        player.sendMessage("§l" + ChatColor.GREEN + "【成功】" + ChatColor.WHITE + " " + message);
    }

    public static void sendWarningMessage(Player player, String message) {
        player.sendMessage("§l" + ChatColor.YELLOW + "【警告】" + ChatColor.WHITE + " " + message);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage("§l" + ChatColor.WHITE + message);
    }
}