package org.cuidl.shortlink.project.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 创建短连接响应对象
 */
@Data
@Builder
public class CreateShortLinkResp {

    /**
     * 短链接
     */
    private String shortUri;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;
}
