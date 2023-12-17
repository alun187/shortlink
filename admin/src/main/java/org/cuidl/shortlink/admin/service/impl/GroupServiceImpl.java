package org.cuidl.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.cuidl.shortlink.admin.common.biz.user.UserContext;
import org.cuidl.shortlink.admin.common.database.BaseDo;
import org.cuidl.shortlink.admin.dao.entity.GroupDo;
import org.cuidl.shortlink.admin.dao.mapper.GroupMapper;
import org.cuidl.shortlink.admin.dto.req.GroupSortReqDto;
import org.cuidl.shortlink.admin.dto.req.UpdateGroupReqDto;
import org.cuidl.shortlink.admin.dto.resp.GroupLIstRespDto;
import org.cuidl.shortlink.admin.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        GroupDo group = GroupDo.builder().name(groupName).username(UserContext.getUsername()).gid(gid).sortOrder(0).build();
        baseMapper.insert(group);
    }

    @Override
    public List<GroupLIstRespDto> listGroup() {
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getDelFlag, "0")
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDo::getSortOrder)
                .orderByDesc(GroupDo::getCreateTime);
        List<GroupDo> groupLIst = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupLIst, GroupLIstRespDto.class);
    }

    @Override
    public void updateGroupName(UpdateGroupReqDto requestParam) {
        LambdaQueryWrapper<GroupDo> groupDoLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getDelFlag, 0)
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getGid, requestParam.getGid());
        GroupDo groupDo = new GroupDo();
        groupDo.setName(requestParam.getName());
        baseMapper.update(groupDo, groupDoLambdaQueryWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaQueryWrapper<GroupDo> groupDoLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(BaseDo::getDelFlag, 0)
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getGid, gid);
        GroupDo groupDo = new GroupDo();
        groupDo.setDelFlag(1);
        baseMapper.update(groupDo, groupDoLambdaQueryWrapper);
    }

    @Override
    public void groupSort(List<GroupSortReqDto> requestParam) {
        requestParam.forEach(each -> {
            GroupDo groupDo = GroupDo.builder()
                    .sortOrder(each.getSortOrder())
                    .build();
            LambdaQueryWrapper<GroupDo> groupDoLambdaQueryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                    .eq(BaseDo::getDelFlag, 0)
                    .eq(GroupDo::getUsername, UserContext.getUsername())
                    .eq(GroupDo::getGid, each.getGid());
            baseMapper.update(groupDo, groupDoLambdaQueryWrapper);
        });
    }

    // 分组标识是否重复
    public boolean isRepeat(String gid) {
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class).eq(GroupDo::getUsername, UserContext.getUsername()).eq(GroupDo::getGid, gid);
        GroupDo groupDo = baseMapper.selectOne(queryWrapper);
        return groupDo == null;
    }
}
