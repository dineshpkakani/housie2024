package com.ecw.admin.service;

import com.ecw.admin.dto.ResponseListObj;
import com.ecw.admin.entity.EventEntity;


public interface EventService {
    ResponseListObj findAllEvents(int start, int length);

    ResponseListObj findAllEvents();

    //  ResponseListObj findByName(String byTitle,int start, int length);
    ResponseListObj findByNameStartsWith(String byTitle,int start, int length);
    ResponseListObj findByEventId(long eventid);


    String save(EventEntity e);
}
