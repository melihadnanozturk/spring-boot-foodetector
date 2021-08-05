package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.SoupEntity;
import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.SoupRepository;
import com.mao.foodetector.repository.repoMtrl.SoupMaterialRepository;
import com.mao.foodetector.request.FoodRequest;
import com.mao.foodetector.request.SoupRequest;
import com.mao.foodetector.request.mtrequest.FoodMaterialRequest;
import com.mao.foodetector.request.mtrequest.SoupMaterialRequest;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.SoupResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.DOMError;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SoupServiceImpTest {

    @InjectMocks
    SoupServiceImp soupServiceImp;

    @Mock
    SoupRepository soupRepository;
    @Mock
    SoupMaterialRepository soupMaterialRepository;

    @Test
    void getAll() {
        List<SoupMaterialEntity> material=new ArrayList<>();
        List<SoupEntity> entities=new ArrayList<>();

        material.add(new SoupMaterialEntity(1,"material","info",1));
        SoupEntity entity=new SoupEntity(1,"test",material);

        entities.add(entity);
        when(soupRepository.findAll()).thenReturn(entities);

        List<SoupResponse> responses=soupServiceImp.getAll();
        Assertions.assertFalse(responses.isEmpty());
        Assertions.assertEquals(responses.get(0).getSoupName(),entities.get(0).getSoupName());
        Assertions.assertEquals(responses.get(0).getMaterials(),null);
    }

    @Test
    void testGetAll_shouldReturnEmptyListIfThereIsNotAnyEntity(){
        when(soupRepository.findAll()).thenReturn(new ArrayList<>());
        List<SoupResponse> liste=soupServiceImp.getAll();

        Assertions.assertTrue(liste.isEmpty());
    }

    @Test
    void getOne() {
        List<SoupMaterialEntity> materials=new ArrayList<>();
        materials.add(new SoupMaterialEntity(1,"malzeme","info",1));
        SoupEntity entity=new SoupEntity(1,"test",materials);

        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(entity));
        SoupResponse response=soupServiceImp.getOne("test");

        Assertions.assertEquals(response.getSoupName(),entity.getSoupName());
        Assertions.assertEquals(response.getMaterials().get(0).getMaterialName(),entity.getMaterials().get(0).getMaterialName());
        Assertions.assertEquals(response.getMaterials().get(0).getMaterialInfo(),entity.getMaterials().get(0).getMaterialInfo());
    }

    @Test
    void testGetOne_shouldThrowExceptionIfRegisterNotFounded(){
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            soupServiceImp.getOne("test");
        });
    }

    @Test
    void testGetOne_ResponseMaterialList_ShouldBeEmptyIfEntityMaterialsListIsEmpty(){
        SoupEntity entity=new SoupEntity(1,"malzemesiz",new ArrayList<>());
        when(soupRepository.findBySoupName("malzemesiz")).thenReturn(Optional.of(entity));

        SoupResponse response=soupServiceImp.getOne("malzemesiz");
        Assertions.assertTrue(response.getMaterials().isEmpty());
    }

    @Test
    void testGetOne_shouldThrowNullPointerExceptionIfEntityMaterialsListIsNull(){
        SoupEntity entity=new SoupEntity(1,"test",null);
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(entity));

        Assertions.assertThrows(NullPointerException.class,()->{
            soupServiceImp.getOne("test");
        });
    }

    @Test
    void updateName() {
        SoupEntity entity=new SoupEntity(1,"test",new ArrayList<>());
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(entity));

        SoupResponse response=soupServiceImp.updateName("newName","test");
        Assertions.assertTrue(response.getSoupName().equals("newName"));
    }

    @Test
    void testUpdateName_shouldThrowExceptionIfRegisterNotFounded(){
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            soupServiceImp.updateName("newName","test");
        });
    }

    @Test
    void testUpdateName_responseMaterialList_ShouldBeNullWhenWeCallUpdateName(){
        SoupEntity entity=new SoupEntity(1,"test",null);
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(entity));
        SoupResponse response=soupServiceImp.updateName("newName","test");
        Assertions.assertEquals(response.getMaterials(),null);
    }

    @Test
    void delete(){
        List<SoupMaterialEntity> materials=new ArrayList<>();
        materials.add(new SoupMaterialEntity(1,"material","info",1));
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(new SoupEntity(1,"test",materials)));

        DoneResponse response=soupServiceImp.delete("test");
        Assertions.assertEquals(response.getMessage(),"Soup deleted, please check it");
    }

    @Test
    void testDelete_soupRepository_shouldWorkTwiceWhenWeCallDelete() {
        List<SoupMaterialEntity> materials=new ArrayList<>();
        materials.add(new SoupMaterialEntity(1,"material","info",1));

        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(new SoupEntity(1,"test",materials)));
        soupServiceImp.delete("test");

        verify(soupRepository,times(1)).findBySoupName(any());
        verify(soupRepository,times(1)).delete(any(SoupEntity.class));

    }

    @Test
    void testDelete_soupMaterialRepository_shouldWorkOneTimeWhenWeCallDelete(){
        List<SoupMaterialEntity> materials=new ArrayList<>();
        materials.add(new SoupMaterialEntity(1,"material","info",1));

        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(new SoupEntity(1,"test",materials)));
        soupServiceImp.delete("test");
        verify(soupMaterialRepository,times(1)).delete(any(SoupMaterialEntity.class));
    }

    @Test
    void testDelete_materialRepositoryShouldNotWorkWhenEntityMaterialsAreEmpty(){
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(new SoupEntity(1,"test",new ArrayList<>())));
        soupServiceImp.delete("test");

        verifyNoInteractions(soupMaterialRepository);
    }

    @Test
    void testDelete_SoupRepositoryDeleteMethodShouldWorkEvenIfMaterialListIsEmpty(){
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.of(new SoupEntity(1,"test",new ArrayList<>())));
        soupServiceImp.delete("test");

        verify(soupRepository,times(1)).delete(any(SoupEntity.class));
    }

    @Test
    void testDelete_shouldThrowExceptionIfRegisterNotFoundedWithGivenSoupName(){
        when(soupRepository.findBySoupName("test")).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            soupServiceImp.delete("test");
        });
    }


    //reuqestde otomatik variable oluştururken @NotNull oluşturuyor buradan validasyon tetsini çıkarabilirisn belki?
    @Test
    void newSoup() {
        List<SoupMaterialRequest> materials=new ArrayList<>();
        materials.add(new SoupMaterialRequest("material","info"));
        SoupRequest request=new SoupRequest("newSoup",materials);
        when(soupRepository.findBySoupName("newSoup")).thenReturn(Optional.empty());
        DoneResponse response=soupServiceImp.newSoup(request);
        Assertions.assertEquals(response.getMessage(),"*"+request.getSoupName()+"*  eklendi");
    }

    @Test
    void testNewSoup_shouldThrowExceptionIfRegisterAddedBeforeThat(){
        when(soupRepository.findBySoupName(any())).thenReturn(Optional.of(new SoupEntity()));
        SoupRequest request=new SoupRequest();
        Assertions.assertThrows(RegisterAddedBeforeThisException.class,()->{
            soupServiceImp.newSoup(request);
        });
    }

    @Test
    void testNewSoup_SoupRepository_ShouldWorksTwiceTimeWhenWorksCorrectly(){
        List<SoupMaterialRequest> material = new ArrayList<>();
        material.add(new SoupMaterialRequest("malzeme", "info"));
        SoupRequest request = new SoupRequest("test", material);

         when(soupRepository.findBySoupName(any())).thenReturn(Optional.empty());
         soupServiceImp.newSoup(request);

        verify(soupRepository,times(1)).findBySoupName(any());
        verify(soupRepository,times(1)).save(any(SoupEntity.class));
    }

    @Test
    void testNewSoup_SoupMaterialRepository_ShouldWorkOnceWhenWorksCorrectly(){
        List<SoupMaterialRequest> material = new ArrayList<>();
        material.add(new SoupMaterialRequest("malzeme", "info"));
        SoupRequest request = new SoupRequest("test", material);

        when(soupRepository.findBySoupName(any())).thenReturn(Optional.empty());
        soupServiceImp.newSoup(request);

        verify(soupMaterialRepository,times(1)).save(any(SoupMaterialEntity.class));
    }

    @Test
    void testNewSoup_SoupMaterialRepository_ShouldNotWorkAnyTimeIfRequestMaterialListIsEmpty(){
        SoupRequest request = new SoupRequest("test", new ArrayList<>());
        when(soupRepository.findBySoupName(any())).thenReturn(Optional.empty());

        soupServiceImp.newSoup(request);
        verifyNoInteractions(soupMaterialRepository);
    }
}