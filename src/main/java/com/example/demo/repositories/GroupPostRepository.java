package com.example.demo.repositories;

import com.example.demo.models.GroupPosts;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupPostRepository extends PagingAndSortingRepository<GroupPosts, String> {
}
