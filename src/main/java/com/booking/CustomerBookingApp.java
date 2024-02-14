package com.booking;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomerBookingApp {

    public static void main(String[] args) {
        String apiUrl = "http://localhost:8088/bookings"; // Assuming the API is running locally

        // Sample booking data
        String customerName = "Customer 1";
        int tableSize = 4;
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1); // Book for tomorrow

        // Prepare JSON request body
        String requestBody = String.format("{\"customerName\": \"%s\", \"tableSize\": %d, \"dateTime\": \"%s\"}",
                customerName, tableSize, dateTime);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create HTTP POST request
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader("Content-Type", "application/json");

            // Set request body
            StringEntity stringEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
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
