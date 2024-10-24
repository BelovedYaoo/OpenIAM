/// <reference types="./_baseFiled" />

/**
 * (Account) 表持久化对象
 * @author BelovedYaoo
 * @version 1.1
 */
interface Account extends BaseFiled {
    openId?: string;
    password?: string;
    phone?: string;
    email?: string;
    nickname?: string;
    avatarAddress?: string;
}