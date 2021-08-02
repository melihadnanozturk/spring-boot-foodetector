package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.FoodRepository;
import com.mao.foodetector.repository.repoMtrl.FoodMaterialRepository;
import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.FoodResponse;
import com.mao.foodetector.response.respoMtrl.FoodMaterialResponse;
import com.mao.foodetector.service.FoodService;
import com.mao.foodetector.service.Implement.ImpMtrl.FoodMaterialServiceImp;
import com.mao.foodetector.service.serviceMtrl.FoodMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class FoodServiceImp implements FoodService {
    FoodRepository foodRepository;
    FoodMaterialRepository foodMaterialRepository;
    @Autowired
    public FoodServiceImp(FoodRepository foodRepository,FoodMaterialRepository foodMaterialRepository){
        this.foodRepository=foodRepository;
        this.foodMaterialRepository=foodMaterialRepository;
    }

    @Override
    public List<FoodResponse> getAll() {
        List<FoodResponse> liste = new ArrayList<>();
        foodRepository.findAll().forEach(x -> {
            FoodResponse response = new FoodResponse();
            response.setFoodName(x.getFoodName());
            liste.add(response);
        });
        return liste;
    }

    @Override
    public FoodResponse getOne(String foodName) {
        FoodEntity entity = foodRepository.findByFoodName(foodName).
                orElseThrow(() -> new RegisterNotFoundException("Girilen isimde yemek bulunamadı!!!"));

        FoodResponse response = new FoodResponse();
        response.setFoodName(entity.getFoodName());

        List<FoodMaterialResponse> materials = new ArrayList<>();
        entity.getMaterials().forEach(x -> {
            FoodMaterialResponse materialResponse = new FoodMaterialResponse();
            materialResponse.setMaterialName(x.getMaterialName());
            materialResponse.setMaterialInfo(x.getMaterialInfo());
            materials.add(materialResponse);
        });
        response.setMaterials(materials);

        return response;
    }

    @Override
    public FoodResponse updateName(String foodName, String newname) {
        FoodEntity entity = foodRepository.findByFoodName(foodName).
                orElseThrow(() -> new RegisterNotFoundException("Verilen isimde yemek bulunamadı!!!"));
        entity.setFoodName(newname);
        foodRepository.save(entity);
        FoodResponse response = new FoodResponse();
        response.setFoodName(entity.getFoodName());
        return response;
    }

    @Override
    public DoneResponse delete(String foodName) {
        FoodEntity entity=foodRepository.findByFoodName(foodName)
                .orElseThrow(()->new RegisterNotFoundException("Register not founded, please write correct foodName"));
        entity.getMaterials().forEach(x->{
            foodMaterialRepository.delete(x);
        });
        foodRepository.delete(entity);
        DoneResponse response=new DoneResponse("Food deleted please check it");
        return response;
    }



    @Override
    public DoneResponse newFood(FoodRequest request) {
        if (kayitCekme(request.getFoodName()).isPresent()) {
            throw new RegisterAddedBeforeThisException("Bu isimden zaten kayıt mevcut!!!");
        }
        FoodEntity entity = new FoodEntity();
        entity.setFoodName(request.getFoodName());
        foodRepository.save(entity);

        request.getMaterials().forEach(x -> {
            FoodMaterialEntity materialEntity = new FoodMaterialEntity();
            materialEntity.setMaterialName(x.getMaterialName());
            materialEntity.setMaterialInfo(x.getMaterialInfo());
            materialEntity.setFoodId(entity.getId());
            foodMaterialRepository.save(materialEntity);
        });
        DoneResponse response = new DoneResponse("*" + request.getFoodName() + "*  eklendi");
        return response;
    }

    private Optional<FoodEntity> kayitCekme(String foodName) {
        return foodRepository.findByFoodName(foodName);
    }
}
