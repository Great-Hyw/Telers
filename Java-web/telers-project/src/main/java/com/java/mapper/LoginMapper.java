package com.java.mapper;

import com.java.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/*
* 登录Mapper
* */
@Mapper
public interface LoginMapper {
    @Select("select id,username,name from telers.emp where username=#{username} and password=#{password} ")
    Emp login(Emp emp);
}
