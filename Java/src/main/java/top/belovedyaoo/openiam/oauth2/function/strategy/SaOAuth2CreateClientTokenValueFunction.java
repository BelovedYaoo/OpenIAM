package top.belovedyaoo.openiam.oauth2.function.strategy;

import java.util.List;

/**
 * 函数式接口：创建一个 ClientToken value
 *
 * @author click33
 * @since 1.39.0
 */
@FunctionalInterface
public interface SaOAuth2CreateClientTokenValueFunction {

    /**
     * 创建一个 ClientToken value
     * @param clientId 应用id
     * @param scopes 权限
     * @return ClientToken value
     */
    String execute(String clientId, List<String> scopes);

}