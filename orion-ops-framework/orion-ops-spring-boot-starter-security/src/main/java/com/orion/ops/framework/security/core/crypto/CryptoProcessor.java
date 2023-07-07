package com.orion.ops.framework.security.core.crypto;

/**
 * 数据加密器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023/7/7 22:48
 */
public abstract class CryptoProcessor<Config extends CryptoConfig> implements ValueCrypto {

    protected final Config config;

    protected CryptoProcessor(Config config) {
        this.config = config;
    }

    /**
     * 初始化秘钥
     */
    protected abstract void initSecretKey();

    /**
     * 初始化参数规格
     */
    protected abstract void initParamSpec();

    /**
     * 构建加密器
     */
    protected abstract void builderCrypto();

}
