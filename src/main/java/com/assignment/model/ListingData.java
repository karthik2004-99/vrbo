package com.assignment.model;

import java.util.List;

public class ListingData {


    private String listingName;

    private String listingId;

    private String detailedListingUrl;

    private List<String> topThreeDates;

    private List<Long> prices;

    private List<Long> highestPrices;

    public ListingData() {
    }

    @Override
    public String toString() {
        return "ListingData{" +
                "listingName='" + listingName + '\'' +
                ", listingId='" + listingId + '\'' +
                ", detailedListingUrl='" + detailedListingUrl + '\'' +
                ", topThreeDates=" + topThreeDates +
                ", prices=" + prices +
                ", highestPrices=" + highestPrices +
                '}';
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getDetailedListingUrl() {
        return detailedListingUrl;
    }

    public void setDetailedListingUrl(String detailedListingUrl) {
        this.detailedListingUrl = detailedListingUrl;
    }

    public List<Long> getPrices() {
        return prices;
    }

    public void setPrices(List<Long> prices) {
        this.prices = prices;
    }

    public List<Long> getHighestPrices() {
        return highestPrices;
    }

    public void setHighestPrices(List<Long> highestPrices) {
        this.highestPrices = highestPrices;
    }


    public List<String> getTopThreeDates() {
        return topThreeDates;
    }

    public void setTopThreeDates(List<String> topThreeDates) {
        this.topThreeDates = topThreeDates;
    }
}
