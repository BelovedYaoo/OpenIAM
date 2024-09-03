package top.belovedyaoo.openiam.core.log;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

/**
 * 接口日志切面类
 * ①切面注解得到请求数据 -> ②发布监听事件 -> ③异步监听日志入库
 *
 * @author BelovedYaoo
 * @version 1.0
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class InterfaceLogAspect {

    private final InterfaceLogPO interfaceLogPO = new InterfaceLogPO();

    /**
     * 事件发布是由ApplicationContext对象管控的，发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **/
    private final ApplicationContext applicationContext;

    /***
     * 拦截规则，拦截InterfaceLog注解的方法
     */
    @Pointcut("@annotation(top.belovedyaoo.openiam.core.log.InterfaceLog)")
    public void interfaceLogAspect() {

    }

    /***
     * 拦截控制层的操作日志
     */
    @Before(value = "interfaceLogAspect()")
    public void recordLog(JoinPoint joinPoint) {
        System.out.println("触发！");
        // 开始时间
        long beginTime = Instant.now().toEpochMilli();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        interfaceLogPO.operatorId(StpUtil.getLoginId("-1"))
                .requestUrl(URLUtil.getPath(request.getRequestURI()))
                .requestIp(ServletUtil.getClientIP(request))
                .methodName(request.getMethod());
        // 获取执行的方法名
        interfaceLogPO.methodName(joinPoint.getSignature().getName());
        // 取出执行方法上的InterfaceLog注解中的businessType
        // 从JoinPoint获取方法签名
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature methodSignature) {
            // 从方法签名中获取到方法对象
            Method method = methodSignature.getMethod();
            // 从方法对象获取特定的注解
            InterfaceLog interfaceLog =  method.getAnnotation(InterfaceLog.class);

            if (interfaceLog != null) {
                interfaceLogPO.businessTypes(interfaceLog.businessType());
                interfaceLogPO.description(interfaceLog.interfaceDesc());
            }
        }
        // 类名
        // sysLogPO.setClassPath(joinPoint.getTarget().getClass().getName());
        // 参数
        interfaceLogPO.params(Arrays.toString(joinPoint.getArgs()));
        // sysLogPO.setDescription(LogUtil.getControllerMethodDescription(joinPoint));
        long endTime = Instant.now().toEpochMilli();
        interfaceLogPO.consumingTime(endTime - beginTime);
    }

    /**
     * 返回通知
     */
    @AfterReturning(returning = "ret", pointcut = "interfaceLogAspect()")
    public void doAfterReturning(Object ret) {
        interfaceLogPO.result(ret.toString());
        // 处理完请求，返回内容
        // R r = Convert.convert(R.class, ret);
        // if (r.getCode() == 200) {
        //     正常返回
            // sysLogPO.setType(1);
        // } else {
        //     sysLogPO.setType(2);
        //     sysLogPO.setExDetail(r.getMsg());
        // }
        // 发布事件
        applicationContext.publishEvent(new InterfaceLogEvent(interfaceLogPO));
    }

    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "interfaceLogAspect()", throwing = "e")
    public void doAfterThrowable(Throwable e) {
        // 异常
        // sysLogPO.setType(2);
        // 异常对象
        // sysLogPO.setExDetail(LogUtil.getStackTrace(e));
        // 异常信息
        // sysLogPO.setExDesc(e.getMessage());
        // 发布事件
        applicationContext.publishEvent(new InterfaceLogEvent(interfaceLogPO));
    }

}