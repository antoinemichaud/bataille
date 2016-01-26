package com.toinouf;

import java.util.ArrayList;
import java.util.List;

public class StorageResponse {

    public List<List<Pair>> boats = new ArrayList<>();
    public List<Pair> shots = new ArrayList<>();

    public static class Pair {
        public int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
