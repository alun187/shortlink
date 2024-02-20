package org.cuidl.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.cuidl.shortlink.project.common.convention.exception.ServiceException;
import org.cuidl.shortlink.project.dao.entity.ShortLinkEntity;
import org.cuidl.shortlink.project.dto.req.CreateShortLinkReq;
import org.cuidl.shortlink.project.dto.resp.CreateShortLinkResp;
import org.cuidl.shortlink.project.service.ShortLinkService;
import org.cuidl.shortlink.project.dao.mapper.ShortLinkMapper;
import org.cuidl.shortlink.project.toolkit.HashUtil;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author cuidl
 */
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkEntity> implements ShortLinkService {

    private final RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;

    @Override
    public CreateShortLinkResp createShortLink(CreateShortLinkReq requestParam) {
        ShortLinkEntity shortLinkDo = BeanUtil.toBean(requestParam, ShortLinkEntity.class);
        String shortUri = generateShortUri(requestParam);
        String fullShortUrl = requestParam.getDomain() + "/" + shortUri;
        shortLinkDo.setEnableStatus(0);
        shortLinkDo.setShortUri(shortUri);
        shortLinkDo.setFullShortUrl(fullShortUrl);  
        try {
            baseMapper.insert(shortLinkDo);
        } catch (DuplicateKeyException ex) {
            if (!shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl)) {
                shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
            }
            throw new ServiceException(String.format("短链接：%s，生成重复", fullShortUrl));
        }
        shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
        return CreateShortLinkResp.builder().
                gid(requestParam.getGid()).
                shortUri(shortUri).
                fullShortUrl(shortLinkDo.getFullShortUrl()).
                build();
    }

    public String generateShortUri(CreateShortLinkReq requestParam) {
        int cycleNumber= 0;
        String shortUri;
        while (true) {
            if (cycleNumber >= 10) {
                throw new ServiceException("短链接频繁创建，请稍后重试！");
            }
            shortUri = HashUtil.hashToBase62(requestParam.getOriginUrl() + System.currentTimeMillis());
            if (!shortUriCreateCachePenetrationBloomFilter.contains(requestParam.getDomain() + "/" + shortUri)) {
                break;
            }
            cycleNumber++;
        }
        return shortUri;
    }
}




