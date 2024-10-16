package top.belovedyaoo.openiam.oauth2.annotation.handler;

import cn.dev33.satoken.annotation.handler.SaAnnotationHandlerInterface;
import cn.dev33.satoken.context.SaHolder;
import top.belovedyaoo.openiam.oauth2.SaOAuth2Manager;
import top.belovedyaoo.openiam.oauth2.annotation.SaCheckClientToken;

import java.lang.reflect.Method;

/**
 * 注解 SaCheckAccessToken 的处理器
 *
 * @author click33
 * @since 1.39.0
 */
public class SaCheckClientTokenHandler implements SaAnnotationHandlerInterface<SaCheckClientToken> {

    @Override
    public Class<SaCheckClientToken> getHandlerAnnotationClass() {
        return SaCheckClientToken.class;
    }

    @Override
    public void checkMethod(SaCheckClientToken at, Method method) {
        _checkMethod(at.scope());
    }

    public static void _checkMethod(String[] scope) {
        String clientToken = SaOAuth2Manager.getDataResolver().readClientToken(SaHolder.getRequest());
        SaOAuth2Manager.getTemplate().checkClientTokenScope(clientToken, scope);
    }

}