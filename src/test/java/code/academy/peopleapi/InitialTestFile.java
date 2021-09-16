package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;



public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;


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

        JSONObject payloadAsObject = new JSONObject();
        payloadAsObject.put("name", "Pero");
        payloadAsObject.put("surname", "Blazevski");
        payloadAsObject.put("age", 56);
        payloadAsObject.put("isEmployed", true);
        payloadAsObject.put("location", "Skopje");

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", payloadAsObject);
        String body = EntityUtils.toString(response.getEntity());

        JSONObject bodyAsObject = new JSONObject(body);

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(messageAsString, expectedMessage);

    }

    @Test
    public void updatePersonLocationTest() throws Exception {

        String expectedMessage ="Person's location succesfully updated !";

        JSONObject payloadAsObject = new JSONObject();
        payloadAsObject.put("location", "London");

        response = peopleApiClient.httpPut("https://people-api1.herokuapp.com/api/person/613de5c9dd85560004b265ae",
                payloadAsObject);

        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(messageAsString, expectedMessage);

    }
}

