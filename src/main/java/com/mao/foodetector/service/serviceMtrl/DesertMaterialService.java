package com.mao.foodetector.service.serviceMtrl;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.repository.repoMtrl.DesertMaterialRepository;
import com.mao.foodetector.request.mtrequest.DesertMaterialRequest;
import com.mao.foodetector.response.BaseResponse;

public interface DesertMaterialService {
    void deleteEntityMaterials(DesertEntity entity);
    void saveEntityMaterials(DesertMaterialEntity materialEntity);
}
