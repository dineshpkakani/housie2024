package com.ecw.admin.service;

import com.ecw.admin.dto.ResponseListObj;
import com.ecw.admin.entity.EventEntity;
import com.ecw.admin.modal.EventNameModel;
import com.ecw.admin.repository.EventRepository;
import com.ecw.admin.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    EventRepository eventRepository;

     @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseListObj findAllEvents(int pageno, int length) {

        Pageable paging = PageRequest.of(pageno, length, Sort.by("name").ascending());
        Page<EventEntity> pageData= eventRepository.findAll(paging);

        ResponseListObj responseListObj= ResponseListObj.builder()
                .currentpage(pageno)
                .totalrecords(pageData.getTotalElements())
                .lst(Collections.singletonList(pageData.getContent()))
                .build();
        return responseListObj;
    }

    @Override
    public ResponseListObj findAllEvents() {

        List<EventNameModel> pageData= eventRepository.findAllEvents();

        ResponseListObj responseListObj= ResponseListObj.builder()
                .lst(Collections.singletonList(pageData))
                .build();
        return responseListObj;
    }
    @Override
    public ResponseListObj findByNameStartsWith(String byTitle, int pageno, int length) {
        Pageable paging = PageRequest.of(pageno, length, Sort.by("Name").ascending());
        Page<EventEntity> pageData= eventRepository.findByNameStartsWith(byTitle,paging);

        ResponseListObj responseListObj= ResponseListObj.builder()
                .currentpage(pageno)
                .totalrecords(pageData.getTotalElements())
                .lst(Collections.singletonList(pageData.getContent()))
                .build();
        return responseListObj;
    }
    @Override
    public ResponseListObj findByEventId(long eventid) {
        EventEntity event= eventRepository.findByEventId(eventid);

        ResponseListObj responseListObj= ResponseListObj.builder()
                .lst(Collections.singletonList(event))
                .build();
        return responseListObj;
    }
    @Override
    public String save(EventEntity e) {
      //  Long userId=Utility.getUserId(SecurityContextHolder.getContext());
        Long userId=122L;
        e.setUserid(userId);
        try {
            eventRepository.save(e);
            return "saved";
        }catch (Exception ee){
            return  "not saved";
        }
    }

}
