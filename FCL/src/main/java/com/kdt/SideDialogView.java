package com.kdt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SideDialogView extends FrameLayout {
    public SideDialogView(Context context) { super(context); }
    public SideDialogView(Context context, AttributeSet attrs) { super(context, attrs); }
    public SideDialogView(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }

    protected void onInflate() {}
    protected void onDestroy() {}

    @Override protected void onAttachedToWindow() { super.onAttachedToWindow(); onInflate(); }
    @Override protected void onDetachedFromWindow() { super.onDetachedFromWindow(); onDestroy(); }
}
