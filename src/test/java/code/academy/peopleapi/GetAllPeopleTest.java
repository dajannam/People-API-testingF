package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.response.GetAllPeopleResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;


import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.*;

public class GetAllPeopleTest {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    GetAllPeopleResponse getAllPeopleResponse;

    public GetAllPeopleTest() throws Exception {

    }

    @Test
    public void GetAllPerson() throws Exception{

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(response.getEntity());
        getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getCode(), "P200");
        Assert.assertEquals(getAllPeopleResponse.getMessage(), "List of people successfully fetched");
        Assert.assertNotNull(getAllPeopleResponse.getNumberOfPeople());
        Assert.assertNotNull(getAllPeopleResponse.getPeopleData().size());


    }

    @Test
    public void getAllPeopleNumberOfPeopleListCounterFieldTest() throws Exception{

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(response.getEntity());
        getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getPeopleData().size(), getAllPeopleResponse.getNumberOfPeople());
    }

}
