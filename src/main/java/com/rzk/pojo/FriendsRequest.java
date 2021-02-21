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
 * 好友请求
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FriendsRequest对象", description="好友请求")
public class FriendsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "发送好友请求id")
    private String sendUserId;

    @ApiModelProperty(value = "回复好友请求id")
    private String acceptUserId;

    @ApiModelProperty(value = "发送请求的时间")
    private Long requestDateTime;


}
