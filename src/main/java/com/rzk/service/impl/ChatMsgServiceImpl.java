package com.rzk.service.impl;

import com.rzk.pojo.ChatMsg;
import com.rzk.mapper.ChatMsgMapper;
import com.rzk.service.IChatMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 聊天消息表 服务实现类
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Service
@Transactional
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg> implements IChatMsgService {

}
