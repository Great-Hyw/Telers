package com.java.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clazz {
    private Integer id;
    private String name;
    private String room;
    private LocalDate beginDate;
    private LocalDate endDate;
    private Integer masterId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;
    private String masterName;
    private String status;
    private String subject;
}
