package com.example.testproject.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.example.testproject.R;

public class AVLoadingIndicatorDialog extends Dialog {



    public AVLoadingIndicatorDialog(Context context) {
        super(context);
        setTitle("");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_avid);
        setMessage("");
        setCancelable(false);
//        getWindow().setBackgroundDrawable(android.R.color.transparent);

    }

    public void setMessage(CharSequence message){
        ((TextView) findViewById(R.id.message)).setText(message);
    }
}
