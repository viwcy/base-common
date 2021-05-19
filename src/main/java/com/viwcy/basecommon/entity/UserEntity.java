package com.viwcy.basecommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2020/9/1 16:29
 * @Author Fuqiang
 * <p>
 *
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user")
public class UserEntity extends AbstractBaseEntity<UserEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Id
    private Long id;
    @TableField("nickname")
    private String nickname;
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
