package top.belovedyaoo.openiam.oauth2.function;


import java.util.function.Supplier;

/**
 * 函数式接口：OAuth-Server端 未登录时返回的View
 *
 * <p>  参数：clientId, scope  </p>
 * <p>  返回：view 视图  </p>
 *
 * @author click33
 * @since 1.39.0
 */
@FunctionalInterface
public interface SaOAuth2NotLoginViewFunction extends Supplier<Object> {

}