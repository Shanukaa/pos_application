package com.springbootacademy.batch17.pos.dto.queryinterface;

import java.util.ArrayList;
import java.util.Date;

public interface OrderDetailInterface {

    String getCustomerName();
    String getCustomerAddress();
    ArrayList<String> getContactNumber();
    Date getDate();
    double getTotal();

}
