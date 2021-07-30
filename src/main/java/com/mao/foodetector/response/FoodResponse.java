package com.mao.foodetector.response;

import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.response.respoMtrl.FoodMaterialResponse;
import lombok.*;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponse {

    private String foodName;
    private List<FoodMaterialResponse> materials;
}
