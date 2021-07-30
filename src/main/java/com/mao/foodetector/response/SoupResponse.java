package com.mao.foodetector.response;

import com.mao.foodetector.entity.material.SoupMaterialEntity;
import com.mao.foodetector.response.respoMtrl.SoupMaterialResponse;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoupResponse {

    private String soupName;
    private List<SoupMaterialResponse> materials;
}
