package com.example.demo.repositories;

import com.example.demo.models.GroupPosts;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPostRepository extends PagingAndSortingRepository<GroupPosts, String> {
}
