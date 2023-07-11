package com.guinto.axolotl.assets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.guinto.axolotl.characters.Axolotl;

import java.io.FileReader;
import java.io.IOException;

public class CharacterLoader {

    public static JsonArray charactersArray;
    public static void loadFile() {
        String filePath = "assets/charactersInfo/charactersTest.json";
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            charactersArray = jsonObject.getAsJsonArray("characters");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject createCharacter(String code) {
        JsonObject characterFound = null;

        for (JsonElement element : charactersArray) {
            JsonObject character = element.getAsJsonObject();
            String jsonCode = character.get("code").getAsString();

            if (jsonCode.equals(code)) {
                characterFound = character;
                System.out.println("Se encontró a " + character.get("name").getAsString());
                System.out.println("Su vida es de " + character.get("life").getAsInt());
                break;
            }
        }

        return characterFound;
    }

}
