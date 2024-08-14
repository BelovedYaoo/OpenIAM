package top.prefersmin.openiam.common.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段
 *
 * @author PrefersMin
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(value = "account")
@Getter(onMethod_ = {@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)})
public class BaseFiled implements Serializable {

    /**
     * 禁用状态
     */
    private Boolean isDisabled;

    /**
     * 逻辑删除
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
