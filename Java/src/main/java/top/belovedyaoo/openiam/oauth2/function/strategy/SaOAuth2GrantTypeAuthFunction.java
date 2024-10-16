package top.belovedyaoo.openiam.oauth2.function.strategy;

import cn.dev33.satoken.context.model.SaRequest;
import top.belovedyaoo.openiam.oauth2.data.model.AccessTokenModel;

import java.util.function.Function;

/**
 * 函数式接口：GrantType 认证
 *
 * <p>  参数：SaRequest、grant_type </p>
 * <p>  返回：处理结果  </p>
 *
 * @author click33
 * @since 1.39.0
 */
@FunctionalInterface
public interface SaOAuth2GrantTypeAuthFunction extends Function<SaRequest, AccessTokenModel> {

}