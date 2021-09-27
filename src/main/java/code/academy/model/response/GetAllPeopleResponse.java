package code.academy.model.response;

import code.academy.model.PersonData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unsend")
@JsonIgnoreProperties(ignoreUnknown = true)

public class GetAllPeopleResponse {

    private String code;
    private String message;
    private List<PersonData> peopleData;
    private int numberOfPeople;

}
