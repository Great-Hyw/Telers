package com.java.mapper;

import com.java.pojo.pojoLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    @Insert("insert into telers.logmessage(Menthed,Spent_time,Args,USER_ID) values (#{Menthed},#{SpentTime},#{Args},#{userID})")
    void insertLog(pojoLog pojoLog1);
}
