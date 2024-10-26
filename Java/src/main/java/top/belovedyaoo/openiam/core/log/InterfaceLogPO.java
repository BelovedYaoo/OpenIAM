package top.belovedyaoo.openiam.core.log;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.mybatisflex.annotation.Table;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.ColumnNotNull;
import com.tangzc.autotable.annotation.ColumnType;
import com.tangzc.autotable.annotation.mysql.MysqlTypeConstant;
import com.tangzc.mybatisflex.annotation.InsertFillData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.belovedyaoo.openiam.core.base.BaseFiled;

import java.util.Date;

/**
 * (InterfaceLog)表持久化对象
 *
 * @author BelovedYaoo
 * @version 1.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter(onMethod_ = @JsonGetter)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true, fluent = true)
@Table(value = "log_interface", dataSource = "primary")
public class InterfaceLogPO extends BaseFiled {

    /**
     * 操作者ID
     */
    @ColumnNotNull
    @ColumnComment("每条日志记录的操作者ID")
    @ColumnType(value = MysqlTypeConstant.VARCHAR, length = 32)
    @InsertFillData(OperatorIdAutoFillProcessor.class)
    private String operatorId;

    /**
     * 业务类型
     */
    @ColumnNotNull
    @ColumnComment("每条日志记录的业务类型")
    @ColumnType(value = MysqlTypeConstant.VARCHAR, length = 50)
    private String businessTypes;

    /**
     * 请求接口路径
     */
    @ColumnNotNull
    @ColumnComment("每条日志记录的请求接口路径")
    @ColumnType(value = MysqlTypeConstant.VARCHAR, length = 50)
    private String requestUrl;

    /**
     * 方法名称
     */
    @ColumnNotNull
    @ColumnComment("每条日志记录的方法名称")
    @ColumnType(value = MysqlTypeConstant.VARCHAR, length = 50)
    private String methodName;

    /**
     * 请求方法描述
     */
    @ColumnComment("每条日志记录的请求方法描述")
    @ColumnType(value = MysqlTypeConstant.TEXT)
    private String description;

    /**
     * 方法入参
     */
    @ColumnComment("每条日志记录的方法入参")
    @ColumnType(value = MysqlTypeConstant.TEXT)
    private String params;

    /**
     * 方法执行结果
     */
    @ColumnComment("每条日志记录的方法执行结果")
    @ColumnType(value = MysqlTypeConstant.MEDIUMTEXT)
    private String result;

    /**
     * 执行时间
     */
    @ColumnNotNull
    @ColumnComment("请求方法开始执行的时间")
    @ColumnType(value = MysqlTypeConstant.DATETIME, length = 3)
    protected Date startTime;

    /**
     * 方法结束时间
     */
    @ColumnNotNull
    @ColumnComment("请求方法执行完成的时间")
    @ColumnType(value = MysqlTypeConstant.DATETIME, length = 3)
    private Date finishTime;

    /**
     * 请求IP地址
     */
    @ColumnNotNull
    @ColumnComment("每条日志记录的请求IP地址")
    @ColumnType(value = MysqlTypeConstant.VARCHAR, length = 50)
    private String requestIp;

    /**
     * 异常情况
     */
    @ColumnComment("异常情况")
    @ColumnType(value = MysqlTypeConstant.TEXT)
    private String exceptionMessage;

}
