package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.SoupEntity;
import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.SoupRepository;
import com.mao.foodetector.repository.repoMtrl.SoupMaterialRepository;
import com.mao.foodetector.request.SoupRequest;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.SoupResponse;
import com.mao.foodetector.response.respoMtrl.SoupMaterialResponse;
import com.mao.foodetector.service.Implement.ImpMtrl.SoupMaterialServiceImp;
import com.mao.foodetector.service.SoupService;
import com.mao.foodetector.service.serviceMtrl.SoupMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class SoupServiceImp implements SoupService {

    private SoupRepository soupRepository;
    private SoupMaterialRepository soupMaterialRepository;

    @Autowired
    public SoupServiceImp(SoupRepository soupRepository, SoupMaterialRepository soupMaterialRepository){
        this.soupRepository=soupRepository;
        this.soupMaterialRepository=soupMaterialRepository;
    }


    @Override
    public List<SoupResponse> getAll() {
        List<SoupResponse> liste = new ArrayList<>();
        soupRepository.findAll().forEach(x -> {
            SoupResponse response = SoupResponse.builder()
                    .soupName(x.getSoupName())
                    .build();
            liste.add(response);
        });
        return liste;
    }

    @Override
    public SoupResponse getOne(String soupName) {
        SoupEntity entity = soupRepository.findBySoupName(soupName).
                orElseThrow(() -> new RegisterNotFoundException("Girilen isimde çorba bulunamadı!!!"));

        SoupResponse response = new SoupResponse();
        response.setSoupName(entity.getSoupName());

        List<SoupMaterialResponse> materials = new ArrayList<>();
        entity.getMaterials().forEach(x -> {
            SoupMaterialResponse materialResponse = new SoupMaterialResponse(
                    x.getMaterialName(),
                    x.getMaterialInfo()
            );
            materials.add(materialResponse);
        });

        response.setMaterials(materials);
        return response;
    }

    @Override
    public SoupResponse updateName(String newname, String soupname) {
        SoupEntity entity = soupRepository.findBySoupName(soupname).
                orElseThrow(() -> new RegisterNotFoundException("Girilen isimde çorba bulunamadı!!!"));

        entity.setSoupName(newname);
        soupRepository.save(entity);
        SoupResponse response = new SoupResponse();
        response.setSoupName(entity.getSoupName());

        return response;
    }

    @Override
    public DoneResponse delete(String soupName) {
        SoupEntity entity = soupRepository.findBySoupName(soupName)
                .orElseThrow(() -> new RegisterNotFoundException("Register not founded,please write correct soupName"));
        entity.getMaterials().forEach(x->{
            soupMaterialRepository.delete(x);
        });
        soupRepository.delete(entity);
        DoneResponse response=new DoneResponse("Soup deleted, please check it");
        return response;
    }


    @Override
    public DoneResponse newSoup(SoupRequest request) {

        if (kayitCekme(request.getSoupName()).isPresent()) {
            throw new RegisterAddedBeforeThisException("Bu isimde kayıt mevcut!!!");
        }
        SoupEntity entity = new SoupEntity();
        entity.setSoupName(request.getSoupName());
        soupRepository.save(entity);
        request.getMaterials().forEach(x -> {
            SoupMaterialEntity materialEntity = new SoupMaterialEntity();
            materialEntity.setMaterialName(x.getMaterialName());
            materialEntity.setMaterialInfo(x.getMaterialInfo());
            materialEntity.setSoupId(entity.getId());
            soupMaterialRepository.save(materialEntity);
        });

        DoneResponse response = new DoneResponse("*" + request.getSoupName() + "*  eklendi");
        return response;
    }

    private Optional<SoupEntity> kayitCekme(String soupName) {
        return soupRepository.findBySoupName(soupName);
    }


}
