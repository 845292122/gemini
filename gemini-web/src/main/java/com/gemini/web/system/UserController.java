package com.gemini.web.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gemini.annotations.log.OperLog;
import com.gemini.framework.core.AjaxResult;
import com.gemini.framework.core.BaseController;
import com.gemini.framework.core.PageQuery;
import com.gemini.framework.enums.OperTypeEnum;
import com.gemini.framework.exception.BizException;
import com.gemini.service.system.user.UserEntity;
import com.gemini.service.system.user.UserService;
import com.gemini.service.system.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author edison
 */
@Api("系统用户模块")
@RequestMapping("system/user")
@RestController
@SaCheckPermission("system:user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "分页查询用户")
    @GetMapping
    public AjaxResult list(UserVO userVO, PageQuery pageQuery) {
        Page<UserEntity> pageRecord = userService.getPageRecord(userVO, pageQuery);
        return AjaxResult.ok(pageRecord);
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("{userId}")
    public AjaxResult info(@PathVariable("userId") Long userId) {
        UserVO userInfo = userService.getUserInfo(userId);
        return AjaxResult.ok(userInfo);
    }

    @OperLog(title = "用户管理", type = OperTypeEnum.ADD, desc = "添加用户")
    @ApiOperation(value = "添加用户")
    @PostMapping
    public AjaxResult add(@RequestBody UserVO userVO) {
        if (StrUtil.isBlank(userVO.getUsername())) {
            throw new BizException("用户名不能为空");
        }
        Boolean uniqueBool = userService.validateUserUnique(userVO);
        if (!uniqueBool) {
            throw new BizException("用户名: " + userVO.getUsername() + "已存在");
        }
        int count = userService.insertUser(userVO);
        return toAjax(count);
    }

    @OperLog(title = "用户管理", type = OperTypeEnum.EDIT, desc = "编辑用户")
    @ApiOperation(value = "编辑用户")
    @PutMapping
    public AjaxResult edit(@RequestBody UserVO userVO) {
        if (StrUtil.isBlank(userVO.getUsername())) {
            throw new BizException("账号不能为空");
        }
        Boolean uniqueBool = userService.validateUserUnique(userVO);
        if (!uniqueBool) {
            throw new BizException("用户名: " + userVO.getUsername() + "已存在");
        }
        int count = userService.modifyUser(userVO);
        return toAjax(count);
    }

    @OperLog(title = "用户管理", type = OperTypeEnum.REMOVE, desc = "删除用户")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("{userId}")
    public AjaxResult remove(@PathVariable("userId") Long userId) {
        int count = userService.removeUser(userId);
        return toAjax(count);
    }

    @ApiOperation(value = "修改个人信息")
    @PutMapping("changeInfo")
    public AjaxResult changeInfo(@RequestBody UserVO userVO) {
        int count = userService.changeInfo(userVO);
        return toAjax(count);
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("changePwd")
    public AjaxResult changePwd(@RequestBody Map<String, String> params) {
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        Integer id = Integer.valueOf(params.get("id"));
        int count = userService.changePwd(id, oldPwd, newPwd);
        return toAjax(count);
    }

    @PostMapping("upload-avatar")
    public AjaxResult uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return AjaxResult.fail("上传失败，请选择文件");
        }
        try {
            // 获取文件名
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            // 指定上传路径 todo 文件应该配置动态
            String filePath = "/Users/edison/home/work/gemini/upload";
            // 如果目录不存在，则创建
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            // 将上传文件保存到目标文件
            file.transferTo(dest);
            String fullName = filePath + fileName;
            int count = userService.updateAvatar(fullName);
            return toAjax(count);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.fail("上传失败");
        }
    }
}
