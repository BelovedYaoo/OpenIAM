package top.belovedyaoo.openiam.oauth2.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import top.belovedyaoo.agcore.result.Result;
import top.belovedyaoo.openiam.oauth2.consts.OpenAuthGrantType;
import top.belovedyaoo.openiam.oauth2.data.loader.OpenAuthDataLoader;
import top.belovedyaoo.openiam.oauth2.data.model.loader.OpenAuthClientModel;
import top.belovedyaoo.openiam.oauth2.enums.OpenAuthResultEnum;
import top.belovedyaoo.openiam.oauth2.function.ConfirmFunction;
import top.belovedyaoo.openiam.oauth2.function.DoLoginFunction;
import top.belovedyaoo.openiam.oauth2.function.NotLoginFunction;
import top.belovedyaoo.openiam.permission.service.impl.AuthenticationServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Sa-Token OAuth2：自定义数据加载器
 *
 * @author click33
 */
@Component
@RequiredArgsConstructor
public class SaOAuth2DataLoaderImpl implements OpenAuthDataLoader {

	private final AuthenticationServiceImpl authenticationService;
	
	// 根据 clientId 获取 Client 信息
	@Override
	public OpenAuthClientModel getClientModel(String clientId) {
		if("1001".equals(clientId)) {
			return new OpenAuthClientModel()
					.setClientId("1001")
					.setClientSecret("aaaa-bbbb-cccc-dddd-eeee")
					.addAllowRedirectUris("*")
					.addContractScopes("openid", "userid", "userinfo", "oidc")
					.addAllowGrantTypes(
							OpenAuthGrantType.authorization_code,
							OpenAuthGrantType.implicit,
							OpenAuthGrantType.refresh_token,
							OpenAuthGrantType.password,
							OpenAuthGrantType.client_credentials,
							"phone_code"
					)
			;
		}
		return null;
	}
	
	// 根据 clientId 和 loginId 获取 openid
	@Override
	public String getOpenid(String clientId, Object loginId) {
		// 此处使用框架默认算法生成 openid，真实环境建议改为从数据库查询
		return OpenAuthDataLoader.super.getOpenid(clientId, loginId);
	}

	public NotLoginFunction notLogin() {
		return () -> Result.failed().resultType(OpenAuthResultEnum.NEED_LOGIN);
	}

	public DoLoginFunction doLogin() {
		return authenticationService::openLogin;
	}

	public ConfirmFunction confirm() {
		return (clientId, scopes) -> Result.success().resultType(OpenAuthResultEnum.NEED_CONFIRM).data("clientId", clientId).data("scope", scopes);
	}

}
