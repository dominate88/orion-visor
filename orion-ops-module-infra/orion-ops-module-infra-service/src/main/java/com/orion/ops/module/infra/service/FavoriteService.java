package com.orion.ops.module.infra.service;

import com.orion.ops.module.infra.entity.request.favorite.FavoriteCreateRequest;
import com.orion.ops.module.infra.entity.request.favorite.FavoriteQueryRequest;
import com.orion.ops.module.infra.entity.vo.FavoriteVO;

import java.util.List;

/**
 * 收藏 服务类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023-9-1 10:30
 */
public interface FavoriteService {

    /**
     * 创建收藏
     *
     * @param request request
     * @return id
     */
    Long addFavorite(FavoriteCreateRequest request);

    /**
     * 查询收藏列表
     *
     * @param request request
     * @return rows
     */
    List<FavoriteVO> getFavoriteList(FavoriteQueryRequest request);

    /**
     * 查询收藏 relId 列表
     *
     * @param request request
     * @return relIdList
     */
    List<Long> getFavoriteRelIdList(FavoriteQueryRequest request);

    /**
     * 通过 userId 删除收藏
     *
     * @param userId userId
     */
    void deleteFavoriteByUserId(Long userId);

    /**
     * 通过 userId 删除收藏
     *
     * @param userIdList userId
     */
    void deleteFavoriteByUserIdList(List<Long> userIdList);

    /**
     * 通过 relId 删除收藏
     *
     * @param relId relId
     */
    void deleteFavoriteByRelId(Long relId);

    /**
     * 通过 relId 删除收藏
     *
     * @param relIdList relIdList
     */
    void deleteFavoriteByRelIdList(List<Long> relIdList);

    /**
     * 删除收藏
     *
     * @param request request
     * @return effect
     */
    Integer deleteFavorite(FavoriteQueryRequest request);

}
