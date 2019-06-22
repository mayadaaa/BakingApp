package com.example.bakingapp.widget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.bakingapp.model.ingredient;
import com.example.bakingapp.model.result;
import com.example.bakingapp.util.constantUTL;
import com.google.gson.Gson;

import java.util.List;

public class widgetService extends IntentService {

    public static final String ACTION_OPEN_RECIPE =
            "io.github.akndmr.yummio.widget.yummio_widget_service";


    public widgetService(String name) {
        super(name);
    }

    public widgetService() {
        super("YummioWidgetService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Yummio service",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_OPEN_RECIPE.equals(action)) {
                handleActionOpenRecipe();
            }
        }
    }

    private void handleActionOpenRecipe() {
        SharedPreferences sharedpreferences =
                getSharedPreferences(constantUTL.YUMMIO_SHARED_PREF, MODE_PRIVATE);
        String jsonRecipe = sharedpreferences.getString(constantUTL.JSON_RESULT_EXTRA, "");
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        result recipe = gson.fromJson(jsonRecipe, result.class);
        int id = recipe.getId();
        int imgResId = constantUTL.recipeIcons[id - 1];
        List<ingredient> ingredientList = recipe.getIngredients();
        for (ingredient ingredient : ingredientList) {
            String quantity = String.valueOf(ingredient.getQuantity());
            String measure = ingredient.getMeasure();
            String ingredientName = ingredient.getIngredient();
            String line = quantity + " " + measure + " " + ingredientName;
            stringBuilder.append(line + "\n");
        }
        String ingredientsString = stringBuilder.toString();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, widgetProvider.class));
        widgetProvider.updateWidgetRecipe(this, ingredientsString, imgResId, appWidgetManager, appWidgetIds);
    }


    // Trigger the service to perform the action
    public static void startActionOpenRecipe(Context context) {
        Intent intent = new Intent(context, widgetService.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        context.startService(intent);
    }

    // For Android O and above
    public static void startActionOpenRecipeO(Context context) {
        Intent intent = new Intent(context, widgetService.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        ContextCompat.startForegroundService(context, intent);
    }
}