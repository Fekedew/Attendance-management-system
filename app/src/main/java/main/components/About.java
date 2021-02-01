package main.components;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import fekedew.R;

public class About extends AppCompatActivity {

    Animation anim1, anim2, anim3, anim4;

    TextView phones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        moveViewToScreenCenter(findViewById(R.id.email));
        moveViewToScreenCenter1(findViewById(R.id.phones));
        moveViewToScreenCenter2(findViewById(R.id.names));
        moveIcon(findViewById(R.id.imageViewAbout));


        phones = findViewById(R.id.phones);
        phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", phones.getText());
                manager.setPrimaryClip(clipData);
                Toast.makeText(About.this, "Phone copied: "+phones.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void moveIcon(View view) {
        int[] originalPos = new int[2];
        view.getLocationOnScreen(originalPos);

        anim3 = new TranslateAnimation(0, 0, 0, originalPos[1] + 100);
        anim3.setDuration(2000);
        anim3.setFillAfter(true);
        view.startAnimation(anim3);
    }

    private void moveViewToScreenCenter(View view) {
        RelativeLayout root = findViewById(R.id.ctr);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int[] originalPos = new int[2];
        view.getLocationOnScreen(originalPos);

        int yDest = dm.heightPixels / 2 - (view.getMeasuredHeight() / 2) - statusBarOffset;

        anim4 = new TranslateAnimation(0, 0, 0, yDest - originalPos[1] + 250);
        anim4.setDuration(1500);
        anim4.setFillAfter(true);
        view.startAnimation(anim4);
    }

    private void moveViewToScreenCenter1(View view) {
        RelativeLayout root = findViewById(R.id.ctr);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int[] originalPos = new int[2];
        view.getLocationOnScreen(originalPos);

        int yDest = dm.heightPixels / 2 - (view.getMeasuredHeight() / 2) - statusBarOffset;

        anim2 = new TranslateAnimation(0, 0, 0, yDest - originalPos[1] + 200);
        anim2.setDuration(1500);
        anim2.setFillAfter(true);
        view.startAnimation(anim2);
    }

    private void moveViewToScreenCenter2(View view) {
        RelativeLayout root = findViewById(R.id.ctr);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

        int[] originalPos = new int[2];
        view.getLocationOnScreen(originalPos);

        int yDest = dm.heightPixels / 2 - (view.getMeasuredHeight() / 2) - statusBarOffset;

        anim1 = new TranslateAnimation(0, 0, 0, yDest - originalPos[1] + 150);
        anim1.setDuration(1500);
        anim1.setFillAfter(true);
        view.startAnimation(anim1);
    }

}
