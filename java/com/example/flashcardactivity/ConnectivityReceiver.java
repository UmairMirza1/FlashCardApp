package com.example.flashcardactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.Toast;


public class ConnectivityReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent){

            String message = "Connection change occurred";

            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
                );
                if (noConnectivity) {
                    Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                }
            }
        }

        /*   SharedPreferences preferences = context.getSharedPreferences("service",Context.MODE_PRIVATE);
            boolean started = preferences.getBoolean("started",false);
            SharedPreferences.Editor editor = preferences.edit();

            if(!started){


                editor.putBoolean("started",true);
            }
            else{


                editor.putBoolean("started",false);
            }

            editor.commit();
            */


        }





