/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.shared.system;

import android.annotation.IntDef;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.android.internal.jank.Cuj;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.jank.InteractionJankMonitor.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class InteractionJankMonitorWrapper {
    // Launcher journeys.
    public static final int CUJ_APP_LAUNCH_FROM_RECENTS = Cuj.CUJ_LAUNCHER_APP_LAUNCH_FROM_RECENTS;
    public static final int CUJ_APP_LAUNCH_FROM_ICON = Cuj.CUJ_LAUNCHER_APP_LAUNCH_FROM_ICON;
    public static final int CUJ_APP_CLOSE_TO_HOME = Cuj.CUJ_LAUNCHER_APP_CLOSE_TO_HOME;
    public static final int CUJ_APP_CLOSE_TO_HOME_FALLBACK =
            Cuj.CUJ_LAUNCHER_APP_CLOSE_TO_HOME_FALLBACK;
    public static final int CUJ_APP_CLOSE_TO_PIP = Cuj.CUJ_LAUNCHER_APP_CLOSE_TO_PIP;
    public static final int CUJ_QUICK_SWITCH = Cuj.CUJ_LAUNCHER_QUICK_SWITCH;
    public static final int CUJ_OPEN_ALL_APPS = Cuj.CUJ_LAUNCHER_OPEN_ALL_APPS;
    public static final int CUJ_CLOSE_ALL_APPS_SWIPE = Cuj.CUJ_LAUNCHER_CLOSE_ALL_APPS_SWIPE;
    public static final int CUJ_CLOSE_ALL_APPS_TO_HOME = Cuj.CUJ_LAUNCHER_CLOSE_ALL_APPS_TO_HOME;
    public static final int CUJ_ALL_APPS_SCROLL = Cuj.CUJ_LAUNCHER_ALL_APPS_SCROLL;
    public static final int CUJ_APP_LAUNCH_FROM_WIDGET = Cuj.CUJ_LAUNCHER_APP_LAUNCH_FROM_WIDGET;
    public static final int CUJ_SPLIT_SCREEN_ENTER = Cuj.CUJ_SPLIT_SCREEN_ENTER;
    public static final int CUJ_LAUNCHER_UNLOCK_ENTRANCE_ANIMATION =
            Cuj.CUJ_LAUNCHER_UNLOCK_ENTRANCE_ANIMATION;
    public static final int CUJ_RECENTS_SCROLLING = Cuj.CUJ_RECENTS_SCROLLING;
    public static final int CUJ_APP_SWIPE_TO_RECENTS = Cuj.CUJ_LAUNCHER_APP_SWIPE_TO_RECENTS;
    public static final int CUJ_OPEN_SEARCH_RESULT = Cuj.CUJ_LAUNCHER_OPEN_SEARCH_RESULT;
    public static final int CUJ_LAUNCHER_UNFOLD_ANIM = Cuj.CUJ_LAUNCHER_UNFOLD_ANIM;
    public static final int CUJ_SEARCH_QSB_OPEN = Cuj.CUJ_LAUNCHER_SEARCH_QSB_OPEN;

    @IntDef({
            CUJ_APP_LAUNCH_FROM_RECENTS,
            CUJ_APP_LAUNCH_FROM_ICON,
            CUJ_APP_CLOSE_TO_HOME,
            CUJ_APP_CLOSE_TO_HOME_FALLBACK,
            CUJ_APP_CLOSE_TO_PIP,
            CUJ_QUICK_SWITCH,
            CUJ_APP_LAUNCH_FROM_WIDGET,
            CUJ_LAUNCHER_UNLOCK_ENTRANCE_ANIMATION,
            CUJ_RECENTS_SCROLLING,
            CUJ_APP_SWIPE_TO_RECENTS,
            CUJ_OPEN_ALL_APPS,
            CUJ_CLOSE_ALL_APPS_SWIPE,
            CUJ_CLOSE_ALL_APPS_TO_HOME,
            CUJ_OPEN_SEARCH_RESULT,
            CUJ_LAUNCHER_UNFOLD_ANIM,
            CUJ_SEARCH_QSB_OPEN,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CujType {
    }

    /**
     * Begin a trace session.
     *
     * @param v       an attached view.
     * @param cujType the specific {@link Cuj.CujType}.
     */
    public static void begin(View v, @CujType int cujType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return;
        InteractionJankMonitor.getInstance().begin(v, cujType);
    }

    /**
     * Begin a trace session.
     *
     * @param v       an attached view.
     * @param cujType the specific {@link Cuj.CujType}.
     * @param timeout duration to cancel the instrumentation in ms
     */
    public static void begin(View v, @CujType int cujType, long timeout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return;
        Configuration.Builder builder =
                Configuration.Builder.withView(cujType, v)
                        .setTimeout(timeout);
        InteractionJankMonitor.getInstance().begin(builder);
    }

    /**
     * Begin a trace session.
     *
     * @param v       an attached view.
     * @param cujType the specific {@link Cuj.CujType}.
     * @param tag the tag to distinguish different flow of same type CUJ.
     */
    public static void begin(View v, @CujType int cujType, String tag) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return;
        Configuration.Builder builder =
                Configuration.Builder.withView(cujType, v);
        if (!TextUtils.isEmpty(tag)) {
            builder.setTag(tag);
        }
        InteractionJankMonitor.getInstance().begin(builder);
    }

    /**
     * End a trace session.
     *
     * @param cujType the specific {@link Cuj.CujType}.
     */
    public static void end(@CujType int cujType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return;
        InteractionJankMonitor.getInstance().end(cujType);
    }

    /**
     * Cancel the trace session.
     */
    public static void cancel(@CujType int cujType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return;
        InteractionJankMonitor.getInstance().cancel(cujType);
    }
}
