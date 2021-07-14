package com.mao.foodetector.entity.material;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="materialofsoup")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoupMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    //@Column(nullable = false)
    @Column(name="material_name")
    private String materialName;

    @Column(name="material_info")
    private String materialInfo;

    @Column(name="soup_id")
    private Integer soupId;

}
