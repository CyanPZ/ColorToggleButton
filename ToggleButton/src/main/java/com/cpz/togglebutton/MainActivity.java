package com.cpz.togglebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cpz.view.ToggleButton;

public class MainActivity extends AppCompatActivity
{
    private boolean state=true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ToggleButton toggleButton= (ToggleButton) findViewById(R.id.toggle_button);
        final TextView textView= (TextView) findViewById(R.id.tv);
        textView.setText(""+state);

        toggleButton.setWidth(100);
        toggleButton.setHeight(50);
        toggleButton.setClickable(true);
        toggleButton.setSliderOnColor(R.color.colorToggleOn);
        toggleButton.setSliderOffColor(R.color.colorToggleOff);
        toggleButton.setToggleState(state);
        toggleButton.setOnToggleStateChangeListener(new ToggleButton.OnToggleStateChangeListener()
        {
            @Override
            public void onToggleStateChange(boolean state)
            {
                textView.setText(""+state);
            }
        });
    }
}
