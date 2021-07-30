package com.mao.foodetector.request;

import com.mao.foodetector.request.mtrequest.SoupMaterialRequest;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoupRequest {

    @NotNull(message = "soupName connot be null")
    @Size(min = 3, max = 20, message = "soupName's size must be between to {min} and {max}")
    private String soupName;

    @NotNull(message = "You must write least one Soup material!!!")
    private List<SoupMaterialRequest> materials;

}
