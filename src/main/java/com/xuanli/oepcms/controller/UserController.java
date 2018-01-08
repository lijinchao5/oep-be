package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.vo.RestResult;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public RestResult<List<User>> find() {
        return ok(userService.find());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResult<User> findById(@PathVariable long id) {
        return ok(userService.findById(id));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public RestResult<User> findByName(@RequestParam("name") String name) {
        return ok(userService.findByName(name));
    }
    
    /**保存用户信息*/
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public RestResult<Integer> insert(User user){
    	return new RestResult<>();
    }
    
    /**用户登录*/
    @RequestMapping(value = "findUser", method = RequestMethod.GET)
    public RestResult<List<User>> findUser(String username,String password) {
        return ok(userService.findUser(username, password));
    }
    
}
