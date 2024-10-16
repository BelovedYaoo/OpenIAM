package top.belovedyaoo.openiam.oauth2.function.strategy;

import java.util.List;

/**
 * 函数式接口：创建一个 code value
 *
 * @author click33
 * @since 1.39.0
 */
@FunctionalInterface
public interface SaOAuth2CreateCodeValueFunction {

    /**
     * 创建一个 code value
     * @param clientId 应用id
     * @param loginId 账号id
     * @param scopes 权限
     * @return code value
     */
    String execute(String clientId, Object loginId, List<String> scopes);

}