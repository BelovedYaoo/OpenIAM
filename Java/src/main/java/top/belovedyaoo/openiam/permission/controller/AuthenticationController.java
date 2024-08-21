package top.belovedyaoo.openiam.permission.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.belovedyaoo.openiam.common.core.result.Result;
import top.belovedyaoo.openiam.permission.entity.Account;
import top.belovedyaoo.openiam.permission.service.impl.AccountServiceImpl;
import top.belovedyaoo.openiam.permission.service.impl.AuthenticationServiceImpl;
import top.belovedyaoo.openiam.permission.toolkit.AuthenticationUtil;

import static top.belovedyaoo.openiam.permission.service.impl.AuthenticationServiceImpl.VERIFY_CODE_PREFIX;

/**
 * 认证控制层
 *
 * @author BelovedYaoo
 * @version 1.3
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
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
                .baseId("111")
                .openId("222")
                .build();
        System.out.println(account);
        accountService.save(Account.builder()
                .openId("123")
                .password("YTY2NWE0NTkyMDQyMmY5ZDQxN2U0ODY3ZWZkYzRmYjhhMDRhMWYzZmZmMWZhMDdlOTk4ZTg2ZjdmN2EyN2FlMw==")
                .email("belovedyaoo@qq.com")
                .build());
        return account;
    }

    @PostMapping("/test3")
    public Result test3(@RequestBody Account account) {
        SaRequest req = SaHolder.getRequest();
        req.getParamMap().forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println(account);
        account.baseId("111");
        return Result.success().data("account", account);
    }

    /**
     * 账号登录方法
     *
     * @param account 账号数据(登录ID、密码)
     *
     * @return 登录结果
     */
    @PostMapping("/accountLogin")
    public Result accountLogin(@RequestBody Account account) {
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
    public Result accountRegister(@RequestParam(value = "usePhone") boolean usePhone, @RequestParam(value = "verifyCode") String verifyCode, @RequestBody Account account) {
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
    public Result getVerifyCode(@RequestParam(value = "usePhone") boolean usePhone, @RequestBody Account account) {
        return authenticationUtil.codeVerify(VERIFY_CODE_PREFIX, usePhone ? account.phone() : account.email());
    }

}
