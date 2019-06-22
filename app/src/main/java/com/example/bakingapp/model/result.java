package com.example.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class result implements Parcelable {

    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<step> getSteps() {
        return steps;
    }

    public void setSteps(List<step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeValue(this.servings);
        dest.writeString(this.image);
    }

    public result() {
    }

    protected result(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, ingredient.class.getClassLoader());
        this.steps = new ArrayList<>();
        in.readList(this.steps, step.class.getClassLoader());
        this.servings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
    }

    public static final Parcelable.Creator<result> CREATOR = new Parcelable.Creator<result>() {
        @Override
        public result createFromParcel(Parcel source) {
            return new result(source);
        }

        @Override
        public result[] newArray(int size) {
            return new result[size];
        }
    };
}
