package org.cuidl.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 保存分组请求参数实体
 */
@Data
public class SaveGroupReq {
    /**
     * 分组名字
     */
    private String name;
}
