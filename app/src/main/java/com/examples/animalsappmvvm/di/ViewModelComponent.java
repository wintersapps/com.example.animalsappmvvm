package com.examples.animalsappmvvm.di;

import com.examples.animalsappmvvm.viewmodel.ListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApiModule.class, AppModule.class, PrefsModule.class})
@Singleton
public interface ViewModelComponent {
    void inject(ListViewModel viewModel);
}
