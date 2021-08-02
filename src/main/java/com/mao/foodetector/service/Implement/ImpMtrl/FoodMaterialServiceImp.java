package com.mao.foodetector.service.Implement.ImpMtrl;

import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.repoMtrl.FoodMaterialRepository;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.service.FoodService;
import com.mao.foodetector.service.serviceMtrl.FoodMaterialService;
import org.springframework.stereotype.Service;

@Service
public class FoodMaterialServiceImp implements FoodMaterialService {

    FoodMaterialRepository foodMaterialRepository;

    public FoodMaterialServiceImp(FoodMaterialRepository foodMaterialRepository) {
        this.foodMaterialRepository = foodMaterialRepository;
    }

    @Override
    public void deleteMaterialEntity(FoodEntity entity) {
        if (entity != null) {
            entity.getMaterials().forEach(x -> {
                foodMaterialRepository.delete(x);
            });
        }
        throw new RegisterNotFoundException("Register not found with your give");
    }

    @Override
    public void saveMaterialEntity(FoodMaterialEntity materialEntity) {
        foodMaterialRepository.save(materialEntity);
    }
}

