package top.belovedyaoo.openiam.oauth2.function;


import java.util.function.BiFunction;

/**
 * 函数式接口：登录函数
 *
 * <p>  参数：name, pwd  </p>
 * <p>  返回：认证返回结果  </p>
 *
 * @author click33
 * @since 1.39.0
 */
@FunctionalInterface
public interface SaOAuth2DoLoginHandleFunction extends BiFunction<String, String, Object> {

}