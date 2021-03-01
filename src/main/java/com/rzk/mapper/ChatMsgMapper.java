package com.rzk.mapper;

import com.rzk.pojo.ChatMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 聊天消息表 Mapper 接口
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
public interface ChatMsgMapper extends BaseMapper<ChatMsg> {


    @Update({"<script> update chat_msg set sign_flag =1 where id in" +
            "<foreach collection ='list' item ='item' index ='index' separator=',' open='(' close=')'  > " +
            "#{item} " +
            "</foreach> </script>" })
    int updateBatchMsgSigned(@Param("item") List<String> item);
}
