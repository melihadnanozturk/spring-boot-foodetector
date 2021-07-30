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
import com.mao.foodetector.service.SoupService;
import org.springframework.beans.factory.annotation.Autowired;
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
    //geliştirilme yapılıcak :D
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
        SoupEntity entity = soupRepository.findBySoupName(soupName).get();
        if (entity != null) {
            entity.getMaterials().forEach(x -> {
                soupMaterialRepository.delete(x);
            });
            soupRepository.delete(entity);
            DoneResponse response = new DoneResponse("Silme işlemi gerçekleşti lütfen el ile kontrol et !!!");
            return response;
        }
        throw new RegisterNotFoundException("Girilen isimde çorba bulunamadoı!!!");
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
