package com.gemini.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import com.gemini.annotations.log.OperLog;
import com.gemini.framework.core.AjaxResult;
import com.gemini.framework.core.BaseController;
import com.gemini.framework.enums.OperTypeEnum;
import com.gemini.framework.exception.BizException;
import com.gemini.service.system.menu.MenuService;
import com.gemini.service.system.menu.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author edison
 */
@Api("系统菜单模块")
@RequestMapping("system/menu")
@RestController
@SaCheckPermission("system:menu")
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    @ApiOperation(value = "树形列表")
    @GetMapping
    public AjaxResult treeList() {
        List<Tree<Integer>> menuTreeList = menuService.getMenuTreeList();
        return AjaxResult.ok(menuTreeList);
    }

    @ApiOperation(value = "菜单详情")
    @GetMapping("{menuId}")
    public AjaxResult info(@PathVariable("menuId") Integer menuId) {
        MenuVO menuInfo = menuService.getMenuInfo(menuId);
        return AjaxResult.ok(menuInfo);
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping
    @OperLog(title = "菜单管理", type = OperTypeEnum.ADD, desc = "添加菜单")
    public AjaxResult add(@RequestBody MenuVO menuVO) {
        if (StrUtil.isBlank(menuVO.getName())) {
            throw new BizException("菜单名称不能为空");
        }
        Boolean uniqueBool = menuService.validateMenuNameUnique(menuVO);
        if (!uniqueBool) {
            throw new BizException("菜单名称: " + menuVO.getName() + "已经存在");
        }
        int count = menuService.insertMenu(menuVO);
        return toAjax(count);
    }

    @ApiOperation(value = "编辑菜单")
    @PutMapping
    @OperLog(title = "菜单管理", type = OperTypeEnum.EDIT, desc = "编辑菜单")
    public AjaxResult edit(@RequestBody MenuVO menuVO) {
        if (StrUtil.isBlank(menuVO.getName())) {
            throw new BizException("菜单名称不能为空");
        }
        Boolean uniqueBool = menuService.validateMenuNameUnique(menuVO);
        if (!uniqueBool) {
            throw new BizException("菜单名称: " + menuVO.getName() + "已经存在");
        }
        int count = menuService.modifyMenu(menuVO);
        return toAjax(count);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("{menuId}")
    @OperLog(title = "菜单管理", type = OperTypeEnum.REMOVE, desc = "删除菜单")
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        int count = menuService.removeMenu(menuId);
        return toAjax(count);
    }
}
