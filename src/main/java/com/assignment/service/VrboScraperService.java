package com.assignment.service;

import com.assignment.utils.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.assignment.consts.Constants.vrboScrapingEndpoint;

public class VrboScraperService {

        Parser parser = new Parser();

        public JSONArray scrapeRates(String listingURL){
                String FULL_URL= vrboScrapingEndpoint +listingURL;
                JSONArray rentNightsArray;
                try {
                        Document document = Jsoup.parse(new URL(FULL_URL), 30000);
                        Elements listingPage = document.getElementsByTag("script");
                        String fullString = "";
                        for (Element e : listingPage) {
                                if (e.toString().contains("window.__INITIAL_STATE__ = {\"router\":{\"location\":{\"pathname\"")) {
                                        fullString = e.toString();
                                }
                        }
                        String[] resultSplit = fullString.split("window.__INITIAL_STATE__ =");
                        String[] furtherSplit = resultSplit[1].split("window.__REQUEST_STATE__ = ");
                        //Removing the semicolon
                        StringBuffer sb = new StringBuffer(furtherSplit[0].trim());
                        sb.deleteCharAt(sb.length() - 1);

                        JSONObject obj =  parser.getJSON(sb.toString());
                        JSONObject childDataObject = parser.getJSONObject(obj,"listingReducer");
                        JSONObject childResultObject = parser.getJSONObject(childDataObject,"rateSummary");
                        rentNightsArray = parser.getJSONArray(childResultObject,"rentNights");
                } catch (Exception e){
                        System.out.println(e.getMessage());
                        rentNightsArray = null;
                }
                return rentNightsArray;
        }

        public List<Long> scrapeGetListingRates(JSONArray rentNightsArray){
                ArrayList<Long> listingRates = new ArrayList<>();
                if(rentNightsArray!=null){

                        for (int i = 0; i < 365; i++) {
                                listingRates.add((Long) rentNightsArray.get(i));
                        }
                }else{
                        listingRates.add(null);
                }
                return listingRates;

        }

        public List<Long> scrapeGetTopThree(List<Long> perYearNightRate){

                List<Long> topThreeRates = new ArrayList<>();
                if(perYearNightRate.size()!=1 && perYearNightRate.get(0)!=null) {
                        TreeSet<Long> count = new TreeSet<>(perYearNightRate);
                        Set<Long> storeCount = count.descendingSet();
                        Iterator<Long> itr = storeCount.iterator();
                        for (int i = 0; i < 3; i++) {
                                try {
                                        topThreeRates.add(itr.next());
                                } catch (Exception e) {
                                        topThreeRates.add(null);
                                }
                        }
                }else{
                        for (int i = 0; i < 3; i++) {
                                topThreeRates.add(null);
                        }
                }
                return topThreeRates;

        }
        public List<String> generateTopThreeDates(List<Long> perListYearlyRate,List<Long> listingTopThree){
                List<String> topThreeDates = new ArrayList<>();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");


                /*System.out.println("Printing Top 3 rates");
                for (Long aLong : listingTopThree) {
                        System.out.println(aLong);
                }
                System.out.println("Printing all rates");
                for (Long aLong : perListYearlyRate) {
                        System.out.print(aLong + " ");
                }*/

                ArrayList<Long> al = new ArrayList<>();
                for(Long price: listingTopThree){
                        if(price!=null) {

                                for (int i = 0; i < perListYearlyRate.size(); i++) {
                                        if ( price.equals(perListYearlyRate.get(i)) && !(al.contains(perListYearlyRate.get(i)))) {
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(new Date());
                                                calendar.add(Calendar.DATE, i);

                                                topThreeDates.add(formatter.format(calendar.getTime()));
                                                al.add(perListYearlyRate.get(i));
                                                break;

                                        }
                                }
                        }
                }
                int count= al.size();
                while (count!=3){
                        topThreeDates.add("null");
                        count+=1;
                }

                return topThreeDates;


        }





}
