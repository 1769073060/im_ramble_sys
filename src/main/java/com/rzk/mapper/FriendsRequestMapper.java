package com.rzk.mapper;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rzk.pojo.FriendsRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzk.vo.FriendsRequestVo;
import com.rzk.vo.MyFriendsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.List;

/**
 * <p>
 * 好友请求 Mapper 接口
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
public interface FriendsRequestMapper extends BaseMapper<FriendsRequest> {
    /**
     * 发送者的发送请求   这里的信息是给接收者看的
     * @param acceptUserId
     * @return
     */
    @Select("select " +
            "    u.id as sendUserId,\n" +
            "    u.user_name as sendUserName,\n" +
            "    u.face_image as sendFaceImage,\n" +
            "    u.nick_name as sendNickName" +
            " from friends_request fr\n" +
            "left join user u\n" +
            "on fr.send_user_id = u.id\n" +
            "where fr.accept_user_id =#{acceptUserId}")
    List<FriendsRequestVo>  queryFriendRequestList(@Param("acceptUserId")String acceptUserId);

    /**
     *
     * @return
     */
    @Select("select\n" +
            "       u.id as friendUserId,\n" +
            "       u.user_name as friendsUserName,\n" +
            "       u.face_image as friendsFaceImage,\n" +
            "       u.nick_name as friendsNickName\n" +
            "from my_friends mf\n" +
            "left join user u\n" +
            "on u.id = mf.my_friend_user_id\n" +
            "where mf.my_user_id = #{userId}")
    List<MyFriendsVo> queryMyFriends(String userId);


}
