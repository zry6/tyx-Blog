package com.zry.simpleBlog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zry.simpleBlog.comment.respBean.RespBean;
import com.zry.simpleBlog.dto.LoginDto;
import com.zry.simpleBlog.dto.UpdatePswDto;
import com.zry.simpleBlog.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zry
 * @since 2022-04-09
 */
public interface IUserService extends IService<User> {
    /**
     * 功能描述: 登录 方法
     *
     * @param loginDto ; 接受 login数据传输对象
     * @return RespBean;
     * @author zry
     */
    RespBean doLogin(LoginDto loginDto);


    /**
     * 更新密码
     * @param pswDto;
     * @param request;
     * @return
     */
    RespBean updatePassword(UpdatePswDto pswDto, HttpServletRequest request);

//    /**
//     * 功能描述: 根据id更新
//     *
//     * @param id,userVo;
//     * @author zry
//     * @create 2022/4/16
//     */
//    void updateUser(Integer id, UserVo userVo);
}
