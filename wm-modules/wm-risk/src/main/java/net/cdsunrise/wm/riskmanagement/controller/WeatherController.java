package net.cdsunrise.wm.riskmanagement.controller;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weatherUrl}")
    private String weatherUrl;

    @GetMapping("query")
    public Map<String, Object> query() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(weatherUrl).build();
        Response response = client.newCall(request).execute();
        System.out.println(response.isSuccessful());
        String body = response.body().string();
        Gson gson = new Gson();
        return gson.fromJson(body, Map.class);
    }
}
