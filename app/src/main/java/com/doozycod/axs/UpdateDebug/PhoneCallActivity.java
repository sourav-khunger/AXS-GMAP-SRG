package com.doozycod.axs.UpdateDebug;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mizuvoip.jvoip.SipStack;
import com.doozycod.axs.R;

import java.util.Calendar;
import java.util.TimeZone;

public class PhoneCallActivity extends AppCompatActivity {
    public static String LOGTAG = "FleetOptics";
    SipStack mysipclient = null;
    boolean terminateNotifThread = false;
    GetNotificationsThread notifThread = null;
    public static PhoneCallActivity instance = null;
    String currentDateandTime;
    Runnable myRunnable;
    boolean isRunning = false;
    boolean isSpeaked = false;
    String empContactNumber = "";
    TextView nameTV, mobileTV;
    SIPNotifications sipNotifications;
    Button endCallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);
        nameTV = findViewById(R.id.nameTV);
        mobileTV = findViewById(R.id.mobileTV);
        endCallBtn = findViewById(R.id.endCallBtn);

        empContactNumber = "011" + getIntent().getStringExtra("number");
        Log.e("TAG", "onCreate: " + getIntent().getStringExtra("name"));
        Log.e("TAG", "onCreate: " + getIntent().getStringExtra("number"));

        nameTV.setText(getIntent().getStringExtra("name"));
        mobileTV.setText(getIntent().getStringExtra("number"));


        voipCallStart();
        myRunnable = new Runnable() {
            @Override
            public void run() {
                finish();

            }
        };
        endCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mysipclient != null) {
                    DisplayLogs("Stop SipStack");
                    mysipclient.Stop(true);
                    finish();
                } else {
                    Toast.makeText(PhoneCallActivity.this, "Call wasn't connected", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
    }

    //    logs for voip call
    @SuppressLint("SimpleDateFormat")
    public void DisplayLogs(String logmsg) {
        if (logmsg == null || logmsg.length() < 1) return;

        if (logmsg.length() > 2500) logmsg = logmsg.substring(0, 300) + "...";
        logmsg = "[" + new java.text.SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime()) + "] " + logmsg + "\r\n";

        Log.e(LOGTAG, logmsg);
    }

    //    notification Thread
    public class GetNotificationsThread extends Thread {
        String sipnotifications = "";

        public void run() {
            try {
                try {
                    Thread.currentThread().setPriority(4);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                //we are lowering this thread priority a bit to give more chance for our main GUI thread

                while (!terminateNotifThread) {
                    try {
                        sipnotifications = "";
                        if (mysipclient != null) {
                            //get notifications from the SIP stack
                            sipnotifications = mysipclient.GetNotificationsSync();

                            if (sipnotifications != null && sipnotifications.length() > 0) {
                                // send notifications to Main thread using a Handler
                                Message messageToMainThread = new Message();
                                Bundle messageData = new Bundle();
                                messageToMainThread.what = 0;
                                messageData.putString("notifmessages", sipnotifications);
                                messageToMainThread.setData(messageData);
                                NotifThreadHandler.sendMessage(messageToMainThread);
                            }
                        }

                        if ((sipnotifications == null || sipnotifications.length() < 1) && !terminateNotifThread) {
                            //some error occured. sleep a bit just to be sure to avoid busy loop
                            SIPNotifications.sleep(1);
                        }
                        continue;
                    } catch (Throwable e) {
                        Log.e(LOGTAG, "ERROR, WorkerThread on run()intern", e);
                    }
                    if (!terminateNotifThread) {
                        SIPNotifications.sleep(10);
                    }
                }
            } catch (Throwable e) {
                Log.e(LOGTAG, "WorkerThread on run()");
            }
        }
    }

    //get the notifications from the GetNotificationsThread thread
    @SuppressLint("HandlerLeak")
    public static Handler NotifThreadHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if (msg == null || msg.getData() == null) return;

                Bundle resBundle = msg.getData();

                String receivedNotif = msg.getData().getString("notifmessages");

                if (receivedNotif != null && receivedNotif.length() > 0)
                    instance.ReceiveNotifications(receivedNotif);

            } catch (Throwable e) {
                Log.e(LOGTAG, "NotifThreadHandler handle Message" + e);
            }
        }
    };
    //process notificatins phrase 1: split by line (we can receive multiple notifications separated by \r\n)
    String[] notarray = null;

    //    public void ProcessNotifications(String receivednot) {
//        if (receivednot == null || receivednot.length() < 1) return;
//        // we can receive multiple notifications at once, so we split them by CRLF or with ",NEOL \r\n" and we end up with a
//        String array_of_notifications;
//        String[] notarray = receivednot.split(",NEOL \n");
//        for (int i = 0; i < notarray.length; i++) {
//            String notifywordcontent = notarray[i];
//            if (notifywordcontent == null || notifywordcontent.length() < 1) continue;
//            notifywordcontent = notifywordcontent.trim();
//            notifywordcontent = notifywordcontent.replace("WPNOTIFICATION,", "");
//            // now we have a single notification in the "notifywordcontent" String variable
//            Log.v("AJVOIP", "Received Notification: " + notifywordcontent);
//            int pos = 0;
//            String notifyword1 = ""; // will hold the notification type
//            String notifyword2 = ""; // will hold the second most important String in the STATUS notifications, which is the
////            third parameter, right after the "line' parameter
//            // First we are checking the first parameter (until the first comma) to determine the event type.
//            // Then we will check for the other parameters.
//            pos = notifywordcontent.indexOf(",");
//            if (pos > 0) {
//                notifyword1 = notifywordcontent.substring(0, pos).trim();
//                notifywordcontent = notifywordcontent.substring(pos + 1, notifywordcontent.length()).trim();
//            } else {
//                notifyword1 = "EVENT";
//            }
//            // Notification type, "notifyword1" can have many values, but the most important ones are the STATUS types.
//            // After each call, you will receive a CDR (call detail record). We can parse this to get valuable information
////            about the latest call.
//            // CDR,line, peername,caller, called,peeraddress,connecttime,duration,discparty,reasontext
//            // Example: CDR,1, 112233, 445566, 112233, voip.mizu-voip.com, 5884, 1429, 2, bye received
//            if (notifyword1.equals("CDR")) {
//                String[] cdrParams = notifywordcontent.split(",");
//                String line = cdrParams[0];
//                String peername = cdrParams[1];
//                String caller = cdrParams[2];
//                String called = cdrParams[3];
//                String peeraddress = cdrParams[4];
//                String connecttime = cdrParams[5];
//                String duration = cdrParams[6];
//                String discparty = cdrParams[7];
//                String reasontext = cdrParams[8];
//            }
//            // lets parse a few STATUS notifications
//            else if (notifyword1.equals("STATUS")) {
//                //ignore line number. we are not handling it for now
//                pos = notifywordcontent.indexOf(",");
//                if (pos > 0)
//                    notifywordcontent = notifywordcontent.substring(pos + 1, notifywordcontent.length()).trim();
//                pos = notifywordcontent.indexOf(",");
//                if (pos > 0) {
//                    notifyword2 = notifywordcontent.substring(0, pos).trim();
//                    notifywordcontent = notifywordcontent.substring(pos + 1, notifywordcontent.length()).trim();
//                } else {
//                    notifyword2 = notifywordcontent;
//                }
//                if (notifyword2.equals("Registered.")) {
//                    // means the SDK is successfully registered to the specified VoIP server
//                } else if (notifyword2.equals("CallSetup")) {
//                    // a call is in the setup stage
//                } else if (notifyword2.equals("Ringing")) {
//                    // check the other parameters to see if it an incoming call and display an alert for the user
//                } else if (notifyword2.equals("CallConnect")) {
//                    // call was just connected
//                } else if (notifyword2.equals("CallDisconnect")) {
//                    // call was just disconnected
//                } else if (notifyword1.equals("CHAT")) {
//                    // we received an incoming chat message (parse the other parameters to get the sender name and the text to
////                    be displayed) }
//                } else if (notifyword1.equals("ERROR")) {
//                    // we received an error notification; at least log it somewhere
//                    Log.e("AJVOIP", "ERROR," + notifywordcontent);
//                } else if (notifyword1.equals("WARNING")) {
//                    // we received a warning notification; at least log it somewhere
//                    Log.w("AJVOIP", "WARNING," + notifywordcontent);
//                } else if (notifyword1.equals("EVENT")) {
//                    // display important event for the user
//                    Log.e("AJVOIP", notifywordcontent);
//                }
//            }
//        }
//
    public class SIPNotifications extends Thread {
        boolean terminated = false; //thread state
        SipStack mysipclient = null;

        //ctor
        public SIPNotifications(SipStack mysipclient_in) {
            mysipclient = mysipclient_in;
        }

        //start thread
        public boolean Start() {
            try {
                this.start();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        //use this function from external code when the sipstack is not needed anymore (when you app is closing).
        public void Stop() {
            terminated = true;
        }

        public void run() {
            try {
                //variable to hold the incoming messages
                String notifications;
                //continuous blocking read until thread is terminated:
                while (!terminated) {
                    notifications = mysipclient.GetNotificationsSync(); //get the notifications from the SIP stack
                    if (notifications.length() > 0) {
                        ProcessNotifications(notifications);
                    }
                }
            } catch (Exception e) {
                if (!terminated) e.printStackTrace();
            }
        }

        //parse sdk messages and modify your state (user interface, program logic) accordingly. This might not be necessary for simple basic usage.
        public void ProcessNotifications(String receivednot) {
            Log.e("FLEET", "ProcessNotifications: " + receivednot + "\r\n");
//                System.out.println("received from ajvoip: " + msg + "\r\n");
            //process notifications here (change your user interface or business logic depending on the sipstack state / call state).
            //see the Notifications section in the documentation for the details. Example code can be found here.
            if (receivednot == null || receivednot.length() < 1) return;
            // we can receive multiple notifications at once, so we split them by CRLF or with ",NEOL \r\n" and
            // we end up with aString array of notifications
            String[] notarray = receivednot.split("," + empContactNumber + " \n");
            for (int i = 0; i < notarray.length; i++) {
                String notifywordcontent = notarray[i];
                if (notifywordcontent == null || notifywordcontent.length() < 1) continue;
                notifywordcontent = notifywordcontent.trim();
                notifywordcontent = notifywordcontent.replace("WPNOTIFICATION,", "");
                // now we have a single notification in the "notifywordcontent" String variable
                Log.v("AJVOIP", "Received Notification: " + notifywordcontent);
                int pos = 0;
                String notifyword1 = ""; // will hold the notification type
                String notifyword2 = ""; // will hold the second most important String in the STATUS notifications,
                // which is thethird parameter, right after the "line' parameter
                // First we are checking the first parameter (until the first comma) to determine the event type.
                // Then we will check for the other parameters.
                pos = notifywordcontent.indexOf(",");
                if (pos > 0) {
                    notifyword1 = notifywordcontent.substring(0, pos).trim();
                    notifywordcontent = notifywordcontent.substring(pos + 1, notifywordcontent.length()).trim();
                } else {
                    notifyword1 = "EVENT";
                }

                // Notification type, "notifyword1" can have many values, but the most important ones are the STATUS types.
                // After each call, you will receive a CDR (call detail record). We can parse this to get valuable
                // information about the latest call.
                // CDR,line, peername,caller, called,peeraddress,connecttime,duration,discparty,reasontext
                // Example: CDR,1, 112233, 445566, 112233, voip.mizu-voip.com, 5884, 1429, 2, bye received
                if (notifyword1.equals("CDR")) {
                    String[] cdrParams = notifywordcontent.split(",");
                    String line = cdrParams[0];
                    String peername = cdrParams[1];
                    String caller = cdrParams[2];
                    String called = cdrParams[3];
                    String peeraddress = cdrParams[4];
                    String connecttime = cdrParams[5];
                    String duration = cdrParams[6];
                    String discparty = cdrParams[7];
                    String reasontext = cdrParams[8];
                }
                // lets parse a few STATUS notifications
                else if (notifyword1.equals("STATUS")) {
                    //ignore line number. we are not handling it for now
                    pos = notifywordcontent.indexOf(",");
                    if (pos > 0)
                        notifywordcontent = notifywordcontent.substring(pos + 1, notifywordcontent.length()).trim();
                    pos = notifywordcontent.indexOf(",");
                    if (pos > 0) {
                        notifyword2 = notifywordcontent.substring(0, pos).trim();
                        notifywordcontent = notifywordcontent.substring(pos + 1, notifywordcontent.length()).trim();
                    } else {
                        notifyword2 = notifywordcontent;
                    }
                    if (notifyword2.equals("Registered.")) {
                        // means the SDK is successfully registered to the specified VoIP server
                    } else if (notifyword2.equals("CallSetup")) {
                        // a call is in the setup stage
                    } else if (notifyword2.equals("Ringing")) {
                        Toast.makeText(PhoneCallActivity.this, "Ringing", Toast.LENGTH_SHORT).show();
                        // check the other parameters to see if it an incoming call and display an alert for the user
                    } else if (notifyword2.equals("CallConnect")) {
                        Toast.makeText(PhoneCallActivity.this, "CallConnect", Toast.LENGTH_SHORT).show();

                        // call was just connected
                    } else if (notifyword2.equals("CallDisconnect")) {
                        Toast.makeText(PhoneCallActivity.this, "CallDisconnect", Toast.LENGTH_SHORT).show();
                        finish();
                        // call was just disconnected
                    } else if (notifyword1.equals("CHAT")) {
                        // we received an incoming chat message (parse the other parameters to get the
                        // sender name and the text tobe displayed)
                    }
                } else if (notifyword1.equals("ERROR")) {
                    Toast.makeText(PhoneCallActivity.this, notifywordcontent, Toast.LENGTH_SHORT).show();
                    finish();
                    // we received an error notification; at least log it somewhere
                    Log.e("AJVOIP", "ERROR," + notifywordcontent);
                } else if (notifyword1.equals("WARNING")) {
                    Toast.makeText(PhoneCallActivity.this, notifywordcontent, Toast.LENGTH_SHORT).show();
                    // we received a warning notification; at least log it somewhere
                    Log.e("AJVOIP", "WARNING," + notifywordcontent);
                } else if (notifyword1.equals("EVENT")) {
                    // display important event for the user
                    Log.e(LOGTAG, notifywordcontent);
                }
            }

        }
    }


    //    receiving status of call
    public void ReceiveNotifications(String notifs) {
        if (notifs == null || notifs.length() < 1) return;
        notarray = notifs.split("\r\n");

        if (notarray == null || notarray.length < 1) return;

        for (String s : notarray) {
            if (s != null && s.length() > 0) {
                ProcessNotifications(s);
            }
        }
        /*for (int i = 0; i < notarray.length; i++) {
            if (notarray[i] != null && notarray[i].length() > 0) {
                ProcessNotifications(notarray[i]);
            }
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DisplayLogs("ondestroy");
        terminateNotifThread = true;
        if (mysipclient != null) {
            DisplayLogs("Stop SipStack");
            mysipclient.Stop(true);
        }
        mysipclient = null;
        notifThread = null;
    }

    //    VOIP call method
    void voipCallStart() {
        DisplayLogs("Start on click");
        try {
            // start SipStack if it's not already running
            if (mysipclient == null) {
                DisplayLogs("Start SipStack");

                //initialize the SIP engine
                mysipclient = new SipStack();
                mysipclient.Init(this);
//                mysipclient.SetParameter("loglevel", "5"); //set loglevel
//                mysipclient.SetParameter("serveraddress", "voip.generatcartage.com"); //set your voip server domain or IP:port
//                mysipclient.SetParameter("username", "7599"); //set SIP username
//                mysipclient.SetParameter("password", "rATgcjgeKvEVIQtbQegt"); //set SIP password

                mysipclient.SetParameter("loglevel", "5"); //set loglevel
                mysipclient.SetParameter("serveraddress", "voip.generalcartage.com"); //set your voip server domain or IP:port
                mysipclient.SetParameter("username", "7081"); //set SIP username
                mysipclient.SetParameter("password", "btv2qLVndAL7PMNKZux6"); //set SIP password

                //start my event listener thread
//                notifThread = new GetNotificationsThread();
//                notifThread.start();
                SIPNotifications sipnotifications = new SIPNotifications(mysipclient);
                sipnotifications.Start();
//                customProgressBar.showProgress();

                //start the SIP engine
                mysipclient.Start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mysipclient.Call(-1, empContactNumber);
                        mysipclient.SetSpeakerMode(!mysipclient.IsLoudspeaker());
                    }
                }, 1500);

                //mysipclient.Register();

            } else {
                DisplayLogs("SipStack already started");
            }
        } catch (Exception e) {
            DisplayLogs("ERROR, StartSipStack");
        }
    }

    //process notificatins phrase 2: processing notification strings
    public void ProcessNotifications(String line) {
        DisplayStatus(line); //we just display them in this simple test application
        //see the Notifications section in the documentation about the possible messages (parse the line string and process them after your needs)
    }

    //    check Call connection/state
    @SuppressLint("SetTextI18n")
    public void DisplayStatus(String stat) {
        if (stat == null) return;

        DisplayLogs("Status: 11 " + stat);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

//            }
//        });
        //        if call state is CALLING / RINGING
        if (stat.contains("Calling") || stat.contains("Ringing")) {
            Log.e(LOGTAG, "DisplayStatus: Ringing...");

        }

//        if call state is SPEAKING/ IN PROGRESS
        if (stat.contains("Speaking (")) {

            Log.e(LOGTAG, "DisplayStatus: Speaking");

            isSpeaked = true;

        }
//        if call state is REJECTED OR NOT PICKED UP
        if (stat.contains("call rejected: Busy Here") || stat.contains("503 server failure Service Unavailable") || stat.contains("Service Unavailable")) {

//                go back to home screen
            delayToHome();
        }
//        if call state is DISCONNECT
        if (stat.contains("Call duration: 0 sec") || stat.contains("Finished,") || stat.contains("Call Finished")) {


            if (isSpeaked) {
                Log.e(LOGTAG, "DisplayStatus: Already Speaked");
            } else {


            }
//                go back to home screen
            delayToHome();
        }
    }

    //    10 sec delay to to go back home activity
    void delayToHome() {
        if (!isRunning) {
            isRunning = true;
//        handler to return to home screen after ten seconds
            new Handler().postDelayed(myRunnable, 10000);
        }
    }
}