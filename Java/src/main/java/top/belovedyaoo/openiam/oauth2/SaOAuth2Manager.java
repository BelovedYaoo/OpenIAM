package top.belovedyaoo.openiam.oauth2;

import top.belovedyaoo.openiam.oauth2.config.SaOAuth2ServerConfig;
import top.belovedyaoo.openiam.oauth2.dao.SaOAuth2Dao;
import top.belovedyaoo.openiam.oauth2.dao.SaOAuth2DaoDefaultImpl;
import top.belovedyaoo.openiam.oauth2.data.convert.SaOAuth2DataConverter;
import top.belovedyaoo.openiam.oauth2.data.convert.SaOAuth2DataConverterDefaultImpl;
import top.belovedyaoo.openiam.oauth2.data.generate.SaOAuth2DataGenerate;
import top.belovedyaoo.openiam.oauth2.data.generate.SaOAuth2DataGenerateDefaultImpl;
import top.belovedyaoo.openiam.oauth2.data.loader.SaOAuth2DataLoader;
import top.belovedyaoo.openiam.oauth2.data.loader.SaOAuth2DataLoaderDefaultImpl;
import top.belovedyaoo.openiam.oauth2.data.resolver.SaOAuth2DataResolver;
import top.belovedyaoo.openiam.oauth2.data.resolver.SaOAuth2DataResolverDefaultImpl;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;

/**
 * Sa-Token-OAuth2 模块 总控类
 * 
 * @author click33
 * @since 1.19.0
 */
public class SaOAuth2Manager {

	/**
	 * OAuth2 配置 Bean 
	 */
	private static volatile SaOAuth2ServerConfig serverConfig;
	public static SaOAuth2ServerConfig getServerConfig() {
		if (serverConfig == null) {
			// 初始化默认值
			synchronized (SaOAuth2Manager.class) {
				if (serverConfig == null) {
					setServerConfig(new SaOAuth2ServerConfig());
				}
			}
		}
		return serverConfig;
	}
	public static void setServerConfig(SaOAuth2ServerConfig serverConfig) {
		SaOAuth2Manager.serverConfig = serverConfig;
	}

	/**
	 * OAuth2 数据加载器 Bean
	 */
	private static volatile SaOAuth2DataLoader dataLoader;
	public static SaOAuth2DataLoader getDataLoader() {
		if (dataLoader == null) {
			synchronized (SaOAuth2Manager.class) {
				if (dataLoader == null) {
					setDataLoader(new SaOAuth2DataLoaderDefaultImpl());
				}
			}
		}
		return dataLoader;
	}
	public static void setDataLoader(SaOAuth2DataLoader dataLoader) {
		SaOAuth2Manager.dataLoader = dataLoader;
	}

	/**
	 * OAuth2 数据解析器 Bean
	 */
	private static volatile SaOAuth2DataResolver dataResolver;
	public static SaOAuth2DataResolver getDataResolver() {
		if (dataResolver == null) {
			synchronized (SaOAuth2Manager.class) {
				if (dataResolver == null) {
					setDataResolver(new SaOAuth2DataResolverDefaultImpl());
				}
			}
		}
		return dataResolver;
	}
	public static void setDataResolver(SaOAuth2DataResolver dataResolver) {
		SaOAuth2Manager.dataResolver = dataResolver;
	}

	/**
	 * OAuth2 数据格式转换器 Bean
	 */
	private static volatile SaOAuth2DataConverter dataConverter;
	public static SaOAuth2DataConverter getDataConverter() {
		if (dataConverter == null) {
			synchronized (SaOAuth2Manager.class) {
				if (dataConverter == null) {
					setDataConverter(new SaOAuth2DataConverterDefaultImpl());
				}
			}
		}
		return dataConverter;
	}
	public static void setDataConverter(SaOAuth2DataConverter dataConverter) {
		SaOAuth2Manager.dataConverter = dataConverter;
	}

	/**
	 * OAuth2 数据构建器 Bean
	 */
	private static volatile SaOAuth2DataGenerate dataGenerate;
	public static SaOAuth2DataGenerate getDataGenerate() {
		if (dataGenerate == null) {
			synchronized (SaOAuth2Manager.class) {
				if (dataGenerate == null) {
					setDataGenerate(new SaOAuth2DataGenerateDefaultImpl());
				}
			}
		}
		return dataGenerate;
	}
	public static void setDataGenerate(SaOAuth2DataGenerate dataGenerate) {
		SaOAuth2Manager.dataGenerate = dataGenerate;
	}

	/**
	 * OAuth2 数据持久 Bean
	 */
	private static volatile SaOAuth2Dao dao;
	public static SaOAuth2Dao getDao() {
		if (dao == null) {
			synchronized (SaOAuth2Manager.class) {
				if (dao == null) {
					setDao(new SaOAuth2DaoDefaultImpl());
				}
			}
		}
		return dao;
	}
	public static void setDao(SaOAuth2Dao dao) {
		SaOAuth2Manager.dao = dao;
	}

	/**
	 * OAuth2 模板方法 Bean
	 */
	private static volatile SaOAuth2Template template;
	public static SaOAuth2Template getTemplate() {
		if (template == null) {
			synchronized (SaOAuth2Manager.class) {
				if (template == null) {
					setTemplate(new SaOAuth2Template());
				}
			}
		}
		return template;
	}
	public static void setTemplate(SaOAuth2Template template) {
		SaOAuth2Manager.template = template;
	}

	/**
	 * OAuth2 StpLogic
	 */
	private static volatile StpLogic stpLogic;
	public static StpLogic getStpLogic() {
		if (stpLogic == null) {
			synchronized (SaOAuth2Manager.class) {
				if (stpLogic == null) {
					setStpLogic(StpUtil.stpLogic);
				}
			}
		}
		return stpLogic;
	}
	public static void setStpLogic(StpLogic stpLogic) {
		SaOAuth2Manager.stpLogic = stpLogic;
	}

}