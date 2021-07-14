package com.mao.foodetector.entity;

import com.mao.foodetector.entity.material.FoodMaterialEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

    @Column(name="foodname")
    private String foodName;


    @OneToMany
    @JoinColumn(name="food_id",referencedColumnName = "id")
    private List<FoodMaterialEntity> materials;

}
