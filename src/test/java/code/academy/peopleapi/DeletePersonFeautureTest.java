package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.request.PostNewPersonRequest;
import code.academy.model.response.PostNewPersonResponse;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

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

    @BeforeClass
    public void beforeClass() throws Exception {

        HttpResponse postResponse = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person",
                objectToJsonString(postNewPersonPayload.createNewpersonPayload()));

        String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = jsonStringToObject(postResponseBodyAsString, PostNewPersonResponse.class);

        createdPersonId = postNewPersonResponse.getPersonData().getId();

    }

    @Test
    public void deletePersonTest() throws Exception {

        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + createdPersonId);
        String body = EntityUtils.toString(response.getEntity());

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
//        Assert.assertEquals(postNewPersonResponse.getCode(), "P200");
//        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person with id=" + createdPersonId + "has been successfully deleted");


    }

    @Test
    public void deletePersonIncorrectIdTest() throws Exception{

        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + incorrectID);
        String body = EntityUtils.toString(response.getEntity());

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
//        Assert.assertEquals(postNewPersonResponse.getCode(),"P404");
//        Assert.assertEquals(postNewPersonResponse.getMessage(),"Cannot delete Person because Id " + incorrectID + " is not existent");


    }

}