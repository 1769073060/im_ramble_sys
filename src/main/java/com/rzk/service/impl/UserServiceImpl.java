package com.rzk.service.impl;

import com.rzk.pojo.User;
import com.rzk.idworker.Sid;
import com.rzk.mapper.UserMapper;
import com.rzk.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzk.utils.FastDFSClient;
import com.rzk.utils.FileUtils;
import com.rzk.utils.MD5Utils;
import com.rzk.utils.QRCodeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author dell
 * @since 2021-01-25
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private Sid sid;
    @Resource
    private QRCodeUtils qrCodeUtils;
    @Resource
    private FastDFSClient fastDFSClient;

    /**
     * 增加用户
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public User insertUser(User user){
        String userId = sid.nextShort();
        //为每个注册用户生成一个唯一的二维码
        String qrCodePath = userId+"qrcode.png";
        //创建二维码对象信息
        qrCodeUtils.createQRCode(qrCodePath,"ramble_qrcode"+user.getUserName());
        //二维码文件生成
        MultipartFile qrcodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeURL = "";
        try {
            //上传到服务器
            qrCodeURL  = fastDFSClient.uploadQRCode(qrcodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setQrcode(qrCodeURL);
        user.setId(userId);
        user.setFaceImage("");
        user.setFaceImageBig("");
        user.setNickName("");
        user.setPersonalizedSignature("");
        user.setPassWord(MD5Utils.getPwd(user.getPassWord()));
        userMapper.insert(user);
        return user;
    }

    /**
     * 修改昵称
     * @param user
     * @return
     */
    @Override
    public User setNickName(User user) {
        userMapper.updateById(user);
        User selectById = userMapper.selectById(user.getId());
        return selectById;
    }

    /**
     * 修改改个性签名
     * @param user
     * @return
     */
    @Override
    public User setPersonalizedSignature(User user) {
        userMapper.updateById(user);
        User selectById = userMapper.selectById(user.getId());
        return selectById;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @Override
    public User updateUserInfo(User user) {
        userMapper.updateById(user);
        User selectById = userMapper.selectById(user.getId());
        return selectById;
    }


}
