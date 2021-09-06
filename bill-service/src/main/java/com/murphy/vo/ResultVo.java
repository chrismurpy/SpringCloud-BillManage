package com.murphy.vo;

import com.murphy.entity.Bill;
import lombok.Data;

import java.util.List;

/**
 * 封装的结果类
 *
 * @author murphy
 * @since 2021/9/4 2:51 下午
 */
@Data
public class ResultVo {
    /**
     * 响应状态
     */
    private int code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 数据条款
     */
    private Long count;
    /**
     * 响应数据
     */
    private List data;

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
