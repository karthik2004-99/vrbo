package com.assignment.utils;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Slf4j
public class Parser {
    JSONParser parser= new JSONParser();

    public  JSONObject getJSON(String response) throws ParseException {
        return (JSONObject) parser.parse(response);
    }
    public  static JSONObject getJSONObject(JSONObject obj, String key)  {

        JSONObject newObject = null;
        try{
            if(obj.containsKey(key) && obj.get(key)!=null && !obj.isEmpty()){
                newObject = (JSONObject) obj.get(key);
            }
        }catch (Exception e){
            String errorMessage="Error when parsing key.."+key+"\n Message: "+e.getMessage();
            log.error(errorMessage);

        }

        return newObject;


    }
    public static String getJSONString(JSONObject obj, String key)  {
        String s="";
        try{
            if(obj.containsKey(key) && obj.get(key)!=null && !obj.isEmpty()){
                s = (String) obj.get(key);
            }
        }catch (Exception e){

            String errorMessage="Error when parsing key.."+key+"\n Message: "+e.getMessage();
            log.error(errorMessage);

        }
        return s;
    }
    public static JSONArray getJSONArray(JSONObject obj, String key)  {
        JSONArray jsonArray = null;
        try {
           jsonArray  = (JSONArray) obj.get(key);
        }catch (Exception e){
            String errorMessage="Error when parsing JSONArray with Key.."+key+"\n Message: "+e.getMessage();
            log.error(errorMessage);
        }
        return jsonArray;

    }
}
