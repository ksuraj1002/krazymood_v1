package com.krazymood.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krazymood.app.entities.ImageGallery;


public interface GalleryRepository extends JpaRepository<ImageGallery, Long> {

}
