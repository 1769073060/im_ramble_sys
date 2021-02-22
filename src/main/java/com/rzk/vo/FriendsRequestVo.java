package com.rzk.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @PackageName : com.rzk.vo
 * @FileName : FriendsRequestVo
 * @Description : vo的意思是 有些数据是不需要给对方展示的
 * @Author : rzk
 * @CreateTime : 2021/2/22 16:08
 * @Version : 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FriendsRequest对象", description="好友请求")
public class FriendsRequestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发送好友请求id")
    private String sendUserId;

    @ApiModelProperty(value = "发送者名字")
    private String sendUserName;

    @ApiModelProperty(value = "发送者昵称")
    private String sendNickName;

    @ApiModelProperty(value = "发送者头像")
    private String sendFaceImage;
}

