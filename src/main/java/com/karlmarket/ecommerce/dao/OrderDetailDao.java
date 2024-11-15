package com.karlmarket.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;

import com.karlmarket.ecommerce.entity.AppUser;
import com.karlmarket.ecommerce.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
    public List<OrderDetail> findByUser(AppUser user);

    public List<OrderDetail> findByOrderStatus(String status);
}
