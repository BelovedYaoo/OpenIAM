package top.belovedyaoo.openiam.core.log;

import org.springframework.context.ApplicationEvent;

/**
 * 接口日志事件
 *
 * @author BelovedYaoo
 * @version 1.0
 */
public class InterfaceLogEvent extends ApplicationEvent {

    public InterfaceLogEvent(InterfaceLogPO source) {
        super(source);
    }

}
