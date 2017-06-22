package com.example.mat.blood.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.mat.blood.R;
import com.example.mat.blood.pojo.BloodRequest;
import com.example.mat.blood.pojo.UserProfile;
import com.example.mat.blood.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import static com.example.mat.blood.utils.DefaultCode.FIREBASE_BLOOD_REQUESTS;

public class CreateReqFragment extends Fragment {
    String TAG = CreateReqFragment.class.getSimpleName();
    Utils utils;
    Context context;

    String[] blood_groups;

    EditText Edit_HospiitalName;
    Button Btn_CreateRequest;
    MaterialBetterSpinner Spinner_BloogGroup;

    String bloodGroup = "";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private OnFragmentInteractionListener mListener;

    public CreateReqFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_create_req, container, false);
        context = v.getContext();
        utils = new Utils(context);

        Edit_HospiitalName = (EditText)v.findViewById(R.id.edit_hospitalName);
        Btn_CreateRequest = (Button)v.findViewById(R.id.btn_createRequest);
        Spinner_BloogGroup = (MaterialBetterSpinner)v.findViewById(R.id.spinner_bloodGroup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FIREBASE_BLOOD_REQUESTS);

        blood_groups = getResources().getStringArray(R.array.blood_groups);
        bloodGroup = blood_groups[0];

        ArrayAdapter<String> blood_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, blood_groups);
        Spinner_BloogGroup.setAdapter(blood_adapter);
        Spinner_BloogGroup.setSelection(0);
        Spinner_BloogGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner_BloogGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bloodGroup = adapterView.getItemAtPosition(i).toString();
            }
        });

        Btn_CreateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRequest();
            }
        });


        return v;
    }

    void createRequest(){
        String hospitalName = Edit_HospiitalName.getText().toString();
        if(!utils.isEmptyString(hospitalName,bloodGroup)){
            UserProfile userProfile = utils.getSignInUser();
            BloodRequest bloodRequest = new BloodRequest(bloodGroup,userProfile.getUid(),userProfile.getUserName(),hospitalName,utils.getTimeStamp());
            databaseReference.push().setValue(bloodRequest);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
