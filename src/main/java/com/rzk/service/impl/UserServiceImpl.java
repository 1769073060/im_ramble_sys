package com.rzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rzk.enums.MsgSignFlagEnum;
import com.rzk.enums.SearchFriendsStatusEnum;
import com.rzk.mapper.ChatMsgMapper;
import com.rzk.mapper.FriendsRequestMapper;
import com.rzk.mapper.MyFriendsMapper;
import com.rzk.netty.ChatMsg;
import com.rzk.pojo.FriendsRequest;
import com.rzk.pojo.MyFriends;
import com.rzk.pojo.User;
import com.rzk.idworker.Sid;
import com.rzk.mapper.UserMapper;
import com.rzk.service.IFriendsRequestService;
import com.rzk.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.utils.FastDFSClient;
import com.rzk.utils.FileUtils;
import com.rzk.utils.MD5Utils;
import com.rzk.utils.QRCodeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MyFriendsMapper myFriendsMapper;
    @Resource
    private FriendsRequestMapper friendsRequestMapper;
    @Resource
    private Sid sid;
    @Resource
    private QRCodeUtils qrCodeUtils;
    @Resource
    private FastDFSClient fastDFSClient;
    @Resource
    private ChatMsgMapper chatMsgMapper;


    /**
     * 增加用户
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public User insertUser(User user){
        String userId = sid.nextShort();
        //为每个注册用户生成一个唯一的二维码
        String qrCodePath = userId+"qrcode.png";
        //创建二维码对象信息
        qrCodeUtils.createQRCode(qrCodePath,"ramble_qrcode"+user.getUserName());
        //二维码文件生成
        MultipartFile qrcodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeURL = "";
        try {
            //上传到服务器
            qrCodeURL  = fastDFSClient.uploadQRCode(qrcodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setQrcode(qrCodeURL);
        user.setId(userId);
        user.setFaceImage("");
        user.setFaceImageBig("");
        user.setNickName(user.getUserName());
        user.setUserName(user.getUserName());
        user.setPersonalizedSignature("");
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setPassWord(MD5Utils.getPwd(user.getPassWord()));
        userMapper.insert(user);
        return user;
    }

    /**
     * 修改昵称
     * @param user
     * @return
     */
    @Override
    public User setNickName(User user) {
        userMapper.updateById(user);
        User selectById = userMapper.selectById(user.getId());
        return selectById;
    }

    /**
     * 修改改个性签名
     * @param user
     * @return
     */
    @Override
    public User setPersonalizedSignature(User user) {
        user.setUpdateTime(System.currentTimeMillis());
        userMapper.updateById(user);
        User selectById = userMapper.selectById(user.getId());
        return selectById;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @Override
    public User updateUserInfo(User user) {
        user.setUpdateTime(System.currentTimeMillis());
        userMapper.updateById(user);
        User selectById = userMapper.selectById(user.getId());
        return selectById;
    }

    /**
     * 搜索好友的前置条件
     * @param myUserId
     * @param friendUserName
     * @return
     */
    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUserName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",friendUserName);
        User user = userMapper.selectOne(queryWrapper);
        //搜索的用户如果不存在,则返回[无此用户]
        if (user == null){
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        //搜索的账号如果是你自己,则返回[不能添加自己]
        if (myUserId.equals(user.getId())){
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }

        //搜索的朋友已经是你的好友,返回[该用户已经是你的好友]
        QueryWrapper<MyFriends> friendsQueryWrapper = new QueryWrapper<>();
        friendsQueryWrapper.eq("my_user_id",myUserId);
        friendsQueryWrapper.eq("my_friend_user_id",user.getId());
        MyFriends myFriends = myFriendsMapper.selectOne(friendsQueryWrapper);
        //如果不等于空,就证明已经是你的好友了
        if (myFriends!=null){
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    /**
     * 0添加失败,1添加成功,2已经添加过
     * @param myUserId
     * @param friendUserName
     * @return
     */
    @Override
    public Integer sendFriendRequest(String myUserId, String friendUserName) {
        //查询好友信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",friendUserName);
        User user = userMapper.selectOne(queryWrapper);
        //查询是否添加过
        QueryWrapper<FriendsRequest> friendsRequestQueryWrapper = new QueryWrapper();
        friendsRequestQueryWrapper.eq("send_user_id",myUserId);
        friendsRequestQueryWrapper.eq("accept_user_id",user.getId());
        FriendsRequest friendsRequest1 = friendsRequestMapper.selectOne(friendsRequestQueryWrapper);
        //如果不等于空的话证明对方还能同意,如果同意的话会把 friends_request表中的数据删除,在my_friends表中添加数据,这时需要在my_friends表中的判断有没有该数据.因为可能删除好友后也会使friends_request表中的数据删除,所以要判断my_friends有没有该好友
        if (friendsRequest1!=null){
            return 2;
        }

        QueryWrapper<MyFriends> friendsQueryWrapper = new QueryWrapper<>();
        friendsQueryWrapper.eq("my_user_id",myUserId);
        friendsQueryWrapper.eq("my_friend_user_id",user.getId());
        MyFriends myFriends = myFriendsMapper.selectOne(friendsQueryWrapper);
        //如果等于空,就证明已经不是你的好友了
        if (myFriends==null){
            FriendsRequest friendsRequest = new FriendsRequest();
            String requestId = sid.nextShort();
            friendsRequest.setId(requestId);
            friendsRequest.setSendUserId(myUserId);//发送申请的用户id
            friendsRequest.setAcceptUserId(user.getId());//同意人id
            friendsRequest.setRequestDateTime(System.currentTimeMillis());
            int insert = friendsRequestMapper.insert(friendsRequest);
            return insert;
        }
        return  0;

    }

    @Override
    public String saveMsg(ChatMsg chatMsg) {
        com.rzk.pojo.ChatMsg msgDB = new com.rzk.pojo.ChatMsg();
        String msgId = sid.nextShort();
        msgDB.setId(msgId);
        msgDB.setAcceptUserId(chatMsg.getReceiverId());//接收者id
        msgDB.setSendUserId(chatMsg.getSenderId());//发送者id
        msgDB.setCreateTime(System.currentTimeMillis());
        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);//状态
        msgDB.setMsg(chatMsg.getMsg());//消息的内容

        chatMsgMapper.insert(msgDB);

        return msgId;
    }

    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        chatMsgMapper.updateBatchMsgSigned(msgIdList);
    }


}
