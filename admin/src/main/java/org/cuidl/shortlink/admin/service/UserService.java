package org.cuidl.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cuidl.shortlink.admin.common.convention.result.Result;
import org.cuidl.shortlink.admin.dao.entity.UserDo;
import org.cuidl.shortlink.admin.dto.req.UserRegisterReqDto;
import org.cuidl.shortlink.admin.dto.resp.UserRespDto;

/**
 * 用户接口类
 */
public interface UserService extends IService<UserDo> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserRespDto getUser(String username);

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return true:不存在，false:存在
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册
     *
     * @param requestParam 用户请求参数实体
     */
    void register(UserRegisterReqDto requestParam);
}
