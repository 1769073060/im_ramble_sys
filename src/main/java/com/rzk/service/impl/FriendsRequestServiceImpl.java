package com.rzk.service.impl;

import com.rzk.pojo.FriendsRequest;
import com.rzk.mapper.FriendsRequestMapper;
import com.rzk.service.IFriendsRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.vo.FriendsRequestVo;
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

    /**
     *
     * @param acceptUserId  被添加人id
     * @return
     */
    @Override
    public List<FriendsRequestVo> queryFriendRequestList(String acceptUserId) {
        return friendsRequestMapper.queryFriendRequestList(acceptUserId);
    }
}
