package com.Digital_Content.Digital_Content_Rights.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Digital_Content.Digital_Content_Rights.Entity.DigitalContent;

public interface DigitalContentRepository extends JpaRepository<DigitalContent, Integer> {

    List<DigitalContent> findByCreatedBy_Id(Integer userId);
}
