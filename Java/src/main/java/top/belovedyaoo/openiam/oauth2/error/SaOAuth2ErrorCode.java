package top.belovedyaoo.openiam.oauth2.error;

/**
 * 定义 sa-token-oauth2 所有异常细分状态码 
 * 
 * @author click33
 * @since 1.33.0
 */
public interface SaOAuth2ErrorCode {

	/** client_id 不可为空 */
	int CODE_30101 = 30101;

	/** scope 不可为空 */
	int CODE_30102 = 30102;

	/** redirect_uri 不可为空 */
	int CODE_30103 = 30103;

	/** LoginId 不可为空 */
	int CODE_30104 = 30104;

	/** 无效 client_id */
	int CODE_30105 = 30105;

	/** 无效 access_token */
	int CODE_30106 = 30106;

	/** 无效 client_token */
	int CODE_30107 = 30107;

	/** Access-Token 不具备指定的 Scope */
	int CODE_30108 = 30108;

	/** Client-Token 不具备指定的 Scope */
	int CODE_30109 = 30109;

	/** 无效 Code 码 */
	int CODE_30110 = 30110;

	/** 无效 Refresh-Token */
	int CODE_30111 = 30111;

	/** 请求的 Scope 暂未签约 */
	int CODE_30112 = 30112;

	/** 无效 redirect_url */
	int CODE_30113 = 30113;

	/** 非法 redirect_url */
	int CODE_30114 = 30114;
	
	/** 无效client_secret */
	int CODE_30115 = 30115;

	/** redirect_uri 不一致 */
	int CODE_30120 = 30120;

	/** client_id 不一致 */
	int CODE_30122 = 30122;

	/** 无效 response_type */
	int CODE_30125 = 30125;

	/** 无效 grant_type */
	int CODE_30126 = 30126;

	/** 无效 state */
	int CODE_30127 = 30127;

	/** 暂未开放授权码模式 */
	int CODE_30131 = 30131;
	
	/** 暂未开放隐藏式模式 */
	int CODE_30132 = 30132;
	
	/** 暂未开放密码式模式 */
	int CODE_30133 = 30133;
	
	/** 暂未开放凭证式模式 */
	int CODE_30134 = 30134;

	/** 系统暂未开放的授权模式 */
	int CODE_30141 = 30141;

	/** 应用暂未开放的授权模式 */
	int CODE_30142 = 30142;

	/** 无效的请求 Method */
	int CODE_30151 = 30151;

	/** 其它异常 */
	int CODE_30191 = 30191;

}
