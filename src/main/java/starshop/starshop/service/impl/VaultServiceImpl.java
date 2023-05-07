package starshop.starshop.service.impl;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import starshop.starshop.StarShop;
import starshop.starshop.service.VaultService;
import starshop.starshop.utils.LogUtil;

import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/6
 */
public class VaultServiceImpl implements VaultService {
    private Economy economy;

    public VaultServiceImpl() {
        if (StarShop.instance.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
            if (economyProvider != null) {
                this.economy = economyProvider.getProvider();
            }
        } else {
            LogUtil.info("未找到经济插件");
        }
    }


    @Override
    public String format(double balance) {
        return this.economy.format(balance);
    }

    @Override
    public Double getBalance(UUID uuid) {
        return economy.getBalance(Bukkit.getOfflinePlayer(uuid));
    }

    @Override
    public boolean transfer(UUID from, UUID to, Double amount) {
        OfflinePlayer pFrom = Bukkit.getOfflinePlayer(from);
        OfflinePlayer pTo = Bukkit.getOfflinePlayer(to);
        if (this.economy.getBalance(pFrom) >= amount) {
            if (this.economy.withdrawPlayer(pFrom, amount).transactionSuccess()) {
                if (!this.economy.depositPlayer(pTo, amount).transactionSuccess()) {
                    this.economy.depositPlayer(pFrom, amount);
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean deposit(UUID uuid, Double amount) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
        return this.economy.depositPlayer(p, amount).transactionSuccess();
    }

    @Override
    public boolean withdraw(UUID uuid, Double amount) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
        return this.economy.withdrawPlayer(p, amount).transactionSuccess();
    }
}
