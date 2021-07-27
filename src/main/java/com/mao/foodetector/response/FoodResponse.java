package com.mao.foodetector.response;

import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.response.respoMtrl.FoodMaterialResponse;
import lombok.*;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponse extends  BaseResponse{

    private String foodName;
    private List<FoodMaterialResponse> materials;
}
