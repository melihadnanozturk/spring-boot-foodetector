package com.mao.foodetector.controller;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.DrinkEntity;
import com.mao.foodetector.response.DrinkResponse;
import com.mao.foodetector.service.DrinkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//buradaki testler eğitim için hazılanmıştır,hiçbir geçerliliği yoktur.
@ExtendWith(MockitoExtension.class)
class DrinkControllerTest {

    @InjectMocks
    private DrinkController drinkController;

    @Mock
    private DrinkService drinkService;



    @Test
    void testGetAll() {
        Mockito.when(drinkService.getAll()).thenReturn(Collections.singletonList(new DrinkResponse()));

        List<DrinkResponse> all = drinkController.getAll();

        Assertions.assertFalse(all.isEmpty());
        //doNothing(); in kaç defa çalıştığını test ediyor  mockito kullandık
        Mockito.verify(drinkService, Mockito.times(2)).doNothing();
    }

    @Test
    void testGetAll_shouldThrowExceptionIfListIsEmpty()  {
        Mockito.when(drinkService.getAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(Exception.class, () -> drinkController.getAll());
        Mockito.verify(drinkService, Mockito.times(1)).doNothing();
    }

    @Test
    void testGetOne_shouldReturnDrink(){
        DrinkResponse response=new DrinkResponse("test-drink");
        Mockito.when(drinkService.getOneDrink()).thenReturn(response);
        DrinkResponse test=drinkController.getOneDrink();
        /*
         -test version 1.0.0-
        Assertions.assertEquals(test.getDrinkName(),response.getDrinkName());
        */
        Mockito.verify(drinkService,Mockito.times(2)).gereksizyazı();
    }

    @Test
    void testMyList_shouldReturnListCorrectly(){
        List<String> liste=new ArrayList<>();
        liste.add("melih");
        Mockito.when(drinkService.mylist()).thenReturn(liste);
        Assertions.assertEquals(drinkController.myList().get(0),liste.get(0));
    }

    @Test
    void testMyList_shouldThrowExceptionIfMyListReturnEmptyList(){
        Mockito.when(drinkService.mylist()).thenReturn(new ArrayList<>());
        Assertions.assertThrows(Exception.class,()->{
            drinkController.myList();
        });
    }

}