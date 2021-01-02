package nicokla.com.musicos.Login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        Register();
      }
    });
    SignInTv.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
      }
    });

    return view;
  }


  private void Register(){
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
          Log.d("a", "onComplete: ");
//          Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
//          Intent intent=new Intent(SignUpActivity.this,DashboardActivity.class);
//          startActivity(intent);
//          finish();
        }
        else{
          Log.d("b", "onComplete: ");
//          Toast.makeText(SignUpActivity.this,"Sign up fail!",Toast.LENGTH_LONG).show();
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


  public SignUpFrag() {
    // Required empty public constructor
  }
}
