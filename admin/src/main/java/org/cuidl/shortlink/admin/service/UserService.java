package org.cuidl.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cuidl.shortlink.admin.common.convention.result.Result;
import org.cuidl.shortlink.admin.dao.entity.UserDo;
import org.cuidl.shortlink.admin.dto.resp.UserRespDto;

/**
 * 用户接口类
 */
public interface UserService extends IService<UserDo> {
    UserRespDto getUser(String username);
}
