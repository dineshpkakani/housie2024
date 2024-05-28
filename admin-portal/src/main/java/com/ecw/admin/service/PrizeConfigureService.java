package com.ecw.admin.service;

import com.ecw.admin.dto.ResponseListObj;
import com.ecw.admin.entity.EventEntity;
import com.ecw.admin.entity.PrizeDetailEntity;
import com.ecw.admin.entity.PrizeEntity;
import java.util.List;


public interface PrizeConfigureService {
    ResponseListObj getAllByEventById(long eventid);
    ResponseListObj getAllByEventByIdNew(long eventid);

    ResponseListObj saveAll(List<PrizeDetailEntity> lst);

    boolean findPrizeConfigureWithEventAndPrize(EventEntity eventEntity, PrizeEntity prizeEntity);

}
