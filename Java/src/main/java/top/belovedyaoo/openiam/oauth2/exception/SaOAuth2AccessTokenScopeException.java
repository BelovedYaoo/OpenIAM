package top.belovedyaoo.openiam.oauth2.exception;

/**
 * 一个异常：代表 Access-Token Scope 相关错误
 * 
 * @author click33
 * @since 1.39.0
 */
public class SaOAuth2AccessTokenScopeException extends SaOAuth2AccessTokenException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130114L;

	/**
	 * 一个异常：代表 Access-Token Scope 相关错误
	 * @param cause 根异常原因
	 */
	public SaOAuth2AccessTokenScopeException(Throwable cause) {
		super(cause);
	}

	/**
	 * 一个异常：代表 Access-Token Scope 相关错误
	 * @param message 异常描述
	 */
	public SaOAuth2AccessTokenScopeException(String message) {
		super(message);
	}

	/**
	 * 具体引起异常的 Access-Token 值
	 */
	public String accessToken;

	/**
	 * 具体引起异常的 scope 值
	 */
	public String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public SaOAuth2AccessTokenScopeException setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public String getScope() {
		return scope;
	}

	public SaOAuth2AccessTokenScopeException setScope(String scope) {
		this.scope = scope;
		return this;
	}

	/**
	 * 如果 flag==true，则抛出 message 异常
	 * @param flag 标记
	 * @param message 异常信息 
	 * @param code 异常细分码 
	 */
	public static void throwBy(boolean flag, String message, int code) {
		if(flag) {
			throw new SaOAuth2AccessTokenScopeException(message).setCode(code);
		}
	}
	
}
