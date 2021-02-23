package com.spark.platform.admin.biz.controller;

import com.spark.platform.admin.api.entity.dept.Dept;
import com.spark.platform.admin.api.vo.VueTree;
import com.spark.platform.admin.biz.service.dept.DeptService;
import com.spark.platform.common.base.support.ApiResponse;
import com.spark.platform.common.base.support.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: wangdingfeng
 * @Date: 2020/3/20 22:42
 * @Description: 部门管理
 */
@RestController
@RequestMapping("/dept")
@Api(tags = "部门管理")
@RequiredArgsConstructor
public class DeptController extends BaseController {

    private final DeptService deptService;

    @GetMapping("/list")
    @ApiOperation(value = "部门列表")
    public ApiResponse<List<Dept>> list(Dept dept){
        return success(deptService.list(dept));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取部门信息")
    public ApiResponse<Dept> findById(@PathVariable Long id){
        return success(deptService.getById(id));
    }

    @GetMapping("/tree")
    @ApiOperation(value = "获取部门树")
    public ApiResponse<List<VueTree>> getTree(boolean isRoot){
        return success(deptService.getTree(isRoot));
    }

    @PostMapping
    @ApiOperation(value = "保存部门信息")
    @PreAuthorize("hasAnyAuthority('dept:add')")
    public ApiResponse save(@RequestBody @Valid Dept dept){
        return success(deptService.save(dept));
    }

    @PutMapping
    @ApiOperation(value = "更新部门信息")
    @PreAuthorize("hasAnyAuthority('dept:edit')")
    public ApiResponse update(@RequestBody @Valid Dept dept){
        return success(deptService.updateById(dept));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门信息")
    @PreAuthorize("hasAnyAuthority('dept:delete')")
    public ApiResponse delete(@PathVariable Long id){
        return success(deptService.removeById(id));
    }


}
