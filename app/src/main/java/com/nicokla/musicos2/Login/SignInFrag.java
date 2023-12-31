package com.nicokla.musicos2.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import io.realm.Realm;
import com.nicokla.musicos2.Firebase.UserFirestore;

import com.nicokla.musicos2.Login.SignInFragDirections;

import com.nicokla.musicos2.MainAndCo.GlobalVars;

import com.nicokla.musicos2.R;

public class SignInFrag extends Fragment implements UserFirestore.MyCallback {

  private EditText emailEt,passwordEt;
  private Button SignInButton;
  private TextView SignUpTv;
  private ProgressDialog progressDialog;
  private FirebaseAuth firebaseAuth;
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
    firebaseAuth=FirebaseAuth.getInstance();
    emailEt=view.findViewById(R.id.email);
    passwordEt=view.findViewById(R.id.password);
    SignInButton=view.findViewById(R.id.login);
    progressDialog=new ProgressDialog(getContext());
    SignUpTv=view.findViewById(R.id.signUpTv);
    SignInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Login(view);
      }
    });
    SignUpTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        Intent intent=new Intent(MainActivity.this, nicokla.com.essai2.SignUpActivity.class);
//        startActivity(intent);
//        finish();
        Navigation.findNavController(view).navigate(
                SignInFragDirections.Companion.actionSignInFragToSignUpFrag()
        );
      }
    });

    return view;
  }


  private void Login(View view){
    String email=emailEt.getText().toString();
    String password=passwordEt.getText().toString();
    if(TextUtils.isEmpty(email)){
      emailEt.setError("Enter your email");
      return;
    }
    else if(TextUtils.isEmpty(password)){
      passwordEt.setError("Enter your password");
      return;
    }
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    progressDialog.setCanceledOnTouchOutside(false);
    UserFirestore.MyCallback callbackContainer = this;
    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(),
      new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
          Log.d("cool:", task.getResult().getUser().toString());
          GlobalVars.getInstance().me = task.getResult().getUser();
          UserFirestore.load(GlobalVars.getInstance().me.getUid(), callbackContainer);
//          Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
//          Intent intent=new Intent(MainActivity.this, nicokla.com.essai2.DashboardActivity.class);
//          startActivity(intent);
//          finish();
          Navigation.findNavController(view).navigate(
                  SignInFragDirections.Companion.actionSignInFragToHomeFragment()
          );
        }
        else{
          Log.d("oups:", task.getException().getMessage());
          Toast.makeText(getActivity(),"Sign in fail!",Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
      }
    });
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  public SignInFrag() {
    // Required empty public constructor
  }

  @Override
  public void onPause() {
    super.onPause();
    View focusedView = getActivity().getCurrentFocus();
    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (focusedView != null) {
      imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  @Override
  public void onCallback(UserFirestore userFirestore) {
    GlobalVars.getInstance().meFirestore = userFirestore;
    Log.i("username:", userFirestore.name);
  }
}
