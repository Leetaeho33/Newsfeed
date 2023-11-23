package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findMenuByUserId(Long userId);
}
