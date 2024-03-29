package com.generally2.finddubactivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getSinglePlace(JSONObject googlePlaceJSON){
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String nameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude;
        String longitude;
        String reference;


        try{
            if (!googlePlaceJSON.isNull("name")){
                nameOfPlace = googlePlaceJSON.getString("name");
            }if (!googlePlaceJSON.isNull("vicinity")){
                vicinity = googlePlaceJSON.getString("vicinity");

            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name", nameOfPlace);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);




        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getAllNearbyPlaces(JSONArray jsonArray){
        int counter = jsonArray.length();
        List<HashMap<String, String>> NearbyPlaceList = new ArrayList<>();

        HashMap<String, String> NearbyPlaceMap = null;


        for (int i = 0; i < counter; i++){

            try{
                NearbyPlaceMap = getSinglePlace((JSONObject)jsonArray.get(i));
                NearbyPlaceList.add(NearbyPlaceMap);

            }
            catch (JSONException e){
                e.printStackTrace();


            }

        }
        return  NearbyPlaceList;
    }

    List<HashMap<String, String>> parse(String jsonData){
        JSONArray jsonArray = null;

        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e){
            e.printStackTrace();

        }
        return getAllNearbyPlaces(jsonArray);


    }
}
