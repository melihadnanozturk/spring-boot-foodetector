package com.mao.foodetector.service.Implement.ImpMtrl;

import com.mao.foodetector.entity.SoupEntity;
import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.SoupRepository;
import com.mao.foodetector.repository.repoMtrl.SoupMaterialRepository;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.service.serviceMtrl.SoupMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoupMaterialServiceImp implements SoupMaterialService {
    SoupMaterialRepository soupMaterialRepository;

    public SoupMaterialServiceImp(SoupMaterialRepository soupMaterialRepository){
        this.soupMaterialRepository=soupMaterialRepository;
    }

    @Override
    public void deleteMaterialEntity(SoupEntity entity){
        if(entity!=null){
            entity.getMaterials().forEach(x->{
                soupMaterialRepository.delete(x);
            });
        }
        throw new RegisterNotFoundException("Register not found with your give");
    }

    @Override
    public void saveMaterialEntity(SoupMaterialEntity materialEntity){
        soupMaterialRepository.save(materialEntity);
    }
}
