package top.prefersmin.openiam.permission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.prefersmin.openiam.common.handler.Result;
import top.prefersmin.openiam.permission.entity.po.Account;
import top.prefersmin.openiam.permission.service.impl.AuthenticationServiceImpl;
import top.prefersmin.openiam.permission.toolkit.AuthenticationUtil;

import static top.prefersmin.openiam.permission.service.impl.AuthenticationServiceImpl.VERIFY_CODE_PREFIX;

/**
 * 认证控制层
 *
 * @author PrefersMin
 * @version 1.2
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
        return authenticationUtil.codeVerify(VERIFY_CODE_PREFIX, usePhone ? account.accountPhone() : account.accountEmail());
    }

}
