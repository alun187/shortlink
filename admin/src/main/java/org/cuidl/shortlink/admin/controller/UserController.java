package org.cuidl.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.admin.common.convention.result.Result;
import org.cuidl.shortlink.admin.common.convention.result.Results;
import org.cuidl.shortlink.admin.dto.req.UserRegisterReqDto;
import org.cuidl.shortlink.admin.dto.req.UserUpdateReqDto;
import org.cuidl.shortlink.admin.dto.resp.UserRealRespDto;
import org.cuidl.shortlink.admin.dto.resp.UserRespDto;
import org.cuidl.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shortlink/v1")
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名获取用户脱敏信息
     */
    @GetMapping("user/{username}")
    public Result<UserRespDto> getUser(@PathVariable("username") String username) {
        return Results.success(userService.getUser(username));
    }

    /**
     * 根据用户名获取用户无脱敏信息
     */
    @GetMapping("user/real/{username}")
    public Result<UserRealRespDto> getRealUser(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUser(username), UserRealRespDto.class));
    }

    /**
     * 判断用户名是否存在
     */
    @GetMapping("user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 用户注册
     */
    @PostMapping("user")
    public Result<Void> register(@RequestBody UserRegisterReqDto requestParam) {
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 用户信息修改
     */
    @PutMapping("user")
    public Result<Void> register(@RequestBody UserUpdateReqDto requestParam) {
        userService.update(requestParam);
        return Results.success();
    }
}
