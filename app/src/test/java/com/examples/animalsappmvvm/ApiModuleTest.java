package com.examples.animalsappmvvm;

import com.examples.animalsappmvvm.di.ApiModule;
import com.examples.animalsappmvvm.model.AnimalApiService;

public class ApiModuleTest extends ApiModule {

    AnimalApiService mockService;

    public ApiModuleTest(AnimalApiService mockService){
        this.mockService = mockService;
    }

    @Override
    public AnimalApiService provideAnimalApiService() {
        return mockService;
    }
}
