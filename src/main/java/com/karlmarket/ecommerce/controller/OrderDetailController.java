package com.karlmarket.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.karlmarket.ecommerce.entity.OrderDetail;
import com.karlmarket.ecommerce.entity.OrderInput;
import com.karlmarket.ecommerce.entity.TransactionDetails;
import com.karlmarket.ecommerce.service.OrderDetailService;

import java.util.List;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PreAuthorize("hasRole('User')")
    @PostMapping({"/placeOrder/{isSingleProductCheckout}"})
    public void placeOrder(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
            @RequestBody OrderInput orderInput) {
        orderDetailService.placeOrder(orderInput, isSingleProductCheckout);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getOrderDetails"})
    public List<OrderDetail> getOrderDetails() {
        return orderDetailService.getOrderDetails();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getAllOrderDetails/{status}"})
    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status") String status) {
        return orderDetailService.getAllOrderDetails(status);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/markOrderAsDelivered/{orderId}"})
    public void markOrderAsDelivered(@PathVariable(name = "orderId") Integer orderId) {
        orderDetailService.markOrderAsDelivered(orderId);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/createTransaction/{amount}"})
    public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
        return orderDetailService.createTransaction(amount);
    /*public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
    	return orderDetailService.createTransactionNoRazorPay(amount);*/
    	
    	// aqui é onde a requisição chama para a o pagamento, eu não removi a utilização da razorpay
    	// em vez disso, eu criei um mock que irá agir como um objeto razorpay para seguir normalmente
    	// com o código. Caso tenhamos que remover esse mock, só precisa descomentar a chamada do 
    	// metodo real e comentar a chamada do metodo mock. /// ºuº \\\
    }
}
