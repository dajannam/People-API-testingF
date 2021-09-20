package code.academy.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

public class ConversionUtils {
    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    public static String getResourcesAsString(String filePath) {
        BufferedReader bufferedReader = null;
        StringBuilder finalString = new StringBuilder();
        try {
            InputStream in = new FileInputStream(new File(filePath));
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String sCurrentLine;
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                finalString.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return finalString.toString();
    }

    public static <T> T jsonStringToObject(String objectAsJsonString, Class clazz) {
        try {
            return objectMapper.readValue(objectAsJsonString, objectMapper.constructType(clazz));
        } catch (Exception e) {
            throw new RuntimeException("Cannot serialize object to JSON.", e);
        }
    }

    public static String objectToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Cannot deserialize JSON.", e);
        }
    }



}
