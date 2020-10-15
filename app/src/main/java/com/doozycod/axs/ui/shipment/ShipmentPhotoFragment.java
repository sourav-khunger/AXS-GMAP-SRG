package com.doozycod.axs.ui.shipment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.R;
import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkPosition;
import com.watermark.androidwm.bean.WatermarkText;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipmentPhotoFragment extends Fragment {

    private CameraView cameraView;
    private Button captureBtn;
    // private ShipmentPhotoViewModel mViewModel;
    View root;

    public static ShipmentPhotoFragment newInstance() {
        return new ShipmentPhotoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_camkit, container, false);


        //mViewModel = ViewModelProviders.of(this).get(ShipmentPhotoViewModel.class);

        cameraView = (CameraView) root.findViewById(R.id.camera);
        captureBtn = (Button) root.findViewById(R.id.captureBtn);

        //cameraView.setJpegQuality(50);
        cameraView.setJpegQuality(20);
        cameraView.setFocus(CameraKit.Constants.FOCUS_TAP);
        cameraView.setFlash(CameraKit.Constants.FLASH_AUTO);
        cameraView.setMethod(CameraKit.Constants.METHOD_STILL);
        String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        WatermarkText watermarkText = new WatermarkText(curDateTime)
                .setPositionX(0.34)
                .setPositionY(0.95)
                .setTextColor(Color.RED)
                .setTextFont(R.font.roboto)
                .setTextShadow(0, 5, 3, Color.BLACK)
                .setTextAlpha(255)
                .setTextSize(15);
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

//                WatermarkBuilder
//                        .create(getActivity(), bmap)
//                        .loadWatermarkText(watermarkText) // use .loadWatermarkImage(watermarkImage) to load an image.
//                        .getWatermark()
//                        .getOutputImage();/
                //                Bitmap bitmap =mark(bmap,curDateTime,new Point(5,-15),0,20,false);
                ShipmentPhotoPreviewFragment.bitmap = WatermarkBuilder
                        .create(getActivity(), bmap)
                        .loadWatermarkText(watermarkText)
                        .getWatermark().getOutputImage();


                Navigation.findNavController(root).navigate(R.id.action_shipmentPhotoFragment_to_shipmentPhotoPreviewFragment);
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

        return root;
    }

    public static Bitmap mark(Bitmap src, String watermark, Point location, int alpha, int size, boolean underline) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, location.x, location.y, paint);

        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cameraView.start();
    }
}