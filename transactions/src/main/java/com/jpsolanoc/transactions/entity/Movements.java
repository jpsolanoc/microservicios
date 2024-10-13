package com.jpsolanoc.transactions.entity;

import com.jpsolanoc.transactions.enumdata.MovementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "movements")
@ToString
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;
    @Enumerated(EnumType.STRING)
    private MovementType type;
    private BigDecimal value;
    private BigDecimal balance;
    private String description;
    @ManyToOne
    @JoinColumn(name = "acount_id", nullable = false)
    private Account acount;
}