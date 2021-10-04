package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.request.PostNewPersonRequest;
import code.academy.model.response.PostNewPersonResponse;
import code.academy.model.response.PutNewLocationResponse;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.*;
import static code.academy.config.EndPointConfig.*;
import static code.academy.config.HostNameConfig.*;
import static code.academy.utils.TestDataUtils.ResponseMessage.*;
import static code.academy.utils.TestDataUtils.ResponseCode.*;

public class PutUpdatePersonLocationTest  {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    PostNewPersonPayload updateLocationPayload = new PostNewPersonPayload();
    PostNewPersonRequest updateLocationRequest = updateLocationPayload.createUpdateLocationPayload();
    PutNewLocationResponse putUpdateLocationResponse = new PutNewLocationResponse();

    String invalidId = "skuhff5498526dfvv";

    String createPersonID;

    public PutUpdatePersonLocationTest() throws Exception{

    }

    @BeforeClass
    public void beforeClass() throws Exception{

        PostNewPersonRequest createNewPersonRequest = updateLocationPayload.createNewpersonPayload();
        String requestBody = objectToJsonString(createNewPersonRequest);

        HttpResponse createPersonResponse = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, requestBody);
        Assert.assertEquals(createPersonResponse.getStatusLine().getStatusCode(), SC_CREATED);

        String responseBody = EntityUtils.toString(createPersonResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = jsonStringToObject(responseBody, PostNewPersonResponse.class);

        createPersonID = postNewPersonResponse.getPersonData().getId();

    }

    @Test
    public void updatePersonLocation() throws Exception{

        String requestBody = objectToJsonString(updateLocationRequest);

        response = peopleApiClient.httpPut(HOSTNAME + PUT_ENDPOINT + createPersonID, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutNewLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(putUpdateLocationResponse.getMessage(), UPDATE_LOCATION);
        Assert.assertEquals(putUpdateLocationResponse.getCode(),OKAY);
        Assert.assertEquals(putUpdateLocationResponse.getPerson().getLocation(), updateLocationRequest.getLocation());

    }

    @Test
    public void updateNonExistingPersonsLocation() throws Exception{

        String requestBody = objectToJsonString(updateLocationRequest);

        response = peopleApiClient.httpPut(HOSTNAME + PUT_ENDPOINT + invalidId, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutNewLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_NOT_FOUND);

        Assert.assertEquals(putUpdateLocationResponse.getMessage(), INCORRECT_ID_PART_1 + invalidId + INCORRECT_ID_PART_2);
    }

    @Test
    public void updatePersonsLocationSetAsNull() throws Exception{

        PostNewPersonRequest updateEmptyLocationRequest = updateLocationPayload.createUpdateEmptyLocationPayload();
        String requestBody = objectToJsonString(updateEmptyLocationRequest);

        response = peopleApiClient.httpPut(HOSTNAME + PUT_ENDPOINT + invalidId, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutNewLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putUpdateLocationResponse.getCode(),BADREQUEST);
        Assert.assertEquals(putUpdateLocationResponse.getMessage(),EMPTY_BODY);

    }

    @Test
    public void updatePersonsLocationEmptyRequest() throws Exception {
        PostNewPersonRequest updateEmptyLocationRequest = updateLocationPayload.createUpdateEmptyLocationPayload();
        updateEmptyLocationRequest.setLocation(null);
        String requestBody = objectToJsonString(updateEmptyLocationRequest);

        response = peopleApiClient.httpPut(HOSTNAME + PUT_ENDPOINT + invalidId, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutNewLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putUpdateLocationResponse.getCode(),BADREQUEST);
        Assert.assertEquals(putUpdateLocationResponse.getMessage(),EMPTY_BODY);

    }

    @AfterClass
    public void afterClass() throws Exception{

        HttpResponse deleteResponse = peopleApiClient.httpDelete(HOSTNAME + PUT_ENDPOINT + createPersonID);
        Assert.assertEquals(deleteResponse.getStatusLine().getStatusCode(), SC_OK);

    }
}


