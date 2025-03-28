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
package org.dromara.visor.module.infra.service;

import cn.orionsec.kit.lang.define.wrapper.DataGrid;
import org.dromara.visor.module.infra.entity.request.operator.OperatorLogQueryRequest;
import org.dromara.visor.module.infra.entity.request.user.SystemUserUpdateRequest;
import org.dromara.visor.module.infra.entity.request.user.UserSessionOfflineRequest;
import org.dromara.visor.module.infra.entity.request.user.UserUpdatePasswordRequest;
import org.dromara.visor.module.infra.entity.vo.LoginHistoryVO;
import org.dromara.visor.module.infra.entity.vo.OperatorLogVO;
import org.dromara.visor.module.infra.entity.vo.SystemUserVO;
import org.dromara.visor.module.infra.entity.vo.UserSessionVO;

import java.util.List;

/**
 * 个人服务
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023/11/1 0:25
 */
public interface MineService {

    /**
     * 获取当前登录用户信息
     *
     * @return user
     */
    SystemUserVO getCurrentUserInfo();

    /**
     * 更新当前登录用户信息
     *
     * @param request request
     * @return effect
     */
    Integer updateCurrentUser(SystemUserUpdateRequest request);

    /**
     * 修改当前用户密码
     *
     * @param request request
     */
    void updateCurrentUserPassword(UserUpdatePasswordRequest request);

    /**
     * 获取当前用户登录日志
     *
     * @param count count
     * @return 登录日志
     */
    List<LoginHistoryVO> getCurrentLoginHistory(Integer count);

    /**
     * 获取当前用户会话列表
     *
     * @return 会话列表
     */
    List<UserSessionVO> getCurrentUserSessionList();

    /**
     * 下线当前用户会话
     *
     * @param request request
     */
    void offlineCurrentUserSession(UserSessionOfflineRequest request);

    /**
     * 查询当前用户操作日志
     *
     * @param request request
     * @return rows
     */
    DataGrid<OperatorLogVO> getCurrentUserOperatorLog(OperatorLogQueryRequest request);

}
