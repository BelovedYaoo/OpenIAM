package top.prefersmin.openiam;

import com.mybatisflex.core.FlexGlobalConfig;
import com.tangzc.autotable.springboot.EnableAutoTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author PrefersMin
 * @version 1.0
 */
@EnableAutoTable
@SpringBootApplication
public class OpenIAMApplication {

    public static void main(String[] args) {
        FlexGlobalConfig.getDefaultConfig().setLogicDeleteColumn("is_deleted");
        SpringApplication.run(OpenIAMApplication.class, args);
    }

}
