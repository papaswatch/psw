package com.papaswatch.psw.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Data
public class PageData <T> {
    private final Long total;
    private final List<T> list;
}
