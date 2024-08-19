package top.prefersmin.openiam.permission.service.impl;

import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.prefersmin.openiam.permission.entity.Account;
import top.prefersmin.openiam.permission.mapper.AccountMapper;

/**
 * (Account)表服务实现类
 *
 * @author PrefersMin
 * @since 2024-04-23 20:49:16
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IService<Account> {

}
