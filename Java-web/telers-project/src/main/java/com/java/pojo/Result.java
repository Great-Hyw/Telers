package com.java.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String msg;
    private Object data;
    private Integer code;


    public static Result susses() {
        Result result = new Result();
        result.msg = "susses";
        result.code = 1;
        return result;
    }

    public static Result susses(Object data) {
        Result result = new Result();
        result.msg = "susses";
        result.code = 1;
        result.data = data;
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }


}
