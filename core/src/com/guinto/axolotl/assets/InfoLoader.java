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
    public static JsonArray weaponsArray  = new JsonArray();
    public static JsonArray armorsArray  = new JsonArray();

    public static void loadFiles() {
        charactersArray = loadFile("characters");
        buildingsArray = loadFile("buildings");
        weaponsArray = loadFile("weapons");
        armorsArray = loadFile("armors");
    }

    public static JsonArray loadFile(String file){
        FileHandle fileHandle = Gdx.files.internal("info/" + file + ".json");
        if (fileHandle.exists()) {
            String jsonString = fileHandle.readString();
            JsonObject jsonObjectString = JsonParser.parseString(jsonString).getAsJsonObject();
            return jsonObjectString.getAsJsonArray(file);
        }
        return null;
    }

    public static JsonObject find(String code, JsonArray array) {
        JsonObject elementFound = null;
        for (JsonElement element : array) {
            JsonObject tempElement = element.getAsJsonObject();
            String jsonCode = tempElement.get("code").getAsString();
            if (jsonCode.equals(code)) {
                elementFound = tempElement;
                break;
            }
        }
        return elementFound;
    }
}