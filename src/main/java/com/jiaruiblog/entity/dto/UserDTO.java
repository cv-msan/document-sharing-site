package com.jiaruiblog.entity.dto;

import lombok.Data;

/**
 * @ClassName UserDTO
 * @Description 注册用户信息
 * @Author luojiarui
 * @Date 2022/12/18 12:56
 * @Version 1.0
 **/
@Data
public class UserDTO {

    private String id;
    private String password;
    private String deptId;
    private String phone;
    private String name;

    private String mail;

    private boolean male = false;

    private String description;

}
