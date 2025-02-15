package com.gemini.service.system.menu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gemini.framework.constant.Constants;
import com.gemini.framework.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author edison
 */
@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    private LambdaQueryWrapper<MenuEntity> buildQueryWrapper(MenuVO menuVO) {
        LambdaQueryWrapper<MenuEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(menuVO.getName()), MenuEntity::getName, menuVO.getName());
        return wrapper;
    }

    public Boolean validateMenuNameUnique(MenuVO menuVO) {
        LambdaQueryWrapper<MenuEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuEntity::getName, menuVO.getName())
                .ne(Objects.nonNull(menuVO.getId()), MenuEntity::getId, menuVO.getId());
        boolean exists = menuMapper.exists(wrapper);
        return !exists;
    }

    public int insertMenu(MenuVO menuVO) {
        MenuEntity menu = new MenuEntity(menuVO);
        if (Objects.isNull(menu.getParentId())) {
            menu.setParentId(Constants.MENU_ROOT);
        }
        return menuMapper.insert(menu);
    }

    public int modifyMenu(MenuVO menuVO) {
        MenuEntity menu = new MenuEntity(menuVO);
        return menuMapper.updateById(menu);
    }

    public int removeMenu(Long menuId) {
        LambdaQueryWrapper<MenuEntity> existsWrapper = Wrappers.lambdaQuery();
        existsWrapper.eq(MenuEntity::getParentId, menuId);
        boolean exists = menuMapper.exists(existsWrapper);
        if (exists) {
            throw new BizException("当前菜单下有子菜单,不能删除");
        }
        return menuMapper.deleteById(menuId);
    }

    public List<Tree<Integer>> getMenuTreeList() {
        List<MenuEntity> menus = menuMapper.selectList(null);
        if (CollectionUtil.isNotEmpty(menus)) {
            List<TreeNode<Integer>> list = menus.stream().map(MenuService::getMenuTreeNode).collect(Collectors.toList());
            return TreeUtil.build(list, Constants.MENU_ROOT);
        }
        return Collections.emptyList();
    }

    public static TreeNode<Integer> getMenuTreeNode(MenuEntity menu) {
        TreeNode<Integer> treeNode = new TreeNode<>();
        treeNode.setId(menu.getId());
        treeNode.setParentId(menu.getParentId());
        treeNode.setName(menu.getName());
        HashMap<String, Object> extra = new HashMap<>();
        extra.put("perm", menu.getPerm());
        extra.put("createTime", menu.getCreateTime());
        extra.put("updateTime", menu.getUpdateTime());
        treeNode.setExtra(extra);
        return treeNode;
    }

    public MenuVO getMenuInfo(Integer menuId) {
        MenuEntity sysMenu = menuMapper.selectById(menuId);
        return new MenuVO(sysMenu);
    }
}
