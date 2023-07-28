package com.guinto.axolotl.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class InfoLoader {

    public static JsonArray charactersArray = new JsonArray();
    public static JsonArray buildingsArray  = new JsonArray();

    public static void loadFile() {
        loadCharactersFile();
        loadBuildingsFile();
    }

    public static void loadCharactersFile() {
        FileHandle fileHandle = Gdx.files.internal("info/charactersTest.json");

        if (fileHandle.exists()) {
            String jsonString = fileHandle.readString();
            JsonObject jsonObjectString = JsonParser.parseString(jsonString).getAsJsonObject();
            charactersArray = jsonObjectString.getAsJsonArray("characters");
        }
    }

    public static void loadBuildingsFile() {
        FileHandle fileHandle = Gdx.files.internal("info/buildingsInfo.json");

        if (fileHandle.exists()) {
            String jsonString = fileHandle.readString();
            JsonObject jsonObjectString = JsonParser.parseString(jsonString).getAsJsonObject();
            buildingsArray = jsonObjectString.getAsJsonArray("buildings");
        }
    }

    public static JsonObject findCharacter(String code) {
        JsonObject characterFound = null;

        for (JsonElement element : charactersArray) {
            JsonObject character = element.getAsJsonObject();
            String jsonCode = character.get("code").getAsString();

            if (jsonCode.equals(code)) {
                characterFound = character;
                break;
            }
        }
        return characterFound;
    }

    public static JsonObject findBuilding(String name) {
        JsonObject buildingFound = null;

        for (JsonElement element : buildingsArray) {
            JsonObject building = element.getAsJsonObject();
            String jsonCode = building.get("code").getAsString();

            if (jsonCode.equals(name)) {
                buildingFound = building;
                break;
            }
        }
        return buildingFound;
    }
}