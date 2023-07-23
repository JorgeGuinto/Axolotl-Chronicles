package com.guinto.axolotl.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class InfoLoader {

    public static JsonArray charactersArray = new JsonArray();
    public static JsonArray buildingsArray  = new JsonArray();

    public static void loadFile() {
        loadCharactersFile();
        loadBuildingsFile();
    }

    public static void loadCharactersFile() {
//        String filePath = "assets/info/charactersTest.json";
//        String filePath = Gdx.files.internal("assets/info/charactersTest.json").file().getAbsolutePath();

//        try (FileReader reader = new FileReader(filePath)) {
//            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
//            charactersArray = jsonObject.getAsJsonArray("characters");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        FileHandle fileHandle = Gdx.files.internal("info/charactersTest.json");

        if (fileHandle.exists()) {
            String jsonString = fileHandle.readString();
            JsonValue jsonValue = new JsonReader().parse(jsonString);

            JsonValue characters = jsonValue.get("characters");

            for (JsonValue character : characters) {
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("code", character.getString("code"));
                jsonObject.addProperty("group", character.getString("group"));
                jsonObject.addProperty("name", character.getString("name"));
                jsonObject.addProperty("life", character.getInt("life"));
                jsonObject.addProperty("damage", character.getInt("damage"));
                jsonObject.addProperty("ability", character.getInt("ability"));
                jsonObject.addProperty("defense", character.getInt("defense"));
                jsonObject.addProperty("attackSpeed", character.getDouble("attackSpeed"));
                jsonObject.addProperty("rechargeAbilitySpeed", character.getDouble("rechargeAbilitySpeed"));
                jsonObject.addProperty("framesRV", character.getInt("framesRV"));
                jsonObject.addProperty("framesRH", character.getInt("framesRH"));
                jsonObject.addProperty("framesWV", character.getInt("framesWV"));
                jsonObject.addProperty("framesWH", character.getInt("framesWH"));
                jsonObject.addProperty("framesIV", character.getInt("framesIV"));
                jsonObject.addProperty("framesIH", character.getInt("framesIH"));

                charactersArray.add(jsonObject);
            }
        }
    }

    public static void loadBuildingsFile() {
//        String filePath = "assets/info/buildingsInfo.json";
//        try (FileReader reader = new FileReader(filePath)) {
//            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
//            buildingsArray = jsonObject.getAsJsonArray("buildings");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        FileHandle fileHandle = Gdx.files.internal("info/buildingsInfo.json");

        if (fileHandle.exists()) {
            String jsonString = fileHandle.readString();
            JsonValue jsonValue = new JsonReader().parse(jsonString);

            JsonValue buildings = jsonValue.get("buildings");

            for (JsonValue building : buildings) {
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("code", building.getString("code"));
                jsonObject.addProperty("name", building.getString("name"));
                jsonObject.addProperty("x", building.getInt("x"));
                jsonObject.addProperty("y", building.getInt("y"));
                jsonObject.addProperty("imageW", building.getInt("imageW"));
                jsonObject.addProperty("imageH", building.getInt("imageH"));
                jsonObject.addProperty("width", building.getInt("width"));
                jsonObject.addProperty("height", building.getInt("height"));



//                JsonArray verticesArray = new JsonArray();
//                for (JsonValue vertex : building.get("vertices")) {
//                    verticesArray.add(vertex.getInt());
//                }
//                jsonObject.add("vertices", verticesArray);

                // Agrega el JsonObject del edificio al JsonArray de edificios
                buildingsArray.add(jsonObject);
            }
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