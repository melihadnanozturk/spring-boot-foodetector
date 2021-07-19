package com.mao.foodetector.request;

import com.mao.foodetector.entity.material.DesertMaterialEntity;
import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.request.mtrequest.DesertMaterialRequest;
import com.mao.foodetector.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesertRequest extends BaseRequest {

    private String desertName;
    private List<DesertMaterialRequest> materials;

}
