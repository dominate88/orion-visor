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
package org.dromara.visor.framework.biz.operator.log.core.factory;

import cn.orionsec.kit.lang.utils.Arrays1;
import org.dromara.visor.framework.biz.operator.log.core.annotation.Module;
import org.dromara.visor.framework.biz.operator.log.core.model.OperatorType;

import javax.annotation.PostConstruct;

/**
 * 操作类型初始化器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023/10/13 17:45
 */
public abstract class InitializingOperatorTypes implements OperatorTypeDefinition {

    @PostConstruct
    public void init() {
        // 获取模块注解
        Module moduleDefinition = this.getClass().getAnnotation(Module.class);
        if (moduleDefinition == null) {
            return;
        }
        // 获取类型
        OperatorType[] types = this.types();
        if (Arrays1.isEmpty(types)) {
            return;
        }
        // 定义类型
        String module = moduleDefinition.value();
        for (OperatorType type : types) {
            type.setModule(module);
            OperatorTypeHolder.set(type);
        }
    }

}
