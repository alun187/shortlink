package org.cuidl.shortlink.admin.dto.req;

import lombok.Data;

@Data
public class GroupSortReqDto {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 排序子弹
     */
    private Integer sortOrder;
}
