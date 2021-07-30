package com.mao.foodetector.service.Implement.ImpMtrl;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.DesertRepository;
import com.mao.foodetector.repository.repoMtrl.DesertMaterialRepository;
import com.mao.foodetector.request.mtrequest.DesertMaterialRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.service.DesertService;
import com.mao.foodetector.service.serviceMtrl.DesertMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesertMaterialServiceImp implements DesertMaterialService {

    DesertMaterialRepository desertMaterialRepository;

    @Autowired
    public DesertMaterialServiceImp(DesertMaterialRepository desertMaterialRepository){
    this.desertMaterialRepository=desertMaterialRepository;
    }

    @Override
    public void deleteEntityMaterials(DesertEntity entity){
        if (entity==null){
            throw new RegisterNotFoundException("Register not found with your give");
        }
        entity.getMaterials().forEach(x->{
            desertMaterialRepository.delete(x);
        });
    }
}

