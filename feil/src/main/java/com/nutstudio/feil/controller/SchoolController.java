package com.nutstudio.feil.controller;

import com.nutstudio.feil.domain.BaseBean;
import com.nutstudio.feil.domain.MySchool;
import com.nutstudio.feil.domain.User;
import com.nutstudio.feil.repository.MySchoolRepository;
import com.nutstudio.feil.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    MySchoolRepository mySchoolRepository;

    @RequestMapping("/getSchool")
    public MySchool getSchool(String id) {
        System.out.println(id + ">>");
        return mySchoolRepository.getOne(Integer.valueOf(id));
    }

    @RequestMapping("/saveSchool")
    public List<MySchool> saveSchool(MySchool mySchool) {
        mySchoolRepository.saveAndFlush(mySchool);
        return mySchoolRepository.findAll();
    }


    @Autowired
    UserRepository userRepository;

    //根据Id查询用户
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        User user = userRepository.getOne(id);
        return user;
    }
    //根据Id查询用户
    @GetMapping("/deleteUserById/{id}")
    public BaseBean deleteUserById(@PathVariable("id") Integer id) {
         userRepository.deleteById(id);
        return new BaseBean(0,"success");
    }

    //根据Id查询用户
    @RequestMapping("/deleteUsersById")
    public BaseBean deleteUsersById(String ids) {
        System.out.println(ids+" >>>");
       String[] myIdArr= ids.split(",");
       for (String str:myIdArr){
           userRepository.deleteById(Integer.valueOf(str));
       }
        return new BaseBean(0,"success");
    }
    //插入用户
    @RequestMapping("/user")
    public User addUser(User user) {
        userRepository.saveAndFlush(user);
        return user;   //返回插入的对象及其自增ID
    }

    //根据phone查询用户
    @GetMapping("/getUserByPhone/{phone}")
    public List<User> getUser(@PathVariable("phone") String phone) {
        List<User> users = userRepository.getAllUser(phone);
        return users;
    }

    //根据phone删除用户
    @GetMapping("/deleteUserByPhone/{phone}")
    public List<User> deleteUserByPhone(@PathVariable("phone") String phone) {
        userRepository.deleteUser(phone);
        return userRepository.findAll();
    }

    @GetMapping("/getUserByPhoneName/{name}/{phone}")
    public List<User> getUserByPhoneName(@PathVariable("name") String name, @PathVariable("phone") String phone) {
        List<User> users = userRepository.getUserByNamePhone(name, phone);
        return users;
    }

    @RequestMapping("/findKey")
    public List<User> findKey(String name, String phone, String address) {
        List<User> users = userRepository.findByNameContainingAndPhoneContainingAndAddressContaining(name, phone,address);
        return users;
    }

    @RequestMapping("/findLikeKey")
    public List<User> findLikeKey(String key) {


        List<User> users = userRepository.findByNameContainingOrPhoneContainingOrAddressContaining(key,key,key);
        return users;
    }
    //分页查询 页码从零开始
    @GetMapping("/userList/{page}/{size}")
    public Page<User> getUserList(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageRequest);
    }

    //分页查询 页码从零开始
    @RequestMapping("/userListPage")
    public BaseBean userListPage(Integer page, Integer limit) {
        System.out.println(page + "??" + limit);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        Page<User> userPage = userRepository.findAll(pageRequest);
        return new BaseBean(0, "success", userPage.getContent(), userRepository.findAll().size());
    }
    //分页带关键词模糊查找
    @RequestMapping("/userListPageKey")
    public BaseBean userListPageKey(Integer page, Integer limit,String key) {
        System.out.println(page + ">>>" + limit+">>>"+key);

        //解决关键词为空的情况
        key=(key==null)?"":key;
        //分页参数
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        //查询当前分页数据
        Page<User> userPage = userRepository.findAllByNameContainingOrPhoneContainingOrAddressContaining(key,key,key,pageRequest);

        //查出相同关键词下的总个数
        Long nowSize=userRepository.countByNameContainingOrPhoneContainingOrAddressContaining(key,key,key);

        return new BaseBean(0, "success", userPage.getContent(), Math.toIntExact(nowSize));
    }
}
