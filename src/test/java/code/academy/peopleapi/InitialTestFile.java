package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import code.academy.model.request.PostNewPersonRequest;
import code.academy.model.request.UpdatePersonLocationRequest;
import code.academy.model.response.PostNewPersonResponse;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static code.academy.utils.ConversionUtils.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;


public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();

    public InitialTestFile() throws Exception {
    }

    @Test

    public void welcomeMessagePeopleApiTest() throws Exception {
        String expectedMessage = "Welcome to People API";
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/");

        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(messageAsString, expectedMessage);
    }

    @Test

    public void getSinglePersonTest() throws Exception {
        String expectedMessage = "Person succesfully fetched";

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/person/612ba20357744c30dc7e6fe7");

        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);

        String personDataAsString = bodyAsObject.get("person").toString();
        JSONObject personData = new JSONObject(personDataAsString);

        String name = personData.get("name").toString();

        Assert.assertEquals(name, "Stefan");


    }
    @Test
    public void getAllPeopleTest() throws Exception {
        String expectedMessage = "List of people successfully fetched";

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(response.getEntity());

        JSONObject bodyAsObject = new JSONObject(body);

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(messageAsString, expectedMessage);

    }
    @Test
    public void postPersonTest() throws Exception {
        String expectedMessage ="Person succesfully inserted";

        postNewPersonRequest = postNewPersonPayload.createNewpersonPayload();

        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        PostNewPersonResponse postNewPersonResponse;
        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P201");
        Assert.assertEquals(postNewPersonResponse.getPersonData().getName(), "Emma");

    }

    @Test
    public void updatePersonLocationTest() throws Exception {

        UpdatePersonLocationRequest updatePersonLocationRequest = UpdatePersonLocationRequest.builder()
                .location("New York")
                .build();
        String updateLocationAsString = objectToJsonString(updatePersonLocationRequest);
        response = peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/61489d13498c2b0004aaf464",updateLocationAsString);

        String body = EntityUtils.toString(response.getEntity());
        PostNewPersonResponse updateLocationResponse;
        updateLocationResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);


    }

    @Test
    public void deletePersonTest() throws Exception {
        HttpResponse postResponse = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person",
                objectToJsonString(postNewPersonPayload.createNewpersonPayload()));

        String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = jsonStringToObject(postResponseBodyAsString, PostNewPersonResponse.class);

        String createdPersonId = postNewPersonResponse.getPersonData().getId();

        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + createdPersonId);

        String body = EntityUtils.toString(response.getEntity());
    }
    }

