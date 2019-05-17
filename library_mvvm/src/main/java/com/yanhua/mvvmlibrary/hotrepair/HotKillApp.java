package com.yanhua.mvvmlibrary.hotrepair;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class HotKillApp {

    public static void killAllOtherProcess(Context context) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcessList = am
                .getRunningAppProcesses();

        if (appProcessList == null) {
            return;
        }
        // NOTE: getRunningAppProcess() ONLY GIVE YOU THE PROCESS OF YOUR OWN PACKAGE IN ANDROID M
        // BUT THAT'S ENOUGH HERE
        for (ActivityManager.RunningAppProcessInfo ai : appProcessList) {
            // KILL OTHER PROCESS OF MINE
            if (ai.uid == android.os.Process.myUid() && ai.pid != android.os.Process.myPid()) {
                android.os.Process.killProcess(ai.pid);
            }
        }

    }
    public static void killAllProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
