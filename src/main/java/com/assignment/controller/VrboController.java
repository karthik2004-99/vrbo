package com.assignment.controller;

import com.assignment.connections.RequestAndResponse;
import com.assignment.exception.RadiusLowException;
import com.assignment.model.ListingData;
import com.assignment.service.CSVService;
import com.assignment.service.VrboService;
import com.assignment.utils.Parser;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@Slf4j
@RestController
public class VrboController {

    Parser parser = new Parser();
    CSVService csvService = new CSVService();
    VrboService service = new VrboService();


    ArrayList<ListingData> preLoadingData(String location, String radius) throws ParseException, IOException, RadiusLowException {
        RequestAndResponse rr = new RequestAndResponse();
        StringBuilder response=null;
        response = rr.sendPostRequest(location);
        JSONObject fullResponse = parser.getJSON(response.toString());
        JSONObject dataObject = Parser.getJSONObject(fullResponse,"data");
        JSONObject resultObject = Parser.getJSONObject(dataObject, "results");
        ArrayList<ListingData> listings=service.getClosestListingsService(resultObject,radius);
        return listings;
    }
    @GetMapping(path = "/vrbo/getListingsClosest")
    void getClosestListings(@RequestParam("location") String location, @RequestParam("radius") String radius, HttpServletResponse httpResponse) throws IOException, ParseException, RadiusLowException {

        log.info("Getting the Listing Data from VRBO....");
        ArrayList<ListingData> listings= preLoadingData(location,radius);
        log.info("Data Received from VRBO....");
        httpResponse.setHeader("Content-Disposition", "attachment;filename=closest50.csv");
        httpResponse.setHeader("Content-Type", "text/csv");
        OutputStream outStream = httpResponse.getOutputStream();
        csvService.createListing50CSV(listings,outStream);
        try {
            outStream.flush();
            outStream.close();
            httpResponse.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write the data to excel " + e.getMessage(), e);
        }
    }
    @GetMapping(path="/vrbo/getTop3PriceDate")
    void getHighestPriceandDate(@RequestParam("location") String location, @RequestParam("radius") String radius, HttpServletResponse httpResponse) throws ParseException, IOException, RadiusLowException {
        log.info("Getting 3 Highest Price and Data from VRBO.....");
        ArrayList<ListingData> listings= preLoadingData(location,radius);
        log.info("Received Data from VRBO.....");
        httpResponse.setHeader("Content-Disposition", "attachment;filename=listing-with3highest.csv");
        httpResponse.setHeader("Content-Type", "text/csv");
        OutputStream outStream = httpResponse.getOutputStream();
        csvService.createListingWithHighestPD(listings,outStream);
        try {
            outStream.flush();
            outStream.close();
            httpResponse.flushBuffer();
        } catch (IOException e) {


            throw new RuntimeException("Unable to write the data to excel " + e.getMessage(), e);
        }



    }

}
