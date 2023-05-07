package starshop.starshop.utils;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/2
 */
public class BeanUtil {
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (Object o : object) {
                if (!isEmpty(o)) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        if (obj.getClass().isArray()) {
            if (Array.getLength(obj) == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < Array.getLength(obj); i++) {
                Object o = Array.get(obj, i);
                if (!isEmpty(o)) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        if (obj instanceof Optional) {
            Optional<?> optional = (Optional<?>) obj;
            return !optional.isPresent() || isEmpty(optional.get());
        }
        return false;
    }
}
