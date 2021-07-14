package com.mao.foodetector.service.serviceMtrl;

import com.mao.foodetector.response.BaseResponse;

public interface SoupMaterialService {
    BaseResponse getAllMaterials(String soupName);
    BaseResponse changeManual(String soupName);

    //düşünülmesi gereken methodlar :)
    BaseResponse addNewMaterial();
    BaseResponse updateMaterial();
    BaseResponse deleteMaterial();
}
