package com.viwcy.basecommon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TODO //
 *
 * <p> Title: ESBaseEntity </p >
 * <p> Description: ESBaseEntity </p >
 * <p> History: 2021/5/20 16:45 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ESBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // LocalDateTime指定存储格式，必须使用uuuu-MM-dd HH:mm:ss，不能使用yyyy，否则查询报错
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
    // 数据格式转换，并加上8小时进行存储
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
