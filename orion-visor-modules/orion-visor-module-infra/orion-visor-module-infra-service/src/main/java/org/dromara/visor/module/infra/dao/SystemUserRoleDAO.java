/*
 * Copyright (c) 2023 - present Dromara, All rights reserved.
 *
 *   https://visor.dromara.org
 *   https://visor.dromara.org.cn
 *   https://visor.orionsec.cn
 *
 * Members:
 *   Jiahang Li - ljh1553488six@139.com - author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dromara.visor.module.infra.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.dromara.visor.framework.mybatis.core.mapper.IMapper;
import org.dromara.visor.module.infra.entity.domain.SystemUserRoleDO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联 Mapper 接口
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023-7-16 22:31
 */
@Mapper
public interface SystemUserRoleDAO extends IMapper<SystemUserRoleDO> {

    /**
     * 查询用户的全部 roleId
     *
     * @param userId userId
     * @return roleId
     */
    default List<Long> selectRoleIdByUserId(Long userId) {
        LambdaQueryWrapper<SystemUserRoleDO> wrapper = this.wrapper()
                .select(SystemUserRoleDO::getRoleId)
                .eq(SystemUserRoleDO::getUserId, userId);
        return this.selectList(wrapper).stream()
                .map(SystemUserRoleDO::getRoleId)
                .collect(Collectors.toList());
    }

    /**
     * 查询角色的全部 userId
     *
     * @param roleId roleId
     * @return userId
     */
    default List<Long> selectUserIdByRoleId(Long roleId) {
        LambdaQueryWrapper<SystemUserRoleDO> wrapper = this.wrapper()
                .select(SystemUserRoleDO::getUserId)
                .eq(SystemUserRoleDO::getRoleId, roleId);
        return this.selectList(wrapper).stream()
                .map(SystemUserRoleDO::getUserId)
                .collect(Collectors.toList());
    }

    /**
     * 查询角色的全部 userId
     *
     * @param roleId roleId
     * @return userId
     */
    default List<Long> selectUserIdByRoleId(List<Long> roleId) {
        LambdaQueryWrapper<SystemUserRoleDO> wrapper = this.wrapper()
                .select(SystemUserRoleDO::getUserId)
                .in(SystemUserRoleDO::getRoleId, roleId);
        return this.selectList(wrapper).stream()
                .map(SystemUserRoleDO::getUserId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 通过 userId 删除
     *
     * @param userId userId
     * @return effect
     */
    default int deleteByUserId(Long userId) {
        LambdaQueryWrapper<SystemUserRoleDO> wrapper = this.wrapper()
                .eq(SystemUserRoleDO::getUserId, userId);
        return this.delete(wrapper);
    }

    /**
     * 通过 userId 删除
     *
     * @param userIdList userIdList
     * @return effect
     */
    default int deleteByUserIdList(List<Long> userIdList) {
        LambdaQueryWrapper<SystemUserRoleDO> wrapper = this.wrapper()
                .in(SystemUserRoleDO::getUserId, userIdList);
        return this.delete(wrapper);
    }

    /**
     * 通过 roleId 删除
     *
     * @param roleId roleId
     * @return effect
     */
    default int deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<SystemUserRoleDO> wrapper = this.wrapper()
                .eq(SystemUserRoleDO::getRoleId, roleId);
        return this.delete(wrapper);
    }

}
