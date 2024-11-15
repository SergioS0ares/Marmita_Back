package com.karlmarket.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.karlmarket.ecommerce.entity.AppUser;

@Repository
public interface UserDao extends CrudRepository<AppUser, String> {
}
