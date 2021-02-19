package com.rzk.service.impl;

import com.rzk.pojo.FriendsRequest;
import com.rzk.mapper.FriendsRequestMapper;
import com.rzk.service.IFriendsRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
