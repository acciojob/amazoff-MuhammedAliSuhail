package com.driver;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

Services services=new Services();

    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        try{
            services.addOrder(order);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

          return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        try {
            services.addPartner(partnerId);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){
        services.addOrderPartnerPair(orderId,partnerId);
        //This is basically assigning that order to that partnerId
        return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){


    return services.getOrderById(orderId);


        //order should be returned with an orderId.



    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){
try {
    DeliveryPartner deliveryPartner = services.PaireDelivaryPartner(partnerId);
    return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
}catch (Exception e){
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
        //deliveryPartner should contain the value given by partnerId


    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")

    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){
       try {
           Integer orderCount = services.OrderCount(partnerId);
           return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
        //orderCount should denote the orders given by a partner-id


    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){
        try {
            List<String> orders = services.OrderBypartnerId(partnerId);
            return new ResponseEntity<>(orders, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //orders should contain a list of orders by PartnerId


    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        try {
            List<String> orders = services.getAllOrders();
            return new ResponseEntity<>(orders, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Get all orders

    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        try {
            Integer countOfOrders = services.GetCountOfUnassignedOrder();
            return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Count of orders that have not been assigned to any DeliveryPartner


    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){
try {
    Integer countOfOrders = services.GetCountOfOrderAfterGivingTime(time, partnerId);
    return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
}catch (Exception e){
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
        //countOfOrders that are left after a particular time of a DeliveryPartner


    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        try {
            String time = services.getLastTime(partnerId);
            return new ResponseEntity<>(time, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Return the time when that partnerId will deliver his last delivery order.


    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        try {
            services.deletePartnerId(partnerId);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){
        try {
            services.deleteOrderId(orderId);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete an order and also
        // remove it from the assigned order of that partnerId

        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
