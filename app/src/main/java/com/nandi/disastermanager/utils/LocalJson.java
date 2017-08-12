package com.nandi.disastermanager.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.nandi.disastermanager.entity.ChartsInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by lemon on 2017/8/11.
 */

public class LocalJson {

    /**
     * filename：newdata.json
     *
     * @param context
     * @param filename
     */
    public static ChartsInfo parseJson(Context context, String filename) {
        ChartsInfo mChartsInfo = null;
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(
                    filename), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            Gson gson = new Gson();
            mChartsInfo = gson.fromJson(stringBuilder.toString(), ChartsInfo.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mChartsInfo;
    }


    /**
     * filename：newdata.json
     *
     * @param context
     * @param filename
     */
    public static ChartsInfo parseJson2(Context context, String filename) {
        ChartsInfo mChartsInfo = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(filename);
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            Gson gson = new Gson();
            mChartsInfo = gson.fromJson(reader, ChartsInfo.class);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mChartsInfo;
    }

}
