package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;



public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;

    @Test
    public void initialTest() throws Exception {

        response = peopleApiClient.getWelcomeRequest();

        String body = EntityUtils.toString(response.getEntity());

    }
}
