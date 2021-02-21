package com.rzk.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 我的好友表
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MyFriends对象", description="我的好友表")
public class MyFriends implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String myUserId;

    @ApiModelProperty(value = "用户的好友id")
    private String myFriendUserId;

    private Long createTime;
}
