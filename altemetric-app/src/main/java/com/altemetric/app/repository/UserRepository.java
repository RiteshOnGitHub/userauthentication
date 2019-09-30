package com.altemetric.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.altemetric.app.entity.UserEntity;

//using PagingAndSortingRepository instead of CrudRepository because CrudRepository does not give any method for pagination.
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findUserByEmail(String email);
	UserEntity findByUserId(String userId);
	void deleteByUserId(String userId);

}
