package org.cuidl.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录响应信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRespDto {
    /**
     * token
     */
    public String token;
}
