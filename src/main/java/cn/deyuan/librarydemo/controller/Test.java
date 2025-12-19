package cn.deyuan.librarydemo.controller;

import cn.deyuan.librarydemo.dao.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * author: admin
 * date: 2025/12/11 13:31
 */
@RestController
public class Test {

    @GetMapping("/users")
    public List<User> test(){
       List<User> users = new ArrayList<User>();
       User user = new User();
       user.setId(1);
       user.setName("张三");
       user.setEmail("zhangsan@example.com");
       users.add(user);

       User user2 = new User();
       user2.setId(2);
       user2.setName("李四");
       user2.setEmail("lisi@example.com");
       users.add(user2);

       User user3 = new User();
       user3.setId(3);
       user3.setName("王二麻子。。");
       user3.setEmail("wanger@example.com");
       users.add(user3);

       return users;
    }
}
