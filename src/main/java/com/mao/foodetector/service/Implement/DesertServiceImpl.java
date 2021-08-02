package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.DesertRepository;
import com.mao.foodetector.repository.repoMtrl.DesertMaterialRepository;
import com.mao.foodetector.request.DesertRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.respoMtrl.DesertMaterialResponse;
import com.mao.foodetector.service.DesertService;
import com.mao.foodetector.service.serviceMtrl.DesertMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class DesertServiceImpl implements DesertService {


    private DesertRepository desertRepository;
    private DesertMaterialRepository desertMaterialRepository;

    @Autowired
    public DesertServiceImpl(DesertRepository repository,DesertMaterialRepository desertMaterialRepository){
        this.desertRepository=repository;
        this.desertMaterialRepository=desertMaterialRepository;
    }


    @Override
    public Iterable<DesertResponse> getAll( List<DesertResponse> liste,DesertResponse response) {
        desertRepository.findAll().forEach(x -> {
            response.setDesertName(x.getDesertName());
            liste.add(response);
        });
        return liste;
    }


    @Override
    public DesertResponse getOne(String desertName) {
        DesertEntity entity = desertRepository.findByDesertName(desertName).
                orElseThrow(() -> new RegisterNotFoundException("Girilen isimde tatlı bulunamadı!!!"));

        List<DesertMaterialResponse> materials = new ArrayList<>();
        entity.getMaterials().forEach(x -> {
            DesertMaterialResponse material = new DesertMaterialResponse();
            material.setMaterialName(x.getMaterialName());
            material.setMaterialInfo(x.getMaterialInfo());
            materials.add(material);
        });

        DesertResponse response = new DesertResponse();
        response.setDesertName(entity.getDesertName());
        response.setMaterials(materials);

        return response;
    }

    @Override
    public DesertResponse updateName(String desertName, String newName) {
        DesertEntity entity = desertRepository.findByDesertName(desertName).
                orElseThrow(() -> new RegisterNotFoundException("Girilen isimde tatlı bulunamadı!!!"));

        entity.setDesertName(newName);
        desertRepository.save(entity);

        DesertResponse response = new DesertResponse();
        response.setDesertName(entity.getDesertName());
        return response;
    }

    @Override
    public DoneResponse delete(String desertName) {
    DesertEntity entity=desertRepository.findByDesertName(desertName)
            .orElseThrow(()->new RegisterNotFoundException("Register not founded, please write correct desertName"));
    entity.getMaterials().forEach(x->{
        desertMaterialRepository.delete(x);
    });
    desertRepository.delete(entity);
    DoneResponse response=new DoneResponse("Desert deleted please check it");
    return response;
    }


    @Override
    public DoneResponse newDesert(DesertRequest request) {
        if (kayıtCekme(request.getDesertName()).isPresent()) {
            throw new RegisterAddedBeforeThisException("Bu isimden zaten kayıt mevcut!!!");
        }
        DesertEntity entity = new DesertEntity();
        entity.setDesertName(request.getDesertName());
        desertRepository.save(entity);

        request.getMaterials().forEach(x -> {
            DesertMaterialEntity materialEntity = new DesertMaterialEntity();
            materialEntity.setMaterialName(x.getMaterialName());
            materialEntity.setMaterialInfo(x.getMaterialInfo());
            materialEntity.setDesertId(entity.getId());
            desertMaterialRepository.save(materialEntity);
        });

        DoneResponse response = new DoneResponse("*" + request.getDesertName() + "*  eklendi");
        return response;
    }

    public Optional<DesertEntity> kayıtCekme(String desertName) {
        return desertRepository.findByDesertName(desertName);
    }
}

