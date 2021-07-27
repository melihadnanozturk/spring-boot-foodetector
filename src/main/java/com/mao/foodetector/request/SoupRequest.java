package com.mao.foodetector.request;

import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.request.mtrequest.SoupMaterialRequest;
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
public class SoupRequest extends BaseRequest{

    private String soupName;
    private List<SoupMaterialRequest> materials;

}
