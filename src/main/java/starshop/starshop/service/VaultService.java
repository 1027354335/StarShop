package starshop.starshop.service;

import java.util.UUID;

/**
 * @author lxh
 * @version 1.0
 * @description -
 * @date 2023/5/6
 */
public interface VaultService {
    /**
     * 格式化金钱标志
     * @param balance 账户
     * @return
     */
    String format(double balance);

    /**
     * 获取用户账户余额
     * @param uuid 用户id
     * @return Double
     */
    Double getBalance(UUID uuid);

    /**
     * 转账
     * @param from   发送者
     * @param to     接收者
     * @param amount 额度
     * @return true / false
     */
    boolean transfer(UUID from, UUID to, Double amount);

    /**
     * 给某个账户打钱
     * @param uuid   用户
     * @param amount 金额
     * @return boolean
     */
    boolean deposit(UUID uuid, Double amount);

    /**
     * 提取某个账户的钱
     * @param uuid   用户
     * @param amount 金额
     * @return boolean
     */
    boolean withdraw(UUID uuid, Double amount);
}
