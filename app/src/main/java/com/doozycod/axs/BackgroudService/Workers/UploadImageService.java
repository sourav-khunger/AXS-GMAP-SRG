package com.doozycod.axs.BackgroudService.Workers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.Utils.ServiceChecker;

public class UploadImageService extends Service {

    Context context;

    final static int RQS_STOP_SERVICE = 1;

    Handler mHandler=new Handler();
    public static boolean servicestarted=false;

    SharedPreferences prefs;
    String mImagePath,sNickname,sPassword;
    public String path, subPath, IMEI;


    String  mCImageName, mCImageDate, mCNewcoID, mCBarcode;

    public static boolean imageupload=false, uploadStarted=false;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        super.onCreate();

        IMEI = ServiceChecker.getUniqueIMEIId(getApplicationContext());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        try {
            runa();
        } catch (Exception e) {
            // TODO Auto-generated catc block
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        servicestarted=false;

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public class NotifyServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            int rqs = arg1.getIntExtra("RQS", 0);
            if (rqs == RQS_STOP_SERVICE){
                stopSelf();
            }
        }
    }
    public void runa() throws Exception{
        mHandler.post(new Runnable(){
            public void run(){

                if(!uploadStarted) {
                    try {
                        String path = Environment.getExternalStorageDirectory().toString() + "/AXSLITE/";
                        Log.d("Files", "Path: " + path);
                        File directory = new File(path);
                        File[] files = directory.listFiles();
                        Log.d("Files", "Size: " + files.length);

                        if (files.length > 0) {
                            File file = files[0];

                            String fileDate = "";
                            if (android.os.Build.VERSION.SDK_INT >= 26) {
                                BasicFileAttributes attr =
                                        Files.readAttributes(Paths.get(path + file.getName()), BasicFileAttributes.class);

                                fileDate = new SimpleDateFormat("yyyy-MM-dd").format(attr.lastModifiedTime().toMillis());
                            } else {
                                fileDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                            }

                            String remoteImgDir = Constants.REMOTE_IMG_ROOT_DIR + "problems" + "/" + fileDate + "/";
                            if(!IMEI.equals("")) {
                                remoteImgDir = Constants.REMOTE_IMG_ROOT_DIR + IMEI + "/" + fileDate + "/";
                            }

                            mImagePath = path + file.getName();
                            subPath = remoteImgDir;

                            imageupload = false;
                            new UploadImage().execute();
                        }

                    } catch (Exception e) {
                        Log.d("EX", e.getMessage());
                    }
                }
                mHandler.postDelayed(this, 10000);
            }
        });
    }





    /******************************** Class to execute GetTaskList webservice  ***********************************/
    class UploadImage extends AsyncTask<String, Void, String> {
        StringBuilder sb=null;
        int GetTaskListCheckResponse;

        @Override
        protected void onPreExecute() {

            uploadStarted=true;
        }

        @Override
        protected String  doInBackground(String... params) {
            /***************** sftp code******************************/
            File uploadFilePath;
            Session session ;
            Channel channel = null;
            ChannelSftp sftp = null;

            sNickname="gtask";
            sPassword="gtask911";

            JSch ssh = new JSch();
            try {
                //session =ssh.getSession("ekaterina", "69.159.198.21");
                session =ssh.getSession(sNickname, "image.gcphone.pw");

                session.setPassword(sPassword);

                session.setPort(3434);

                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                channel = session.openChannel("sftp");
                channel.connect();
                sftp= (ChannelSftp)channel;

                path= subPath;
                Log.d("path is ",path);


                String[] folders = path.split( "/" );
                sftp.cd( "/" );
                for ( String folder : folders ) {
                    if ( folder.length() > 0 ) {
                        try {
                            sftp.cd( folder );
                            //Log.d("ppp ",folder);

                        }
                        catch ( SftpException e ) {
                            sftp.mkdir( folder );
                            sftp.cd( folder );
                            //Log.d("ppp2 ",folder);
                        }
                    }
                }

                try {
                    Log.d("pwd...", sftp.pwd());
                }catch (SftpException e){
                    Log.d("EX1", e.getMessage());
                }

            }
            catch(Exception e){
                Log.d("the sesson here",e.toString());
            }
            //uploadFilePath=new File(Environment.getExternalStorageDirectory()
            //	+"/completed.png");

            Log.d("the imagenamepath",mImagePath);
            uploadFilePath=new File(mImagePath);

            byte[] bufr = new byte[(int) uploadFilePath.length()];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(uploadFilePath);
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                fis.read(bufr);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            ByteArrayInputStream in = new ByteArrayInputStream(bufr);

            try {
                sftp.put(in, uploadFilePath.getName(), null);
                in.close();
                if (sftp.getExitStatus()==-1) {
                    Log.v("upload result", "succeeded");

                    GetTaskListCheckResponse=1;

                } else {
                    Log.v("upload faild ", "faild");

                    GetTaskListCheckResponse=2;
                }
            } catch (SftpException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                GetTaskListCheckResponse=2;
            }
            catch (Exception e) {
                // TODO: handle exception
                GetTaskListCheckResponse=2;
            }

            /***************** sftp code******************************/

            return "yes";
        }

        protected void onPostExecute(String result) {

            uploadStarted=false;
            switch(GetTaskListCheckResponse)
            {
                case 1:
                    imageupload=true;
                    try {
                        File file = new File(mImagePath);
                        boolean deleted = file.delete();
                        if (deleted) {
                            Log.d("deleting file  ", mImagePath);
                        }
                    } catch (Exception e) {

                    }
                    break;
                default:
                    imageupload=false;
                    break;
            }
        }
    }
}