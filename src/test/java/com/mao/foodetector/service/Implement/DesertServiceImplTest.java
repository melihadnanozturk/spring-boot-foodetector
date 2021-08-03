package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.DesertRepository;
import com.mao.foodetector.repository.repoMtrl.DesertMaterialRepository;
import com.mao.foodetector.request.DesertRequest;
import com.mao.foodetector.request.mtrequest.DesertMaterialRequest;
import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.response.DoneResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DesertServiceImplTest {

    @Mock
    DesertRepository desertRepository;
    @Mock
    DesertMaterialRepository desertMaterialRepository;

    @InjectMocks
    DesertServiceImpl desertServiceImp;


    @Test
    void getAllRunProperly() {
        List<DesertEntity> entity = new ArrayList<>();
        entity.add(new DesertEntity(1, "test1"));

        when(desertRepository.findAll()).thenReturn(entity);
        Assertions.assertEquals(desertServiceImp.getAll().get(0).getDesertName(), "test1");
    }

    @Test
    void testGetAll_shouldBeTrueWhenListIsEmpty(){
        Mockito.when(desertRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertTrue(desertServiceImp.getAll().isEmpty());
    }

    @Test
    void testGetOne() {
        List<DesertMaterialEntity> material = new ArrayList<>();
        material.add(new DesertMaterialEntity(1, "test-desert", "test-info", 1));
        material.add(new DesertMaterialEntity(2, "test-desert-2", "test-info-3", 1));
        DesertEntity entity =new DesertEntity(1, "test1", material);

        when(desertRepository.findByDesertName("test-desert")).thenReturn(Optional.of(entity));

        DesertResponse response=desertServiceImp.getOne("test-desert");

        Assertions.assertEquals(response.getDesertName(),entity.getDesertName());
        Assertions.assertEquals(response.getMaterials().get(0).getMaterialName(),entity.getMaterials().get(0).getMaterialName());
        Assertions.assertEquals(response.getMaterials().get(1).getMaterialName(),entity.getMaterials().get(1).getMaterialName());
    }

    @Test
    void testGetOne_shouldThrowExceptionIfRegisterNotFounded(){
        when(desertRepository.findByDesertName("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            desertServiceImp.getOne("test");
        });
    }

    //bu durumun düşük bir olası,zaten kullanıcı malzemesi null olan bir yemeği kayıt edemiyor.
    //Bu yüzden eğer malzemesiz bir entity çağırılması söz konusu oluşurseki düşük ihtimal null pointer olur
    //BU testi yazmam gereksiz olmuş olabilir :D
    @Test
    void testGetOne_shouldThrowNullPointerExceptionIfAnyEntityDoesNotMaterials(){
        DesertEntity entity=new DesertEntity(1,"testEntity");
        when(desertRepository.findByDesertName("testEntity")).thenReturn(Optional.of(entity));
        Assertions.assertThrows(NullPointerException.class,()->{
            desertServiceImp.getOne("testEntity");
        });
    }


    @Test
    void updateName() {
        DesertEntity entity=new DesertEntity(1,"test");
        when(desertRepository.findByDesertName("test")).thenReturn(Optional.of(entity));
        DesertResponse response=desertServiceImp.updateName("test","new-name");

        Assertions.assertEquals(response.getDesertName(),"new-name");
        Assertions.assertEquals(response.getMaterials(),null);
    }

    @Test
    void testUpdateName_shouldhrowExceptionWhenRegisterNotFounded(){
        when(desertRepository.findByDesertName("test")).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            desertServiceImp.updateName("test","new-name");
        });
    }

    /*@Test
    //delete(); kodunun nasıl testini yazabilirim?
    void delete() {
        List<DesertMaterialEntity> material = new ArrayList<>();
        material.add(new DesertMaterialEntity(1, "test-desert", "test-info", 1));
        DesertEntity entity =new DesertEntity(1, "test", material);
        when(any(DesertRepository.class)).thenReturn((DesertRepository) entity)
    }*/

    @Test
    void testDelete_souldThrowNullPointerExceptionWhenEntityDoesNotHaveAnyMaterial(){
        DesertEntity entity=new DesertEntity(1,"malzemesiz");
        when(desertRepository.findByDesertName("malzemesiz")).thenReturn(Optional.of(entity));
        Assertions.assertThrows(NullPointerException.class,()->desertServiceImp.delete("malzemesiz"));
    }

    @Test
    void testDelete_shouldThrowRegisterNotFoundExceptionWhenRegisterNotFounded(){
        when(desertRepository.findByDesertName("exception")).thenReturn(Optional.empty());

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            desertServiceImp.delete("exception");
        });
    }

    @Test
    void newDesert() {

        List<DesertMaterialRequest> material=new ArrayList<>();
        material.add(new DesertMaterialRequest("material-name","material-info"));
        DesertRequest request=new DesertRequest("test-mat",material);

        when(desertRepository.findByDesertName("test-mat")).thenReturn(Optional.empty());
        when(desertRepository.save(any(DesertEntity.class))).thenReturn(any(DesertEntity.class));
        when(desertMaterialRepository.save(any(DesertMaterialEntity.class))).thenReturn(any(DesertMaterialEntity.class));

        DoneResponse response=desertServiceImp.newDesert(request);
        Assertions.assertEquals(response.getMessage(),"*"+request.getDesertName()+"*  eklendi");
    }

    @Test
    void kayıtCekme() {
    }
}