package com.assignment.service;


import com.assignment.exception.RadiusLowException;
import com.assignment.model.ListingData;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface VrboServiceInterface {
    ArrayList<ListingData>  getClosestListingsService(JSONObject resultObject, String rad) throws RadiusLowException;
}
