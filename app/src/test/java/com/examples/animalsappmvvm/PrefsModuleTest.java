package com.examples.animalsappmvvm;

import android.app.Application;

import com.examples.animalsappmvvm.di.PrefsModule;
import com.examples.animalsappmvvm.util.SharedPreferencesHelper;

public class PrefsModuleTest extends PrefsModule {

    SharedPreferencesHelper mockPrefs;

    public PrefsModuleTest(SharedPreferencesHelper mockPrefs){
        this.mockPrefs = mockPrefs;
    }

    @Override
    public SharedPreferencesHelper provideSharedPreferences(Application app) {
        return mockPrefs;
    }
}
