package com.springbootacademy.batch17.pos.repo;

import com.springbootacademy.batch17.pos.dto.queryinterface.OrderDetailInterface;
import com.springbootacademy.batch17.pos.dto.response.ResponseOrderDetailsDTO;
import com.springbootacademy.batch17.pos.entity.Orders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderRepo extends JpaRepository<Orders,Integer> {

    @Query(value = "select c.customer_name as customerName, c.customer_address as customerAddress, c.contact_number as contactNumber, o.order_date as date, o.total as total from customer c , orders o where o.active_state = ?1 and c.customer_id = o.customer_id",nativeQuery = true)
    List<OrderDetailInterface> getAllOrderDetails(Boolean state, Pageable pageable);

    @Query(value ="select count(*) from customer c, orders o where o.active_state = ?1 and c.customer_id = o.customer_id ",nativeQuery = true)
    long countAllOrderDetails(Boolean state);
}
