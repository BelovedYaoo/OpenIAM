package top.prefersmin.backend.permission.entity.po;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import top.prefersmin.backend.common.annotations.Sensitization;
import top.prefersmin.backend.common.annotations.SensitizationType;
import top.prefersmin.backend.common.base.BaseFiled;

import java.io.Serializable;

/**
 * (Account)表实体类
 *
 * @author PrefersMin
 * @since 2024-05-11 18:35:10
 */
@Data
@Builder
@AllArgsConstructor
@Table(value = "account")
@Getter(onMethod_ = @JsonGetter)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true, fluent = true)
public class Account extends BaseFiled implements Serializable {

    /**
     * 账号ID，该ID为系统内部使用
     */
    @Id
    private String accountId;

    /**
     * 登录ID，该ID为用户登录使用
     */
    private String accountLoginId;

    /**
     * 登录密码，使用哈希散列加密存储
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String accountLoginPassword;

    /**
     * 手机号
     */
    @Sensitization(type = SensitizationType.MOBILE_PHONE)
    private String accountPhone;

    /**
     * 邮箱地址
     */
    @Sensitization(type = SensitizationType.EMAIL, prefixLen = 3, suffixLen = 2)
    private String accountEmail;

    /**
     * 昵称
     */
    private String accountNickname;

    /**
     * 头像地址
     */
    private String accountAvatarAddress;

}

