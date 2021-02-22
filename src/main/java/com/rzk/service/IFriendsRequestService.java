package com.rzk.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rzk.pojo.FriendsRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rzk.vo.FriendsRequestVo;
import com.rzk.vo.MyFriendsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 好友请求 服务类
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
public interface IFriendsRequestService extends IService<FriendsRequest> {

    /**
     * 根据同意人的id去查询是否有人来添加同意人
     * @param acceptUserId
     * @return
     */
    List<FriendsRequestVo> queryFriendRequestList(String acceptUserId);


    /**
     * 处理好友请求 忽略好友请求
     * @param friendsRequest
     */
    void deleteFriendRequest(FriendsRequest friendsRequest);

    /**
     * 处理好友请求 通过好友请求
     * @param sendUserId
     * @param acceptUserId
     */
    void passFriendRequest(String sendUserId,String acceptUserId);

    /**
     * 好友列表查询
     * @param userId
     * @return
     */
    List<MyFriendsVo> queryMyFriends(String userId);
}
