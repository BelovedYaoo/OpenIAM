package top.belovedyaoo.openiam.oauth2.handler;

import cn.dev33.satoken.context.model.SaRequest;
import top.belovedyaoo.openiam.oauth2.SaOAuth2Manager;
import top.belovedyaoo.openiam.oauth2.consts.GrantType;
import top.belovedyaoo.openiam.oauth2.consts.SaOAuth2Consts;
import top.belovedyaoo.openiam.oauth2.data.model.AccessTokenModel;
import top.belovedyaoo.openiam.oauth2.data.model.request.ClientIdAndSecretModel;

import java.util.List;

/**
 * authorization_code grant_type 处理器
 *
 * @author click33
 * @since 1.39.0
 */
public class AuthorizationCodeGrantTypeHandler implements SaOAuth2GrantTypeHandlerInterface {

    @Override
    public String getHandlerGrantType() {
        return GrantType.authorization_code;
    }

    @Override
    public AccessTokenModel getAccessToken(SaRequest req, String clientId, List<String> scopes) {
        // 获取参数
        ClientIdAndSecretModel clientIdAndSecret = SaOAuth2Manager.getDataResolver().readClientIdAndSecret(req);
//        String clientId = clientIdAndSecret.clientId;
        String clientSecret = clientIdAndSecret.clientSecret;
        String code = req.getParamNotNull(SaOAuth2Consts.Param.code);
        String redirectUri = req.getParam(SaOAuth2Consts.Param.redirect_uri);

        // 校验参数
        SaOAuth2Manager.getTemplate().checkGainTokenParam(code, clientId, clientSecret, redirectUri);

        // 构建 Access-Token、返回
        AccessTokenModel accessTokenModel = SaOAuth2Manager.getDataGenerate().generateAccessToken(code);
        return accessTokenModel;
    }

}