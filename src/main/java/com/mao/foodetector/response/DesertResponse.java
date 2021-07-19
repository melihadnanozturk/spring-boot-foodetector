package com.mao.foodetector.response;

import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.response.respoMtrl.DesertMaterialResponse;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesertResponse extends BaseResponse{

    private String DesertName;
    private List<DesertMaterialEntity> DesertMaterial ;
}
