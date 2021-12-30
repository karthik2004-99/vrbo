package com.assignment.connections;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.nio.charset.StandardCharsets;


import static com.assignment.consts.Constants.*;

@Slf4j
@Service
public class RequestAndResponse {

    public StringBuilder sendPostRequest(String location) throws IOException {

        String requestString= requestStringPart1+location+requestStringPart2;
        URL url = new URL(vrboListingEndpoint);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);

        try(OutputStream os = con.getOutputStream()) {
            os.write(requestString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            log.error("Error Sending Post Request....");
            log.error(e.getMessage());

        }
        StringBuilder response;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        return response;


    }



}

