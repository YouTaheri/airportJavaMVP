package com.yousef.khedmatazma.safebodatest.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.yousef.khedmatazma.safebodatest.R;
import java.util.Objects;

/**
 * The type Custom progress dialog.
 */
public class CustomProgressDialog extends Dialog {

    private Context context;
    private ImageView imgSpinner;
    private TextView txtMessage;

    /**
     * Instantiates a new Custom progress dialog.
     *
     * @param context the context
     */
    @SuppressLint("NewApi")
    public CustomProgressDialog(Context context) {

        super(context, R.style.CustomProgressDialog);
        this.context = context;

        WindowManager.LayoutParams wlmp = Objects.requireNonNull(getWindow()).getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);

        setContentView(R.layout.progress_spinner);
        imgSpinner = findViewById(R.id.imgSpinner);
        txtMessage = findViewById(R.id.txtMessage);
    }

    @Override
    public void show() {
        super.show();

        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1500);

        imgSpinner.setAnimation(anim);
        imgSpinner.startAnimation(anim);
    }

    /**
     * Show.
     *
     * @param message the message
     */
    private void show(CharSequence message) {
        show();
        if (message.toString().isEmpty())
            txtMessage.setVisibility(View.GONE);
        txtMessage.setText(message);
    }

    /**
     * Show.
     *
     * @param messageId the message id
     */
    public void show(int messageId) {
        show(context.getString(messageId));
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        if (this.isShowing()) {
            txtMessage.setVisibility(View.VISIBLE);
            txtMessage.setText(message);
        }
    }
}