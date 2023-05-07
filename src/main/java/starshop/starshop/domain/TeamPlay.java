package starshop.starshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxh
 * @version 1.0
 * @description -商店玩家中间表
 * @date 2023/4/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamPlay {
    /**
     * 商店组id
     */
    private String teamId;
    /**
     * 用户UUID
     */
    private String playId;
    /**
     * 用户身份 0组长 1组员
     */
    private Integer identity;
    /**
     * 分润比例 90是90% 40是40%
     */
    private Integer scale;
}
