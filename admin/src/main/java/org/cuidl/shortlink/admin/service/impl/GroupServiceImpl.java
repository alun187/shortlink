package org.cuidl.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.cuidl.shortlink.admin.dao.entity.GroupDo;
import org.cuidl.shortlink.admin.dao.mapper.GroupMapper;
import org.cuidl.shortlink.admin.service.GroupService;
import org.springframework.stereotype.Service;

import static org.cuidl.shortlink.admin.common.toolkit.RandomUtil.getRandomNumber;

/**
 * 短连接分组实现层
 */
@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {
    @Override
    public void save(String groupName) {
        String gid;
        do {
            gid = getRandomNumber();
        } while (!isRepeat(gid));
        // todo 用户名需要填充
        GroupDo group = GroupDo.builder().name(groupName).username(null).gid(gid).sortOrder(0).build();
        baseMapper.insert(group);
    }

    // 分组标识是否重复
    public boolean isRepeat(String gid) {
        // todo 用户名需要填充
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class).eq(GroupDo::getUsername, null).eq(GroupDo::getGid, gid);
        GroupDo groupDo = baseMapper.selectOne(queryWrapper);
        return groupDo == null;
    }
}
