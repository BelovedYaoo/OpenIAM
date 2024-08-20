package top.belovedyaoo.openiam.permission.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.belovedyaoo.openiam.permission.entity.Account;

/**
 * (Account)表数据库访问层
 *
 * @author BelovedYaoo
 * @since 2024-04-23 20:49:16
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}

