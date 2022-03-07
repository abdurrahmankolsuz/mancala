package com.cycleon.game.mancala.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@EqualsAndHashCode(of = {"id"})
@MappedSuperclass
@Data
public class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @CreatedBy
    @Column
    protected String createdBy;

    @CreatedDate
    @CreationTimestamp
    @Temporal(TIMESTAMP)
    protected Date createdDate;

    @LastModifiedBy
    @Column
    protected String updatedBy;

    @LastModifiedDate
    @UpdateTimestamp
    @Temporal (TIMESTAMP)
    protected Date updatedDate;

}
