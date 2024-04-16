package com.springboot.bootstrap.repository;

import com.springboot.bootstrap.entity.Anh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnhRepo extends JpaRepository<Anh, String> {
}
