package com.esemip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esemip.dao.TestDao;
import com.esemip.entity.User;
import com.esemip.service.TestService;

@Service
public class TestServiceImpl implements TestService {
    
    @Autowired
    private TestDao dao;
    
    public String testQuery() throws Exception {
        List<User> users = dao.testQuery();
//        User u1 = new User("zhangsan", 18);
//        User u2 = new User("lisi", 19);
//        User u3 = new User("wangwu", 12);
//        users.add(u1);
//        users.add(u2);
//        users.add(u3);
//        List<User> users = new ArrayList<User>();
        String res = "";
        if (users != null && users.size() > 0) {
            for (User user : users) {
                res += user.toString() + "|";
            }
        } else {
            res = "Not found.";
        }
        return res;
    }
}
