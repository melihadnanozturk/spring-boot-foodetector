package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.FoodRepository;
import com.mao.foodetector.repository.repoMtrl.FoodMaterialRepository;
import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.FoodResponse;
import com.mao.foodetector.response.respoMtrl.FoodMaterialResponse;
import com.mao.foodetector.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/*bu yorum silinmediyse problem şu:
 silinen yemeğin malzemeleri yemek silindiğindehalen silinmiyor,
 material repository' yi service implemente edip çağırmak daha manıtklı
*/
public class FoodImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodMaterialRepository foodMaterialRepository;

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
    //response Return olucağına direkt mesaj da verebilirsin
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
        FoodEntity entity=foodRepository.findByFoodName(foodName).get();
                if(entity!=null){
                    entity.getMaterials().forEach(x->{
                        foodMaterialRepository.delete(x);
                    });
                    foodRepository.delete(entity);
                    DoneResponse response=new DoneResponse("Silme işlemi gerçekleşti lütfen el ile kontrol ediniz!!!");
                    return  response;
                }
                throw new RegisterNotFoundException("Verilen isimde yemek bulunamadı!!!");
    }

    @Override
    public BaseResponse newFood(FoodRequest request) {
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
