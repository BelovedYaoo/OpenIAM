package top.belovedyaoo.openiam.oauth2.function.strategy;

import top.belovedyaoo.openiam.oauth2.data.model.ClientTokenModel;

import java.util.function.Consumer;

/**
 * 函数式接口：ClientTokenModel 加工
 *
 * <p>  参数：ClientTokenModel </p>
 * <p>  返回：无  </p>
 *
 * @author click33
 * @since 1.39.0
 */
@FunctionalInterface
public interface SaOAuth2ScopeWorkClientTokenFunction extends Consumer<ClientTokenModel> {

}