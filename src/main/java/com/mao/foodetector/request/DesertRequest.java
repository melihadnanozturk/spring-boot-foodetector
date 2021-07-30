package com.mao.foodetector.request;

import com.mao.foodetector.request.mtrequest.DesertMaterialRequest;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DesertRequest {

    @NotNull(message = "desertName cannot be null!!!")
    @Size(min = 5, max = 10, message = "desertName's size must be between to {min} and {max}!!!")
    private String desertName;

    @NotNull(message = "You must write least one Desert material!!!")
    private List<DesertMaterialRequest> materials;

}
