package top.belovedyaoo.openiam.oauth2.scope.handler;

import top.belovedyaoo.openiam.oauth2.data.model.AccessTokenModel;
import top.belovedyaoo.openiam.oauth2.data.model.ClientTokenModel;

/**
 * 所有 OAuth2 权限处理器的父接口
 *
 * @author click33
 * @since 1.39.0
 */
public interface SaOAuth2ScopeHandlerInterface {

    /**
     * 获取所要处理的权限
     *
     * @return /
     */
    String getHandlerScope();

    /**
     * 当构建的 AccessToken 具有此权限时，所需要执行的方法
     *
     * @param at /
     */
    void workAccessToken(AccessTokenModel at);

    /**
     * 当构建的 ClientToken 具有此权限时，所需要执行的方法
     *
     * @param ct /
     */
    void workClientToken(ClientTokenModel ct);

}