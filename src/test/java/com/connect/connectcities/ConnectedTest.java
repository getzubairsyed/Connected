package com.connect.connectcities;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ConnectedTest {


    @Autowired
    private TestRestTemplate testTemplate;
    @Value("${city1}")
    private String city1;
    @Value("${city2}")
    private String city2;
    @Value("${city3}")
    private String city3;
    @Value("${city4}")
    private String city4;


    @Test
    public void connectedYes() {

        String uri = "/findConnectedCities?city1=" + city1 + "&city2=" + city2;

        String response = testTemplate.postForObject(uri, HttpMethod.POST, String.class);
        boolean dataAvail=false;
        if (response != null && response.equalsIgnoreCase("Yes")) {
            dataAvail = true;
            assertEquals(true, dataAvail);

        }
    }

    @Test
    public void connectedNo() {

        String uri = "/findConnectedCities?city1=" + city3 + "&city2=" + city4;

        String response = testTemplate.postForObject(uri, HttpMethod.POST, String.class);
        boolean dataAvail=false;
        if (response != null && response.equalsIgnoreCase("No")) {
            dataAvail = true;
            assertEquals(true, dataAvail);
        }
    }
}
