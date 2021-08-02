package com.mao.foodetector.service.serviceMtrl;

import com.mao.foodetector.entity.SoupEntity;
import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.response.BaseResponse;

public interface SoupMaterialService {

    void deleteMaterialEntity(SoupEntity entity);

    void saveMaterialEntity(SoupMaterialEntity materialEntity);
}
