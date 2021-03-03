package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.rzk.enums.MsgActionEnum;
import com.rzk.enums.UserChannelRel;
import com.rzk.idworker.Sid;
import com.rzk.mapper.MyFriendsMapper;
import com.rzk.netty.DataContent;
import com.rzk.pojo.FriendsRequest;
import com.rzk.mapper.FriendsRequestMapper;
import com.rzk.pojo.MyFriends;
import com.rzk.service.IFriendsRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.utils.JsonUtils;
import com.rzk.vo.FriendsRequestVo;
import com.rzk.vo.MyFriendsVo;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 好友请求 服务实现类
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Service
@Transactional
public class FriendsRequestServiceImpl extends ServiceImpl<FriendsRequestMapper, FriendsRequest> implements IFriendsRequestService {

    @Resource
    private FriendsRequestMapper friendsRequestMapper;
    @Resource
    private Sid sid;
    @Resource
    private MyFriendsMapper myFriendsMapper;


    /**
     *
     * @param acceptUserId  被添加人id
     * @return
     */
    @Override
    public List<FriendsRequestVo> queryFriendRequestList(String acceptUserId) {
        return friendsRequestMapper.queryFriendRequestList(acceptUserId);
    }

    /**
     * 忽略好友添加请求
     * @param friendsRequest
     */
    @Override
    public void deleteFriendRequest(FriendsRequest friendsRequest) {
        UpdateWrapper<FriendsRequest> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("send_user_id",friendsRequest.getSendUserId());
        updateWrapper.eq("accept_user_id",friendsRequest.getAcceptUserId());
        friendsRequestMapper.delete(updateWrapper);
    }

    /**
     * 通过好友请求
     * @param sendUserId
     * @param acceptUserId
     */
    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        //进行双向好友数据保存
        saveFriends(sendUserId,acceptUserId);
        saveFriends(acceptUserId,sendUserId);
        //保存成功后删除好友请求表中的数据
        FriendsRequest friendsRequest = new FriendsRequest();
        friendsRequest.setSendUserId(sendUserId);
        friendsRequest.setAcceptUserId(acceptUserId);

        deleteFriendRequest(friendsRequest);



        Channel sendChannel = UserChannelRel.get(sendUserId);
        //如果这个通道不等于空就主动推送消息
        if (sendChannel!=null){
            //websocket主动推送消息到请求推送者,更新通讯录列表为最新的
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);

            //消息推送
            sendChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
        }
    }

    /**
     * 查询好友表中的列表数据
     * @param userId 用户id
     * @return
     */
    @Override
    public List<MyFriendsVo> queryMyFriends(String userId) {
        return friendsRequestMapper.queryMyFriends(userId);
    }

    //通过好友请求并保存数据到my_friends表中
    private void saveFriends(String sendUserId, String acceptUserId){
        MyFriends myFriends = new MyFriends();
        String recordId = sid.nextShort();

        myFriends.setId(recordId);
        myFriends.setMyUserId(sendUserId);//发送好友请求人的id
        myFriends.setMyFriendUserId(acceptUserId);//接收请求人的id
        myFriends.setCreateTime(System.currentTimeMillis());

        myFriendsMapper.insert(myFriends);
    }

}
