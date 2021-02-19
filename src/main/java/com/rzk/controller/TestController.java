package com.rzk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @PackageName : com.rzk.controller
 * @FileName : TestController
 * @Description :
 * @Author : rzk
 * @CreateTime : 22/1/2021 上午3:25
 * @Version : 1.0.0
 */
@Controller
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "test";
    }

    @RequestMapping("userList")
    public String user(){
        return "user_list";
    }
}
