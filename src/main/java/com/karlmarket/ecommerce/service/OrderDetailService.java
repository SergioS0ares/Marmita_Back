package com.karlmarket.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karlmarket.ecommerce.configuration.JwtRequestFilter;
import com.karlmarket.ecommerce.dao.CartDao;
import com.karlmarket.ecommerce.dao.OrderDetailDao;
import com.karlmarket.ecommerce.dao.ProductDao;
import com.karlmarket.ecommerce.dao.UserDao;
import com.karlmarket.ecommerce.entity.AppUser;
import com.karlmarket.ecommerce.entity.Cart;
import com.karlmarket.ecommerce.entity.OrderDetail;
import com.karlmarket.ecommerce.entity.OrderInput;
import com.karlmarket.ecommerce.entity.OrderProductQuantity;
import com.karlmarket.ecommerce.entity.Product;
import com.karlmarket.ecommerce.entity.TransactionDetails;

@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Adicionado";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );
        }


         return orderDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        AppUser user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            AppUser user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                  orderInput.getFullName(),
                  orderInput.getFullAddress(),
                  orderInput.getContactNumber(),
                  orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user,
                    orderInput.getTransactionId()
            );

            // empty the cart.
            if(!isSingleProductCheckout) {
                List<Cart> carts = cartDao.findByUser(user);
                carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
            }

            orderDetailDao.save(orderDetail);
        }
    }

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Entregue");
            orderDetailDao.save(orderDetail);
        }

    }
    
    public TransactionDetails createTransaction(Double amount) {
        try {
        	
            TransactionDetails transactionDetails = new TransactionDetails("Macaco", "Macaco", 1);
            
            String simulatedOrderId = "SIMULATED_ORDER_" + System.currentTimeMillis();
            
            transactionDetails.setOrderId(simulatedOrderId);
            transactionDetails.setAmount((int) (amount * 100));
            transactionDetails.setCurrency("BRL");
            return transactionDetails;
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order) {
        String orderId = "test";
        String currency = "test";
        Integer amount = 1;

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount);
        return transactionDetails;
    }
}
