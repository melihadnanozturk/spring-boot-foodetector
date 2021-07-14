package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.SoupEntity;
import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.repository.SoupRepository;
import com.mao.foodetector.repository.repoMtrl.SoupMaterialRepository;
import com.mao.foodetector.request.SoupRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.SoupResponse;
import com.mao.foodetector.service.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoupImp implements SoupService {

    @Autowired
    private SoupRepository soupRepository;
    @Autowired
    private SoupMaterialRepository soupMaterialRepository;

    @Override
    public Iterable<SoupResponse> getAll() {
        List<SoupResponse> liste=new ArrayList<>();
        soupRepository.findAll().stream().forEach(x->{
            SoupResponse response= SoupResponse.builder()
                    .soupName(x.getSoupName())
                    .build();
            liste.add(response);
        });
        return liste;
    }

    @Override
    public BaseResponse getOne(String soupName) {
        if (soupRepository.findBySoupName(soupName) == null) {
            DoneResponse response = new DoneResponse("Girilen bilgiden mevcut tatlı yoktur!!!");
            return response;
        } else {
            SoupEntity entity = soupRepository.findBySoupName(soupName);
            SoupResponse response = new SoupResponse(
                    entity.getSoupName(),
                    entity.getMaterials());
            return response;
        }
    }

    @Override
    //geliştirilme yapılıcak :D
    public BaseResponse updateName(String  newname,String soupname) {
        if (soupRepository.findBySoupName(soupname) != null) {
            SoupEntity entity = soupRepository.findBySoupName(soupname);
            entity.setSoupName(newname);
            soupRepository.save(entity);
            SoupResponse response = SoupResponse.builder()
                    .soupName(entity.getSoupName())
                    .build();

            return response;

        } else {
            DoneResponse response = new DoneResponse("Geçerli bilgide güncellenicek veri yok!!!");
            return response;
        }
    }

    @Override
    public BaseResponse delete(String soupName) {
        if (soupRepository.findBySoupName(soupName) != null) {

            soupRepository.delete(soupRepository.findBySoupName(soupName));
            DoneResponse response=new DoneResponse("Silme işlemi gerçekleşti. Lütfen silindiğine dair kontrolü el ile yapınız!");
            return response;

        } else {
            DoneResponse response = new DoneResponse("Girilen bilgide mevcut kayıt yoktur!!");
            return response;
        }
    }

    @Override
    public BaseResponse newSoup(SoupRequest request) {

        if (soupRepository.findBySoupName(request.getSoupName()) != null) {
            DoneResponse response = new DoneResponse("Aynı isimde kayıt var lütfen yemeğin adını değiştirin!!!");
            return response;
        } else {
            SoupEntity entity= SoupEntity.builder()
                    .soupName(request.getSoupName())
                    .build();
            soupRepository.save(entity);

            List<SoupMaterialEntity>liste=request.getMaterials();
            liste.stream().forEach(x->{
                SoupMaterialEntity materialEntity=new SoupMaterialEntity();
                materialEntity.setMaterialName(x.getMaterialName());
                materialEntity.setMaterialInfo(x.getMaterialInfo());
                materialEntity.setSoupId(entity.getId());
                soupMaterialRepository.save(materialEntity);
            });

            DoneResponse response=new DoneResponse("Çorba eklendi");
            return response;
        }
    }
}
