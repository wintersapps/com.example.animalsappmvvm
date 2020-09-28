package com.examples.animalsappmvvm;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.examples.animalsappmvvm.di.AppModule;
import com.examples.animalsappmvvm.di.DaggerViewModelComponent;
import com.examples.animalsappmvvm.model.AnimalApiService;
import com.examples.animalsappmvvm.model.AnimalModel;
import com.examples.animalsappmvvm.model.ApiKeyModel;
import com.examples.animalsappmvvm.util.SharedPreferencesHelper;
import com.examples.animalsappmvvm.viewmodel.ListViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    AnimalApiService animalApiService;

    @Mock
    SharedPreferencesHelper prefs;

    Application application = Mockito.mock(Application.class);

    ListViewModel listViewModel = new ListViewModel(application, true);

    private String key = "Test key";

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);

        DaggerViewModelComponent.builder()
                .appModule(new AppModule(application))
                .apiModule(new ApiModuleTest(animalApiService))
                .prefsModule(new PrefsModuleTest(prefs))
                .build()
                .inject(listViewModel);
    }

    @Test
    public void getAnimalsSuccess(){
        Mockito.when(prefs.getPrefApiKey()).thenReturn(key);

        AnimalModel animal = new AnimalModel("cow");
        ArrayList<AnimalModel> animalList = new ArrayList<>();
        animalList.add(animal);

        Single<List<AnimalModel>> testSingle = Single.just(animalList);

        Mockito.when(animalApiService.getAnimals(key)).thenReturn(testSingle);

        listViewModel.refresh();

        if(listViewModel.animals.getValue() != null){
            Assert.assertEquals(1, listViewModel.animals.getValue().size());
        }
        Assert.assertEquals(false,listViewModel.loadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());
    }

    @Test
    public void getAnimalsFailure(){
        Mockito.when(prefs.getPrefApiKey()).thenReturn(key);

        Single<List<AnimalModel>> testSingle = Single.error(new Throwable());
        Single<ApiKeyModel> keySingle = Single.just(new ApiKeyModel("OK",key));

        Mockito.when(animalApiService.getAnimals(key)).thenReturn(testSingle);
        Mockito.when(animalApiService.getApiKey()).thenReturn(keySingle);

        listViewModel.refresh();

        Assert.assertNull(listViewModel.animals.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());
        Assert.assertEquals(true,listViewModel.loadError.getValue());
    }

    @Test
    public void getKeySuccess(){
        Mockito.when(prefs.getPrefApiKey()).thenReturn(null);
        ApiKeyModel apiKeyModel = new ApiKeyModel("OK", key);
        Single<ApiKeyModel> keySingle = Single.just(apiKeyModel);

        Mockito.when(animalApiService.getApiKey()).thenReturn(keySingle);

        AnimalModel animal = new AnimalModel("cow");
        ArrayList<AnimalModel> animalList = new ArrayList<>();
        animalList.add(animal);

        Single<List<AnimalModel>> testSingle = Single.just(animalList);

        Mockito.when(animalApiService.getAnimals(key)).thenReturn(testSingle);

        listViewModel.refresh();

        if(listViewModel.animals.getValue() != null){
            Assert.assertEquals(1, listViewModel.animals.getValue().size());
        }
        Assert.assertEquals(false,listViewModel.loadError.getValue());
        Assert.assertEquals(false,listViewModel.loading.getValue());
    }

    @Test
    public void getKeyFailure(){
        Mockito.when(prefs.getPrefApiKey()).thenReturn(null);
        Single<ApiKeyModel> keySingle = Single.error(new Throwable());

        Mockito.when(animalApiService.getApiKey()).thenReturn(keySingle);

        listViewModel.refresh();

        Assert.assertNull(listViewModel.animals.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());
        Assert.assertEquals(true, listViewModel.loadError.getValue());
    }

    @Before
    public void setupRxSchedulers(){
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, true);
            }
        };

        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);
    }
}
