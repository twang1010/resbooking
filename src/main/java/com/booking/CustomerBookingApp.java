package com.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerBookingApp {

    private static final String API_URL = "http://localhost:8080/api/bookings"; // Assuming the API is running locally

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        //this is no more needed
        //objectMapper.registerModule(new JavaTimeModule());

        try (BufferedReader reader = new BufferedReader(new FileReader("src/resources/bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Booking booking = objectMapper.readValue(line, Booking.class);
                makeBooking(booking);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeBooking(Booking booking) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create HTTP POST request
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");

            // Set request body
            StringEntity stringEntity = new StringEntity(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(booking), ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            // Execute request and get response
            HttpResponse response = httpClient.execute(httpPost);

            // Process response
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println("Response from server: " + responseString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
