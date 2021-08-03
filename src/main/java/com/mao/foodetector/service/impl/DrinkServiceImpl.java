package com.mao.foodetector.service.impl;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.repository.DesertRepository;
import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.response.DrinkResponse;
import com.mao.foodetector.service.DrinkService;
import org.springframework.stereotype.Service;

import javax.xml.transform.sax.SAXSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrinkServiceImpl implements DrinkService {

    private final DesertRepository desertRepository;

    public DrinkServiceImpl(DesertRepository desertRepository) {
        this.desertRepository = desertRepository;
    }

    @Override
    public List<DrinkResponse> getAll() {
        return desertRepository.findAll()
                .stream()
                .filter(entity -> entity.getId() % 2 ==0)
                .map(entity -> new DrinkResponse())
                .collect(Collectors.toList());
    }

    @Override
    public void doNothing() {

    }

    @Override
    public DrinkResponse getOneDrink() {
        DrinkResponse response=new DrinkResponse();
        return response;
    }

    @Override
    public void gereksizyazı()  {

    }

    @Override
    public List<String> mylist() {
        return null;
    }


}
