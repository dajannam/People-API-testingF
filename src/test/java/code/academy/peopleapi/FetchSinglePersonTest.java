package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.response.PostNewPersonResponse;
import code.academy.payloads.PostNewPersonPayload;
import code.academy.utils.ConversionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;


public class FetchSinglePersonTest {

    HttpResponse response;
    PeopleApiClient peopleApiClient = new PeopleApiClient();
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    PostNewPersonResponse postNewPersonResponse;
    String personId;
    String body;

    public FetchSinglePersonTest() throws Exception{

    }

    @BeforeClass
    public void beforeClass() throws Exception{

        HttpResponse postResponse = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person",objectToJsonString(postNewPersonPayload.createNewpersonPayload()));

        String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = ConversionUtils.jsonStringToObject(postResponseBodyAsString, PostNewPersonResponse.class);
        personId = postNewPersonResponse.getPersonData().getId();;
    }

    @Test
    public void correctIdTest() throws Exception{

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/person/" + personId);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(postNewPersonResponse.getCode(),"P200");
        Assert.assertEquals(postNewPersonResponse.getMessage(),"Person succesfully fetched");
    }

    @Test
    public void incorrectIdTest() throws Exception{

        String incorrectID = "564a6dga45g646dg64v";
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/person/" + incorrectID);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
        Assert.assertEquals(postNewPersonResponse.getCode(),"P404");
        Assert.assertEquals(postNewPersonResponse.getMessage(),"Person with id " + incorrectID +" not found");
    }

    @AfterClass
    public void afterClass() throws Exception{

        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + personId);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
    }
}
