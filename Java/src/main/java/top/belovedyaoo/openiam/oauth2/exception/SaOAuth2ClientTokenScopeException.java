package top.belovedyaoo.openiam.oauth2.exception;

/**
 * 一个异常：代表 Client-Token Scope 相关错误
 * 
 * @author click33
 * @since 1.39.0
 */
public class SaOAuth2ClientTokenScopeException extends SaOAuth2ClientTokenException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130114L;

	/**
	 * 一个异常：代表 Client-Token Scope 相关错误
	 * @param cause 根异常原因
	 */
	public SaOAuth2ClientTokenScopeException(Throwable cause) {
		super(cause);
	}

	/**
	 * 一个异常：代表 Client-Token Scope 相关错误
	 * @param message 异常描述
	 */
	public SaOAuth2ClientTokenScopeException(String message) {
		super(message);
	}

	/**
	 * 具体引起异常的 Client-Token 值
	 */
	public String clientToken;

	/**
	 * 具体引起异常的 scope 值
	 */
	public String scope;

	public String getClientToken() {
		return clientToken;
	}

	public SaOAuth2ClientTokenScopeException setClientToken(String clientToken) {
		this.clientToken = clientToken;
		return this;
	}

	public String getScope() {
		return scope;
	}

	public SaOAuth2ClientTokenScopeException setScope(String scope) {
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
			throw new SaOAuth2ClientTokenScopeException(message).setCode(code);
		}
	}
	
}
