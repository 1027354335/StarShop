package starshop.starshop.sqlite;

import lombok.SneakyThrows;
import starshop.starshop.StarShop;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/4/27
 */
public class Db {

    private volatile static Db instance;

    private final Connection connection;

    @SneakyThrows
    public Db() {
        String pluginDir = new File(StarShop.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
        String dbFilePath = pluginDir + File.separator + "shop.db";
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
    }

    /**
     * 单例模式
     */
    public static Db getInstance() {
        if (instance == null) {
            synchronized (Db.class) {
                //  双重检测机制
                if (instance == null) {
                    instance = new Db();
                }
            }
        }
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }
}
