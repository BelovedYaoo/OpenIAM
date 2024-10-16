package top.belovedyaoo.openiam.oauth2.data.model.request;

import java.io.Serializable;

/**
 * Client 的 id 和 secret
 *
 * @author click33
 * @since 1.39.0
 */
public class ClientIdAndSecretModel implements Serializable {

	private static final long serialVersionUID = -6541180061782004705L;

	/**
	 * 应用id
	 */
	public String clientId;

	/**
	 * 应用秘钥
	 */
	public String clientSecret;

	public ClientIdAndSecretModel() {
	}
	public ClientIdAndSecretModel(String clientId, String clientSecret) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	/**
	 * @return 应用id
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId 应用id 
	 * @return 对象自身 
	 */
	public ClientIdAndSecretModel setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	/**
	 * @return 应用秘钥 
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * @param clientSecret 应用秘钥 
	 * @return 对象自身 
	 */
	public ClientIdAndSecretModel setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}

	@Override
	public String toString() {
		return "ClientIdAndSecretModel{" +
				"clientId='" + clientId + '\'' +
				", clientSecret='" + clientSecret + '\'' +
				'}';
	}
}
