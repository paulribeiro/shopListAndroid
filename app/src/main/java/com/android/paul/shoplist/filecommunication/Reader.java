package com.android.paul.shoplist.filecommunication;

import android.util.JsonReader;

import com.android.paul.shoplist.Entities.Ingredient;
import com.android.paul.shoplist.Entities.Quantity;
import com.android.paul.shoplist.Entities.ShopElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Reader {

    public static List<ShopElement> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return Reader.readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }
    private static List<ShopElement> readMessagesArray(JsonReader reader) throws IOException {
        List<ShopElement> messages = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    private static ShopElement readMessage(JsonReader reader) throws IOException {
        Quantity quantity = new Quantity("","");
        Ingredient ingredient = new Ingredient("");

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("quantity")) {
                quantity = readQuantity(reader);
            } else if (name.equals("ingredient")) {
                ingredient.setName(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ShopElement(ingredient, quantity);
    }

    private static Quantity readQuantity(JsonReader reader) throws IOException {
        String number = "";
        String unit = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("number")) {
                number = reader.nextString();
            } else if (name.equals("unit")) {
                unit = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Quantity(number, unit);
    }
}
