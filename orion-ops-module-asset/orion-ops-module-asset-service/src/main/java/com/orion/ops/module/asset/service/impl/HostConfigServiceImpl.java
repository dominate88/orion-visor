package com.orion.ops.module.asset.service.impl;

import com.alibaba.fastjson.JSON;
import com.orion.ops.framework.biz.operator.log.core.uitls.OperatorLogs;
import com.orion.ops.framework.common.constant.Const;
import com.orion.ops.framework.common.constant.ErrorMessage;
import com.orion.ops.framework.common.enums.EnableStatus;
import com.orion.ops.framework.common.handler.data.model.GenericsDataModel;
import com.orion.ops.framework.common.handler.data.strategy.MapDataStrategy;
import com.orion.ops.framework.common.utils.Valid;
import com.orion.ops.module.asset.convert.HostConfigConvert;
import com.orion.ops.module.asset.dao.HostConfigDAO;
import com.orion.ops.module.asset.dao.HostDAO;
import com.orion.ops.module.asset.entity.domain.HostConfigDO;
import com.orion.ops.module.asset.entity.domain.HostDO;
import com.orion.ops.module.asset.entity.request.host.HostConfigUpdateRequest;
import com.orion.ops.module.asset.entity.request.host.HostConfigUpdateStatusRequest;
import com.orion.ops.module.asset.entity.vo.HostConfigVO;
import com.orion.ops.module.asset.enums.HostConfigTypeEnum;
import com.orion.ops.module.asset.service.HostConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主机配置 服务实现类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023-9-11 14:16
 */
@Slf4j
@Service
public class HostConfigServiceImpl implements HostConfigService {

    @Resource
    private HostDAO hostDAO;

    @Resource
    private HostConfigDAO hostConfigDAO;

    // FIXME
    // T 动态初始化
    // T 改为小写
    // F 保存后重置有问题
    // F 前端逻辑
    // F 测试

    @Override
    public HostConfigVO getHostConfig(Long hostId, String type) {
        HostConfigTypeEnum configType = Valid.valid(HostConfigTypeEnum::of, type);
        // 查询配置
        HostConfigDO config = hostConfigDAO.getHostConfigByHostId(hostId, type);
        Valid.notNull(config, ErrorMessage.CONFIG_ABSENT);
        // 转换
        HostConfigVO vo = HostConfigConvert.MAPPER.to(config);
        // 获取配置
        Map<String, Object> configMap = configType.getStrategyBean().toView(config.getConfig());
        vo.setConfig(configMap);
        return vo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends GenericsDataModel> T getHostConfig(Long hostId, HostConfigTypeEnum type) {
        // 查询配置
        HostConfigDO config = hostConfigDAO.getHostConfigByHostId(hostId, type.getType());
        if (config == null) {
            return null;
        }
        return (T) JSON.parseObject(config.getConfig(), type.getModel());
    }

    @Override
    public List<HostConfigVO> getHostConfigList(Long hostId) {
        // 查询
        List<HostConfigDO> configs = hostConfigDAO.getHostConfigByHostId(hostId);
        // 返回
        return configs.stream().map(s -> {
            HostConfigVO vo = HostConfigConvert.MAPPER.to(s);
            // 获取配置
            Map<String, Object> config = HostConfigTypeEnum.of(s.getType())
                    .getStrategyBean()
                    .toView(s.getConfig());
            vo.setConfig(config);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer updateHostConfig(HostConfigUpdateRequest request) {
        Long id = request.getId();
        // 查询原配置
        HostConfigDO record = hostConfigDAO.selectById(id);
        Valid.notNull(record, ErrorMessage.CONFIG_ABSENT);
        HostConfigTypeEnum type = Valid.valid(HostConfigTypeEnum::of, record.getType());
        GenericsDataModel newConfig = type.parse(request.getConfig());
        // 查询主机
        HostDO host = hostDAO.selectById(record.getHostId());
        Valid.notNull(host, ErrorMessage.HOST_ABSENT);
        // 添加日志参数
        OperatorLogs.add(OperatorLogs.REL_ID, host.getId());
        OperatorLogs.add(OperatorLogs.NAME, host.getName());
        OperatorLogs.add(OperatorLogs.TYPE, type.getType());
        // 检查版本
        Valid.eq(record.getVersion(), request.getVersion(), ErrorMessage.DATA_MODIFIED);
        MapDataStrategy<GenericsDataModel> strategy = type.getStrategyBean();
        GenericsDataModel beforeConfig = type.parse(record.getConfig());
        // 更新前校验
        strategy.doValidChain(beforeConfig, newConfig);
        // 修改配置
        HostConfigDO update = new HostConfigDO();
        update.setId(id);
        update.setVersion(request.getVersion());
        update.setConfig(newConfig.serial());
        int effect = hostConfigDAO.updateById(update);
        Valid.version(effect);
        return update.getVersion();
    }

    @Override
    public Integer updateHostConfigStatus(HostConfigUpdateStatusRequest request) {
        Long id = request.getId();
        Long hostId = request.getHostId();
        Integer status = request.getStatus();
        EnableStatus statusEnum = Valid.valid(EnableStatus::of, status);
        HostConfigTypeEnum type = Valid.valid(HostConfigTypeEnum::of, request.getType());
        // 查询主机
        HostDO host = hostDAO.selectById(hostId);
        Valid.notNull(host, ErrorMessage.HOST_ABSENT);
        // 添加日志参数
        OperatorLogs.add(OperatorLogs.REL_ID, host.getId());
        OperatorLogs.add(OperatorLogs.NAME, host.getName());
        OperatorLogs.add(OperatorLogs.STATUS_NAME, statusEnum.name());
        if (id != null) {
            // 修改 查询配置
            HostConfigDO record = hostConfigDAO.selectById(id);
            Valid.notNull(record, ErrorMessage.CONFIG_ABSENT);
            // 修改状态
            HostConfigDO update = new HostConfigDO();
            update.setId(id);
            update.setStatus(status);
            update.setVersion(request.getVersion());
            int effect = hostConfigDAO.updateById(update);
            Valid.version(effect);
            return update.getVersion();
        } else {
            // 新增 初始化
            HostConfigDO defaultConfig = this.getDefaultConfig(hostId, type);
            defaultConfig.setStatus(status);
            // 插入数据
            hostConfigDAO.insert(defaultConfig);
            return defaultConfig.getVersion();
        }
    }

    @Override
    public void initHostConfig(Long hostId) {
        List<HostConfigDO> configs = Arrays.stream(HostConfigTypeEnum.values())
                .map(s -> this.getDefaultConfig(hostId, s))
                .collect(Collectors.toList());
        hostConfigDAO.insertBatch(configs);
    }

    /**
     * 获取默认配置
     *
     * @param hostId hostId
     * @param type   type
     * @return config
     */
    private HostConfigDO getDefaultConfig(Long hostId, HostConfigTypeEnum type) {
        HostConfigDO insert = new HostConfigDO();
        insert.setHostId(hostId);
        insert.setType(type.getType());
        insert.setStatus(type.getDefaultStatus());
        insert.setConfig(type.getStrategyBean().getDefault().serial());
        insert.setVersion(Const.DEFAULT_VERSION);
        return insert;
    }

}
