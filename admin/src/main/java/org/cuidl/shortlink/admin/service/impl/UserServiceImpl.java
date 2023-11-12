package org.cuidl.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cuidl.shortlink.admin.common.convention.exception.ClientException;
import org.cuidl.shortlink.admin.common.enums.UserErrorCodeEnum;
import org.cuidl.shortlink.admin.dao.entity.UserDo;
import org.cuidl.shortlink.admin.dao.mapper.UserMapper;
import org.cuidl.shortlink.admin.dto.req.UserLoginReqDto;
import org.cuidl.shortlink.admin.dto.req.UserRegisterReqDto;
import org.cuidl.shortlink.admin.dto.req.UserUpdateReqDto;
import org.cuidl.shortlink.admin.dto.resp.UserLoginRespDto;
import org.cuidl.shortlink.admin.dto.resp.UserRespDto;
import org.cuidl.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.cuidl.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static org.cuidl.shortlink.admin.common.enums.UserErrorCodeEnum.USER_NAME_EXIST;
import static org.cuidl.shortlink.admin.common.enums.UserErrorCodeEnum.USER_NULL;

/**
 * 用户接口实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserRespDto getUser(String username) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class).eq(UserDo::getUsername, username);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if (userDo == null) {
            throw new ClientException(USER_NULL);
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
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            if (lock.tryLock()) {
                int result = baseMapper.insert(BeanUtil.toBean(requestParam, UserDo.class));
                if (result < 1) {
                    throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDto requestParam) {
        //TODO 判断用户名是否是单当前登录用户
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class).eq(UserDo::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDo.class), queryWrapper);
    }

    @Override
    public UserLoginRespDto login(UserLoginReqDto requestParam) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class).eq(UserDo::getUsername, requestParam.getUsername()).
                eq(UserDo::getPassword, requestParam.getPassword()).eq(UserDo::getDelFlag, 0);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if (userDo == null) {
            throw new ClientException("用户名或密码错误");
        }
        Boolean isLogin = stringRedisTemplate.hasKey("login_" + requestParam.getUsername());
        if (isLogin != null && isLogin) {
            throw new ClientException("用户已登录");
        }
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put("login_" + requestParam.getUsername(), uuid, JSON.toJSONString(userDo));
        stringRedisTemplate.expire("login_" + requestParam.getUsername(), 30L, TimeUnit.MINUTES);
        log.info("【{}】 {}", requestParam.getUsername(), "登录系统");
        return new UserLoginRespDto(uuid);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get("login_" + username, token) != null;
    }

    @Override
    public void logout(String username, String token) {
        if (checkLogin(username, token)) {
            stringRedisTemplate.delete("login_" + username);
            log.info("【{}】 {}", username, "退出系统");
            return;
        }
        throw new ClientException("token不存在或用户未登录");
    }
}
