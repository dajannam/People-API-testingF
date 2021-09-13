package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;



public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    HttpResponse getPeople;
    HttpResponse getOnePerson;

    @Test
    public void initialTest() throws Exception {

        response = peopleApiClient.getWelcomeRequest();
        getPeople = peopleApiClient.getPeople();
        getOnePerson = peopleApiClient.getOnePerson();


        String body = EntityUtils.toString(response.getEntity());
        String bodyAllPeople = EntityUtils.toString(getPeople.getEntity());
        String bodyOfOnePerson = EntityUtils.toString(getOnePerson.getEntity());

    }
    }
