package com.soulsurfer.android.listener;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.soulsurfer.android.utils.BroadcastUtils;
import com.soulsurfer.android.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ApplicationStateListener extends EmptyActivityLifecycleCallbacks {

    public static void listen(Application application) {
        Log.d(Constants.TAG, "ApplicationStateListener::listen()");
        getInstance(application);
    }

    public static boolean isForeground() {
        return applicationStateListener.currentState == State.FOREGROUND;
    }

    public static boolean isBackground() {
        return applicationStateListener.currentState == State.BACKGROUND;
    }

    /*------------------- Listener engine. Don't mess with it. -----------------*/
    private static ApplicationStateListener applicationStateListener;

    private final Application application;
    private State currentState = State.DEFAULT;
    private final List<Activity> activities = new ArrayList<>();

    private ApplicationStateListener(Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    private static synchronized ApplicationStateListener getInstance(Application application) {
        if (applicationStateListener == null) {
            synchronized (ApplicationStateListener.class) {
                applicationStateListener = new ApplicationStateListener(application);
            }
        }
        return applicationStateListener;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        activities.add(activity);
        updateState();
        super.onActivityStarted(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activities.remove(activity);
        updateState();
        super.onActivityStopped(activity);
    }

    private void updateState() {
        State oldState = currentState;
        currentState = activities.size() > 0 ? State.FOREGROUND : State.BACKGROUND;

        if (oldState != currentState) {
            BroadcastUtils broadcastUtils  = BroadcastUtils.newInstance(application).build();
            broadcastUtils.sendBroadcast(Constants.ACTION_APP_STATE_CHANGED);
        }
    }

    private enum State {
        FOREGROUND,
        BACKGROUND;

        private static final State DEFAULT = BACKGROUND;
    }
}
