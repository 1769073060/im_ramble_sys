package com.rzk.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.rzk.pojo.FriendsRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rzk.vo.FriendsRequestVo;
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

}
