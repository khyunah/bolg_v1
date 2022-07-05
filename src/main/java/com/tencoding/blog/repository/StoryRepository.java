package com.tencoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.Image;

public interface StoryRepository extends JpaRepository<Image, Integer>{

}
