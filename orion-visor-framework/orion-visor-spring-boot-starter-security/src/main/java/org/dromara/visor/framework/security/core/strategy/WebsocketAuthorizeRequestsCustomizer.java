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
package org.dromara.visor.framework.security.core.strategy;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * websocket 认证策略
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023/7/7 13:04
 */
public class WebsocketAuthorizeRequestsCustomizer extends AuthorizeRequestsCustomizer {

    private final String prefix;

    public WebsocketAuthorizeRequestsCustomizer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        // websocket 允许匿名访问
        registry.antMatchers(prefix + "/**").permitAll();
    }

}
