package top.belovedyaoo.openiam.oauth2.strategy;

import cn.dev33.satoken.SaManager;
import top.belovedyaoo.openiam.oauth2.SaOAuth2Manager;
import top.belovedyaoo.openiam.oauth2.config.SaOAuth2ServerConfig;
import top.belovedyaoo.openiam.oauth2.consts.GrantType;
import top.belovedyaoo.openiam.oauth2.consts.SaOAuth2Consts;
import top.belovedyaoo.openiam.oauth2.data.model.loader.SaClientModel;
import top.belovedyaoo.openiam.oauth2.data.model.request.ClientIdAndSecretModel;
import top.belovedyaoo.openiam.oauth2.error.SaOAuth2ErrorCode;
import top.belovedyaoo.openiam.oauth2.exception.SaOAuth2Exception;
import top.belovedyaoo.openiam.oauth2.function.strategy.*;
import top.belovedyaoo.openiam.oauth2.handler.AuthorizationCodeGrantTypeHandler;
import top.belovedyaoo.openiam.oauth2.handler.PasswordGrantTypeHandler;
import top.belovedyaoo.openiam.oauth2.handler.RefreshTokenGrantTypeHandler;
import top.belovedyaoo.openiam.oauth2.handler.SaOAuth2GrantTypeHandlerInterface;
import top.belovedyaoo.openiam.oauth2.scope.CommonScope;
import top.belovedyaoo.openiam.oauth2.scope.handler.OidcScopeHandler;
import top.belovedyaoo.openiam.oauth2.scope.handler.OpenIdScopeHandler;
import top.belovedyaoo.openiam.oauth2.scope.handler.SaOAuth2ScopeHandlerInterface;
import top.belovedyaoo.openiam.oauth2.scope.handler.UserIdScopeHandler;
import cn.dev33.satoken.util.SaFoxUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Sa-Token OAuth2 相关策略
 *
 * @author click33
 * @since 1.39.0
 */
public final class SaOAuth2Strategy {

	private SaOAuth2Strategy() {
		registerDefaultScopeHandler();
		registerDefaultGrantTypeHandler();
	}

	/**
	 * 全局单例引用
	 */
	public static final SaOAuth2Strategy instance = new SaOAuth2Strategy();

	// 权限处理器

	/**
	 * 权限处理器集合
	 */
	public Map<String, SaOAuth2ScopeHandlerInterface> scopeHandlerMap = new LinkedHashMap<>();

	/**
	 * 注册所有默认的权限处理器
	 */
	public void registerDefaultScopeHandler() {
		scopeHandlerMap.put(CommonScope.OPENID, new OpenIdScopeHandler());
		scopeHandlerMap.put(CommonScope.USERID, new UserIdScopeHandler());
		scopeHandlerMap.put(CommonScope.OIDC, new OidcScopeHandler());
	}

	/**
	 * 注册一个权限处理器
	 */
	public void registerScopeHandler(SaOAuth2ScopeHandlerInterface handler) {
		scopeHandlerMap.put(handler.getHandlerScope(), handler);
		SaManager.getLog().info("自定义 SCOPE [{}] (处理器: {})", handler.getHandlerScope(), handler.getClass().getCanonicalName());
	}

	/**
	 * 移除一个权限处理器
	 */
	public void removeScopeHandler(String scope) {
		scopeHandlerMap.remove(scope);
	}

	/**
	 * 根据 scope 信息对一个 AccessTokenModel 进行加工处理
	 */
	public SaOAuth2ScopeWorkAccessTokenFunction workAccessTokenByScope = (at) -> {
		if(at.scopes != null && !at.scopes.isEmpty()) {
			for (String scope : at.scopes) {
				SaOAuth2ScopeHandlerInterface handler = scopeHandlerMap.get(scope);
				if(handler != null) {
					handler.workAccessToken(at);
				}
			}
		}
		SaOAuth2ScopeHandlerInterface finallyWorkScopeHandler = scopeHandlerMap.get(SaOAuth2Consts._FINALLY_WORK_SCOPE);
		if(finallyWorkScopeHandler != null) {
			finallyWorkScopeHandler.workAccessToken(at);
		}
	};

	/**
	 * 根据 scope 信息对一个 ClientTokenModel 进行加工处理
	 */
	public SaOAuth2ScopeWorkClientTokenFunction workClientTokenByScope = (ct) -> {
		if(ct.scopes != null && !ct.scopes.isEmpty()) {
			for (String scope : ct.scopes) {
				SaOAuth2ScopeHandlerInterface handler = scopeHandlerMap.get(scope);
				if(handler != null) {
					handler.workClientToken(ct);
				}
			}
		}
		SaOAuth2ScopeHandlerInterface finallyWorkScopeHandler = scopeHandlerMap.get(SaOAuth2Consts._FINALLY_WORK_SCOPE);
		if(finallyWorkScopeHandler != null) {
			finallyWorkScopeHandler.workClientToken(ct);
		}
	};

	// grant_type 处理器

	/**
	 * grant_type 处理器集合
	 */
	public Map<String, SaOAuth2GrantTypeHandlerInterface> grantTypeHandlerMap = new LinkedHashMap<>();

	/**
	 * 注册所有默认的权限处理器
	 */
	public void registerDefaultGrantTypeHandler() {
		grantTypeHandlerMap.put(GrantType.authorization_code, new AuthorizationCodeGrantTypeHandler());
		grantTypeHandlerMap.put(GrantType.password, new PasswordGrantTypeHandler());
		grantTypeHandlerMap.put(GrantType.refresh_token, new RefreshTokenGrantTypeHandler());
	}

	/**
	 * 注册一个权限处理器
	 */
	public void registerGrantTypeHandler(SaOAuth2GrantTypeHandlerInterface handler) {
		grantTypeHandlerMap.put(handler.getHandlerGrantType(), handler);
		SaManager.getLog().info("自定义 GRANT_TYPE [{}] (处理器: {})", handler.getHandlerGrantType(), handler.getClass().getCanonicalName());
	}

	/**
	 * 移除一个权限处理器
	 */
	public void removeGrantTypeHandler(String scope) {
		scopeHandlerMap.remove(scope);
	}

	/**
	 * 根据 scope 信息对一个 AccessTokenModel 进行加工处理
	 */
	public SaOAuth2GrantTypeAuthFunction grantTypeAuth = (req) -> {
		String grantType = req.getParamNotNull(SaOAuth2Consts.Param.grant_type);
		SaOAuth2GrantTypeHandlerInterface grantTypeHandler = grantTypeHandlerMap.get(grantType);
		if(grantTypeHandler == null) {
			throw new SaOAuth2Exception("无效 grant_type: " + grantType).setCode(SaOAuth2ErrorCode.CODE_30126);
		}

		// 看看全局是否开启了此 grantType
		SaOAuth2ServerConfig config = SaOAuth2Manager.getServerConfig();
		if(grantType.equals(GrantType.authorization_code) && !config.getEnableAuthorizationCode() ) {
			throw new SaOAuth2Exception("系统未开放的 grant_type: " + grantType).setCode(SaOAuth2ErrorCode.CODE_30126);
		}
		if(grantType.equals(GrantType.password) && !config.getEnablePassword() ) {
			throw new SaOAuth2Exception("系统未开放的 grant_type: " + grantType).setCode(SaOAuth2ErrorCode.CODE_30126);
		}

		// 校验 clientSecret 和 scope
		ClientIdAndSecretModel clientIdAndSecretModel = SaOAuth2Manager.getDataResolver().readClientIdAndSecret(req);
		List<String> scopes = SaOAuth2Manager.getDataConverter().convertScopeStringToList(req.getParam(SaOAuth2Consts.Param.scope));
		SaClientModel clientModel = SaOAuth2Manager.getTemplate().checkClientSecretAndScope(clientIdAndSecretModel.getClientId(), clientIdAndSecretModel.getClientSecret(), scopes);

		// 检测应用是否开启此 grantType
		if(!clientModel.getAllowGrantTypes().contains(grantType)) {
			throw new SaOAuth2Exception("应用未开放的 grant_type: " + grantType).setCode(SaOAuth2ErrorCode.CODE_30141);
		}

		// 调用 处理器
		return grantTypeHandler.getAccessToken(req, clientIdAndSecretModel.getClientId(), scopes);
	};


	// ----------------------- 所有策略

	/**
	 * 创建一个 code value
	 */
	public SaOAuth2CreateCodeValueFunction createCodeValue = (clientId, loginId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

	/**
	 * 创建一个 AccessToken value
	 */
	public SaOAuth2CreateAccessTokenValueFunction createAccessToken = (clientId, loginId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

	/**
	 * 创建一个 RefreshToken value
	 */
	public SaOAuth2CreateRefreshTokenValueFunction createRefreshToken = (clientId, loginId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

	/**
	 * 创建一个 ClientToken value
	 */
	public SaOAuth2CreateClientTokenValueFunction createClientToken = (clientId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

}
