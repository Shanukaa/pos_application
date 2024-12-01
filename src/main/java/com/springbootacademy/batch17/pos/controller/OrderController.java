package com.springbootacademy.batch17.pos.controller;

import com.springbootacademy.batch17.pos.dto.paginated.PaginatedResponseOrderDetails;
import com.springbootacademy.batch17.pos.dto.request.RequestOrderSaveDTO;
import com.springbootacademy.batch17.pos.service.OrderService;
import com.springbootacademy.batch17.pos.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse> saveOrder(@RequestBody RequestOrderSaveDTO requestOrderSaveDTO){

        String message = orderService.saveOrder(requestOrderSaveDTO);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"success",message),
                HttpStatus.CREATED
        );
    }

    @GetMapping(
            path = "/get-order-details",
            params = {"activeState","page","size"}
    )
    public ResponseEntity<StandardResponse> getAllOrderDetails(
            @RequestParam(value = "activeState") String stateType,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ){
        PaginatedResponseOrderDetails p = null;
        if(stateType.equalsIgnoreCase("active") | stateType.equalsIgnoreCase("inactive")){
            Boolean state = stateType.equalsIgnoreCase("active") ? true : false;

            p = orderService.getAllOrderDetails(state,page,size);

        }
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"Success",p),
                HttpStatus.OK
        );

    }
}
