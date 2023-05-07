package starshop.starshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxh
 * @version 1.0
 * @description -玩家头材质信息
 * @date 2023/4/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Head {
    /**
     * 玩家UUID
     */
    private String playId;
    /**
     * 用户名
     */
    private String playName;
    /**
     * 头像data
     */
    private String headData;
    /**
     * 更新时间
     */
    private String updateTime;
}
