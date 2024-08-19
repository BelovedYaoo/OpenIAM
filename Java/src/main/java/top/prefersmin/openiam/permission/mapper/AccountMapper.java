package top.prefersmin.openiam.permission.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.prefersmin.openiam.permission.entity.Account;

/**
 * (Account)表数据库访问层
 *
 * @author PrefersMin
 * @since 2024-04-23 20:49:16
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}

