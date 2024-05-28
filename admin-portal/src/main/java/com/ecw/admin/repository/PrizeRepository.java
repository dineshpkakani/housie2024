package com.ecw.admin.repository;

import com.ecw.admin.entity.PrizeEntity;
import com.ecw.admin.modal.PrizeNameListModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends CrudRepository<PrizeEntity,Long> {
    @Query("SELECT p.id as id,p.prizename as prizename,p.imagename as imagename  FROM PrizeEntity p")
    List<PrizeNameListModel> findNameByQuery();
}
