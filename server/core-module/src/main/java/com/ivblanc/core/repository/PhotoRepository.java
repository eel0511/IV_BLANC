package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivblanc.core.entity.Photo;
public interface PhotoRepository extends JpaRepository<Photo,Integer>,PhotoRepoCommon {
}
