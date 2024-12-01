package com.springbootacademy.batch17.pos.dto.request;

import com.springbootacademy.batch17.pos.entity.Item;
import com.springbootacademy.batch17.pos.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderDetailsSave {

    private String itemName;
    private double qty;
    private double amount;
    private int items;
}
