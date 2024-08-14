package top.prefersmin.backend.permission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.prefersmin.backend.permission.toolkit.AuthorizationUtil;

/**
 * 授权控制层
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthorizationController {

    /**
     * 授权工具类
     */
    private final AuthorizationUtil authorizationUtil;

}
