package starshop.starshop.ui.menu;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import starshop.starshop.common.constants.Constant;
import starshop.starshop.common.constants.MenuTypeTitle;
import starshop.starshop.domain.vo.AllShopVo;
import starshop.starshop.service.ShopService;
import starshop.starshop.service.impl.ShopServiceImpl;
import starshop.starshop.utils.HeadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lxh
 * @version 1.0
 * @description -初始菜单单例
 * @date 2023/4/27
 */
@Data
public class ShowAllShopMenu {

    private volatile static ShowAllShopMenu instance;

    /**
     * 唯一菜单
     */
    private final Inventory menu;

    private final ShopService shopService = new ShopServiceImpl();
    private final Integer[] itemSlot = new Integer[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};
    private final Integer[] banSlot = new Integer[]{1, 3, 4, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 49, 50, 51, 52};
    private Integer pageNo = 1;
    private Integer pageSize = 28;
    private List<AllShopVo> allShopVoList;
    private Map<Integer, AllShopVo> shopSlotMap = new HashMap<>();

    /**
     * 构造菜单
     */
    private ShowAllShopMenu() {
        menu = Bukkit.createInventory(null, Constant.MENU_SIZE, MenuTypeTitle.SHOW_ALL_SHOP);
        for (Integer item : banSlot) {
            menu.setItem(item, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
        }

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("我的商店");
        chest.setItemMeta(chestMeta);
        menu.setItem(2, chest);

        ItemStack ice = new ItemStack(Material.ICE);
        ItemMeta iceMeta = ice.getItemMeta();
        iceMeta.setDisplayName("§9 §l 返回上一级");
        ice.setItemMeta(iceMeta);
        menu.setItem(8, ice);

        refreshShopList(pageNo, pageSize);
    }

    /**
     * 单例模式
     */
    public static ShowAllShopMenu getInstance() {
        if (instance == null) {
            synchronized (ShowAllShopMenu.class) {
                //  双重检测机制
                if (instance == null) {
                    instance = new ShowAllShopMenu();
                }
            }
        }
        return instance;
    }

    public void refreshShopList(Integer pageNo, Integer pageSize) {
        shopSlotMap.clear();
        allShopVoList = shopService.getAllShopInfoPage(pageNo, pageSize);
        for (int i = 0; i < allShopVoList.size(); i++) {
            AllShopVo item = allShopVoList.get(i);
            List<String> nbtDataList = HeadUtil.getNbtDataList(item.getHeadData());
            //把每个头都放到对应的slot上去
            ItemStack skull = HeadUtil.getHeadItem(nbtDataList);
            ItemMeta meta = skull.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + allShopVoList.get(i).getShopName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "商店状态: " + (allShopVoList.get(i).getShopStatus() == 1 ? ChatColor.GREEN + "在线" : "下线"));
            lore.add(ChatColor.GRAY + "累销金额: " + allShopVoList.get(i).getAmount());
            lore.add(ChatColor.GRAY + "商店描述: " + allShopVoList.get(i).getDescribe());
            meta.setLore(lore);
            skull.setItemMeta(meta);
            shopSlotMap.put(itemSlot[i], allShopVoList.get(i));
            menu.setItem(itemSlot[i], skull);
        }
    }

}
