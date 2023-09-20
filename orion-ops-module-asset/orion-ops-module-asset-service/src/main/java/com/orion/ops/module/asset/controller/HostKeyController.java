package com.orion.ops.module.asset.controller;

import com.orion.lang.define.wrapper.DataGrid;
import com.orion.ops.framework.common.annotation.IgnoreLog;
import com.orion.ops.framework.common.annotation.RestWrapper;
import com.orion.ops.framework.common.constant.IgnoreLogMode;
import com.orion.ops.framework.common.valid.group.Page;
import com.orion.ops.module.asset.service.*;
import com.orion.ops.module.asset.entity.vo.*;
import com.orion.ops.module.asset.entity.request.host.*;
import com.orion.ops.module.asset.entity.export.*;
import com.orion.ops.module.asset.convert.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 主机秘钥 api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2023-9-20 11:55
 */
@Tag(name = "asset - 主机秘钥服务")
@Slf4j
@Validated
@RestWrapper
@RestController
@RequestMapping("/asset/host-key")
@SuppressWarnings({"ELValidationInJSP", "SpringElInspection"})
public class HostKeyController {

    @Resource
    private HostKeyService hostKeyService;

    @PostMapping("/create")
    @Operation(summary = "创建主机秘钥")
    @PreAuthorize("@ss.hasPermission('asset:host-key:create')")
    public Long createHostKey(@Validated @RequestBody HostKeyCreateRequest request) {
        return hostKeyService.createHostKey(request);
    }

    @PutMapping("/update")
    @Operation(summary = "通过 id 更新主机秘钥")
    @PreAuthorize("@ss.hasPermission('asset:host-key:update')")
    public Integer updateHostKey(@Validated @RequestBody HostKeyUpdateRequest request) {
        return hostKeyService.updateHostKeyById(request);
    }

    @IgnoreLog(IgnoreLogMode.RET)
    @GetMapping("/get")
    @Operation(summary = "通过 id 查询主机秘钥")
    @Parameter(name = "id", description = "id", required = true)
    @PreAuthorize("@ss.hasPermission('asset:host-key:query')")
    public HostKeyVO getHostKey(@RequestParam("id") Long id) {
        return hostKeyService.getHostKeyById(id);
    }

    @IgnoreLog(IgnoreLogMode.RET)
    @GetMapping("/list")
    @Operation(summary = "通过 id 批量查询主机秘钥")
    @Parameter(name = "idList", description = "idList", required = true)
    @PreAuthorize("@ss.hasPermission('asset:host-key:query')")
    public List<HostKeyVO> getHostKeyList(@RequestParam("idList") List<Long> idList) {
        return hostKeyService.getHostKeyByIdList(idList);
    }

    @IgnoreLog(IgnoreLogMode.RET)
    @PostMapping("/list-all")
    @Operation(summary = "查询主机秘钥")
    @PreAuthorize("@ss.hasPermission('asset:host-key:query')")
    public List<HostKeyVO> getHostKeyListAll(@Validated @RequestBody HostKeyQueryRequest request) {
        return hostKeyService.getHostKeyList(request);
    }

    @IgnoreLog(IgnoreLogMode.RET)
    @PostMapping("/query")
    @Operation(summary = "分页查询主机秘钥")
    @PreAuthorize("@ss.hasPermission('asset:host-key:query')")
    public DataGrid<HostKeyVO> getHostKeyPage(@Validated(Page.class) @RequestBody HostKeyQueryRequest request) {
        return hostKeyService.getHostKeyPage(request);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 id 删除主机秘钥")
    @Parameter(name = "id", description = "id", required = true)
    @PreAuthorize("@ss.hasPermission('asset:host-key:delete')")
    public Integer deleteHostKey(@RequestParam("id") Long id) {
        return hostKeyService.deleteHostKeyById(id);
    }

    @DeleteMapping("/delete-batch")
    @Operation(summary = "通过 id 批量删除主机秘钥")
    @Parameter(name = "idList", description = "idList", required = true)
    @PreAuthorize("@ss.hasPermission('asset:host-key:delete')")
    public Integer batchDeleteHostKey(@RequestParam("idList") List<Long> idList) {
        return hostKeyService.batchDeleteHostKeyByIdList(idList);
    }

    @PostMapping("/export")
    @Operation(summary = "导出主机秘钥")
    @PreAuthorize("@ss.hasPermission('asset:host-key:export')")
    public void exportHostKey(@Validated @RequestBody HostKeyQueryRequest request,
                              HttpServletResponse response) throws IOException {
        hostKeyService.exportHostKey(request, response);
    }

}

