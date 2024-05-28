package com.ecw.admin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Builder
public class ResponseListObj {
    private List<Object> lst;
    private long totalrecords;
    private int currentpage;
}
