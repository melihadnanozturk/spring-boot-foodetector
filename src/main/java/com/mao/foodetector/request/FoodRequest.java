package com.mao.foodetector.request;

import com.mao.foodetector.entity.material.FoodMaterialEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest extends BaseRequest{

    private String foodName;

    private List<FoodMaterialEntity> materials;
}
