package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.response.GetAllPeopleResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;


import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static org.apache.http.HttpStatus.*;
import static code.academy.config.HostNameConfig.*;
import static code.academy.config.EndPointConfig.*;
import static code.academy.utils.TestDataUtils.ResponseCode.*;
import static code.academy.utils.TestDataUtils.ResponseMessage.*;

public class GetAllPeopleTest {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    GetAllPeopleResponse getAllPeopleResponse;

    public GetAllPeopleTest() throws Exception {

    }

    @Test
    public void GetAllPerson() throws Exception{

        response = peopleApiClient.httpGet(HOSTNAME + GET_ALL_ENDPOINT);
        String body = EntityUtils.toString(response.getEntity());
        getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getMessage(), LIST_OF_PEOPLE);
        Assert.assertEquals(getAllPeopleResponse.getCode(), OKAY);
        Assert.assertNotNull(getAllPeopleResponse.getNumberOfPeople());
        Assert.assertNotNull(getAllPeopleResponse.getPeopleData().size());


    }

    @Test
    public void getAllPeopleNumberOfPeopleListCounterFieldTest() throws Exception{

        response = peopleApiClient.httpGet(HOSTNAME + GET_ALL_ENDPOINT);
        String body = EntityUtils.toString(response.getEntity());
        getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getPeopleData().size(), getAllPeopleResponse.getNumberOfPeople());
    }

}
