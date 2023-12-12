package org.cuidl.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cuidl.shortlink.admin.dao.entity.GroupDo;

/**
 * 短连接分组接口层
 */
public interface GroupService extends IService<GroupDo> {

    /**
     * 新增分组
     *
     * @param groupName 分组名字
     */
    void save(String groupName);
}
