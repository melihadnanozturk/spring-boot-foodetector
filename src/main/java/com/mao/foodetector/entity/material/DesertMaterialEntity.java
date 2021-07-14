package com.mao.foodetector.entity.material;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="materialofdesert")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesertMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="material_name")
    private String materialName;

    @Column(name="material_info")
    private String materialInfo;

    @Column(name="desert_id")
    private Integer desertId;
}
