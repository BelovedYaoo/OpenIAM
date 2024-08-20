package top.belovedyaoo.openiam.permission.service.impl;

import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.belovedyaoo.openiam.permission.entity.Account;
import top.belovedyaoo.openiam.permission.mapper.AccountMapper;

/**
 * (Account)表服务实现类
 *
 * @author BelovedYaoo
 * @since 2024-04-23 20:49:16
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IService<Account> {

}
