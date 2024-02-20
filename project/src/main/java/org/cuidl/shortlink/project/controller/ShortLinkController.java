package org.cuidl.shortlink.project.controller;

import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.project.common.convention.result.Result;
import org.cuidl.shortlink.project.common.convention.result.Results;
import org.cuidl.shortlink.project.dto.req.CreateShortLinkReq;
import org.cuidl.shortlink.project.dto.resp.CreateShortLinkResp;
import org.cuidl.shortlink.project.service.ShortLinkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shortlink/v1/")
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @PostMapping("save")
    public Result<CreateShortLinkResp> createShortLink(@RequestBody CreateShortLinkReq requestParam) {
        return Results.success(shortLinkService.createShortLink(requestParam));
    }
}
