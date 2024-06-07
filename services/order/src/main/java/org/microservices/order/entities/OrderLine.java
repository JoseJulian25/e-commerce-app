package org.microservices.order.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class OrderLine {

    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_i")
    private Order order;
    private Integer productId;
    private int quantity;
}
