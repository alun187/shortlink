package org.cuidl.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.admin.common.convention.result.Result;
import org.cuidl.shortlink.admin.common.convention.result.Results;
import org.cuidl.shortlink.admin.dto.req.SaveGroupReq;
import org.cuidl.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短连接分组控制层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shortlink/v1/group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("save")
    public Result<Void> save(@RequestBody SaveGroupReq requestParam) {
        groupService.save(requestParam.getName());
        return Results.success();
    }
}
