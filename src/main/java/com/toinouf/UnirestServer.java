package com.toinouf;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

public class UnirestServer {


    HttpRequest getGrid(String playerName) {
        return Unirest.get("http://fuckingendpoint:8080/grid")
                .queryString("name", playerName);
    }
}
