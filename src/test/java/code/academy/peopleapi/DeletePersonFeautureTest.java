package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.request.PostNewPersonRequest;
import code.academy.model.response.DeleteResponse;
import code.academy.model.response.PostNewPersonResponse;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;


import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static code.academy.config.HostNameConfig.*;
import static code.academy.config.EndPointConfig.*;

import static code.academy.utils.TestDataUtils.ResponseMessage.*;
import static code.academy.utils.TestDataUtils.ResponseCode.*;

public class DeletePersonFeautureTest {

    public DeletePersonFeautureTest () throws Exception {
    }


    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();
    PostNewPersonResponse postNewPersonResponse = new PostNewPersonResponse();
    String createdPersonId;
    String incorrectID = "564a6dga45g646dg64v";
    DeleteResponse deleteResponse;

    @BeforeClass
    public void beforeClass() throws Exception {


        HttpResponse postResponse = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT,
                objectToJsonString(postNewPersonPayload.createNewpersonPayload()));

        String body = EntityUtils.toString(response.getEntity());
        PostNewPersonResponse bodyAsObject = jsonStringToObject(body,PostNewPersonResponse.class);
        String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = jsonStringToObject(postResponseBodyAsString, PostNewPersonResponse.class);

        createdPersonId = postNewPersonResponse.getPersonData().getId();

    }

    @Test
    public void deletePersonTest() throws Exception {

        response = peopleApiClient.httpDelete(HOSTNAME + PUT_ENDPOINT + createdPersonId);
        String body = EntityUtils.toString(response.getEntity());

        deleteResponse = jsonStringToObject(body,DeleteResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(deleteResponse.getCode(),OKAY);
        Assert.assertEquals( (deleteResponse.getMessage()),
                INCORRECT_ID_PART_1 + createdPersonId + SUCCESSFULLY_DELETED);


    }

    @Test
    public void deletePersonIncorrectIdTest() throws Exception{

        response = peopleApiClient.httpDelete(HOSTNAME + PUT_ENDPOINT + incorrectID);
        String body = EntityUtils.toString(response.getEntity());

        deleteResponse = jsonStringToObject(body,DeleteResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
        Assert.assertEquals(deleteResponse.getMessage(),
                INCORRECT_ID_PART_1 + incorrectID + INCORRECT_ID_PART_2);


    }

}