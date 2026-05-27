package com.java.mapper;

import com.java.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
 *
 * @Description: 部门Mapper
 *
 * */
@Mapper
public interface DeptMapper {

    @Select("SELECT *from telers.dept order by update_time desc ")
    List<Dept> findALL();


    @Delete("delete from telers.dept where id=#{id}")
    void deleteDept(Integer id);

    @Insert("insert into telers.dept values (#{id},#{name},#{createTime},#{updateTime})")
    void addDept(Dept dept);

    @Update("update telers.dept set name=#{name},update_time=#{updateTime} where id=#{id} ")
    void update(Dept dept);

    @Select("select * from telers.dept where id=#{id}")
    Dept goInfo(Integer id);
}
