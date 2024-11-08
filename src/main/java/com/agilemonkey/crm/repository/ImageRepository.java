package com.agilemonkey.crm.repository;

import com.agilemonkey.crm.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByImageKey(String key);
}
