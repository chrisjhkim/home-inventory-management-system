package com.chrisjhkim.home.repository;

import com.chrisjhkim.home.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
