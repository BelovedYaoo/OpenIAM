package top.belovedyaoo.openiam.oauth2.strategy;

import cn.dev33.satoken.SaManager;
import top.belovedyaoo.openiam.oauth2.OpenAuthManager;
import top.belovedyaoo.openiam.oauth2.config.ServerConfig;
import top.belovedyaoo.openiam.oauth2.consts.OpenAuthGrantType;
import top.belovedyaoo.openiam.oauth2.consts.OpenAuthConst;
import top.belovedyaoo.openiam.oauth2.data.model.loader.OpenAuthClientModel;
import top.belovedyaoo.openiam.oauth2.data.model.request.ClientIdAndSecretModel;
import top.belovedyaoo.openiam.oauth2.error.OpenAuthErrorCode;
import top.belovedyaoo.openiam.oauth2.exception.OpenAuthException;
import top.belovedyaoo.openiam.oauth2.function.strategy.*;
import top.belovedyaoo.openiam.oauth2.handler.AuthorizationCodeGrantTypeHandler;
import top.belovedyaoo.openiam.oauth2.handler.PasswordGrantTypeHandler;
import top.belovedyaoo.openiam.oauth2.handler.RefreshTokenGrantTypeHandler;
import top.belovedyaoo.openiam.oauth2.handler.GrantTypeHandlerInterface;
import top.belovedyaoo.openiam.oauth2.scope.CommonScope;
import top.belovedyaoo.openiam.oauth2.scope.handler.OidcScopeHandler;
import top.belovedyaoo.openiam.oauth2.scope.handler.OpenIdScopeHandler;
import top.belovedyaoo.openiam.oauth2.scope.handler.OpenAuthScopeHandlerInterface;
import top.belovedyaoo.openiam.oauth2.scope.handler.UserIdScopeHandler;
import cn.dev33.satoken.util.SaFoxUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenAuth 相关策略
 *
 * @author BelovedYaoo
 * @version 1.0
 */
public final class OpenAuthStrategy {

	private OpenAuthStrategy() {
		registerDefaultScopeHandler();
		registerDefaultGrantTypeHandler();
	}

	/**
	 * 全局单例引用
	 */
	public static final OpenAuthStrategy INSTANCE = new OpenAuthStrategy();

	// 权限处理器

	/**
	 * 权限处理器集合
	 */
	public Map<String, OpenAuthScopeHandlerInterface> scopeHandlerMap = new LinkedHashMap<>();

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
	public void registerScopeHandler(OpenAuthScopeHandlerInterface handler) {
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
	public ScopeWorkAccessTokenFunction workAccessTokenByScope = (at) -> {
		if(at.scopes != null && !at.scopes.isEmpty()) {
			for (String scope : at.scopes) {
				OpenAuthScopeHandlerInterface handler = scopeHandlerMap.get(scope);
				if(handler != null) {
					handler.workAccessToken(at);
				}
			}
		}
		OpenAuthScopeHandlerInterface finallyWorkScopeHandler = scopeHandlerMap.get(OpenAuthConst._FINALLY_WORK_SCOPE);
		if(finallyWorkScopeHandler != null) {
			finallyWorkScopeHandler.workAccessToken(at);
		}
	};

	/**
	 * 根据 scope 信息对一个 ClientTokenModel 进行加工处理
	 */
	public ScopeWorkClientTokenFunction workClientTokenByScope = (ct) -> {
		if(ct.scopes != null && !ct.scopes.isEmpty()) {
			for (String scope : ct.scopes) {
				OpenAuthScopeHandlerInterface handler = scopeHandlerMap.get(scope);
				if(handler != null) {
					handler.workClientToken(ct);
				}
			}
		}
		OpenAuthScopeHandlerInterface finallyWorkScopeHandler = scopeHandlerMap.get(OpenAuthConst._FINALLY_WORK_SCOPE);
		if(finallyWorkScopeHandler != null) {
			finallyWorkScopeHandler.workClientToken(ct);
		}
	};

	// grant_type 处理器

	/**
	 * grant_type 处理器集合
	 */
	public Map<String, GrantTypeHandlerInterface> grantTypeHandlerMap = new LinkedHashMap<>();

	/**
	 * 注册所有默认的权限处理器
	 */
	public void registerDefaultGrantTypeHandler() {
		grantTypeHandlerMap.put(OpenAuthGrantType.authorization_code, new AuthorizationCodeGrantTypeHandler());
		grantTypeHandlerMap.put(OpenAuthGrantType.password, new PasswordGrantTypeHandler());
		grantTypeHandlerMap.put(OpenAuthGrantType.refresh_token, new RefreshTokenGrantTypeHandler());
	}

	/**
	 * 注册一个权限处理器
	 */
	public void registerGrantTypeHandler(GrantTypeHandlerInterface handler) {
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
	public GrantTypeAuthFunction grantTypeAuth = (req) -> {
		String grantType = req.getParamNotNull(OpenAuthConst.Param.grant_type);
		GrantTypeHandlerInterface grantTypeHandler = grantTypeHandlerMap.get(grantType);
		if(grantTypeHandler == null) {
			throw new OpenAuthException("无效 grant_type: " + grantType).setCode(OpenAuthErrorCode.CODE_30126);
		}

		// 看看全局是否开启了此 grantType
		ServerConfig config = OpenAuthManager.getServerConfig();
		if(grantType.equals(OpenAuthGrantType.authorization_code) && !config.getEnableAuthorizationCode() ) {
			throw new OpenAuthException("系统未开放的 grant_type: " + grantType).setCode(OpenAuthErrorCode.CODE_30126);
		}
		if(grantType.equals(OpenAuthGrantType.password) && !config.getEnablePassword() ) {
			throw new OpenAuthException("系统未开放的 grant_type: " + grantType).setCode(OpenAuthErrorCode.CODE_30126);
		}

		// 校验 clientSecret 和 scope
		ClientIdAndSecretModel clientIdAndSecretModel = OpenAuthManager.getDataResolver().readClientIdAndSecret(req);
		List<String> scopes = OpenAuthManager.getDataConverter().convertScopeStringToList(req.getParam(OpenAuthConst.Param.scope));
		OpenAuthClientModel clientModel = OpenAuthManager.getTemplate().checkClientSecretAndScope(clientIdAndSecretModel.getClientId(), clientIdAndSecretModel.getClientSecret(), scopes);

		// 检测应用是否开启此 grantType
		if(!clientModel.getAllowGrantTypes().contains(grantType)) {
			throw new OpenAuthException("应用未开放的 grant_type: " + grantType).setCode(OpenAuthErrorCode.CODE_30141);
		}

		// 调用 处理器
		return grantTypeHandler.getAccessToken(req, clientIdAndSecretModel.getClientId(), scopes);
	};


	// ----------------------- 所有策略

	/**
	 * 创建一个 code value
	 */
	public CreateCodeValueFunction createCodeValue = (clientId, loginId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

	/**
	 * 创建一个 AccessToken value
	 */
	public CreateAccessTokenValueFunction createAccessToken = (clientId, loginId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

	/**
	 * 创建一个 RefreshToken value
	 */
	public CreateRefreshTokenValueFunction createRefreshToken = (clientId, loginId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

	/**
	 * 创建一个 ClientToken value
	 */
	public CreateClientTokenValueFunction createClientToken = (clientId, scopes) -> {
		return SaFoxUtil.getRandomString(60);
	};

}
