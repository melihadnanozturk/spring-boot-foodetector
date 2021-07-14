package com.mao.foodetector.service.Implement.ImpMtrl;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.repository.DesertRepository;
import com.mao.foodetector.repository.repoMtrl.DesertMaterialRepository;
import com.mao.foodetector.request.mtrequest.DesertMaterialRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.service.DesertService;
import com.mao.foodetector.service.serviceMtrl.DesertMaterialService;
import org.springframework.beans.factory.annotation.Autowired;


public class DesertMaterialImp implements DesertMaterialService {

    @Autowired
    private DesertMaterialRepository desertMaterialRepository;
    @Autowired
    private DesertRepository desertRepository;

    @Override
    public Iterable<DesertMaterialEntity> getAllMaterials(String desertName) {
         return desertMaterialRepository.findAll();
    }

    @Override
    public BaseResponse changeManual(DesertMaterialRequest request, String desertName) {
        return null;
    }

    @Override
    public BaseResponse addNewMaterial() {
        return null;
    }

    @Override
    public BaseResponse updateMaterial() {
        return null;
    }

    @Override
    public BaseResponse deleteMaterial() {
        return null;
    }
}
