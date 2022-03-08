package com.viwcy.basecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageEntity<T> implements Serializable {
    private static final long serialVersionUID = -7518675912627630317L;

    private long total;
    private T records;

    public static <T> PageEntity<T> of(long total, T records) {
        return new PageEntity(total, records);
    }

}
