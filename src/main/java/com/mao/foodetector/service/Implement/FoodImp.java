package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.repository.FoodRepository;
import com.mao.foodetector.repository.repoMtrl.FoodMaterialRepository;
import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.FoodResponse;
import com.mao.foodetector.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodMaterialRepository foodMaterialRepository;

    @Override
    public Iterable<FoodResponse> getAll() {
        List<FoodResponse> liste=new ArrayList<>();
         foodRepository.findAll().stream().forEach(x->{
             FoodResponse response= FoodResponse.builder()
                     .foodName(x.getFoodName())
                     .build();
             liste.add(response);
         });
         return liste;
    }

    @Override
    public BaseResponse getOne(String foodName) {
        if (foodRepository.findByFoodName(foodName) == null) {
            DoneResponse response = new DoneResponse("Giriln bilgiden mevcut kayıt yoktur!!!");
            return response;
        } else {
            FoodEntity entity = foodRepository.findByFoodName(foodName);
            FoodResponse response = new FoodResponse( entity.getFoodName(),
                     entity.getMaterials());
            return response;
        }
    }

    @Override
    //Geliştirilicek
    //Buradan update yaparken kullanıcı malzemeleride girmke sıkıntı yapacağından
    //daha farklı bir update işlemi geliştirebilirsin
    public BaseResponse updateName( String foodName,String newname) {
        if (foodRepository.findByFoodName(foodName) == null) {
            DoneResponse response = new DoneResponse("Geçerli bilgide güncellenicek veri yok!!!");
            return response;
        } else {
            FoodEntity entity = foodRepository.findByFoodName(foodName);
            entity.setFoodName(newname);
            foodRepository.save(entity);
            FoodResponse response = FoodResponse.builder()
                    .foodName(entity.getFoodName())
                    .build();
            return response;
        }
    }

    @Override
    public BaseResponse delete(String foodName) {
        if (foodRepository.findByFoodName(foodName) != null) {
            foodRepository.delete(foodRepository.findByFoodName(foodName));
            DoneResponse response = new DoneResponse("Silme işlemi gerçekleşti. Lütfen silindiğine dair kontrolü el ile yapınız!");
            return response;
        } else {
            DoneResponse response = new DoneResponse("geçerli bilgide mevcut kayıt yoktur!!!");
            return response;
        }
    }

    @Override
    //hata olabilir deneme yap!!!
    //sadece yeni kayıt oluşturur malzemeler eklenmez!!!
    public BaseResponse newFood(FoodRequest request) {

        if (foodRepository.findByFoodName(request.getFoodName())!=null) {
            DoneResponse response = new DoneResponse("Aynı isimde kayıt var lütfen yemeğin adını değiştirin!!!");
            return response;
        } else {

            FoodEntity entity = FoodEntity.builder()
                    .foodName(request.getFoodName())
                    .build();
            foodRepository.save(entity);

            List<FoodMaterialEntity> liste=request.getMaterials();
            liste.stream().forEach(x->{
                FoodMaterialEntity materialEntity=new FoodMaterialEntity();
                materialEntity.setMaterialName(x.getMaterialName());
                materialEntity.setMaterialInfo(x.getMaterialInfo());
                materialEntity.setFoodId(entity.getId());
                foodMaterialRepository.save(materialEntity);
            });
                DoneResponse response=new DoneResponse("Yemek eklendi");
            return response;
        }
    }
}
