package starshop.starshop;


import cn.hutool.log.LogFactory;
import cn.hutool.log.dialect.jdk.JdkLogFactory;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import starshop.starshop.Listeners.PlayerListener;
import starshop.starshop.Listeners.menu.MenuListener;
import starshop.starshop.command.SS;
import starshop.starshop.sqlite.Db;
import starshop.starshop.utils.LogUtil;

import java.sql.Connection;

/**
 * @author lxh
 */
public final class StarShop extends JavaPlugin {

    public static StarShop instance;

    @Override
    @SneakyThrows
    public void onEnable() {
        instance = this;
        LogFactory.setCurrentLogFactory(new JdkLogFactory());
        LogUtil.info("测试日志输出1");
        LogUtil.info("插件运行哩");
        //注册监听事件
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        //注册命令事件
        this.getCommand("ss").setExecutor(new SS(this));
        //插件运行开始链接sqlite
        Connection connection = Db.getInstance().getConnection();
    }

    @Override
    @SneakyThrows
    public void onDisable() {
        //插件结束时关闭sqlite
        Db.getInstance().getConnection().close();
    }
}
