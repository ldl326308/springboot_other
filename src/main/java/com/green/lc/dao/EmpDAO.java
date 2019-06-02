package com.green.lc.dao;

import com.green.lc.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpDAO extends JpaRepository<Emp, Long> {



}
