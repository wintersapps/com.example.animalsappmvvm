package com.examples.animalsappmvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AnimalModel implements Parcelable {

    private String name;
    private Taxonomy taxonomy;
    private String location;
    private Speed speed;
    private String diet;
    private String lifespan;

    @SerializedName("image")
    private String imageUrl;

    public AnimalModel(String name) {
        this.name = name;
        this.taxonomy = null;
        this.location = "";
        this.speed = null;
        this.diet = "";
        this.lifespan = "";
        this.imageUrl = "";
    }

    public AnimalModel(String name, Taxonomy taxonomy, String location, Speed speed, String diet, String lifespan, String imageUrl) {
        this.name = name;
        this.taxonomy = taxonomy;
        this.location = location;
        this.speed = speed;
        this.diet = diet;
        this.lifespan = lifespan;
        this.imageUrl = imageUrl;
    }

    protected AnimalModel(Parcel in) {
        name = in.readString();
        taxonomy = in.readParcelable(Taxonomy.class.getClassLoader());
        location = in.readString();
        speed = in.readParcelable(Speed.class.getClassLoader());
        diet = in.readString();
        lifespan = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(taxonomy, flags);
        dest.writeString(location);
        dest.writeParcelable(speed, flags);
        dest.writeString(diet);
        dest.writeString(lifespan);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimalModel> CREATOR = new Creator<AnimalModel>() {
        @Override
        public AnimalModel createFromParcel(Parcel in) {
            return new AnimalModel(in);
        }

        @Override
        public AnimalModel[] newArray(int size) {
            return new AnimalModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getLifespan() {
        return lifespan;
    }

    public void setLifespan(String lifespan) {
        this.lifespan = lifespan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

class Taxonomy implements Parcelable{
    String kingdom;
    String order;
    String family;

    protected Taxonomy(Parcel in) {
        kingdom = in.readString();
        order = in.readString();
        family = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kingdom);
        dest.writeString(order);
        dest.writeString(family);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Taxonomy> CREATOR = new Creator<Taxonomy>() {
        @Override
        public Taxonomy createFromParcel(Parcel in) {
            return new Taxonomy(in);
        }

        @Override
        public Taxonomy[] newArray(int size) {
            return new Taxonomy[size];
        }
    };
}

class Speed implements Parcelable{
    String metric;
    String imperial;

    protected Speed(Parcel in) {
        metric = in.readString();
        imperial = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(metric);
        dest.writeString(imperial);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Speed> CREATOR = new Creator<Speed>() {
        @Override
        public Speed createFromParcel(Parcel in) {
            return new Speed(in);
        }

        @Override
        public Speed[] newArray(int size) {
            return new Speed[size];
        }
    };
}
