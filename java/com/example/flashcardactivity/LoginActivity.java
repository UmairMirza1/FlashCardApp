package com.example.flashcardactivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements LoginService.observer {

    private EditText email;
    private EditText password;
    private IBinder Binder;
    private Button loginbutton ;
    private LoginService loginservice;
    private boolean bound = false;
    Messenger messenger;
    FirebaseAuth mAuth;
    Messenger incomingMessenger = new Messenger(new IncomingHandler());
    ActivityResultLauncher<Intent> MainLauncher;
    ConnectivityReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        email= (EditText) findViewById(R.id.email);
        password = ( EditText)  findViewById(R.id.password);
        loginbutton =( Button) findViewById(R.id.loginbutton);
        MainLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            // mAdapter.notifyDataSetChanged();

                        }
                        if(result.getResultCode()== RESULT_CANCELED){

                        }
                    }
                });



        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLoginservice();

            }
        });
        mAuth = FirebaseAuth.getInstance();
        IntentFilter intent = new
                IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new ConnectivityReceiver();
        registerReceiver(receiver,intent);


    }

  private  ServiceConnection connection = new ServiceConnection() {
      @Override
      public void onServiceConnected(ComponentName componentName, IBinder iBinder) {



        //  LoginService.LoginBinder binder = (LoginService.LoginBinder) iBinder;
         // loginservice= binder.getService();
          //bound=true;

          messenger = new Messenger(iBinder);
          bound = true;

          Toast.makeText(getApplicationContext(), "sending msg ", Toast.LENGTH_SHORT);
          Message message = Message.obtain(null,1);
          message.replyTo = incomingMessenger;
          Bundle data= new Bundle();

          data.putString("id",email.getText().toString());
          data.putString("pass", password.getText().toString());
         // data.putSerializable("");
          message.setData(data);
          try {
              messenger.send(message);
          } catch (RemoteException e) {
              Log.i("Notes","-------- exception --------");
          }

      }

      @Override
      public void onServiceDisconnected(ComponentName componentName) {
            bound=false;
      }
  } ;

    @Override
    public void update() {

    }


    public class IncomingHandler extends Handler {

        public void handleMessage(Message msg){

            switch(msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    if (bundle.getInt("result")==1){
                        launchmain(email.getText().toString());
                        //Toast.makeText(getApplicationContext(),bundle.getString("result"),Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();

                    }

                    default:
                    super.handleMessage(msg);
            }
        }
    }

public void startLoginservice(){

    Intent intent= new Intent(LoginActivity.this,LoginService.class);
    bindService(intent,connection,BIND_AUTO_CREATE);


}

public void launchmain(String email)
{

    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    intent.putExtra("email", email);
    MainLauncher.launch(intent);


}

@Override
    public void onStart () {
    super.onStart();
    FirebaseUser user = mAuth.getCurrentUser();
   /* if ( user!=null)
    {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        MainLauncher.launch(intent);
    } */

}
@Override
    public void onDestroy() {


    super.onDestroy();
    unregisterReceiver(receiver);
}

}
