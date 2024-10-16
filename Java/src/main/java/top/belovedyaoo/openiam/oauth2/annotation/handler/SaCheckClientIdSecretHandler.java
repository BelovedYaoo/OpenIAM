package top.belovedyaoo.openiam.oauth2.annotation.handler;

import cn.dev33.satoken.annotation.handler.SaAnnotationHandlerInterface;
import top.belovedyaoo.openiam.oauth2.annotation.SaCheckClientIdSecret;
import top.belovedyaoo.openiam.oauth2.processor.SaOAuth2ServerProcessor;

import java.lang.reflect.Method;

/**
 * 注解 SaCheckClientSecret 的处理器
 *
 * @author click33
 * @since 1.39.0
 */
public class SaCheckClientIdSecretHandler implements SaAnnotationHandlerInterface<SaCheckClientIdSecret> {

    @Override
    public Class<SaCheckClientIdSecret> getHandlerAnnotationClass() {
        return SaCheckClientIdSecret.class;
    }

    @Override
    public void checkMethod(SaCheckClientIdSecret at, Method method) {
        _checkMethod();
    }

    public static void _checkMethod() {
        SaOAuth2ServerProcessor.instance.checkCurrClientSecret();
    }

}