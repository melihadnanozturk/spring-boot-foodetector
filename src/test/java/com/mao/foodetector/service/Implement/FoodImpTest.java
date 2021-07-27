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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FoodImpTest {

    @InjectMocks
    private FoodImp foodImp;

    @Mock
    private FoodRepository foodRepository;
    @Mock
    private FoodMaterialRepository foodMaterialRepository;

    @Test
    void getAll() {
        List<FoodMaterialEntity> materialListe = null;
        List<FoodEntity> liste = new ArrayList<>();
        liste.add(new FoodEntity(1, "test-1", materialListe));
        liste.add(new FoodEntity(2, "test-2", materialListe));
        liste.add(new FoodEntity(3, "test-3", materialListe));
        when(foodRepository.findAll()).thenReturn(liste);
        List<FoodResponse> responseListe = foodImp.getAll();
        Assertions.assertEquals(responseListe.size(), 3);
        Assertions.assertEquals(responseListe.get(1).getFoodName(), "test-2");
        Assertions.assertEquals(responseListe.isEmpty(), false);

    }

    @Test
    void getOne() {
        List<FoodMaterialEntity> materialListe = new ArrayList<>();

        materialListe.add(new FoodMaterialEntity(1, "test-material-1", "test-info-1", 1));
        materialListe.add(new FoodMaterialEntity(2, "test-material-2", "test-info-2", 1));
        materialListe.add(new FoodMaterialEntity(3, "test-material-3", "test-info-3", 2));

        when(foodRepository.findByFoodName("test-food")).
                thenReturn(java.util.Optional.of(new FoodEntity(1, "test-food", materialListe)));

        FoodResponse response = foodImp.getOne("test-food");
        Assertions.assertEquals(response.getFoodName(), "test-food");
        Assertions.assertEquals(response.getMaterials().size(), 3);
    }

    @Test
    void getOneFail() {
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(RegisterNotFoundException.class, () -> {
            foodImp.getOne("test");
        });
    }

    @Test
    void updateName() {
        FoodEntity entity = new FoodEntity();
        entity.setId(1);
        entity.setFoodName("test-food");
        when(foodRepository.findByFoodName("test-food")).thenReturn(Optional.of(entity));
        FoodResponse response = foodImp.updateName("test-food", "test-newName");
        Assertions.assertEquals(response.getFoodName(), "test-newName");

    }

    @Test
    void updateNameFail() {
        when(foodRepository.findByFoodName("test-food")).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(RegisterNotFoundException.class, () -> {
            foodImp.getOne("test-food");
        });
    }

    @Test
        //silme işlemi daha iyi test edilebilir
    void delete() {
        FoodEntity entity = new FoodEntity();
        entity.setFoodName("test");
        entity.setId(1);
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));
        DoneResponse response = foodImp.delete("test");
        Assertions.assertEquals(response.getMessage(), "Silme işlemi");
    }

    @Test
    void deleteFail() {
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(RegisterNotFoundException.class, () -> {
            foodImp.delete("test");
        });
    }

    @Test
    void newFood() {
        List<FoodMaterialEntity> materials = new ArrayList<>();
        materials.add(new FoodMaterialEntity("m-1", "info-1"));
        materials.add(new FoodMaterialEntity("m-2", "info-2"));
        materials.add(new FoodMaterialEntity("m-3", "info-3"));

        //FoodRequest request = new FoodRequest("test-food", materials);
        //DoneResponse response = (DoneResponse) foodImp.newFood(request);
        //Assertions.assertEquals(response.getMessage(),"*test-food*  eklendi");

    }

    @Test
    void newFoodFail() {
        List<FoodMaterialEntity> materials = new ArrayList<>();
        materials.add(new FoodMaterialEntity("m-1", "info-1"));
        materials.add(new FoodMaterialEntity("m-2", "info-2"));
        materials.add(new FoodMaterialEntity("m-3", "info-3"));
        //FoodRequest request = new FoodRequest("test-food", materials);
        FoodEntity entity = new FoodEntity();
        entity.setFoodName("test-food");

        when(foodRepository.findByFoodName("test-food")).thenReturn(Optional.of(entity));

        //Assertions.assertThrows(RegisterAddedBeforeThisException.class, () -> {
        //    foodImp.newFood(request);
        //});
    }
}