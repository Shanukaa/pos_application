package com.springbootacademy.batch17.pos.service.impl;

import com.springbootacademy.batch17.pos.dto.paginated.PaginatedResponseOrderDetails;
import com.springbootacademy.batch17.pos.dto.queryinterface.OrderDetailInterface;
import com.springbootacademy.batch17.pos.dto.request.RequestOrderSaveDTO;
import com.springbootacademy.batch17.pos.dto.response.ResponseOrderDetailsDTO;
import com.springbootacademy.batch17.pos.entity.OrderDetails;
import com.springbootacademy.batch17.pos.entity.Orders;
import com.springbootacademy.batch17.pos.repo.CustomerRepo;
import com.springbootacademy.batch17.pos.repo.ItemRepo;
import com.springbootacademy.batch17.pos.repo.OrderDetailRepo;
import com.springbootacademy.batch17.pos.repo.OrderRepo;
import com.springbootacademy.batch17.pos.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceIMPL implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Override
    @Transactional
    public String saveOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        Orders orders = new Orders(
                customerRepo.getById(requestOrderSaveDTO.getCustomer()),
                requestOrderSaveDTO.isActive(),
                requestOrderSaveDTO.getDate(),
                requestOrderSaveDTO.getTotal()
        );

        orderRepo.save(orders);
        if(orderRepo.existsById(orders.getOrderId())){
            List<OrderDetails> orderDetails = modelMapper.map(requestOrderSaveDTO.getOrderDetails(), new TypeToken<List<OrderDetails>>() {
            }.getType());

            for(int i = 0 ; i < orderDetails.size(); i++){
                orderDetails.get(i).setOrders(orders);
                orderDetails.get(i).setItems(itemRepo.getById(requestOrderSaveDTO.getOrderDetails().get(i).getItems()));

            }
            if (orderDetails.size() > 0){
                orderDetailRepo.saveAll(orderDetails);
            }
            return "saved";
        }

        return null;
    }

    @Override
    public PaginatedResponseOrderDetails getAllOrderDetails(Boolean state, int page, int size) {
        List<OrderDetailInterface> responseOrderDetailsDTOS = orderRepo.getAllOrderDetails(state, PageRequest.of(page,size));
        List<ResponseOrderDetailsDTO> list1 = new ArrayList<>();

        for(OrderDetailInterface r : responseOrderDetailsDTOS){
            ResponseOrderDetailsDTO responseOrderDetailsDTO = new ResponseOrderDetailsDTO(
                    r.getCustomerName(),
                    r.getCustomerAddress(),
                    r.getContactNumber(),
                    r.getDate(),
                    r.getTotal()
            );
            list1.add(responseOrderDetailsDTO);
        }
        PaginatedResponseOrderDetails paginatedResponseOrderDetails = new PaginatedResponseOrderDetails(
                list1,
                orderRepo.countAllOrderDetails(state)
        );
     return paginatedResponseOrderDetails;
    }
}
