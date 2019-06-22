package com.example.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeDetailsActivity;
import com.example.bakingapp.Widgetservices;
import com.example.bakingapp.model.result;
import com.example.bakingapp.util.constantUTL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private final Context mContext;
    private final ArrayList<result> mRecipeList;
    private String mJsonResult;
    String recipeJson;

    public RecipeAdapter(Context context, ArrayList<result> recipeList, String jsonResult) {
        this.mContext = context;
        this.mRecipeList = recipeList;
        this.mJsonResult = jsonResult;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tv_recipe_name)
        TextView recipeName;

        @Nullable
        @BindView(R.id.iv_recipe_icon)
        ImageView recipeIcon;

        public RecipeViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.result_card, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {

        holder.recipeName.setText(mRecipeList.get(position).getName());

        switch (position){
            case 0 : holder.recipeIcon.setImageResource(R.drawable.pie);
                break;
            case 1 : holder.recipeIcon.setImageResource(R.drawable.brownie);
                break;
            case 2 : holder.recipeIcon.setImageResource(R.drawable.yellow_cake);
                break;
            case 3 : holder.recipeIcon.setImageResource(R.drawable.cheese_cake);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result recipe = mRecipeList.get(holder.getAdapterPosition());
                recipeJson = jsonToString(mJsonResult, holder.getAdapterPosition());
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                ArrayList<result> recipeArrayList = new ArrayList<>();
                recipeArrayList.add(recipe);
                intent.putParcelableArrayListExtra(constantUTL.RECIPE_INTENT_EXTRA, recipeArrayList);
                intent.putExtra(constantUTL.JSON_RESULT_EXTRA, recipeJson);
                mContext.startActivity(intent);

                SharedPreferences.Editor editor = mContext.getSharedPreferences(constantUTL.YUMMIO_SHARED_PREF, MODE_PRIVATE).edit();
                editor.putString(constantUTL.JSON_RESULT_EXTRA, recipeJson);
                editor.apply();

                if(Build.VERSION.SDK_INT > 25){
                    //Start the widget service to update the widget
                    Widgetservices.startActionOpenRecipeO(mContext);
                }
                else{
                    //Start the widget service to update the widget
                    Widgetservices.startActionOpenRecipe(mContext);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    // Get selected Recipe as Json String
    private String jsonToString(String jsonResult, int position){
        JsonElement jsonElement = new JsonParser().parse(jsonResult);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonElement recipeElement = jsonArray.get(position);
        return recipeElement.toString();
    }

}
