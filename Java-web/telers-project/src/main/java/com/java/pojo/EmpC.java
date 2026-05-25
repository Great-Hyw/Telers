package com.java.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpC {
    private String name;
    private Integer gender;
    private LocalDate begin;
    private LocalDate end;
    private Integer page;
    private Integer pageSize;
}
