package com.examples.animalsappmvvm.di;

import com.examples.animalsappmvvm.model.AnimalApiService;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(AnimalApiService service);
}
