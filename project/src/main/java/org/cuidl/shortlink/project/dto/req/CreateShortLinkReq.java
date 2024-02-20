package org.cuidl.shortlink.project.dto.req;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建短连接请求对象
 */
@Data
public class CreateShortLinkReq {
    /**
     * 域名
     */
    private String domain;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 创建类型 0：接口创建 1：控制台创建
     */
    private Integer createdType;

    /**
     * 有效期类型 0：永久有效 1：自定义
     */
    private Integer validDateType;

//    /**
//     * 有效期
//     */
//    private LocalDateTime validDate;
}
