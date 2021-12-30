package com.assignment.service;

import com.assignment.model.ListingData;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CSVService {

        public void createListing50CSV(ArrayList<ListingData> listings, OutputStream outStream) throws IOException {
                String rowheads="listingName,listingId,detailedListingUrl";
                StringBuilder yearDates = new StringBuilder();

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                String dateNext = formatter.format(calendar.getTime());
                for(int i=0; i<365; i++) {
                        yearDates.append(",").append(dateNext);
                        calendar.add(Calendar.DATE, 1);
                        dateNext = formatter.format(calendar.getTime());
                }
                rowheads+=yearDates+"\n";
                outStream.write(rowheads.getBytes());

                for (ListingData listing : listings) {
                        StringBuilder row= new StringBuilder();
                        String listingName = listing.getListingName();
                        listingName = listingName.replace(","," ");
                        row.append(listingName).append(",");
                        row.append(listing.getListingId()).append(",");
                        row.append(listing.getDetailedListingUrl()).append(",");
                        row.append(StringUtils.join(listing.getPrices(), ',')).append(",");

                        outStream.write(row.append("\n").toString().getBytes());

                }








        }
        public void createListingWithHighestPD(ArrayList<ListingData> listings, OutputStream outStream) throws IOException {
                String rowheads="listingName,Detailed Listing URL,Highest Price Date-1, Highest Price Date-2,Highest Price Date-3,Highest Price-1,Highest Price-2,Highest Price-3";
                rowheads+="\n";
                outStream.write(rowheads.getBytes());

                for (ListingData listing : listings) {
                        StringBuilder row= new StringBuilder();
                        String listName=listing.getListingName();
                        listName=listName.replace(","," ");
                        row.append(listName).append(",");
                        row.append(listing.getDetailedListingUrl()).append(",");
                     /*   if(listing.getTopThreeDates()!=null) {
                                for (int i = 0; i < listing.getTopThreeDates().size(); i++) {
                                        if (listing.getTopThreeDates().get(i)!= null) {
                                                row.append(listing.getTopThreeDates().get(i)).append(",");
                                        } else {
                                                row.append((String) null).append(",");
                                        }

                                }
                        }else{
                                row.append("null,null,null").append(",");
                        }*/
                        /*if(listing.getHighestPrices()!=null) {
                                for (int i = 0; i < listing.getHighestPrices().size(); i++) {
                                        if (listing.getHighestPrices().get(i) != null) {
                                                row.append(listing.getHighestPrices().get(i)).append(",");
                                        } else {
                                                row.append((String) null).append(",");
                                        }

                                }
                        }else{
                                row.append("null,null,null").append(",");
                        }*/
                        System.out.println("printing price and rate");
                        System.out.println(StringUtils.join(listing.getTopThreeDates(), ','));
                        System.out.println(StringUtils.join(listing.getHighestPrices(), ','));
                        row.append(StringUtils.join(listing.getTopThreeDates(), ",")).append(",");
                        row.append(StringUtils.join(listing.getHighestPrices()," ,")).append(",");
                        outStream.write(row.append("\n").toString().getBytes());

                }

        }
}
