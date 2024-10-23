package top.belovedyaoo.openiam.oauth2.function;


import java.util.List;
import java.util.function.BiFunction;

/**
 * 函数式接口：OAuth-Server端 确认授权时返回的View
 *
 * <p>  参数：无  </p>
 * <p>  返回：view 视图  </p>
 *
 * @author click33
 * @since 1.39.0
 */
@FunctionalInterface
public interface SaOAuth2ConfirmViewFunction extends BiFunction<String, List<String>, Object> {

}