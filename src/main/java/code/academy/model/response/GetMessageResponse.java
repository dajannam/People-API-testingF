package code.academy.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data


@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unsend")
@JsonIgnoreProperties(ignoreUnknown = true)

public class GetMessageResponse extends DefaultPeopleResponse {


}
