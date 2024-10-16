package top.belovedyaoo.openiam.oauth2.scope.handler;

import top.belovedyaoo.openiam.oauth2.SaOAuth2Manager;
import top.belovedyaoo.openiam.oauth2.consts.SaOAuth2Consts;
import top.belovedyaoo.openiam.oauth2.data.model.AccessTokenModel;
import top.belovedyaoo.openiam.oauth2.data.model.ClientTokenModel;
import top.belovedyaoo.openiam.oauth2.scope.CommonScope;

/**
 * OpenId 权限处理器：在 AccessToken 扩展参数中追加 openid 字段
 *
 * @author click33
 * @since 1.39.0
 */
public class OpenIdScopeHandler implements SaOAuth2ScopeHandlerInterface {

    public String getHandlerScope() {
        return CommonScope.OPENID;
    }

    @Override
    public void workAccessToken(AccessTokenModel at) {
        at.extraData.put(SaOAuth2Consts.ExtraField.openid, SaOAuth2Manager.getDataLoader().getOpenid(at.clientId, at.loginId));
    }

    @Override
    public void workClientToken(ClientTokenModel ct) {

    }

}