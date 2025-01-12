package com.example.game1;
import java.util.ArrayList;
import java.util.List;

public class Maplol {
    private List<Platform> platforms = new ArrayList<>();

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }
}