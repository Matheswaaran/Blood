package com.example.mat.blood.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mat.blood.R;
import com.example.mat.blood.pojo.UserProfile;
import com.example.mat.blood.utils.DefaultCode;
import com.example.mat.blood.utils.Utils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.mat.blood.utils.DefaultCode.FIREBASE_USERS;
import static com.example.mat.blood.utils.DefaultCode.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {
    String TAG = LoginActivity.class.getSimpleName();
    Utils utils;
    Context context;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    EditText Edit_EmailId,Edit_Password;
    Button Btn_SignIn;
    TextView Txt_createAccount;

    CardView Card_GoogleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils = new Utils(this);
        context = this;

        Card_GoogleSignIn = (CardView) findViewById(R.id.card_google_signin);

        Edit_EmailId = (EditText)findViewById(R.id.edit_email);
        Edit_Password = (EditText)findViewById(R.id.edit_password);
        Btn_SignIn = (Button)findViewById(R.id.btn_signin);
        Btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginUsingEmail();
            }
        });
        Card_GoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        Txt_createAccount = (TextView) findViewById(R.id.txt_create_account);
        Txt_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.Goto(RegisterActivity.class);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    storeUserDetails(user,false);
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this  , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        utils.setProgressMessage("Authenticating Please wait.");
        utils.showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            storeUserDetails(user,true);
                            Log.e(TAG, "signInWithCredential:success");
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            utils.Toast("Authentication failed.");
                        }
                        utils.hideProgressDialog();
                    }
                });
    }

    private void storeUserDetails(final FirebaseUser user,boolean signInFlag){
        Log.e(TAG,"init");
        if(user == null){
            utils.signOut();
            return;
        }
        if(signInFlag){
            final UserProfile userProfile = new UserProfile(user.getUid(),user.getDisplayName(),user.getEmail(),user.getPhotoUrl().toString());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(FIREBASE_USERS).child(user.getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() == null){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FIREBASE_USERS);
                        reference.child(user.getUid()).setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    utils.storePref(userProfile);
                                    Intent i = new Intent(context,RegisterActivity.class);
                                    i.putExtra(DefaultCode.INTENT_INITIAL_PROFILE_EDIT,true);
                                    i.putExtra(DefaultCode.INTENT_PROFILE_KEY,user.getUid());
                                    startActivity(i);
                                    utils.Toast("Welcome "+user.getDisplayName()+". Your Account is successfully created.");
                                }
                            }
                        });
                    }else{
                        utils.Toast("Login Successful.");
                        utils.Goto(HomeActivity.class);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            Log.e(TAG,"normal login");
            utils.Goto(HomeActivity.class);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                mAuth.removeAuthStateListener(mAuthListener);
            } else {
                utils.Toast("SignIn Unsuccessful.Please try again later.");
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG,"connection failed");
        utils.Toast("Connection Failed\n"+connectionResult.toString());
    }

}
