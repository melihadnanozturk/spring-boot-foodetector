package com.mao.foodetector.response;

import com.mao.foodetector.entity.material.SoupMaterialEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoupResponse extends BaseResponse{

    private String soupName;
    private List<SoupMaterialEntity> materials;
}
