package com.example.yard.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONInteractor {
    private String filename;
    private Gson gson = new Gson();

    private Context context;

    public JSONInteractor(Context context, String filename) {
        this.filename = filename;
        this.context = context;
    }

    /**
     * @param clazz the class of T
     * @param <T>   the type of the desired object
     * @return an object of type T from the JSON file from constructor
     * @throws FileNotFoundException
     */
    public <T> T readData(Class<T> clazz) throws FileNotFoundException {
        return this.gson.fromJson(new FileReader(context.getFilesDir() + "/" + this.filename), clazz);
    }

    /**
     * Converting data into JSON and saving into file from constructor
     *
     * @param object object needs to be converted in JSON file
     * @return JSON String
     * @throws IOException
     */
    public String writeData(Object object) throws IOException {
        String json = this.gson.toJson(object);
        new FileWriter(context.getFilesDir() + "/" + filename).write(json);
        return json;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
