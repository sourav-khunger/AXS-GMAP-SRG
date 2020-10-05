package com.doozycod.axs.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.doozycod.axs.R;

public class PreviewImgActivity extends Activity {

    ImageView img;
    Button clearBtn, saveBtn;
    public static final String BROADCAST_ACTION = "com.unipos.tc.previewimg";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_preview_img);

        intent = new Intent(BROADCAST_ACTION);

        img = (ImageView) findViewById(R.id.taken_image);
        clearBtn = (Button) findViewById(R.id.clear_button);
        saveBtn = (Button) findViewById(R.id.save_button);


        if(CameraKitActivity.cubitmap != null) {

            img.setImageBitmap(CameraKitActivity.cubitmap);
        }

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewImgActivity.this.finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendBroadcast(intent);
                PreviewImgActivity.this.finish();
            }
        });
    }
}
