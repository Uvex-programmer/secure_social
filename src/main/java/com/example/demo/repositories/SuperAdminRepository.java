package com.example.demo.repositories;

import com.example.demo.models.SuperAdmin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperAdminRepository extends PagingAndSortingRepository<SuperAdmin, String> {

    Optional<SuperAdmin> findByUsername(String username);

}
