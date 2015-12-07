package com.saad.asaad.toucheventassignment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

public class MainActivity extends Activity
{
    private GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        gv = new GameView(this);

        FrameLayout outerLayout = new FrameLayout(this);
        outerLayout.addView(gv);

        setContentView(outerLayout);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gv.killThread();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gv.onDestroy();
    }
}