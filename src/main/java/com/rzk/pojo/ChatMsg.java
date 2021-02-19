package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 聊天消息表
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ChatMsg对象", description="聊天消息表")
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "发送消息人的id")
    private String sendUserId;

    @ApiModelProperty(value = "接收消息人的id")
    private String acceptUserId;

    @ApiModelProperty(value = "聊天的内容")
    private String msg;

    @ApiModelProperty(value = "消息是否签收状态：1:签收 0:未签收")
    private Integer signFlag;

    @ApiModelProperty(value = "发送请求的时间")
    private Date createTime;


}
