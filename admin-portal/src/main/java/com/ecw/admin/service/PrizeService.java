package com.ecw.admin.service;

import com.ecw.admin.dto.ResponseListObj;
import org.springframework.http.ResponseEntity;

public interface PrizeService {
    ResponseEntity<ResponseListObj> findAllPrizeList();

    ResponseEntity<ResponseListObj> findNameList();
}
