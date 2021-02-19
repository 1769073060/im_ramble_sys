package com.rzk.service.impl;

import com.rzk.pojo.MyFriends;
import com.rzk.mapper.MyFriendsMapper;
import com.rzk.service.IMyFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 我的好友表 服务实现类
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Service
@Transactional
public class MyFriendsServiceImpl extends ServiceImpl<MyFriendsMapper, MyFriends> implements IMyFriendsService {

}
