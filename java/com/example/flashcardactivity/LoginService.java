package com.example.flashcardactivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.Executor;

public class LoginService extends Service {


    public interface observer  {

        void update();

    }

    private observer serviceobserver;

    private final Binder mBinder = new LoginBinder();
    Messenger messenger = new Messenger(new IncomingHandler());
    final int MSG_TEST=1;
    public int DB_RESULT=0;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseDatabase rootnode;
    DatabaseReference ref;
    String res;

    @Override
    public void onCreate (){

       //Toast.makeText(this,"Service started",Toast.LENGTH_SHORT).show();
       // super.onCreate();
    }


    public int onStartCommand(Intent intent,int flags,int startId){
        Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    public class LoginBinder extends Binder{
        public LoginService getService(){
            return LoginService.this;

        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"Binding",Toast.LENGTH_SHORT).show();
      //  return mBinder;
    return messenger.getBinder();

    }



    public class IncomingHandler extends Handler {

        public void handleMessage(Message msg){
            switch(msg.what) {

                case MSG_TEST:


                    Bundle bundle = new Bundle();

                    Message reply = Message.obtain(null, 1);
                    Bundle bun = msg.getData();
                    String testemail = bun.getString("id");
                    String testpass = bun.getString("pass");
                    Messenger receiver =msg.replyTo;

                    mAuth.signInWithEmailAndPassword(testemail, testpass)
                            .addOnCompleteListener(( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("fb2", "signInWithEmail:success");

                                        bundle.putInt("result", 1);
                                        //bundle.putString("email",testemail);
                                        reply.setData(bundle);

                                        try {
                                            receiver.send(reply);
                                        } catch (RemoteException ex) {
                                            ex.printStackTrace();
                                        }



                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("fb3", "signInWithEmail:failure", task.getException());


                                    }
                                }
                            }));



                default:
                    super.handleMessage(msg);


            }}}
       /* private int checkfirebase(String testemail, String testpass) {

            rootnode= FirebaseDatabase.getInstance();
            ref= rootnode.getReference("users");
//            rootnode.setPersistenceEnabled(true);
           // final int[] res = {0};
           // Query authenticate = ref.orderByChild("id").equalTo(testemail);
          //  User user= new User(testemail,testpass);
         //   ArrayList<User> data = new ArrayList<>();

        //  ref.child(testemail).setValue(user);

            DatabaseReference childref= ref.child(testemail).child("pass");
            Task<DataSnapshot> d = childref.get();


     /*       childref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    res="";
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                         res = String.valueOf(task.getResult().getValue());
                        if ( res.equals(testpass))
                        {
                            setDB_RESULT(1);
                        }
                     //   Toast.makeText(getApplicationContext(),String.valueOf(task.getResult().getValue()),Toast.LENGTH_SHORT );
                    }
                }
            }); */

    public void onDestroy(){

        Toast.makeText(this,"Service stopped",Toast.LENGTH_SHORT).show();
    }



}

