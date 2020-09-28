package com.examples.animalsappmvvm.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String PREF_API_KEY = "pref_api_key";

    private SharedPreferences preferences;

    public SharedPreferencesHelper(Context context){
        preferences = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    public void saveApiKey(String key){
        preferences.edit().putString(PREF_API_KEY, key).apply();
    }

    public String getPrefApiKey(){
        return preferences.getString(PREF_API_KEY,null);
    }
}
