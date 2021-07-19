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
import com.mao.foodetector.service.DesertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/*bu yorum silinmediyse problem şu:
 silinen yemeğin malzemeleri yemek silindiğindehalen silinmiyor,
 material repository' yi service implemente edip çağırmak daha manıtklı
*/
public class DesertImp implements DesertService {

    @Autowired
    private DesertRepository desertRepository;
    @Autowired
    private DesertMaterialRepository desertMaterialRepository;


    @Override
    public Iterable<DesertResponse> getAll() {
        List<DesertResponse>liste=new ArrayList<>();
     desertRepository.findAll().forEach(x->{
         DesertResponse response=new DesertResponse();
         response.setDesertName(x.getDesertName());
         liste.add(response);
     });
        return liste;
    }


    @Override
    public DesertResponse getOne(String desertName) {
          DesertEntity entity= desertRepository.findByDesertName(desertName).
                  orElseThrow(()->new RegisterNotFoundException("Girilen isimde tatlı bulunamadı!!!"));

          DesertResponse response=new DesertResponse();
          response.setDesertName(entity.getDesertName());
          response.setDesertMaterial(entity.getMaterials());
          return  response;
    }

    @Override
    public DesertResponse updateName(String desertName, String newName) {
        DesertEntity entity= desertRepository.findByDesertName(desertName).
                orElseThrow(()->new RegisterNotFoundException("Girilen isimde tatlı bulunamadı!!!"));
        entity.setDesertName(newName);
        desertRepository.save(entity);
        DesertResponse response=new DesertResponse();
        response.setDesertName(entity.getDesertName());
        return response;
    }

    @Override
    public DoneResponse delete(String desertName) {
        desertRepository.delete(desertRepository.findByDesertName(desertName)
                .orElseThrow(()->new RegisterNotFoundException("Girilen isimde tatlı bulunamadı!!!")));
        DoneResponse response=new DoneResponse("Silme işlemi");
        return response;
    }

    @Override
    //bu yazı silinmediyse iyileştirme daha yapılmamıştır
    public DesertResponse newDesert(DesertRequest request) {
        if (kayıtCekme(request.getDesertName()).isPresent()){
            throw  new RegisterAddedBeforeThisException("Bu isimden zaten kayıt mevcut!!!",HttpStatus.BAD_REQUEST);
        }
            DesertEntity entity=new DesertEntity();
            entity.setDesertName(request.getDesertName());
            //entity.setMaterials(request.getMaterials());
            desertRepository.save(entity);

            request.getMaterials().forEach(x->{
                DesertMaterialEntity materialEntity=new DesertMaterialEntity();
                materialEntity.setMaterialName(x.getMaterialName());
                materialEntity.setMaterialInfo(x.getMaterialInfo());
                materialEntity.setDesertId(entity.getId());
                desertMaterialRepository.save(materialEntity);
            });
            DesertResponse response=new DesertResponse();
            response.setDesertName(entity.getDesertName());
            response.setDesertMaterial(entity.getMaterials());
            return response;
    }

    public Optional<DesertEntity> kayıtCekme(String desertName){
        return desertRepository.findByDesertName(desertName);
    }
}

