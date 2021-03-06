package com.mao.foodetector.entity;

import com.mao.foodetector.entity.material.DesertMaterialEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="desert")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="desertname")
    private String desertName;

    @OneToMany
    @JoinColumn(name="desert_id",referencedColumnName = "id")
    private List<DesertMaterialEntity> materials;

    public DesertEntity(Integer id,String desertName){
        this.id=id;
        this.desertName=desertName;
    }

}
