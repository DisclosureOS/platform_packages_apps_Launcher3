/*
 * Copyright (C) 2018 The Android Open Source Project
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
package com.android.quickstep;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.android.launcher3.BaseActivity;
import com.android.launcher3.InsettableFrameLayout;
import com.android.launcher3.R;
import com.android.launcher3.util.Themes;

public class RecentsRootView extends InsettableFrameLayout {

    private final BaseActivity mActivity;

    public RecentsRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = BaseActivity.fromContext(context);
    }

    @TargetApi(23)
    @Override
    protected boolean fitSystemWindows(Rect insets) {
        // Update device profile before notifying the children.
        mActivity.getDeviceProfile().updateInsets(insets);
        setInsets(insets);
        return true; // I'll take it from here
    }

    @Override
    public void setInsets(Rect insets) {
        // If the insets haven't changed, this is a no-op. Avoid unnecessary layout caused by
        // modifying child layout params.
        if (!insets.equals(mInsets)) {
            super.setInsets(insets);
        }
        setBackground(insets.top == 0 ? null
                : Themes.getAttrDrawable(getContext(), R.attr.workspaceStatusBarScrim));
    }
}