package top.prefersmin.openiam.common.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Id;
import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.autotable.annotation.ColumnDefault;
import com.tangzc.autotable.annotation.ColumnNotNull;
import com.tangzc.autotable.annotation.ColumnType;
import com.tangzc.autotable.annotation.PrimaryKey;
import com.tangzc.mybatisflex.annotation.InsertFillData;
import com.tangzc.mybatisflex.annotation.InsertFillTime;
import com.tangzc.mybatisflex.annotation.InsertUpdateFillTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.prefersmin.openiam.common.processor.BaseIdAutoFillProcessor;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
@Getter(onMethod_ = {@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)})
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
    protected Date createTime;

    /**
     * 更新时间
     */
    @InsertUpdateFillTime
    @ColumnComment("更新时间")
    protected Date updateTime;

    /**
     * 禁用状态
     */
    @ColumnDefault("0")
    @ColumnComment("禁用状态")
    protected Boolean isDisabled;

    /**
     * 逻辑删除
     */
    @ColumnDefault("0")
    @ColumnComment("逻辑删除")
    protected Boolean isDeleted;

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
