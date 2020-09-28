package com.examples.animalsappmvvm.di;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.examples.animalsappmvvm.util.SharedPreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.examples.animalsappmvvm.di.TypeOfContext.CONTEXT_ACTIVITY;
import static com.examples.animalsappmvvm.di.TypeOfContext.CONTEXT_APP;

@Module
public class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    public SharedPreferencesHelper provideSharedPreferences(Application app){
        return new SharedPreferencesHelper(app);
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    public SharedPreferencesHelper provideActivitySharedPreferences(AppCompatActivity activity){
        return new SharedPreferencesHelper(activity);
    }

}
