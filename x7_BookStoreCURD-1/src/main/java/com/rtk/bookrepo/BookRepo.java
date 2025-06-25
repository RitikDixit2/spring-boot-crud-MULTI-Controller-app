package com.rtk.bookrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtk.entities.BookEntity;

public interface BookRepo extends JpaRepository<BookEntity, Integer> {
	List<BookEntity> findByActiveSw(String activeSw);
}
