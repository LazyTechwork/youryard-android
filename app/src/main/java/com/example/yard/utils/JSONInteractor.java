package com.example.yard.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JSONInteractor {
    private File file;
    private Gson gson = new Gson();

    private Context context;

    public JSONInteractor(Context context, String filename) {
        this.file = new File(context.getExternalFilesDir(null), filename);
        this.context = context;
    }

    public String readRawJSON() throws FileNotFoundException {
        Scanner fileReader = new Scanner(this.file);
        StringBuilder stringBuilder = new StringBuilder();
        while (fileReader.hasNextLine()) {
            stringBuilder.append(fileReader.nextLine());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public JSONInteractor writeRawJSON(String json) throws IOException {
        FileWriter fileWriter = new FileWriter(this.file);
        fileWriter.write(json);
        fileWriter.close();
        return this;
    }

    public <T> T readJSON(Class<T> clazz) throws FileNotFoundException {
        return this.gson.fromJson(this.readRawJSON(), clazz);
    }

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
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public File getFile() {
        return file;
    }

    public JSONInteractor setFile(String filename) {
        this.file = new File(context.getExternalFilesDir(null), filename);
        return this;
    }

    public Context getContext() {
        return context;
    }

    public JSONInteractor setContext(Context context) {
        this.context = context;
        return this;
    }
}
