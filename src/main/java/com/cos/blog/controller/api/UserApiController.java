package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;


    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){ //username, password,  email

        System.out.println("UserApiController : save 호출됨");
        //DB삽입후 리턴 하면 된다.
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); //자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
    }

    //스프링 시큐리티 이용해서 로그인





}
