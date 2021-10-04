package code.academy.utils;

public class TestDataUtils {

    public static class ResponseMessage {

        public static final String PERSON_SUCCESSFULLY_INSERTED = "Person ssfully inserted";
        public static final String NO_NAME = "Person's name cannot be empty";
        public static final String NO_SURNAME = "Person's surname cannot be empty";
        public static final String EMPTY_EMPLOYED_FIELD = "Person must provide if he is employed or not";
        public static final String UPDATE_LOCATION = "Person's location successfully updated !";
        public static final String INCORRECT_ID_PART_1 = "Person with id=";
        public static final String INCORRECT_ID_PART_2 = " not found";
        public static final String EMPTY_BODY = "Request body cannot be empty";
        public static final String PERSON_FETCHED = "Person successfully fetched";
        public static final String LIST_OF_PEOPLE = "List of people successfully fetched";
        public static final String SUCCESSFULLY_DELETED = " has been successfully deleted";
    }

    public static class ResponseCode {

        public static final String OKAY = "P200";
        public static final String CREATED = "P201";
        public static final String BADREQUEST = "P400";
        public static final String NOTFOUND = "P404";
    }
}