package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
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
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DesertImpTest{

    @InjectMocks
    private DesertImp desertImp;

    @Mock
    private DesertRepository desertRepository;
    @Mock
    private DesertMaterialRepository desertMaterialRepository;



    @Test
    void getAll() {

    }

    @Test
    void getOneFail(){
        /*when(desertRepository.findByDesertName("test")).
                thenThrow(new RegisterNotFoundException("test-exception-message"));*/

        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            desertImp.getOne("test");
        });
    }


     @Test
     void getOne() {
         String desertName="test";
         DesertEntity entity=new DesertEntity();
         entity.setDesertName("test");

         when(desertRepository.findByDesertName(desertName)).thenReturn(java.util.Optional.of(entity));
         DesertResponse response=desertImp.getOne(desertName);
         Assertions.assertEquals(response.getDesertName(),"test");
    }

    @Test
    void updateNameFail() {
        Assertions.assertThrows(RegisterNotFoundException.class,()->
                desertImp.updateName("test","test-2"));
    }

    @Test
    void updateName() {
        DesertEntity entity=new DesertEntity();
        entity.setDesertName("test");
        when(desertRepository.findByDesertName("test")).thenReturn(java.util.Optional.of(entity));
        DesertResponse response=  desertImp.updateName("test","test-second-name");
        Assertions.assertEquals(response.getDesertName(),"test-second-name");
    }

    @Test
    void delete() {
        DesertEntity entity=new DesertEntity();
        entity.setDesertName("test-entity");
        when(desertRepository.findByDesertName("test-entity")).thenReturn(java.util.Optional.of(entity));
        DoneResponse response= desertImp.delete("test-entity");
        Assertions.assertEquals(response.getMessage(),"Silme işlemi");
    }

    @Test
    void deleteFail() {
        when(desertRepository.findByDesertName("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(RegisterNotFoundException.class,()->{
            desertImp.delete("test");
        });
    }

    @Test
    void newDesert() {
    }

    @Test
    void newDesertFail() {
        DesertEntity entity=new DesertEntity();
        DesertRequest request=new DesertRequest();
        request.setDesertName("test");
        when(desertRepository.findByDesertName("test")).thenReturn(java.util.Optional.of(entity));
        Assertions.assertThrows(RegisterAddedBeforeThisException.class,()->{
            desertImp.newDesert(request);
        });

    }
}