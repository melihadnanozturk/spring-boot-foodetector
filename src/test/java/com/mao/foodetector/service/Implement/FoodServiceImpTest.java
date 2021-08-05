package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.FoodEntity;
import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.FoodRepository;
import com.mao.foodetector.repository.repoMtrl.FoodMaterialRepository;
import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.request.mtrequest.FoodMaterialRequest;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.FoodResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class FoodServiceImpTest {

    @InjectMocks
    FoodServiceImp foodServiceImp;
    @Mock
    FoodRepository foodRepository;
    @Mock
    FoodMaterialRepository foodMaterialRepository;

    @Test
    void getAll() {
        List<FoodEntity> liste = new ArrayList<>();
        liste.add(new FoodEntity(1, "test"));

        when(foodRepository.findAll()).thenReturn(liste);
        List<FoodResponse> test = foodServiceImp.getAll();

        Assertions.assertFalse(test.isEmpty());
        Assertions.assertEquals(test.get(0).getFoodName(), liste.get(0).getFoodName());
        Assertions.assertEquals(test.get(0).getMaterials(), null);
    }

    @Test
    void testGetAll_shouldReturnEmptyListIfThereIsNotAnyEntity() {
        when(foodRepository.findAll()).thenReturn(new ArrayList<>());
        List<FoodResponse> liste = foodServiceImp.getAll();
        Assertions.assertTrue(liste.isEmpty());
    }

    @Test
    void getOne() {
        List<FoodMaterialEntity> materials = new ArrayList<>();
        materials.add(new FoodMaterialEntity(1, "test", "test-material", 1));
        FoodEntity entity = new FoodEntity(1, "test", materials);

        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));

        FoodResponse response = foodServiceImp.getOne("test");
        Assertions.assertEquals(response.getFoodName(), entity.getFoodName());
        Assertions.assertEquals(response.getMaterials().get(0).getMaterialName(), entity.getMaterials().get(0).getMaterialName());
        Assertions.assertEquals(response.getMaterials().get(0).getMaterialInfo(), entity.getMaterials().get(0).getMaterialInfo());
    }

    @Test
    void testGetOne_shouldThrowExceptionIfRegisterNotFounded() {
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class, () -> {
            foodServiceImp.getOne("test");
        });
    }

    @Test
    void testGetOne_MaterialsShouldBeEmptyWhenEntityDoesNotAnyMaterial() {
        FoodEntity entity = new FoodEntity(1, "malzemesiz", new ArrayList<>());
        when(foodRepository.findByFoodName("malzemesiz")).thenReturn(Optional.of(entity));

        FoodResponse response = foodServiceImp.getOne("malzemesiz");
        Assertions.assertTrue(response.getMaterials().isEmpty());
    }

    @Test
    void testGetOne_shouldThrowNullPointerExceptionIfEntitysMaterialListIsNull() {
        FoodEntity entity = new FoodEntity(1, "null", null);
        when(foodRepository.findByFoodName("null")).thenReturn(Optional.of(entity));

        Assertions.assertThrows(NullPointerException.class, () -> {
            foodServiceImp.getOne("null");
        });
    }

    @Test
    void updateName() {
        FoodEntity entity = new FoodEntity(1, "test", null);
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));

        FoodResponse response = foodServiceImp.updateName("test", "newName");
        verify(foodRepository, times(1)).save(entity);
        Assertions.assertTrue(response.getFoodName().equals("newName"));
    }

    @Test
    void testUpdateName_shouldThrowRegisterNotFoundedExceptionWhenRegisterAreNotFound() {
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class, () -> {
            foodServiceImp.updateName("test", "newName");
        });
    }

    @Test
    void testUpdateName_ResponseMaterialsShouldBeNullWhenWeAreCalledUpdateNameCorrectly() {
        FoodEntity entity = new FoodEntity(1, "test", new ArrayList<>());
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));

        FoodResponse response = foodServiceImp.updateName("test", "newName");
        Assertions.assertNull(response.getMaterials());
    }
    /*
    *
    *
    *
    *
    *
    *
    * ---------------deleteye geldin --------------yazılacaklar var !!!
    *
    *
    *
    *
    *
     */

    @Test
    void delete() {
        List<FoodMaterialEntity> material = new ArrayList<>();
        material.add(new FoodMaterialEntity("material", "info", 1));
        FoodEntity entity = new FoodEntity(1, "test", material);

        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));
        foodServiceImp.delete("test");
        verify(foodRepository, Mockito.times(1)).delete(any(FoodEntity.class));
        verify(foodMaterialRepository, Mockito.times(1)).delete(any(FoodMaterialEntity.class));
    }

    @Test
    void testDelete_shouldThrowExceptionIfFoodAreNotFoundWithGivenFoodName() {
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(RegisterNotFoundException.class, () -> {
            foodServiceImp.delete("test");
        });
    }

    @Test
    void testDelete_shouldNotWorkMaterialRepositoryWhenMaterialListIsEmpty() {
        FoodEntity entity = new FoodEntity(1, "test", new ArrayList<>());
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));

        foodServiceImp.delete("test");
        verifyNoInteractions(foodMaterialRepository);
    }

    @Test
    void testDelete_foodRepository_shouldWorkTwiceWhenWeCallDelete(){
        List<FoodMaterialEntity> material = new ArrayList<>();
        material.add(new FoodMaterialEntity(1,"material", "info", 1));

        FoodEntity entity = new FoodEntity(1, "test", material);
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));

        foodServiceImp.delete("test");

        verify(foodRepository,times(1)).findByFoodName(any());
        verify(foodRepository,times(1)).delete(any(FoodEntity.class));
    }

    @Test
    void testDelete_fooodMaterialRepository_shouldWorkOnceWhenWeCallDelete(){
        List<FoodMaterialEntity> material = new ArrayList<>();
        material.add(new FoodMaterialEntity("material", "info", 1));

        FoodEntity entity = new FoodEntity(1, "test", material);
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.of(entity));

        foodServiceImp.delete("test");
        verify(foodMaterialRepository,times(1)).delete(any(FoodMaterialEntity.class));
    }

    @Test
    void newFood() {
        List<FoodMaterialRequest> material = new ArrayList<>();
        material.add(new FoodMaterialRequest("malzeme", "info"));
        FoodRequest request = new FoodRequest("test", material);

        when(foodRepository.findByFoodName("test")).thenReturn(Optional.empty());

        DoneResponse response = foodServiceImp.newFood(request);
        verify(foodRepository, times(1)).save(any(FoodEntity.class));
        verify(foodMaterialRepository, times(1)).save(any(FoodMaterialEntity.class));
    }

    @Test
    void testNewFood_shouldThrowRegisterAddedBeforeThatExceptionIfRegisterAddedBeforeThat() {
        when(foodRepository.findByFoodName(any())).thenReturn(Optional.of(new FoodEntity()));
        FoodRequest request = new FoodRequest();
        Assertions.assertThrows(RegisterAddedBeforeThisException.class, () -> {
            foodServiceImp.newFood(request);
        });
    }

    @Test
    void testNewFood_foodRepository_shouldWorkTwiceWhenWeCallNewFood(){
        List<FoodMaterialRequest> material = new ArrayList<>();
        material.add(new FoodMaterialRequest("malzeme", "info"));

        FoodRequest request = new FoodRequest("test", material);
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.empty());

        foodServiceImp.newFood(request);
        verify(foodRepository,times(1)).findByFoodName(request.getFoodName());
        verify(foodRepository,times(1)).save(any(FoodEntity.class));
    }

    @Test
    void testNewFood_foodMaterialRepository_shouldWhenWeCallNewFood(){
        List<FoodMaterialRequest> material = new ArrayList<>();
        material.add(new FoodMaterialRequest("malzeme", "info"));

        FoodRequest request = new FoodRequest("test", material);
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.empty());

        foodServiceImp.newFood(request);
        verify(foodMaterialRepository,times(1)).save(any(FoodMaterialEntity.class));
    }

    @Test
    void testNewFood_foodMaterialRepository_shouldNotWorkIfRequestMaterialListIsEmpty(){
        FoodRequest request=new FoodRequest("test",new ArrayList<>());
        when(foodRepository.findByFoodName("test")).thenReturn(Optional.empty());

        foodServiceImp.newFood(request);
        verifyNoInteractions(foodMaterialRepository);
    }

}