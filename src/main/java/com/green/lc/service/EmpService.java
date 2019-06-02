package com.green.lc.service;

import com.green.lc.entity.Emp;

import java.util.List;
import java.util.Optional;


public interface EmpService {

    List<Emp> findAll();

    Optional<Emp> findById(Long empId);
}
