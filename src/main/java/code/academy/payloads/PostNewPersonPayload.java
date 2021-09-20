package code.academy.payloads;

import code.academy.model.request.PostNewPersonRequest;

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
}

