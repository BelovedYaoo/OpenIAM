package top.belovedyaoo.openiam.oauth2.scope.handler;

import top.belovedyaoo.openiam.oauth2.consts.SaOAuth2Consts;
import top.belovedyaoo.openiam.oauth2.data.model.AccessTokenModel;
import top.belovedyaoo.openiam.oauth2.data.model.ClientTokenModel;
import top.belovedyaoo.openiam.oauth2.scope.CommonScope;

/**
 * UserId 权限处理器：在 AccessToken 扩展参数中追加 userid 字段
 *
 * @author click33
 * @since 1.39.0
 */
public class UserIdScopeHandler implements SaOAuth2ScopeHandlerInterface {

    public String getHandlerScope() {
        return CommonScope.USERID;
    }

    @Override
    public void workAccessToken(AccessTokenModel at) {
        at.extraData.put(SaOAuth2Consts.ExtraField.userid, at.loginId);
    }

    @Override
    public void workClientToken(ClientTokenModel ct) {

    }

}