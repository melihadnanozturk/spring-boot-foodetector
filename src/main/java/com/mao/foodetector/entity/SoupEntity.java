package com.mao.foodetector.entity;

import com.mao.foodetector.entity.material.SoupMaterialEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="soup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="soupname")
    private String soupName;

    @OneToMany
    @JoinColumn(name="soup_id",referencedColumnName = "id")
    private List<SoupMaterialEntity> materials;

}
