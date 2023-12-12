package org.cuidl.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短连接分组控制层
 */
@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
}
