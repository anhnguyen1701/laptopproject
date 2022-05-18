package vn.ptit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ptit.entities.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> { }


