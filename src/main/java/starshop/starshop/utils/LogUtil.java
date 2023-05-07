package starshop.starshop.utils;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import starshop.starshop.StarShop;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/6
 */
public class LogUtil {
    private static final Log log = LogFactory.get();

    public static void info(String format, Object... arguments) {
        if (StarShop.instance.getConfig().getBoolean("logging.enabled")) {
            log.info(format, arguments);
        }
    }
}
