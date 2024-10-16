package top.belovedyaoo.openiam.oauth2.handler;

import cn.dev33.satoken.context.model.SaRequest;
import top.belovedyaoo.openiam.oauth2.SaOAuth2Manager;
import top.belovedyaoo.openiam.oauth2.consts.GrantType;
import top.belovedyaoo.openiam.oauth2.consts.SaOAuth2Consts;
import top.belovedyaoo.openiam.oauth2.data.model.AccessTokenModel;
import top.belovedyaoo.openiam.oauth2.data.model.RefreshTokenModel;
import top.belovedyaoo.openiam.oauth2.error.SaOAuth2ErrorCode;
import top.belovedyaoo.openiam.oauth2.exception.SaOAuth2ClientModelException;
import top.belovedyaoo.openiam.oauth2.exception.SaOAuth2RefreshTokenException;

import java.util.List;

/**
 * refresh_token grant_type 处理器
 *
 * @author click33
 * @since 1.39.0
 */
public class RefreshTokenGrantTypeHandler implements SaOAuth2GrantTypeHandlerInterface {

    @Override
    public String getHandlerGrantType() {
        return GrantType.refresh_token;
    }

    @Override
    public AccessTokenModel getAccessToken(SaRequest req, String clientId, List<String> scopes) {
        // 获取参数
        String refreshToken = req.getParamNotNull(SaOAuth2Consts.Param.refresh_token);

        // 校验：Refresh-Token 是否存在
        RefreshTokenModel rt = SaOAuth2Manager.getDao().getRefreshToken(refreshToken);
        SaOAuth2RefreshTokenException.throwBy(rt == null, "无效refresh_token: " + refreshToken, refreshToken, SaOAuth2ErrorCode.CODE_30111);

        // 校验：Refresh-Token 代表的 ClientId 与提供的 ClientId 是否一致
        SaOAuth2ClientModelException.throwBy( ! rt.clientId.equals(clientId), "无效client_id: " + clientId, clientId, SaOAuth2ErrorCode.CODE_30122);

        // 获取新 Access-Token
        AccessTokenModel accessTokenModel = SaOAuth2Manager.getDataGenerate().refreshAccessToken(refreshToken);
        return accessTokenModel;
    }

}