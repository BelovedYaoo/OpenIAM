package top.prefersmin.openiam.common.config;

import com.mybatisflex.core.FlexGlobalConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.prefersmin.openiam.common.processor.EntityInsertListener;
import top.prefersmin.openiam.common.processor.EntityUpdateListener;

/**
 * Mybatis-Flex 框架的配置类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Configuration
public class MybatisFlexConfig {

    /**
     * Mybatis-Flex 配置初始化
     */
    @Bean
    public static void init() {
        // 设置全局逻辑删除字段
        FlexGlobalConfig.getDefaultConfig().setLogicDeleteColumn("is_deleted");
        // 注册插入监听器、更新监听器
        FlexGlobalConfig.getDefaultConfig().registerInsertListener(new EntityInsertListener(), Object.class);
        FlexGlobalConfig.getDefaultConfig().registerUpdateListener(new EntityUpdateListener(), Object.class);
    }

}
