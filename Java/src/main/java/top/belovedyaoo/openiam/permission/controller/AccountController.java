package top.belovedyaoo.openiam.permission.controller;

import com.mybatisflex.core.BaseMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.belovedyaoo.openiam.core.base.BaseController;
import top.belovedyaoo.openiam.permission.entity.Account;

/**
 * Account控制器
 *
 * @author BelovedYaoo
 * @version 1.0
 */
@RestController
@RequestMapping("/acc")
public class AccountController extends BaseController<Account> {
    public AccountController(BaseMapper<Account> baseMapper) {
        super(baseMapper);
    }
}
