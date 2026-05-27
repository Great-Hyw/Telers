package com.java.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class pojoLog {

    private String Menthed;
    private Long SpentTime;
    private String Args;
    private Integer userID;

}
