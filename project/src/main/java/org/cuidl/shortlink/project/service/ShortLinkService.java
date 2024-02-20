package org.cuidl.shortlink.project.service;

import org.cuidl.shortlink.project.dao.entity.ShortLinkEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cuidl.shortlink.project.dto.req.CreateShortLinkReq;
import org.cuidl.shortlink.project.dto.resp.CreateShortLinkResp;

/**
* @author cuidl
*/
public interface ShortLinkService extends IService<ShortLinkEntity> {

    CreateShortLinkResp createShortLink(CreateShortLinkReq requestParam);
}
