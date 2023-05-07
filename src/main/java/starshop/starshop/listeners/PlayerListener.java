package starshop.starshop.listeners;

import com.mojang.authlib.GameProfile;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import starshop.starshop.StarShop;
import starshop.starshop.utils.HeadUtil;
import starshop.starshop.utils.LogUtil;


/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/4/25
 */
public class PlayerListener implements Listener {

    @EventHandler
    @SneakyThrows
    public void onClick(PlayerInteractEvent e) {
    }


    @EventHandler
    public void onLoginGame(PlayerJoinEvent e) {
        LogUtil.info("玩家登录游戏");
        //首先判断登录用户是否为新用户
        Player player = e.getPlayer();
        CraftPlayer craftPlayer = (CraftPlayer) player;
        GameProfile profile = craftPlayer.getProfile();
        //异步更新玩家头颅信息
        Bukkit.getScheduler().runTaskAsynchronously(StarShop.instance, () -> {
            HeadUtil.updateSkin(player, profile);
        });
    }

}
