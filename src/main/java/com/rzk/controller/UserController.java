package com.rzk.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rzk.bo.UserBo;
import com.rzk.consts.MsgConsts;
import com.rzk.enums.SearchFriendsStatusEnum;
import com.rzk.pojo.User;
import com.rzk.service.IFriendsRequestService;
import com.rzk.service.IUserService;
import com.rzk.utils.FastDFSClient;
import com.rzk.utils.FileUtils;
import com.rzk.utils.MD5Utils;
import com.rzk.utils.Result;
import com.rzk.vo.FriendsRequestVo;
import com.rzk.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.List;

import static com.rzk.utils.ChkUtils.isEmpty;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService iUserService;
    @Resource
    private IFriendsRequestService iFriendsRequestService;

    @Resource
    private FastDFSClient fastDFSClient;


    @ApiOperation(httpMethod = "GET", value = "根据用户名查询好友")
    @GetMapping("queryUserNameAndIsExit")
    public Result queryUserNameAndIsExit(@RequestParam("usersname") String username){
        Result result = null;
        if (isEmpty(username)){
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("username",username);
            User user = iUserService.getOne(queryWrapper);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, user);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            result = new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG);
        }
        return result;
    }


    /**
     * 根据用户名,查询是否有该用户
     * @param username
     * @return
     */
    public User queryUserNameIsExit(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",username);
        User user = iUserService.getOne(queryWrapper);
        return user;
    }

    @ApiOperation(httpMethod = "POST", value = "用户登录与注册")
    @PostMapping(value = "registerOrLogin")
    public Result registerOrLogin(@RequestBody User user) throws Exception {
        User userResult = queryUserNameIsExit(user.getUserName());
        if (userResult!=null){//此用户存在,可登录
            if (!userResult.getPassWord().equals(MD5Utils.getPwd(user.getPassWord()))){
                Result result = new Result(MsgConsts.FAIL_CODE, MsgConsts.PASSWORD_ERROR);
                return result;
            }
        }else {
            //注册
            userResult = iUserService.insertUser(user);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userResult,userVo);
        return new Result(MsgConsts.SUCCESS_CODE,MsgConsts.SUCCESS_MSG,userVo);
    }

    /**
     * 用户头像上传访问方法
     * @param userBo
     * @return
     */
    @PostMapping("/uploadFaceBase64")
    @ResponseBody
    public Result registerOrlogin(@RequestBody UserBo userBo) throws Exception {
        System.out.println(userBo);
        //获取前端传过来的base64的字符串,然后转为文件对象在进行上传
        String base64Data = userBo.getFaceData();
        String userFacePath = "/usr/local/face/"+userBo.getUserId()+"userFaceBase64.png";//指定路径
        //调用FileUtils 类中的方法将base64  字符串转为文件对象
        FileUtils.base64ToFile(userFacePath,base64Data);
        //multipartFile:特殊文件的图片
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);
        //获取fastDFS上传的路径
        String url = fastDFSClient.uploadBase64(multipartFile);
        System.out.println(url);

        String thump = "_150x150.";

        String[] arr = url.split("\\.");

        //缩略图地址
        String thumpImgUrl = arr[0]+thump+arr[1];
        //更新用户对象
        User user = new User();
        user.setId(userBo.getUserId());
        //头像
        user.setFaceImage(thumpImgUrl);
        //大头像
        user.setFaceImageBig(url);
        User userInfo = iUserService.updateUserInfo(user);
        return new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG,userInfo);
    }

    /**
     * 修改昵称方法
     * @param user
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "修改昵称方法")
    @PostMapping("/setNickname")
    public Result setNickName(@RequestBody User user){
        Result result = null;
        if (isEmpty(user.getId(),user.getNickName())){
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        User selectUser = iUserService.setNickName(user);
        return new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, selectUser);
    }


    /**
     * 搜索好友
     *
     * 前置条件
     * 1.搜索的用户如果不存在,则返回[无此用户]
     * 2.搜索的账号如果是你自己,则返回[不能添加自己]
     * 3.搜索的朋友已经是你的好友,返回[该用户已经是你的好友]
     * @param id
     * @param userName
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "搜索好友方法")
    @PostMapping("/searchFriend")
    public Result searchFriend(@RequestParam("myUserId") String id,@RequestParam("friendUserName")String userName){
        System.out.println("id:"+id);
        System.out.println("userName:"+userName);
        Result result = null;
        if (isEmpty(id,userName)){
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.Enter_User_Name);
            return result;
        }
        Integer status = iUserService.preconditionSearchFriends(id,userName);
        //如果状态等于0就可以添加好友
        if (status == SearchFriendsStatusEnum.SUCCESS.status){
            User user = queryUserNameIsExit(userName);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            return new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, userVo);
        }else {
            String msg = SearchFriendsStatusEnum.getMsgByKey(status);
            return new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, msg);
        }
    }

    /**
     * 发送添加好友请求
     * @param id
     * @param userName
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "发送添加好友请求")
    @PostMapping("/addFriendRequest")
    public Result addFriendRequest(@RequestParam("myUserId") String id,@RequestParam("friendUserName")String userName){
        System.out.println(userName);
        Result result = null;
        if (isEmpty(id,userName)){
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.Enter_User_Name);
            return result;
        }
        Integer status = iUserService.preconditionSearchFriends(id,userName);
        //如果状态等于0就可以添加好友
        if (status == SearchFriendsStatusEnum.SUCCESS.status){
            iUserService.sendFriendRequest(id, userName);
        }else {
            String msg = SearchFriendsStatusEnum.getMsgByKey(status);
            return new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, msg);
        }
        return new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, MsgConsts.SEND_SUCCESS);

    }

    /**
     * 好友请求列表查询 @RequestParam("userId")这个对应前端请求过来的参数
     * @param userId
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "好友请求列表查询")
    @PostMapping("/queryFriendRequest")
    public Result addFriendRequest(@RequestParam("userId") String userId) {
        List<FriendsRequestVo> friendsRequestVos = iFriendsRequestService.queryFriendRequestList(userId);
        return new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, friendsRequestVos);
    }

        /**
         * 修改个性签名方法
         * @param user
         * @return
         */
    @ApiOperation(httpMethod = "POST", value = "修改个性签名方法")
    @PostMapping("/setPersonalizedSignature")
    public Result setPersonalizedSignature(@RequestBody User user){
        Result result = null;
        if (isEmpty(user.getId(),user.getPersonalizedSignature())){
            result = new Result(MsgConsts.FAIL_CODE, MsgConsts.FAIL_MSG, MsgConsts.MISS_PARAM_MSG);
            return result;
        }
        User selectUser = iUserService.setPersonalizedSignature(user);
        return new Result(MsgConsts.SUCCESS_CODE, MsgConsts.SUCCESS_MSG, selectUser);
    }

}
