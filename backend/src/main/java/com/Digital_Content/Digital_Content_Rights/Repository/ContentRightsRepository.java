package com.Digital_Content.Digital_Content_Rights.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Digital_Content.Digital_Content_Rights.Entity.ContentRights;

public interface ContentRightsRepository extends JpaRepository<ContentRights, Integer> {

    List<ContentRights> findByDigitalContent_Id(Integer contentId);
}
