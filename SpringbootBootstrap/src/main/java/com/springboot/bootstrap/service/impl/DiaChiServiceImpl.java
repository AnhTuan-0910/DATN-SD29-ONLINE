package com.springboot.bootstrap.service.impl;

import com.springboot.bootstrap.entity.DiaChi;
import com.springboot.bootstrap.repository.DiaChiRepository;
import com.springboot.bootstrap.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepository;




}
