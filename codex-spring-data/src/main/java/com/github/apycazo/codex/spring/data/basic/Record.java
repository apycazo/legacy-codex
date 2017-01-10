package com.github.apycazo.codex.spring.data.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "records")
@NoArgsConstructor
@AllArgsConstructor
public class Record
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "record-id-gen", sequenceName = "record-id-gen-sec")
    private Long id;
    @Column(name = "name", length = 64, nullable = false)
    private String name;
    @Column(name = "updates", length = 10, nullable = false)
    private Integer updates;

    @PrePersist
    protected void beforeCreate ()
    {
        updates = 0;
    }

    @PreUpdate
    protected void beforeUpdate ()
    {
        updates++;
    }

}
