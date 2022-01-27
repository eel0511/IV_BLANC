package com.ivblanc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivblanc.core.entity.Clothes;

@Repository
public interface HistoryRepository extends JpaRepository<Clothes,Integer>, HistoryRepoCommon {
}
