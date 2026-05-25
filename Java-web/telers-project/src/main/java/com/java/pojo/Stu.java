package com.java.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Stu {

    private Integer id;
    private String name;
    private String no;
    private Integer gender;
    private String phone;
    private Integer degree;
    private String idCard;
    private Integer isCollege;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate graduationDate;
    private Integer violationCount;
    private Integer violationScore;
    private Integer clazzId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String clazzName;


    private Integer page;
    private Integer pageSize;

}
