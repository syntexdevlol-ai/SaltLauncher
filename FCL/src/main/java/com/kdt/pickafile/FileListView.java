package com.kdt.pickafile;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.io.File;

public class FileListView extends LinearLayout {
    private FileSelectedListener listener;
    public FileListView(AlertDialog dialog, String extension) {
        super(dialog.getContext());
    }
    public FileListView(Context context, AttributeSet attrs) { super(context, attrs); }
    public void listFileAt(File dir) {}
    public void lockPathAt(File dir) {}
    public void setFileSelectedListener(FileSelectedListener l) { this.listener = l; }
}
