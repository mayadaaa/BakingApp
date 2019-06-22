package com.example.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.ingredient;
import com.example.bakingapp.util.constantUTL;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.RecipeDetailsViewHolder>{

    private final Context mContext;
    private final List<ingredient> mIngredientList;

    public DetailsAdapter(Context context, List<ingredient> ingredientList) {
        this.mContext = context;
        this.mIngredientList = ingredientList;
    }

    public class RecipeDetailsViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.iv_unit_icon)
        ImageView unitIcon;

        @Nullable
        @BindView(R.id.tv_ingredient_name)
        TextView ingredientName;

        @Nullable
        @BindView(R.id.tv_unit_number)
        TextView unitNumber;


        @Nullable
        @BindView(R.id.tv_unit_long_name)
        TextView ingredientUnitLongName;


        public RecipeDetailsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @NonNull
    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_card, parent, false);

        return new RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeDetailsViewHolder holder, int position) {
        ingredient ingredient = mIngredientList.get(position);

        holder.ingredientName.setText(ingredient.getIngredient());
        holder.unitNumber.setText(String.valueOf(ingredient.getQuantity()));
       // holder.ingredientRowNumber.setText(String.valueOf(position+1));

        String measure = ingredient.getMeasure();
        int unitNo = 0;

        for(int i = 0; i < constantUTL.units.length; i++){
            if(measure.equals(constantUTL.units[i])){
                unitNo = i;
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }
}
