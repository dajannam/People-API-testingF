package code.academy.payloads;

import code.academy.model.request.PostNewPersonRequest;
import org.json.JSONObject;

public class PostNewPersonPayload {
    public PostNewPersonRequest createNewpersonPayload() {
        return PostNewPersonRequest.builder()
                .name("Emma")
                .surname("Watson")
                .age(30)
                .isEmployed(true)
                .location("Paris")
                .build();
    }

    public PostNewPersonRequest createUpdateLocationPayload(){
        return PostNewPersonRequest.builder()
                .location("Milano")
                .build();
    }

    public PostNewPersonRequest createUpdateEmptyLocationPayload(){
        return PostNewPersonRequest.builder()
                .location("")
                .build();

    }

    public JSONObject createNewPersonPayloadEmployeAsString(){

        JSONObject personObject = new JSONObject();
        personObject.put("name","Lara");
        personObject.put("surname","Stefanova");
        personObject.put("age",25);
        personObject.put("isEmployed","kako string");
        personObject.put("location","Struga");

        return personObject;
    }
}

