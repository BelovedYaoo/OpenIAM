package top.belovedyaoo.openiam.common.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.logicdelete.impl.DateTimeLogicDeleteProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.belovedyaoo.openiam.common.core.eo.EntityInsertListener;
import top.belovedyaoo.openiam.common.core.eo.EntityUpdateListener;

/**
 * Mybatis-Flex 框架的配置类
 *
 * @author BelovedYaoo
 * @version 1.0
 */
@Configuration
public class MybatisFlexConfig {

    /**
     * Mybatis-Flex 配置初始化
     */
    @Bean
    public static void init() {
        FlexGlobalConfig globalConfig = FlexGlobalConfig.getDefaultConfig();
        // 设置逻辑删除处理器
        LogicDeleteManager.setProcessor(new DateTimeLogicDeleteProcessor());
        // 注册插入监听器、更新监听器
        globalConfig.registerInsertListener(new EntityInsertListener(), Object.class);
        globalConfig.registerUpdateListener(new EntityUpdateListener(), Object.class);
    }

}
