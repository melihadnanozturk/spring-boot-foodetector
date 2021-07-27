package com.mao.foodetector.entity.material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="materialoffood")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="material_name")
    private String materialName;

    @Column(name="material_info")
    private String materialInfo;

    @Column(name="food_id")
    private Integer foodId;

    public FoodMaterialEntity(String material_name,String material_info){
        this.materialName=material_name;
        this.materialInfo=material_info;
    }
}
