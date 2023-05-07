package starshop.starshop.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 带 -  的UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 不带 - 的UUID
     */
    public static String getContinuouslyUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}