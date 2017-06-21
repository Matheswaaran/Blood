package com.example.mat.blood.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.mat.blood.pojo.UserProfile;
import com.example.mat.blood.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.mat.blood.utils.DefaultCode.DEFAULT_TIMESTAMP_FORMAT;
import static com.example.mat.blood.utils.DefaultCode.FIREBASE_USERS;
import static com.example.mat.blood.utils.DefaultCode.PREF_USER_PROFILE;

/**
 * Created by allu on 6/18/17.
 */

public class Utils {
    public String TAG = Utils.class.getSimpleName();
    Context context;

    ProgressDialog progressDialog;

    static UserProfile userProfile;
    Gson gson;

    SharedPreferences sharedPreferences;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference;

    public Utils(Context context) {
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        sharedPreferences = context.getSharedPreferences(DefaultCode.PREF_STRING,Context.MODE_PRIVATE);
    }

    public void setProgressMessage(String Msg){
        this.progressDialog.setMessage(Msg);
    }

    public void showProgressDialog(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    public void hideProgressDialog(){
        if(progressDialog.isShowing()){
            progressDialog.hide();
        }
    }

    public void Goto(Class destination){
        Intent i = new Intent(context,destination);
        context.startActivity(i);
    }

    public void Toast(String Msg){
        Toast.makeText(context,Msg,Toast.LENGTH_SHORT).show();
    }

    public void loadUserDetails(){
        if(sharedPreferences.contains(PREF_USER_PROFILE)){
            gson = new Gson();
            String json = sharedPreferences.getString(PREF_USER_PROFILE,"");
            userProfile = gson.fromJson(json,UserProfile.class);
            Log.e(TAG,userProfile.getEmailId());
        }else {
            setProgressMessage("Loading User Data's");
            showProgressDialog();
            firebaseDatabase = FirebaseDatabase.getInstance();
            if(!getUserUid().equals("")){
                userReference = firebaseDatabase.getReference(FIREBASE_USERS).child(getUserUid());
                userReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hideProgressDialog();
                        if(dataSnapshot.getValue() == null){
                            Toast("Error in fetching the UserData");
                            signOut();
                        }else{
                            Log.e(TAG,"UserSignIn");
                            storePref(dataSnapshot.getValue(UserProfile.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        hideProgressDialog();
                        Log.e(TAG,"firebase error"+databaseError.toString());
                    }
                });
            }
        }
    }

    public String getTimeStamp(){
        String timeStamp =new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT, Locale.getDefault()).format(new Date());
        return timeStamp;
    }

    public String getUserUid(){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            signOut();
            return "";
        }else {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

    }

    public String getUserName(){
        return userProfile.getUserName();
    }

    public UserProfile getSignInUser() {
        return userProfile;
    }

    public void storePref(UserProfile user){
        userProfile = user;
        gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER_PROFILE,gson.toJson(user));
        editor.apply();
        Log.e(TAG,"profile updated");
    }

    public void signOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREF_USER_PROFILE);
        editor.apply();
        userProfile = null;
        Log.e(TAG,"signout");
        FirebaseAuth.getInstance().signOut();
        Goto(LoginActivity.class);
    }


    public boolean isEmptyString(String... Args){
        for (String value: Args) {
            if(value.equals("") || value.isEmpty()){
                return true;
            }
        }
        return false;
    }

}
