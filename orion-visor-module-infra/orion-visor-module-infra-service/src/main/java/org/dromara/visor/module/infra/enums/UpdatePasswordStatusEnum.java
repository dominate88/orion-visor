/*
 * Copyright (c) 2023 - present Dromara (visor.dromara.org ljh1553488six@139.com).
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
package org.dromara.visor.module.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 更新密码状态
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2024/12/11 14:37
 */
@Getter
@AllArgsConstructor
public enum UpdatePasswordStatusEnum {

    /**
     * 无需修改
     */
    NO_REQUIRE(0),

    /**
     * 需要修改
     */
    REQUIRED(1),

    ;

    private final Integer status;

}
