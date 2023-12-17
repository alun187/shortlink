package org.cuidl.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cuidl.shortlink.admin.dao.entity.GroupDo;
import org.cuidl.shortlink.admin.dto.req.GroupSortReqDto;
import org.cuidl.shortlink.admin.dto.req.UpdateGroupReqDto;
import org.cuidl.shortlink.admin.dto.resp.GroupLIstRespDto;

import java.util.List;

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

    /**
     * 查询分组
     *
     * @return 分组列表
     */
    List<GroupLIstRespDto> listGroup();

    /**
     * 修改分组名字
     *
     * @param requestParam 参数
     */
    void updateGroupName(UpdateGroupReqDto requestParam);

    /**
     * 删除分组
     *
     * @param gid 参数
     */
    void deleteGroup(String gid);

    /**
     * 分组排序功能
     *
     * @param requestParam 参数
     */
    void groupSort(List<GroupSortReqDto> requestParam);
}
