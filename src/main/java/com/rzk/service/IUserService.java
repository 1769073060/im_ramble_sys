package com.rzk.service;

import com.rzk.enums.SearchFriendsStatusEnum;
import com.rzk.pojo.FriendsRequest;
import com.rzk.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
public interface IUserService extends IService<User> {

    /**
     * 增加用户
     * @param user
     * @return
     * @throws Exception
     */
    public User insertUser(User user)throws Exception;

    /**
     * 修改昵称方法
     * @param user
     * @return
     */
    public User setNickName(User user);

    /**
     * 修改个性签名方法
     * @param user
     * @return
     */
    public User setPersonalizedSignature(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    public User updateUserInfo(User user);

    /**
     * 搜索好友的前置条件接口
     * @param myUserId
     * @param friendUserName
     * @return
     */
    Integer preconditionSearchFriends(String myUserId, String friendUserName);

    /**
     * 发送好友请求
     * @param myUserId
     * @param friendUserName
     */
    Integer sendFriendRequest(String myUserId, String friendUserName);


}
