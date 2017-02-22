package com.example.meteor.network;

import com.example.meteor.network.model.Meteor;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;
import timber.log.Timber;

/**
 * @author martin
 * @since 21/02/2017.
 */

public interface NasaService {

    String BASE_URL = "https://data.nasa.gov/resource/";

    @GET("y77d-th95.json")
    Observable<Response<List<Meteor>>> getAllMeteors();

    class TypeAdapter implements JsonDeserializer<Meteor> {

        @Override
        public Meteor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Meteor meteor = new Gson().fromJson(json, Meteor.class);
            JsonElement yearJson = json.getAsJsonObject().get("year");

            if (yearJson != null) {
                String yearStr = yearJson.getAsString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss", Locale.getDefault());

                try {
                    meteor.setYear(dateFormat.parse(yearStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                Timber.d("json is null");
            }

            JsonElement coordinatesJson = json.getAsJsonObject().get("geolocation");

            if (coordinatesJson != null) {
                coordinatesJson = coordinatesJson.getAsJsonObject().get("coordinates");

                if (coordinatesJson != null) {
                    double latitude = coordinatesJson.getAsJsonArray().get(0).getAsDouble();
                    double longitude = coordinatesJson.getAsJsonArray().get(1).getAsDouble();
                    meteor.setLatitude(latitude);
                    meteor.setLongitude(longitude);
                }
            }

            return meteor;
        }
    }
}
