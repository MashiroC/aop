package org.redrock.aopdemo.controller;

import org.redrock.aopdemo.annotation.Cache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.timer.Timer;
import javax.servlet.http.HttpSession;

/**
 * @author: Shiina18
 * @date: 2019/3/15 19:40
 * @description:
 */
@RestController
public class MainController {
    @GetMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "hello world!";
    }

    @GetMapping("/welcome")
    @Cache
    public String welcome(String name){
        System.out.println("我需要计算!");
        try {
            Thread.sleep(5 * Timer.ONE_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "welcome!"+name;
    }

    @GetMapping("/admin/deleteAllDatabases")
    public String deleteAllDb(){
        return "删除成功";
    }

    @GetMapping("/login")
    public String adminLogin(String username, String password, HttpSession session){
        if("admin".equals(username)&&"admin".equals(password)){
            session.setAttribute("role","admin");
        }
        return "登陆成功！";
    }
}
