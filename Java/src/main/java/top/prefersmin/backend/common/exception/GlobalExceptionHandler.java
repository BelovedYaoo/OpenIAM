// package top.prefersmin.backend.common.exception;
//
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import top.prefersmin.backend.common.handler.Result;
// import top.prefersmin.backend.common.toolkit.LogUtil;
//
// /**
//  * 全局异常捕捉
//  *
//  * @author PrefersMin
//  * @version 1.1
//  */
// @ControllerAdvice
// @RestControllerAdvice
// @RequiredArgsConstructor
// public class GlobalExceptionHandler {
//
//     /**
//      * 捕捉运行时异常
//      *
//      * @author PrefersMin
//      *
//      * @param e 异常内容
//      */
//     @ExceptionHandler(value = RuntimeException.class)
//     public Result handler(RuntimeException e) {
//         LogUtil.error("运行时异常:" + e.getMessage());
//         return Result.failed().message("系统异常，请联系管理员");
//     }
//
//     /**
//      * 捕捉空指针异常
//      *
//      * @author PrefersMin
//      *
//      * @param e 异常内容
//      */
//     @ExceptionHandler(value = NullPointerException.class)
//     public Result handler(NullPointerException e) {
//         LogUtil.error("空指针异常:" + e.getMessage());
//         return Result.failed().message("系统异常，请联系管理员");
//     }
//
// }
