package com.toinouf;

import com.google.gson.Gson;
import com.mashape.unirest.request.HttpRequest;
import net.codestory.http.WebServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App {

    private UnirestServer unirestServer = new UnirestServer();

    public static void main(String[] args) {
        App gridAndShots = new App();

        new WebServer().configure(
                routes -> routes
                        .get("/gridAndShots", (context) -> {
                            String response = getStored(gridAndShots, context.get("playerName"));
                            Gson gson = new Gson();
                            StorageResponse storageResponse = gson.fromJson(response, StorageResponse.class);
                            List<List<VisuState>> ownStates = getOWnPlayerGrid(storageResponse);
                            List<List<VisuState>> opponentStates = initFlaque();

                            String opponentName = "1";
                            if (context.get("playerName").equals("1")) {
                                opponentName = "2";
                            }

                            for (List<StorageResponse.Pair> boat : storageResponse.boats) {
                                for (StorageResponse.Pair coordinate : boat) {
                                    states.get(coordinate.x).set(coordinate.y, VisuState.CLEAR_BOAT);
                                }
                            }

                            for (StorageResponse.Pair shot : storageResponse.shots) {
                                if (states.get(shot.x).get(shot.y) == VisuState.CLEAR_BOAT) {
                                    states.get(shot.x).set(shot.y, VisuState.TOUCHED_BOAT);
                                } else {
                                    states.get(shot.x).set(shot.y, VisuState.TOUCHED_WATER);
                                }
                            }
                            return states;

                            return ownStates;
                        })

        ).start(8080);
    }

    private static List<List<VisuState>> getOWnPlayerGrid(StorageResponse storageResponse) {
        List<List<VisuState>> states = initFlaque();

        for (List<StorageResponse.Pair> boat : storageResponse.boats) {
            for (StorageResponse.Pair coordinate : boat) {
                states.get(coordinate.x).set(coordinate.y, VisuState.CLEAR_BOAT);
            }
        }

        for (StorageResponse.Pair shot : storageResponse.shots) {
            if (states.get(shot.x).get(shot.y) == VisuState.CLEAR_BOAT) {
                states.get(shot.x).set(shot.y, VisuState.TOUCHED_BOAT);
            } else {
                states.get(shot.x).set(shot.y, VisuState.TOUCHED_WATER);
            }
        }
        return states;
    }

    private static List<List<VisuState>> initFlaque() {
        List<List<VisuState>> states = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<VisuState> current = new ArrayList<>();
            states.add(current);
            for (int j = 0; j < 10; j++) {
                current.add(VisuState.CLEAR_WATER);
            }
        }
        return states;
    }

    private static String getStored(App gridAndShots, String playerName) {
        HttpRequest request = gridAndShots.unirestServer.getGrid(playerName);
        return request.getBody().toString();
    }

}
