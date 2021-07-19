package com.mao.foodetector.response.respoMtrl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesertMaterialResponse {
    private String materialName;
    private String materialInfo;
    private Integer desertId;
}
