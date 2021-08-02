package com.mao.foodetector.service.serviceMtrl;

import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface FoodMaterialService {


    void deleteMaterialEntity(FoodEntity entity);

    void saveMaterialEntity(FoodMaterialEntity materialEntity);
}
