package top.prefersmin.backend;

import com.mybatisflex.core.FlexGlobalConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author PrefersMin
 * @version 1.0
 */
@SpringBootApplication
public class PrefersMinForBackEndApplication {

    public static void main(String[] args) {
        FlexGlobalConfig.getDefaultConfig().setLogicDeleteColumn("is_deleted");
        SpringApplication.run(PrefersMinForBackEndApplication.class, args);
    }

}
