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
import static code.academy.config.EndPointConfig.*;
import static code.academy.config.HostNameConfig.*;
import static code.academy.utils.TestDataUtils.ResponseMessage.*;
import static code.academy.utils.TestDataUtils.ResponseCode.*;


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
                (HOSTNAME + POST_ENDPOINT,objectToJsonString(postNewPersonPayload.createNewpersonPayload()));

        String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = ConversionUtils.jsonStringToObject(postResponseBodyAsString, PostNewPersonResponse.class);
        personId = postNewPersonResponse.getPersonData().getId();;
    }

    @Test
    public void correctIdTest() throws Exception{

        response = peopleApiClient.httpGet(HOSTNAME + POST_ENDPOINT + personId);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(postNewPersonResponse.getCode(),OKAY);
        Assert.assertEquals(postNewPersonResponse.getMessage(),PERSON_FETCHED);
    }

    @Test
    public void incorrectIdTest() throws Exception{

        String incorrectID = "564a6dga45g646dg64v";
        response = peopleApiClient.httpGet(HOSTNAME + PUT_ENDPOINT + incorrectID);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
        Assert.assertEquals(postNewPersonResponse.getCode(),NOTFOUND);
        Assert.assertEquals(postNewPersonResponse.getMessage(),
                INCORRECT_ID_PART_1 + incorrectID + INCORRECT_ID_PART_2);
    }

    @AfterClass
    public void afterClass() throws Exception{

        response = peopleApiClient.httpDelete(HOSTNAME + PUT_ENDPOINT + personId);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
    }
}
