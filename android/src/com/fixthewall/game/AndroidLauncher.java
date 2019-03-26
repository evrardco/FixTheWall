package com.fixthewall.game;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.fixthewall.game.Game;

import java.util.List;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Game(), config);

        PackageManager manager = getPackageManager();
        if (!manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            Gdx.app.log("Sensor", "No step counter sensor detected.");
            Helpers.steps = (int) Helpers.getRandom(20, 50);
            Gdx.app.log("Sensor", "Debug steps generated: " + Helpers.steps);
            return;
        }

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        Gdx.app.log("Sensor", sensor.toString());
	}
}
