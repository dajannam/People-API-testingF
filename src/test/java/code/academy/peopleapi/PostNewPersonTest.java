package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.request.PostNewPersonRequest;
import code.academy.model.response.PostNewPersonResponse;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;


import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.*;
import static code.academy.config.HostNameConfig.*;
import static code.academy.config.EndPointConfig.*;
import static code.academy.utils.TestDataUtils.ResponseCode.*;
import static code.academy.utils.TestDataUtils.ResponseMessage.*;

public class PostNewPersonTest {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();
    PostNewPersonResponse postNewPersonResponse = new PostNewPersonResponse();
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    String personOneId;
    String personTwoId;
    String personThreeID;

    public PostNewPersonTest() throws Exception {

    }

    @Test
    public void PostPerson() throws Exception {

        postNewPersonRequest = postNewPersonPayload.createNewpersonPayload();
        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, newPersonPayloadAsString);


        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), CREATED);
        Assert.assertEquals(postNewPersonResponse.getMessage(), PERSON_SUCCESSFULLY_INSERTED);

        Assert.assertEquals(postNewPersonResponse.getPersonData().getName(), postNewPersonRequest.getName());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getSurname(), postNewPersonRequest.getSurname());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getAge(), postNewPersonRequest.getAge());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getIsEmployed(), postNewPersonRequest.getIsEmployed());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getLocation(), postNewPersonRequest.getLocation());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getLocation(), postNewPersonRequest.getLocation());

        personOneId = postNewPersonResponse.getPersonData().getId();

    }

    @Test
    public void PostPersonWithoutAge() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewpersonPayload();
        postNewPersonRequest.setAge(null);
        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), CREATED);
        Assert.assertEquals(postNewPersonResponse.getMessage(), PERSON_SUCCESSFULLY_INSERTED);
        Assert.assertNull(postNewPersonResponse.getPersonData().getAge());

        personTwoId = postNewPersonResponse.getPersonData().getId();
    }

    @Test
    public void PostPersonWithoutLocation() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewpersonPayload();
        postNewPersonRequest.setLocation(null);
        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), CREATED);
        Assert.assertEquals(postNewPersonResponse.getMessage(), PERSON_SUCCESSFULLY_INSERTED);
        Assert.assertNull(postNewPersonResponse.getPersonData().getLocation());

        personThreeID = postNewPersonResponse.getPersonData().getId();
    }

    @Test
    public void PostPersonWithoutSurname() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewpersonPayload();
        postNewPersonRequest.setSurname(null);
        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(), BADREQUEST);
        Assert.assertEquals(postNewPersonResponse.getMessage(), NO_SURNAME);

    }

    @Test
    public void PostPersonWithoutIsEmploye() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewpersonPayload();
        postNewPersonRequest.setIsEmployed(null);
        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(),BADREQUEST );
        Assert.assertEquals(postNewPersonResponse.getMessage(), EMPTY_EMPLOYED_FIELD);

    }

    @Test
    public void PostPersonEmployeAsString() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject = postNewPersonPayload.createNewPersonPayloadEmployeAsString();


        response = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, jsonObject.toString());

        String body = EntityUtils.toString(response.getEntity());

        JSONObject bodyAsObject = new JSONObject(body);
        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_INTERNAL_SERVER_ERROR);
        String expectedMessage = "Person validation failed:";
        Boolean passes = messageAsString.contains(expectedMessage);
        Assert.assertTrue(passes);

    }

    @AfterClass
    public void afterClass() throws Exception {
        peopleApiClient.httpDelete(HOSTNAME + PUT_ENDPOINT + personOneId);
        peopleApiClient.httpDelete(HOSTNAME + PUT_ENDPOINT + personTwoId);
        peopleApiClient.httpDelete(HOSTNAME + PUT_ENDPOINT + personThreeID);

    }
}


