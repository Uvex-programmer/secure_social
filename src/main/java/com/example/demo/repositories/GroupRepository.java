package com.example.demo.repositories;

import com.example.demo.models.Group;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group, String> {

    @Query(value = "{$or : [{'admins._id' : ?0},{'moderators._id' : ?0}, {'members._id' : ?0}]}")
    List<Group> findGroupsByUser(String username);
}
