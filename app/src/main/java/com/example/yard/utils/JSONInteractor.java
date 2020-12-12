package com.example.yard.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class JSONInteractor {
    private File file;
    private final Gson gson = new Gson();

    private Context context;

    public JSONInteractor(Context context, String filename) {
        this.file = new File(context.getExternalFilesDir(null), filename);
        this.context = context;
    }

    /**
     * Reads JSON from file and returns it
     *
     * @return raw JSON
     * @throws FileNotFoundException
     */
    public String readRawJSON() throws FileNotFoundException {
        Scanner fileReader = new Scanner(this.file);
        StringBuilder stringBuilder = new StringBuilder();
        while (fileReader.hasNextLine()) {
            stringBuilder.append(fileReader.nextLine());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Writes raw JSON into file
     *
     * @param json JSON string
     * @return
     * @throws IOException
     */
    public JSONInteractor writeRawJSON(String json) throws IOException {
        FileWriter fileWriter = new FileWriter(this.file);
        fileWriter.write(json);
        fileWriter.close();
        return this;
    }

    /**
     * Parses JSON from file and returns object of type clazz
     *
     * @param clazz Object class
     * @param <T>   type of object
     * @return parsed object
     * @throws FileNotFoundException
     */
    public <T> T readJSON(Class<T> clazz) throws FileNotFoundException {
        return this.gson.fromJson(this.readRawJSON(), clazz);
    }

    /**
     * Parse object and convert it to JSON
     *
     * @param object that need to be parsed
     * @return instance of this
     * @throws IOException
     */
    public JSONInteractor writeJSON(Object object) throws IOException {
        return this.writeRawJSON(this.gson.toJson(object));
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = this.context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public Context getContext() {
        return context;
    }

    public JSONInteractor setContext(Context context) {
        this.context = context;
        return this;
    }
}
