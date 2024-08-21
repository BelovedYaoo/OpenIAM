package top.belovedyaoo.openiam.permission.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import top.belovedyaoo.openiam.core.sensitization.Sensitization;
import top.belovedyaoo.openiam.core.sensitization.SensitizationType;
import top.belovedyaoo.openiam.core.base.BaseFiled;

import java.io.Serializable;

/**
 * (Account)表实体类
 *
 * @author BelovedYaoo
 * @since 2024-05-11 18:35:10
 */
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@Getter(onMethod_ = @JsonGetter)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true, fluent = true)
@Table(value = "account", dataSource = "permission")
public class Account extends BaseFiled implements Serializable {

    /**
     * 登录ID，该ID为用户登录使用
     */
    private String openId;

    /**
     * 登录密码，使用哈希散列加密存储
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 手机号
     */
    @Sensitization(type = SensitizationType.MOBILE_PHONE)
    private String phone;

    /**
     * 邮箱地址
     */
    @Sensitization(type = SensitizationType.EMAIL, prefixLen = 3, suffixLen = 2)
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatarAddress;

}

