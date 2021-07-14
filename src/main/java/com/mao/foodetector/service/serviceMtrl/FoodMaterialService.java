package com.mao.foodetector.service.serviceMtrl;

import com.mao.foodetector.response.BaseResponse;

public interface FoodMaterialService {
    BaseResponse getAllMaterials(String foodName);
    BaseResponse changeManual(String foodName);

    //düşünülmesi gereken methodlar :)
    BaseResponse addNewMaterial();
    BaseResponse updateMaterial();
    BaseResponse deleteMaterial();
}
