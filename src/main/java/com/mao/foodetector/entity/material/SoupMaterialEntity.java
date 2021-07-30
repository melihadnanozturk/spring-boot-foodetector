package com.mao.foodetector.entity.material;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="materialofsoup")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoupMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="material_name")
    private String materialName;

    @Column(name="material_info")
    private String materialInfo;

    @Column(name="soup_id")
    private Integer soupId;

}
