package org.cuidl.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.cuidl.shortlink.admin.dao.entity.GroupDo;
import org.cuidl.shortlink.admin.dao.mapper.GroupMapper;
import org.cuidl.shortlink.admin.service.GroupService;
import org.springframework.stereotype.Service;

/**
 * 短连接分组实现层
 */
@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {
}
