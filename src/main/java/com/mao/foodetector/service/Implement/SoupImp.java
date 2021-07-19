package com.mao.foodetector.service.Implement;

import com.mao.foodetector.entity.SoupEntity;
import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.exeptions.RegisterAddedBeforeThisException;
import com.mao.foodetector.exeptions.RegisterNotFoundException;
import com.mao.foodetector.repository.SoupRepository;
import com.mao.foodetector.repository.repoMtrl.SoupMaterialRepository;
import com.mao.foodetector.request.SoupRequest;
import com.mao.foodetector.response.BaseResponse;
import com.mao.foodetector.response.DoneResponse;
import com.mao.foodetector.response.SoupResponse;
import com.mao.foodetector.service.SoupService;
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
public class SoupImp implements SoupService {

    @Autowired
    private SoupRepository soupRepository;
    @Autowired
    private SoupMaterialRepository soupMaterialRepository;

    @Override
    public Iterable<SoupResponse> getAll() {
        List<SoupResponse> liste=new ArrayList<>();
        soupRepository.findAll().forEach(x->{
            SoupResponse response= SoupResponse.builder()
                    .soupName(x.getSoupName())
                    .build();
            liste.add(response);
        });
        return liste;
    }

    @Override
    public BaseResponse getOne(String soupName) {
        SoupEntity entity=soupRepository.findBySoupName(soupName).
                orElseThrow(()->new RegisterNotFoundException("Girilen isimde çorba bulunamadı!!!"));

        SoupResponse response=new SoupResponse();
        response.setSoupName(entity.getSoupName());
        response.setMaterials(entity.getMaterials());
        return  response;
    }

    @Override
    //geliştirilme yapılıcak :D
    public BaseResponse updateName(String  newname,String soupname) {
        SoupEntity entity=soupRepository.findBySoupName(soupname).
                orElseThrow(()->new RegisterNotFoundException("Girilen isimde çorba bulunamadı!!!"));

        entity.setSoupName(newname);
        soupRepository.save(entity);
        SoupResponse response=new SoupResponse();
        response.setSoupName(entity.getSoupName());

        return response;
    }

    @Override
    public BaseResponse delete(String soupName) {
       soupRepository.delete(soupRepository.findBySoupName(soupName)
               .orElseThrow(()->new RegisterNotFoundException("Girilen isimde çorba bulunamadı!!!")));
       DoneResponse response=new DoneResponse("Silme işlemi");
       return response;
    }

    @Override
    public BaseResponse newSoup(SoupRequest request) {

        if (kayitCekme(request.getSoupName()).isPresent()) {
            throw new RegisterAddedBeforeThisException("Bu isimde kayıt mevcut!!!", HttpStatus.BAD_REQUEST);
        }
            SoupEntity entity= SoupEntity.builder()
                    .soupName(request.getSoupName())
                    .build();
            soupRepository.save(entity);

            request.getMaterials().forEach(x->{
                SoupMaterialEntity materialEntity=new SoupMaterialEntity();
                materialEntity.setMaterialName(x.getMaterialName());
                materialEntity.setMaterialInfo(x.getMaterialInfo());
                materialEntity.setSoupId(entity.getId());
                soupMaterialRepository.save(materialEntity);
            });

            DoneResponse response=new DoneResponse("Çorba eklendi"+" *El ile kontrol ediniz!!!*");
            return response;

    }

    private Optional<SoupEntity> kayitCekme(String soupName) {
        return soupRepository.findBySoupName(soupName);
    }


}
