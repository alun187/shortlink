package org.cuidl.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.admin.common.convention.errorcode.BaseErrorCode;
import org.cuidl.shortlink.admin.common.convention.exception.ClientException;
import org.cuidl.shortlink.admin.common.enums.UserErrorCodeEnum;
import org.cuidl.shortlink.admin.dao.entity.UserDo;
import org.cuidl.shortlink.admin.dao.mapper.UserMapper;
import org.cuidl.shortlink.admin.dto.req.UserRegisterReqDto;
import org.cuidl.shortlink.admin.dto.resp.UserRespDto;
import org.cuidl.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现类
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Override
    public UserRespDto getUser(String username) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class).eq(UserDo::getUsername, username);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if (userDo == null) {
            throw new ClientException(BaseErrorCode.USER_NULL);
        }
        return BeanUtil.toBean(userDo, UserRespDto.class);
    }

    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDto requestParam) {
        if (!hasUsername(requestParam.getUsername())) {
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        int result = baseMapper.insert(BeanUtil.toBean(requestParam, UserDo.class));
        if (result < 1) {
            throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
        }
        userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
    }
}
