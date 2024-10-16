package top.belovedyaoo.openiam.oauth2.annotation.handler;

import cn.dev33.satoken.annotation.handler.SaAnnotationHandlerInterface;
import cn.dev33.satoken.context.SaHolder;
import top.belovedyaoo.openiam.oauth2.SaOAuth2Manager;
import top.belovedyaoo.openiam.oauth2.annotation.SaCheckAccessToken;

import java.lang.reflect.Method;

/**
 * 注解 SaCheckAccessToken 的处理器
 *
 * @author click33
 * @since 1.39.0
 */
public class SaCheckAccessTokenHandler implements SaAnnotationHandlerInterface<SaCheckAccessToken> {

    @Override
    public Class<SaCheckAccessToken> getHandlerAnnotationClass() {
        return SaCheckAccessToken.class;
    }

    @Override
    public void checkMethod(SaCheckAccessToken at, Method method) {
        _checkMethod(at.scope());
    }

    public static void _checkMethod(String[] scope) {
        String accessToken = SaOAuth2Manager.getDataResolver().readAccessToken(SaHolder.getRequest());
        SaOAuth2Manager.getTemplate().checkAccessTokenScope(accessToken, scope);
    }

}