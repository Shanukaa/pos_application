package com.springbootacademy.batch17.pos.dto.request;

import com.springbootacademy.batch17.pos.entity.Customer;
import com.springbootacademy.batch17.pos.entity.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestOrderSaveDTO {

    private int customer;
    private boolean active;
    private Date date;
    private double total;
    private List<RequestOrderDetailsSave> orderDetails;
}
