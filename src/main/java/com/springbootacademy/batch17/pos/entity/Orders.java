package com.springbootacademy.batch17.pos.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@TypeDefs({
        @TypeDef(name = "json",typeClass = JsonType.class)
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders {
    @Id
    @Column(name = "order_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @Column(name = "active_state", columnDefinition = "TINYINT default 0")
    private boolean active;

    @Column(name = "order_date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name = "total", nullable = false)
    private double total;

    @OneToMany(mappedBy="orders")
    private Set<OrderDetails> orderDetails;

    public Orders(Customer customer, Date date, double total) {
        this.customer = customer;
        this.date = date;
        this.total = total;
    }

    public Orders(Customer customer, boolean active, Date date, double total) {
        this.customer = customer;
        this.active = active;
        this.date = date;
        this.total = total;
    }
}
