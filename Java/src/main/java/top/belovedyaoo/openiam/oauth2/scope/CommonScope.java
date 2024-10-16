package top.belovedyaoo.openiam.oauth2.scope;

/**
 * OAuth2 常见权限定义
 *
 * @author click33
 * @since 1.39.0
 */
public final class CommonScope {

    private CommonScope() {
    }

    /**
     * 获取 openid
     */
    public static final String OPENID = "openid";

    /**
     * 获取 userid
     */
    public static final String USERID = "userid";

    /**
     * 获取 id_token
     */
    public static final String OIDC = "oidc";

}