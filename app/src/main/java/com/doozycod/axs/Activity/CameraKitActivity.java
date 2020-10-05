package com.doozycod.axs.Activity;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.doozycod.axs.R;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraKitActivity extends Activity {

    public static final String BROADCAST_PC_TAKEN_ACTION = "com.unipos.tc.picktiretaken";

    private CameraView cameraView;
    private Button captureBtn;
    public static Bitmap cubitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camkit);

        init_variables();

        cameraView = (CameraView) findViewById(R.id.camera);
        captureBtn = (Button) findViewById(R.id.captureBtn);

        cameraView.setJpegQuality(50);
        cameraView.setFocus(CameraKit.Constants.FOCUS_TAP);
        cameraView.setFlash(CameraKit.Constants.FLASH_AUTO);
        cameraView.setMethod(CameraKit.Constants.METHOD_STILL);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bmap = cameraKitImage.getBitmap();

                cubitmap = bmap;
                // saveImage(bmap);

                Intent intent = new Intent(CameraKitActivity.this, PreviewImgActivity.class);
                startActivity(intent);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cameraView.captureImage();
            }
        });


        try {
            registerReceiver(imgConfBroadcastReceiver, new IntentFilter(PreviewImgActivity.BROADCAST_ACTION));
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
            CameraKitActivity.this.finish();
        }
    }


    private BroadcastReceiver imgConfBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            saveImage(cubitmap);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        try {
            unregisterReceiver(imgConfBroadcastReceiver);
        } catch (IllegalArgumentException e) {

        }

        super.onDestroy();
    }


    public static String unix_time_stamp_name;
    public static String subPath;
    public static String fileName;

    public static String STATIC_ID, BOL_S, arrive_s;
    public static int STATIC_NEWCODATARECID;
    public static String Type;
    public String root;



    public void init_variables() {

        unix_time_stamp_name = "";
    }

    public void saveImage(Bitmap bitmap) {
        unix_time_stamp_name = "";
  /*      try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date curdate = format.parse(prefs.getString("curdate", ""));
            // Format formatter3 = new SimpleDateFormat("dd-MMM-yyyy");
            Format formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            String today3 = formatter3.format(curdate);
            subPath = "/scan/Drivers/" + prefs.getString("s_app_id", "") + "/" + today3 + "/";


            //	String path;
            root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/NTASKER");
            myDir.mkdirs();
            //Date date=new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            //System.out.println(dateFormat.format(date));
            long unix_timestamp = CommonFunction.strDateToUnixTimestamp(dateFormat.format(date));

            String selected_bol = "";
            try {
                Bundle b = this.getIntent().getExtras();
                selected_bol = b.getString("bol");
            } catch (Exception e) {
                selected_bol = "";
            }

            if (selected_bol != null && !selected_bol.trim().equals("")) {
                String imgPrefix = selected_bol + "_" + prefs.getString("password", "") + "_";


                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);

                unix_time_stamp_name = imgPrefix + unix_timestamp + n + ".jpg";
                Log.d("StringToTimestamp==", "" + unix_timestamp);
                fileName = root + "/NTASKER/" + imgPrefix + unix_timestamp + n + ".jpg";

            } else {
                String imgPrefix = "TASKERKIT_" + prefs.getString("password", "") + "_" ;


                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);

                unix_time_stamp_name = imgPrefix + unix_timestamp + n + ".jpg";
                Log.d("StringToTimestamp==", "" + unix_timestamp);
                fileName = root + "/NTASKER/" + imgPrefix + unix_timestamp + n + ".jpg";
            }

        } catch (Exception e) {


            e.printStackTrace();
        }

*/
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


        String path = root + "/NTASKER/";
        FileOutputStream outFile = null;

        File file = new File(path, unix_time_stamp_name);

        try {

            outFile = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);

            outFile.flush();

            outFile.close();

            fileName = file.getPath();

            if(CameraKitActivity.Type.equals("EditTask")) {
                Intent intent = new Intent(BROADCAST_PC_TAKEN_ACTION);
                sendBroadcast(intent);
            }

            try {

            } catch (Exception e) {
                saveImagetoDatabse();
            }

        }
        catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.finish();
    }

    public void saveImagetoDatabse() {


    }

    public void geoTag(String filename, double latitude1, double longitude1){
        ExifInterface exif;
        
        try {
            double latitude = Math.abs(latitude1);
            double longitude = Math.abs(longitude1);

            exif = new ExifInterface(filename);
            int num1Lat = (int)Math.floor(latitude);
            int num2Lat = (int)Math.floor((latitude - num1Lat) * 60);
            double num3Lat = (latitude - ((double)num1Lat+((double)num2Lat/60))) * 3600000;

            int num1Lon = (int)Math.floor(longitude);
            int num2Lon = (int)Math.floor((longitude - num1Lon) * 60);
            double num3Lon = (longitude - ((double)num1Lon+((double)num2Lon/60))) * 3600000;

            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat+"/1,"+num2Lat+"/1,"+num3Lat+"/1000");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon+"/1,"+num2Lon+"/1,"+num3Lon+"/1000");


            if (latitude1 > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
            }

            if (longitude1 > 0) {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
            } else {
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
            }

            exif.saveAttributes();

            saveImagetoDatabse();

        } catch (IOException e) {
            Log.e("PictureActivity", e.getLocalizedMessage());
            saveImagetoDatabse();
        }

    }
}
