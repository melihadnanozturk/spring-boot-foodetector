package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.DesertEntity;
import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.entity.material.FoodMaterialEntity;
import com.mao.foodetector.repository.DesertRepository;
import com.mao.foodetector.repository.repoMtrl.DesertMaterialRepository;
import com.mao.foodetector.request.DesertRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DesertResponse;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.service.DesertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DesertImp implements DesertService {

    @Autowired
    private DesertRepository desertRepository;
    @Autowired
    private DesertMaterialRepository desertMaterialRepository;


    @Override
    //malzemeler getalla eklenmedi
    //kullanıcı sadece yemek seçsin diye
    public Iterable<DesertResponse> getAll() {
        List<DesertResponse> liste=new ArrayList<>();
        desertRepository.findAll().stream().forEach(x->{
            DesertResponse response= DesertResponse.builder()
                    .DesertName(x.getDesertName())
                    .build();
            liste.add(response);
        });
        return liste;
    }

    @Override
    public BaseResponse getOne(String desertName) {
        if(desertRepository.findByDesertName(desertName)==null){
            DoneResponse response=new DoneResponse("Mevcut bilgide geçerli kayıt yoktur!!!");
            return response;
        }else{
            DesertEntity entity=desertRepository.findByDesertName(desertName);
            DesertResponse response= new DesertResponse(entity.getDesertName(),
                    entity.getMaterials());
            return response;
        }
    }

    @Override
    public BaseResponse updateName(String desertName, String newName) {
        if (desertRepository.findByDesertName(desertName) == null) {
            DoneResponse response = new DoneResponse(" Geçerli bilgide kayıt mevcut değildir!!!");
            return response;
        } else {
            DesertEntity entity=desertRepository.findByDesertName(desertName);
            entity.setDesertName(newName);
            desertRepository.save(entity);
            DesertResponse response= DesertResponse.builder()
                    .DesertName(entity.getDesertName())
                    .build();
            return  response;
        }
    }

    @Override
    public BaseResponse delete(String desertName) {
        if (desertRepository.findByDesertName(desertName) == null) {
            DoneResponse response = new DoneResponse("geçerli bilgide mevcut kayıt yoktur!!!");
            return response;
        } else {
            desertRepository.delete(desertRepository.findByDesertName(desertName));
            DoneResponse response=new DoneResponse("Silme işlemi gerçekleşti. Lütfen silindiğine dair kontrolü el ile yapınız!");
            return  response;
        }
    }

    @Override

    public BaseResponse newDesert(DesertRequest request) {
        if (desertRepository.findByDesertName(request.getDesertName())!=null) {
            DoneResponse response = new DoneResponse("Aynı isimde kayıt var lütfen yemeğin adını değiştirin!!!");
            return response;
        } else {
        DesertEntity entity= DesertEntity.builder()
                .desertName (request.getDesertName())
                .build();
        desertRepository.save(entity);

        List<DesertMaterialEntity> liste=request.getMaterials();
        liste.stream().forEach(x->{
            DesertMaterialEntity materialEntity=new DesertMaterialEntity();
            materialEntity.setMaterialName(x.getMaterialName());
            materialEntity.setMaterialInfo(x.getMaterialInfo());
            materialEntity.setDesertId(entity.getId());
            desertMaterialRepository.save(materialEntity);
        });
        DoneResponse response=new DoneResponse("Yeni tatlı eklendi");
        return  response;
        }
    }
}
