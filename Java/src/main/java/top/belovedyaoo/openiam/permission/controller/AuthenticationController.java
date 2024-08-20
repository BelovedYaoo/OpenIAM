package top.belovedyaoo.openiam.permission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.belovedyaoo.openiam.permission.toolkit.AuthenticationUtil;
import top.belovedyaoo.openiam.common.base.result.Result;
import top.belovedyaoo.openiam.permission.entity.Account;
import top.belovedyaoo.openiam.permission.service.impl.AccountServiceImpl;
import top.belovedyaoo.openiam.permission.service.impl.AuthenticationServiceImpl;

import static top.belovedyaoo.openiam.permission.service.impl.AuthenticationServiceImpl.VERIFY_CODE_PREFIX;

/**
 * 认证控制层
 *
 * @author BelovedYaoo
 * @version 1.3
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    /**
     * 认证工具类
     */
    private final AuthenticationUtil authenticationUtil;

    private final AuthenticationServiceImpl authenticationService;

    private final AccountServiceImpl accountService;

    @PostMapping("/test")
    public boolean test(String str) {
        return accountService.removeById(str);
    }

    @PostMapping("/test2")
    public Account test2() {
        Account account = Account.builder()
                .baseId("123123")
                .build();
        account.openId("123123121231233123");
        System.out.println(account);
        accountService.save(Account.builder()
                .openId("123")
                .password("012e36e1e55a4c3bab6845b3438f8aff")
                .email("belovedyaoo@qq.com")
                .build());
        return account;
    }

    @PostMapping("/test3")
    public Account test3(Account account) {
        System.out.println(account);
        account.baseId("111");
        return account;
    }

    /**
     * 账号登录方法
     *
     * @param account 账号数据(登录ID、密码)
     *
     * @return 登录结果
     */
    @PostMapping("/accountLogin")
    public Result accountLogin(@ModelAttribute Account account) {
        System.out.println(account);
        return authenticationService.accountLogin(account);
    }

    /**
     * 账号注册方法
     *
     * @param account 账号数据(登录ID、密码)
     *
     * @return 注册结果
     */
    @PostMapping("/accountRegister")
    public Result accountRegister(@ModelAttribute Account account, @RequestParam(value = "usePhone") boolean usePhone, @RequestParam(value = "verifyCode") String verifyCode) {
        return authenticationService.accountRegister(account, usePhone, verifyCode);
    }

    /**
     * 验证码生成方法
     *
     * @param account 账号数据
     *
     * @return 生成结果
     */
    @PostMapping("/getVerifyCode")
    public Result getVerifyCode(@ModelAttribute Account account, @RequestParam(value = "usePhone") boolean usePhone) {
        return authenticationUtil.codeVerify(VERIFY_CODE_PREFIX, usePhone ? account.phone() : account.email());
    }

}
