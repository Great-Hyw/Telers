package com.java.mapper;

import com.java.pojo.Clazz;
import com.java.pojo.EmpC;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * @Description: 班级Mapper
 *
 * */
@Mapper
public interface ClazzsMapper {

    List<Clazz> selectClazzs(EmpC empC);

    void deleteClazz(Integer id);

    void addClazzs(Clazz clazz);

    @Select("select c.*,e.name master_name from telers.clazz c left join telers.emp e on c.master_id=e.id")
    List<Clazz> selectAll(EmpC empC);

    void updateClazz(Clazz clazz);

    Clazz selectClazz(Integer id);
}
