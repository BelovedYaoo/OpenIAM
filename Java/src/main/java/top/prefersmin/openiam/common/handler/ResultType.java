package top.prefersmin.openiam.common.handler;

/**
 * 返回结果类型接口
 *
 * @author PrefersMin
 * @version 1.0
 */
public interface ResultType {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取状态信息
     *
     * @return 状态信息
     */
    String getMessage();

    /**
     * 获取状态描述
     *
     * @return 状态描述
     */
    String getDescription();

}
