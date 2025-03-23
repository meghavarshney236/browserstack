package org.browserstack;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GoogleTranslatorUtil {
    private static final String API_KEY = "AIzaSyC937fYN42x3afd9YH43woeBdQMEqykX4I";

    public static String translate(String text) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://translation.googleapis.com/language/translate/v2?key=" + API_KEY;

        RequestBody body = new FormBody.Builder()
                .add("q", text)
                .add("source", "es")
                .add("target", "en")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);
        return rootNode.path("data").path("translations").get(0).path("translatedText").asText();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(translate("España vendió a Israel armamento “no letal” por casi 50 millones, según un informe del Gobierno"));
    }
}