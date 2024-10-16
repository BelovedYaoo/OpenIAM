package top.belovedyaoo.openiam.oauth2.exception;

/**
 * 一个异常：代表 Code 授权码相关错误
 * 
 * @author click33
 * @since 1.39.0
 */
public class SaOAuth2AuthorizationCodeException extends SaOAuth2Exception {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 6806129545290130114L;

	/**
	 * 一个异常：代表 Access-Token 相关错误
	 * @param cause 根异常原因
	 */
	public SaOAuth2AuthorizationCodeException(Throwable cause) {
		super(cause);
	}

	/**
	 * 一个异常：代表 Access-Token 相关错误
	 * @param message 异常描述
	 */
	public SaOAuth2AuthorizationCodeException(String message) {
		super(message);
	}

	/**
	 * 具体引起异常的 code 值
	 */
	public String authorizationCode;

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public SaOAuth2AuthorizationCodeException setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
		return this;
	}

	/**
	 * 如果 flag==true，则抛出 message 异常
	 * @param flag 标记
	 * @param message 异常信息 
	 * @param authorizationCode 引入异常的 code 值
	 * @param code 异常细分码
	 */
	public static void throwBy(boolean flag, String message, String authorizationCode, int code) {
		if(flag) {
			throw new SaOAuth2AuthorizationCodeException(message).setAuthorizationCode(authorizationCode).setCode(code);
		}
	}
	
}
