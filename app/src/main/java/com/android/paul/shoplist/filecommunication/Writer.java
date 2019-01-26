package com.android.paul.shoplist.filecommunication;

import android.util.JsonWriter;

import com.android.paul.shoplist.Quantity;
import com.android.paul.shoplist.ShopElement;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public final class Writer {

    public static void writeJsonStream(OutputStream out, List<ShopElement> message) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeMessagesArray(writer, message);
        writer.close();
    }

    private static void writeMessagesArray(JsonWriter writer, List<ShopElement> messages) throws IOException {
        writer.beginArray();
        for (ShopElement message : messages) {
            writeMessage(writer, message);
        }
        writer.endArray();
    }
    private static void writeMessage(JsonWriter writer, ShopElement message) throws IOException {
        writer.beginObject();
        writer.name("ingredient").value(message.getIngredient().getName());
        writer.name("quantity");
        writeQuantity(writer, message.getQuantity());
        writer.endObject();
    }
    private static void writeQuantity(JsonWriter writer, Quantity quantity) throws IOException {
        writer.beginObject();
        writer.name("number").value(quantity.getNumber());
        writer.name("unit").value(quantity.getUnit());
        writer.endObject();
    }
}
