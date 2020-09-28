package com.examples.animalsappmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.examples.animalsappmvvm.di.AppModule;
import com.examples.animalsappmvvm.di.DaggerViewModelComponent;
import com.examples.animalsappmvvm.di.TypeOfContext;
import com.examples.animalsappmvvm.model.AnimalApiService;
import com.examples.animalsappmvvm.model.AnimalModel;
import com.examples.animalsappmvvm.model.ApiKeyModel;
import com.examples.animalsappmvvm.util.SharedPreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.examples.animalsappmvvm.di.TypeOfContext.CONTEXT_APP;

public class ListViewModel extends AndroidViewModel {

    @Inject
    AnimalApiService apiService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<List<AnimalModel>> animals = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    @TypeOfContext(CONTEXT_APP)
    SharedPreferencesHelper preferencesHelper;

    private Boolean invalidApiKey = false;

    private Boolean injected = false;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public ListViewModel(@NonNull Application application, Boolean isTest){
        super(application);
        injected = isTest;
    }

    private void inject(){
        if(!injected){
            DaggerViewModelComponent.builder()
                    .appModule(new AppModule(getApplication()))
                    .build()
                    .inject(this);
        }
    }

    public void refresh() {
        inject();
        loading.setValue(true);
        invalidApiKey = false;
        String key = preferencesHelper.getPrefApiKey();
        if(key == null || key.equals("")){
            getKey();
        }else {
            getAnimals(key);
        }
    }

    public void hardRefresh(){
        loading.setValue(true);
        getKey();
    }

    private void getKey() {
        disposable.add(
                apiService.getApiKey()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiKeyModel>() {
                            @Override
                            public void onSuccess(ApiKeyModel key) {
                                if (key.getKey().isEmpty()) {
                                    loadError.setValue(true);
                                    loading.setValue(false);
                                } else {
                                    preferencesHelper.saveApiKey(key.getKey());
                                    getAnimals(key.getKey());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                loading.setValue(false);
                                loadError.setValue(true);
                            }
                        })
        );
    }

    private void getAnimals(String key) {
        disposable.add(
                apiService.getAnimals(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<AnimalModel>>() {
                            @Override
                            public void onSuccess(List<AnimalModel> animalList) {
                                loadError.setValue(false);
                                animals.setValue(animalList);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if(!invalidApiKey){
                                    invalidApiKey = true;
                                    getKey();
                                }else {
                                    e.printStackTrace();
                                    loading.setValue(false);
                                    loadError.setValue(true);
                                }
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
