package org.cuidl.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 用户请求参数实体
 */
@Data
public class UserRegisterReqDto {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}
