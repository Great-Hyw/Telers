package com.java.service.impl;

import com.java.Util.JwtUtil;
import com.java.mapper.LoginMapper;
import com.java.pojo.Emp;
import com.java.service.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *
 * @Description: 登录业务实现类
 *
 * */
@Service
public class LoginImpl implements Login {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Emp login(Emp emp) {

        //数据库查询（有数据去生成token无则返回空）
        Emp emp1 = loginMapper.login(emp);
        if (emp1 != null) {
            String s = jwtUtil.CreatToken(emp1.getUsername(), emp1.getId());
            emp1.setToken(s);
            return emp1;
        }
        return null;
    }
}
