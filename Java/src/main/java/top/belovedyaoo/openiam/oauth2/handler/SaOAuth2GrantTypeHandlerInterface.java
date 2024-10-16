package top.belovedyaoo.openiam.oauth2.handler;

import cn.dev33.satoken.context.model.SaRequest;
import top.belovedyaoo.openiam.oauth2.data.model.AccessTokenModel;

import java.util.List;

/**
 * 所有 OAuth2 GrantType 处理器的父接口
 *
 * @author click33
 * @since 1.39.0
 */
public interface SaOAuth2GrantTypeHandlerInterface {

    /**
     * 获取所要处理的 GrantType
     *
     * @return /
     */
    String getHandlerGrantType();

    /**
     * 获取 AccessTokenModel 对象
     *
     * @param req /
     * @return /
     */
    AccessTokenModel getAccessToken(SaRequest req, String clientId, List<String> scopes);

}