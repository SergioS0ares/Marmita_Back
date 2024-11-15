package com.karlmarket.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.karlmarket.ecommerce.entity.AppUser;
import com.karlmarket.ecommerce.entity.Cart;

import java.util.List;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer > {
    public List<Cart> findByUser(AppUser user);
}
