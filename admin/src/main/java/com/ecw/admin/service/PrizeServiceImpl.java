package com.ecw.admin.service;

import com.ecw.admin.dto.ResponseListObj;

import com.ecw.admin.entity.PrizeEntity;
import com.ecw.admin.modal.PrizeNameListModel;
import com.ecw.admin.repository.PrizeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PrizeServiceImpl implements PrizeService{
    PrizeRepository prizeRepository;

    public PrizeServiceImpl(PrizeRepository prizeRepository) {
        this.prizeRepository = prizeRepository;
    }
    @Override
    public ResponseEntity<ResponseListObj> findAllPrizeList() {
        List<PrizeEntity> pageData= (List<PrizeEntity>) prizeRepository.findAll();
        ResponseListObj responseListObj= ResponseListObj.builder()
                .currentpage(1)
                .totalrecords(pageData.size())
                .lst(Collections.singletonList(pageData))
                .build();
        return ResponseEntity.ok(responseListObj);

    }

    @Override
    public ResponseEntity<ResponseListObj> findNameList() {
        List<PrizeNameListModel> pageData= prizeRepository.findNameByQuery();
        ResponseListObj responseListObj= ResponseListObj.builder()
                .lst(Collections.singletonList(pageData))
                .build();
        return ResponseEntity.ok(responseListObj);
    }
}
