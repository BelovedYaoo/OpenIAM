package top.belovedyaoo.openiam.oauth2.function;


import top.belovedyaoo.openiam.core.result.Result;

import java.util.function.BiFunction;

/**
 * 函数式接口：登录函数
 *
 * <p>  参数：name, pwd  </p>
 * <p>  返回：登录状态  </p>
 *
 * @author BelovedYaoo
 * @version 1.0
 */
@FunctionalInterface
public interface DoLoginFunction extends BiFunction<String, String, Result> {

}