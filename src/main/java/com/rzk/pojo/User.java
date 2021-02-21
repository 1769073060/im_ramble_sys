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
 * 用户
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户名，账号，慕信号")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String passWord;

    @ApiModelProperty(value = "我的头像，如果没有默认给一张")
    private String faceImage;

    @ApiModelProperty(value = "大头像")
    private String faceImageBig;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "新用户注册后默认后台生成二维码，并且上传到fastdfs")
    private String qrcode;

    @ApiModelProperty(value = "个人签名")
    private String personalizedSignature;

    @ApiModelProperty(value = "每个手机的id")
    private String cid;
    private Long createTime;
    private Long updateTime;

}
