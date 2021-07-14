package com.mao.foodetector.service.serviceMtrl;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.request.mtrequest.DesertMaterialRequest;
import com.mao.foodetector.response.BaseResponse;

public interface DesertMaterialService {
    Iterable<DesertMaterialEntity> getAllMaterials(String desertName);
    BaseResponse changeManual(DesertMaterialRequest request, String desertName);

    //düşünülmesi gereken methodlar :)
    BaseResponse addNewMaterial();
    BaseResponse updateMaterial();
    BaseResponse deleteMaterial();
}
