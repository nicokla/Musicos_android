package nicokla.com.musicos.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

import nicokla.com.musicos.R;

public class SignUpFrag extends Fragment {
  private EditText emailEt,passwordEt1,passwordEt2;
  private Button SignUpButton;
  private TextView SignInTv;
  private ProgressDialog progressDialog;
  private FirebaseAuth firebaseAuth;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
    firebaseAuth=FirebaseAuth.getInstance();
    emailEt=view.findViewById(R.id.email);
    passwordEt1=view.findViewById(R.id.password1);
    passwordEt2=view.findViewById(R.id.password2);
    SignUpButton=view.findViewById(R.id.register);
    progressDialog=new ProgressDialog(getContext());
    SignInTv=view.findViewById(R.id.signInTv);
    SignUpButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Register(view);
      }
    });
    SignInTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(view).navigate(
                SignUpFragDirections.Companion.actionSignUpFragToSignInFrag()
        );
//        Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
      }
    });

    return view;
  }


  private void Register(View view){
    String email=emailEt.getText().toString();
    String password1=passwordEt1.getText().toString();
    String password2=passwordEt2.getText().toString();
    if(TextUtils.isEmpty(email)){
      emailEt.setError("Enter your email");
      return;
    }
    else if(TextUtils.isEmpty(password1)){
      passwordEt1.setError("Enter your password");
      return;
    }
    else if(TextUtils.isEmpty(password2)){
      passwordEt2.setError("Confirm your password");
      return;
    }
    else if(!password1.equals(password2)){
      passwordEt2.setError("Different password");
      return;
    }
    else if(password1.length()<4){
      passwordEt1.setError("Length should be > 4");
      return;
    }
    else if(!isValidEmail(email)){
      emailEt.setError("invalid email");
      return;
    }
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    progressDialog.setCanceledOnTouchOutside(false);
    firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
          Log.d("cool:", task.getResult().getUser().toString());
//          Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
//          Intent intent=new Intent(SignUpActivity.this,DashboardActivity.class);
//          startActivity(intent);
//          finish();
          Navigation.findNavController(view).navigate(
                  SignUpFragDirections.Companion.actionSignUpFragToHomeFragment()
          );
        }
        else{
          Log.d("oups:", task.getException().getMessage());
          Toast.makeText(getActivity(),"Sign up fail!",Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
      }
    });
  }

  private Boolean isValidEmail(CharSequence target){
    return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
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

  public SignUpFrag() {
    // Required empty public constructor
  }
}
