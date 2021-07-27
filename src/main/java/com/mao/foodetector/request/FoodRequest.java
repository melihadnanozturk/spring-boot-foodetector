package com.mao.foodetector.request;

import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.request.mtrequest.FoodMaterialRequest;
import com.mao.foodetector.response.respoMtrl.FoodMaterialResponse;
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
    private List<FoodMaterialRequest> materials;
}
