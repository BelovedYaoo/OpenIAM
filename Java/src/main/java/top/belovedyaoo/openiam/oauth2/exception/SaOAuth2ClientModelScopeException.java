package top.belovedyaoo.openiam.oauth2.exception;

/**
 * 一个异常：代表 ClientModel Scope 相关错误
 * 
 * @author click33
 * @since 1.39.0
 */
public class SaOAuth2ClientModelScopeException extends SaOAuth2ClientModelException {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130114L;

	/**
	 * 一个异常：代表 ClientModel Scope 相关错误
	 * @param cause 根异常原因
	 */
	public SaOAuth2ClientModelScopeException(Throwable cause) {
		super(cause);
	}

	/**
	 * 一个异常：代表 ClientModel Scope 相关错误
	 * @param message 异常描述
	 */
	public SaOAuth2ClientModelScopeException(String message) {
		super(message);
	}

	/**
	 * 具体引起异常的 ClientId 值
	 */
	public String clientId;

	/**
	 * 具体引起异常的 scope 值
	 */
	public String scope;

	public String getClientId() {
		return clientId;
	}

	public SaOAuth2ClientModelScopeException setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	public String getScope() {
		return scope;
	}

	public SaOAuth2ClientModelScopeException setScope(String scope) {
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
			throw new SaOAuth2ClientModelScopeException(message).setCode(code);
		}
	}
	
}
