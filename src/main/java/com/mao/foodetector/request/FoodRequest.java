package com.mao.foodetector.request;

import com.mao.foodetector.request.mtrequest.FoodMaterialRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {

    @NotNull(message = "foodName cannot be null!!!")
    @Size(min = 3, max = 20, message = "foodname's size must be between to {min} and {max}")
    private String foodName;

    @NotNull(message = "You must write least one Food material!!!")
    private List<FoodMaterialRequest> materials;
}
