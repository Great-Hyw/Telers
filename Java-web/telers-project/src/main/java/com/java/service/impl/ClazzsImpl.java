package com.java.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.mapper.ClazzsMapper;
import com.java.pojo.Clazz;
import com.java.pojo.EmpC;
import com.java.pojo.PageResult;
import com.java.service.Clazzs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/*
 * 班级业务实现类
 *
 * */
@Slf4j
@Service
public class ClazzsImpl implements Clazzs {
    @Autowired
    private ClazzsMapper clazzsMapper;

    @Override
    public PageResult<Clazz> selectClazzs(EmpC empC) {

        PageHelper.startPage(empC.getPage(), empC.getPageSize());
        List<Clazz> page1 = clazzsMapper.selectClazzs(empC);
        LocalDate now = LocalDate.now();
        for (var i : page1) {
            if (i.getBeginDate().isBefore(now) && i.getEndDate().isAfter(now)) {
                i.setStatus("已开课");
            } else if (i.getBeginDate().isAfter(now)) {
                i.setStatus("未开课");
            } else {
                i.setStatus("已结课");
            }
        }
        log.info("返回的数据：{}", page1);
        Page<Clazz> page = (Page<Clazz>) page1;
        return new PageResult<Clazz>(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteClazz(Integer id) {

        clazzsMapper.deleteClazz(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void addClazzs(Clazz clazz) {

        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());

        clazzsMapper.addClazzs(clazz);
        log.info("操作成功");
    }

    @Override
    public PageResult<Clazz> selectAll(EmpC empC) {
        PageHelper.startPage(empC.getPage(), empC.getPageSize());
        List<Clazz> page1 = clazzsMapper.selectAll(empC);
        LocalDate now = LocalDate.now();
        for (var i : page1) {
            if (i.getBeginDate().isBefore(now) && i.getEndDate().isAfter(now)) {
                i.setStatus("已开课");
            } else if (i.getBeginDate().isAfter(now)) {
                i.setStatus("未开课");
            } else {
                i.setStatus("已结课");
            }
        }
        log.info("返回的数据：{}", page1);
        Page<Clazz> page = (Page<Clazz>) page1;
        return new PageResult<Clazz>(page.getTotal(), page.getResult());
    }

    @Override
    public void updateClazz(Clazz clazz) {

        clazzsMapper.updateClazz(clazz);
    }

    @Override
    public Clazz selectClazz(Integer id) {
        Clazz clazz = (Clazz) clazzsMapper.selectClazz(id);
        return clazz;
    }


}

