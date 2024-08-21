package top.belovedyaoo.openiam.common.base;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.ColumnDefault;
import com.tangzc.autotable.annotation.ColumnNotNull;
import com.tangzc.autotable.annotation.ColumnType;
import com.tangzc.autotable.annotation.PrimaryKey;
import com.tangzc.mybatisflex.annotation.InsertFillData;
import com.tangzc.mybatisflex.annotation.InsertFillTime;
import com.tangzc.mybatisflex.annotation.InsertUpdateFillTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import top.belovedyaoo.openiam.common.processor.BaseIdAutoFillProcessor;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段
 * Data注解用于提供各属性的Getter、Setter、equals、hashCode、canEqual、toString方法
 * SuperBuilder注解用于协同构造派生类，缺失会导致构造派生类时无法同时设置基类与派生类属性
 * Getter注解用于Jackson的反序列化，缺失会导致拿不到BaseFiled的属性
 * NoArgsConstructor注解用于提供无参构造方法，缺失会导致派生类中报错
 * Accessors用于去除Getter、Setter前缀并开启链式调用，使Getter、Setter返回当前对象
 *
 * @author BelovedYaoo
 * @version 1.2
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Getter(onMethod_ = @JsonGetter)
@Accessors(fluent = true, chain = true)
public class BaseFiled implements Serializable {

    /**
     * 内部ID
     */
    @Id
    @PrimaryKey(value = false)
    @ColumnType(length = 32)
    @ColumnNotNull
    @ColumnComment("基础ID,仅系统内部使用")
    @InsertFillData(BaseIdAutoFillProcessor.class)
    protected String baseId;

    /**
     * 创建时间
     */
    @InsertFillTime
    @ColumnComment("创建时间")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Date createTime;

    /**
     * 更新时间
     */
    @InsertUpdateFillTime
    @ColumnComment("更新时间")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Date updateTime;

    /**
     * 禁用状态
     */
    @ColumnDefault("0")
    @ColumnComment("禁用状态")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Boolean isDisabled;

    /**
     * 逻辑删除
     */
    @ColumnComment("逻辑删除")
    @Column(isLogicDelete = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected Date deletedAt;

    /**
     * 派生类方法在调用基类setter时会传递基类类型对象<p>
     * 此方法用于将基类类型对象转换为指定的派生类类型对象<p>
     * 请通过上下文确保类型一致
     *
     * @param clazz 需要转换的派生类类型
     * @param <T>   派生类类型
     *
     * @return 转换后的派生类类型对象
     */
    public <T extends BaseFiled> T convertToSubclass(Class<T> clazz) {
        if (clazz.isInstance(this)) {
            return clazz.cast(this);
        } else {
            throw new IllegalArgumentException(clazz.getSimpleName() + "与预期的类型不一致，请检查");
        }
    }

}
