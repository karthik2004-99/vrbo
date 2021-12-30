package com.assignment.service;

import com.assignment.exception.RadiusLowException;
import com.assignment.model.ListingData;
import com.assignment.utils.InsideRadius;
import com.assignment.utils.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VrboService implements VrboServiceInterface {

    Parser parser = new Parser();

    VrboScraperService scraper = new VrboScraperService();


    private void setListingDataWithValues(ListingData ld, JSONObject listingJson)  {
        JSONObject propertyMetadata = Parser.getJSONObject(listingJson,"propertyMetadata");
        String listingId = Parser.getJSONString(listingJson,"listingId");
        String detailedURL = Parser.getJSONString(listingJson,"detailPageUrl");
        List<Long> perListYearlyRate =null;
        List<Long> listingTopThree=null;
        List<String> topThreeDates=null;
        JSONArray rentNightsArray = scraper.scrapeRates(detailedURL);
        if(rentNightsArray!=null) {
            if (!(rentNightsArray.size() == 1) && !(rentNightsArray.get(0) == null)) {
                perListYearlyRate = scraper.scrapeGetListingRates(rentNightsArray);

                listingTopThree = scraper.scrapeGetTopThree(perListYearlyRate);
                topThreeDates = scraper.generateTopThreeDates(perListYearlyRate, listingTopThree);
                //topThreeDates.sort(Collections.reverseOrder());
            }
        }

        ld.setListingName((String) propertyMetadata.get("headline"));
        ld.setListingId(listingId);
        ld.setDetailedListingUrl(detailedURL);
        ld.setPrices(perListYearlyRate);
        ld.setTopThreeDates(topThreeDates);
        ld.setHighestPrices(listingTopThree);


    }

    @Override
    public ArrayList<ListingData> getClosestListingsService(JSONObject resultObject, String rad) throws RadiusLowException {
        JSONObject geography = Parser.getJSONObject(resultObject,"geography");
        JSONObject location =  Parser.getJSONObject(geography,"location");
        double SearchLocationLat = (double) location.get("latitude");
        double SearchLocationLong = (double) location.get("longitude");

        double radius = Double.parseDouble(rad);
        ArrayList<ListingData> listings = new ArrayList<>();

        JSONArray childListingsJsonArray = parser.getJSONArray(resultObject,"listings");
        for (Object o: childListingsJsonArray){
            ListingData ld = new ListingData();
            JSONObject listingJson = (JSONObject) o;
            JSONObject listingGeoCode = parser.getJSONObject(listingJson,"geoCode");
            double listingLocationLat  = (double) listingGeoCode.get("latitude");
            double listingLocationLong = (double) listingGeoCode.get("longitude");
            if (InsideRadius.getDistance(SearchLocationLat,SearchLocationLong,listingLocationLat,listingLocationLong) <= radius){
                setListingDataWithValues(ld,listingJson);
                listings.add(ld);
            }
        }
        if (listings.size() == 0){
            throw new RadiusLowException("Radius is too Low. Increase the Radius to find more listings");
        }


        /*for (ListingData listing : listings) {
            System.out.println(listing.toString());
        }
        System.out.println(listings.size());*/

        return listings;
    }


}
