package com.example.bakingapp.util;

import com.example.bakingapp.model.result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("baking.json")
    Call<ArrayList<result>> getRecipes();
}
