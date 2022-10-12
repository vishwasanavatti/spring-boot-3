package com.learn.springboot.restapi.user;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails, Long>{
	public List<UserDetails> findByRole(String role);
}
