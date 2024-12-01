package com.springbootacademy.batch17.pos.service;

import com.springbootacademy.batch17.pos.dto.paginated.PaginatedResponseOrderDetails;
import com.springbootacademy.batch17.pos.dto.request.RequestOrderSaveDTO;

public interface OrderService {
    String saveOrder(RequestOrderSaveDTO requestOrderSaveDTO);

    PaginatedResponseOrderDetails getAllOrderDetails(Boolean state, int page, int size);
}
