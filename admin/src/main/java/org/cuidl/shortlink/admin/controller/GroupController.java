package org.cuidl.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.admin.common.convention.result.Result;
import org.cuidl.shortlink.admin.common.convention.result.Results;
import org.cuidl.shortlink.admin.dto.req.GroupSortReqDto;
import org.cuidl.shortlink.admin.dto.req.SaveGroupReq;
import org.cuidl.shortlink.admin.dto.req.UpdateGroupReqDto;
import org.cuidl.shortlink.admin.dto.resp.GroupLIstRespDto;
import org.cuidl.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短连接分组控制层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shortlink/v1/group")
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增分组
     */
    @PostMapping("save")
    public Result<Void> save(@RequestBody SaveGroupReq requestParam) {
        groupService.save(requestParam.getName());
        return Results.success();
    }

    /**
     * 查询分组
     */
    @GetMapping("list")
    public Result<List<GroupLIstRespDto>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改分组
     */
    @PutMapping("name")
    public Result<Void> updateGroupName(@RequestBody UpdateGroupReqDto requestParam) {
        groupService.updateGroupName(requestParam);
        return Results.success();
    }

    /**
     * 删除分组
     */
    @DeleteMapping("name")
    public Result<Void> deleteGroup(@RequestParam String gid) {
        groupService.deleteGroup(gid);
        return Results.success();
    }

    /**
     * 修改排序
     */
    @PostMapping("sort")
    public Result<Void> groupSort(@RequestBody List<GroupSortReqDto> requestParam) {
        groupService.groupSort(requestParam);
        return Results.success();
    }
}
