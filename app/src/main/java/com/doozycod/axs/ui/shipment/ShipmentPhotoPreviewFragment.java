package com.doozycod.axs.ui.shipment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.Utils.ServiceChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipmentPhotoPreviewFragment extends Fragment {

    // private ShipmentPhotoViewModel mViewModel;
    public static Bitmap bitmap;
    ImageView img;
    TextView curdate;
    RelativeLayout imageViewLayout;
    Button clearBtn, saveBtn;
    // DriverInfoRepository driverInfoRepository;
    String IMEI;

    public static ShipmentPhotoPreviewFragment newInstance() {
        return new ShipmentPhotoPreviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_show_preview_img, container, false);

        curdate = root.findViewById(R.id.curdate);
        imageViewLayout = root.findViewById(R.id.imageViewLayout);
        img = (ImageView) root.findViewById(R.id.taken_image);
        clearBtn = (Button) root.findViewById(R.id.clear_button);
        saveBtn = (Button) root.findViewById(R.id.save_button);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_shipmentPhotoPreviewFragment_to_shipmentPhotoFragment);
            }
        });
        String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        curdate.setText(curDateTime);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage(bitmap);
//                saveImage(createBitmapFromView(getActivity(), imageViewLayout));
                Navigation.findNavController(view).navigate(R.id.action_shipmentPhotoPreviewFragment_to_shipment_recipient);
            }
        });

        // driverInfoRepository = new DriverInfoRepository(getActivity().getApplication());
        IMEI = ServiceChecker.getUniqueIMEIId(getActivity());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // mViewModel = ViewModelProviders.of(this).get(ShipmentPhotoViewModel.class);

        img.setImageBitmap(bitmap);
        // TODO: Use the ViewModel
    }

    private Bitmap createBitmapFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public void saveImage(Bitmap bitmap) {

        Toast.makeText(getActivity().getApplicationContext(), "Saving Photo", Toast.LENGTH_SHORT).show();

        //	String path;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/AXSLITE");
        myDir.mkdirs();
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String curDateTime = new SimpleDateFormat("yyyyMMddHHmmss.mmm").format(new Date());


        String localpath = root + "/AXSLITE/";
        String imgFileName = "";


        //DriverInfoRepository driverInfoRepository = new DriverInfoRepository(getActivity().getApplication());
        //List<DriverInfoEntity> driverInfoEntities = driverInfoRepository.getDriverInfo();
        String remoteImgDir = Constants.REMOTE_IMG_ROOT_DIR + "problems" + "/" + curDate + "/";
        if (!IMEI.equals("")) {
            remoteImgDir = Constants.REMOTE_IMG_ROOT_DIR + IMEI + "/" + curDate + "/";
        }


        try {
            String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
            LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);


            String selected_bol = ShipmentActivity.selectedTask.getBarcode();


            if (selected_bol != null && !selected_bol.trim().equals("")) {
                String imgPrefix = selected_bol + "_" + ShipmentActivity.selectedTask.getTaskId() + "_";

                imgFileName = imgPrefix + curDateTime + ".jpg";

            } else {
                String imgPrefix = "TASKERKIT_" + loginResponse.getDriverInfo().getImei() + "_";
                imgFileName = imgPrefix + curDateTime + ".jpg";
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

        FileOutputStream outFile = null;

        File file = new File(localpath, imgFileName);

        try {

            outFile = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);
            outFile.flush();
            outFile.close();
            String fileName = file.getPath();


            // saving local db
            ShipmentActivity.selectedTask.setImageTaken(1);

            String imgs = ShipmentActivity.selectedTask.getImagePath();

            if (imgs != null && !imgs.equals("")) {
                imgs += remoteImgDir + imgFileName;
            } else {
                imgs = remoteImgDir + imgFileName;
            }
            Log.e("TAG", "saveImage: " + imgs);

            ShipmentActivity.selectedTask.setImagePath(imgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}