package starshop.starshop.utils;

import cn.hutool.core.util.ObjectUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import lombok.SneakyThrows;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import starshop.starshop.domain.Head;
import starshop.starshop.service.HeadService;
import starshop.starshop.service.impl.HeadServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/4/28
 */
public class HeadUtil {
    private static final HeadService headService = new HeadServiceImpl();

    public static String getId(NBTItem headNbt) {
        return headNbt.getCompound("SkullOwner").getString("Id");
    }

    public static String getSignature(NBTItem headNbt) {
        NBTCompoundList compoundList = headNbt.getCompound("SkullOwner").getCompound("Properties").getCompoundList("textures");
        return compoundList.get(0).getString("Signature");
    }

    public static String getValue(NBTItem headNbt) {
        NBTCompoundList compoundList = headNbt.getCompound("SkullOwner").getCompound("Properties").getCompoundList("textures");
        return compoundList.get(0).getString("Value");
    }

    public static String getName(NBTItem headNbt) {
        return headNbt.getCompound("SkullOwner").getString("Name");
    }

    @SneakyThrows
    public static org.bukkit.inventory.ItemStack getHeadItem(List<String> list) {
        //String id,String signature,String value,String name
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        NBTItem nbtItem = new NBTItem(head);
        if (list == null || list.size() == 0) {
            return nbtItem.getItem();
        }
        NBTCompound skull = nbtItem.addCompound("SkullOwner");
        skull.setString("Name", list.get(3));
        skull.setString("Id", list.get(0));
        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Signature", list.get(1));
        texture.setString("Value", list.get(2));

        return nbtItem.getItem();
    }

    @SneakyThrows
    public static void updateSkin(Player player, GameProfile newProfile) {
        // 获取 CraftPlayer 对象
        CraftPlayer craftPlayer = (CraftPlayer) player;
        // 获取 EntityPlayer 对象
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        // 设置新的 GameProfile 对象
        entityPlayer.getProfile().getProperties().clear();
        for (Property property : newProfile.getProperties().get("textures")) {
            entityPlayer.getProfile().getProperties().put("textures", property);
        }

        //获取玩家是否有皮肤信息存储
        Head head = headService.isHaveHead(player.getUniqueId(), player.getName());
        if (ObjectUtil.isEmpty(head)) {
            NBTItem nbtItem = new NBTItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
            for (int i = 0; i < 3; i++) {
                // 发送 PacketPlayOutPlayerInfo 包更新皮肤信息
                PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
                sendPacket(player, packet);
                packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                sendPacket(player, packet);

                org.bukkit.inventory.ItemStack playerHead = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwningPlayer(player);
                playerHead.setItemMeta(skullMeta);
                nbtItem = new NBTItem(playerHead);
                if (nbtItem.getCompound("SkullOwner").getCompound("Properties") != null) {
                    break;
                }
            }
            //没有相关信息新增一条玩家信息
            Boolean is = headService.insertHeadInfo(player.getUniqueId(), player.getName(), getNBTData(nbtItem), DateUtil.dateToString(new Date(), DateUtil.DATE_FORMAT_DAY));
            if (!is) {
                MsgUtil.sendErrorMessage(player, "皮肤存储失败");
            }
        } else if (head.getHeadData().length() < 20) {
            NBTItem nbtItem = new NBTItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
            for (int i = 0; i < 3; i++) {
                // 发送 PacketPlayOutPlayerInfo 包更新皮肤信息
                PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
                sendPacket(player, packet);
                packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                sendPacket(player, packet);

                org.bukkit.inventory.ItemStack playerHead = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwningPlayer(player);
                playerHead.setItemMeta(skullMeta);
                nbtItem = new NBTItem(playerHead);
                if (nbtItem.getCompound("SkullOwner").getCompound("Properties") != null) {
                    break;
                }
            }
            if (!getNBTData(nbtItem).equals(head.getHeadData())) {
                Boolean is = headService.updateHeadInfo(head.getPlayId(), head.getPlayName(), getNBTData(nbtItem));
                if (!is) {
                    MsgUtil.sendErrorMessage(player, "皮肤更新失败");
                }
            }
        } else {
            int i = DateUtil.daysBetween(DateUtil.dateToString(new Date(), DateUtil.DATE_FORMAT_DAY), head.getUpdateTime());
            if (i >= 1) {
                NBTItem nbtItem = new NBTItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
                for (int j = 0; j < 3; j++) {
                    // 发送 PacketPlayOutPlayerInfo 包更新皮肤信息
                    PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
                    sendPacket(player, packet);
                    packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer);
                    sendPacket(player, packet);

                    org.bukkit.inventory.ItemStack playerHead = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                    skullMeta.setOwningPlayer(player);
                    playerHead.setItemMeta(skullMeta);
                    nbtItem = new NBTItem(playerHead);
                    if (nbtItem.getCompound("SkullOwner").getCompound("Properties") != null) {
                        break;
                    }
                }
                //如果数据库中data和当前data不一样则更新
                if (!getNBTData(nbtItem).equals(head.getHeadData())) {
                    Boolean is = headService.updateHeadInfo(head.getPlayId(), head.getPlayName(), getNBTData(nbtItem));
                    if (!is) {
                        MsgUtil.sendErrorMessage(player, "皮肤更新失败");
                    }
                }
            }
        }
    }

    @SneakyThrows
    public static void sendPacket(Player player, Packet<?> packet) {
        // 获取 CraftPlayer 对象
        CraftPlayer craftPlayer = (CraftPlayer) player;
        // 获取 PlayerConnection 对象
        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;
        // 发送包
        playerConnection.sendPacket(packet);
    }

    @SneakyThrows
    public static String getNBTData(NBTItem nbtItem) {
        if (nbtItem.getCompound("SkullOwner").getCompound("Properties") == null) {
            return " , , , ,";
        }
        LogUtil.info("{}", nbtItem);
        return getId(nbtItem) + "," + getSignature(nbtItem) + "," + getValue(nbtItem) + "," + getName(nbtItem);
    }

    @SneakyThrows
    public static List<String> getNbtDataList(String data) {
        if (data == null) {
            return null;
        }
        String[] split = data.split(",");
        return Arrays.asList(split);
    }
}