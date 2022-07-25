package com.example.rqchallenge.config;

import com.example.rqchallenge.employees.model.Employee;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConvertEmployeeJsonToEmployee implements ResponseExtractor<Map<String, Object>> {
    public Map<String, Object> extractData(ClientHttpResponse response) throws IOException {
        String jsonResponse = response.getBody().toString();
        JSONParser parser = new JSONParser(jsonResponse);
        try {
            LinkedHashMap<String, Object> stringObjectLinkedHashMap = parser.parseObject();
            System.out.println(stringObjectLinkedHashMap);
            return stringObjectLinkedHashMap;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
