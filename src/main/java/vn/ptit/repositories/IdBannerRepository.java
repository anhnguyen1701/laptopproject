package vn.ptit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ptit.entities.IdBanner;

@Repository
public interface IdBannerRepository extends JpaRepository<IdBanner, Integer> { }


