package com.viwcy.basecommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User extends AbstractBaseEntity<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String userName;
    private String headPhoto;
    private String phone;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField("password")
    private String password;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
