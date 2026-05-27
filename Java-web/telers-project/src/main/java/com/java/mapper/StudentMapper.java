package com.java.mapper;

import com.java.pojo.Stu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/*
 * @Description: 学生Mapper
 *
 * */
@Mapper
public interface StudentMapper {


    List<Stu> select(Stu stu);

}
