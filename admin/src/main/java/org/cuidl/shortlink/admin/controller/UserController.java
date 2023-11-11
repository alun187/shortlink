package org.cuidl.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.admin.common.convention.result.Result;
import org.cuidl.shortlink.admin.common.convention.result.Results;
import org.cuidl.shortlink.admin.dto.resp.UserRealRespDto;
import org.cuidl.shortlink.admin.dto.resp.UserRespDto;
import org.cuidl.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shortlink/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名获取用户脱敏信息
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("{username}")
    public Result<UserRespDto> getUser(@PathVariable("username") String username) {
        return Results.success(userService.getUser(username));
    }

    /**
     * 根据用户名获取用户真是信息
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("real/{username}")
    public Result<UserRealRespDto> getRealUser(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUser(username), UserRealRespDto.class));
    }
}
