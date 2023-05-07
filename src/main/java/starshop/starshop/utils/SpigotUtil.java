package starshop.starshop.utils;

import lombok.SneakyThrows;
import net.minecraft.server.v1_12_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/3
 */
public class SpigotUtil {
    /**
     * 根据uuid获取玩家名称
     * @param uuid uuid
     * @return 玩家名称
     */
    public static String getNameById(UUID uuid) {
        String playerName = null;
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            playerName = player.getName();
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.hasPlayedBefore()) {
                playerName = offlinePlayer.getName();
            }
        }
        return playerName;
    }


    @SneakyThrows
    public static String getEncodeProductData(org.bukkit.inventory.ItemStack itemStack) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        NBTTagCompound compound = new NBTTagCompound();
        net.minecraft.server.v1_12_R1.ItemStack itemStack1 = CraftItemStack.asNMSCopy(itemStack);
        itemStack1.save(compound);
        NBTCompressedStreamTools.a(compound, outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @SneakyThrows
    public static org.bukkit.inventory.ItemStack getProductItem(String data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
        NBTTagCompound nbtTagCompound = NBTCompressedStreamTools.a(inputStream);
        net.minecraft.server.v1_12_R1.ItemStack nmsItem = new net.minecraft.server.v1_12_R1.ItemStack(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
}
