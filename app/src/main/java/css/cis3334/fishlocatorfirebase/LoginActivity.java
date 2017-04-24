package css.cis3334.fishlocatorfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, NewUser;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        NewUser = (Button) findViewById(R.id.buttonNewUser);
        mAuth = FirebaseAuth.getInstance();  //declare object for Firebase


        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Normal login ");
                signIn(email.getText().toString(), password.getText().toString());
            }
        });
        NewUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Create Account ");
                createAccount(email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void signIn(String email, String password){
        //sign in the recurrent user with email and password previously created.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //add to listener
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "SignIn--Authentication failed.",Toast.LENGTH_LONG).show();
                } else {
                    //return to MainActivity is login works
                    finish();
                }
            }
        });
    }

    private void createAccount(String email, String password) {
        //create account for new users
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {  //update listener.
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "createAccount--Authentication failed.",Toast.LENGTH_LONG).show();
                } else {
                    //return to MainActivity is login works
                    finish();
                }
            }
        });
    }
}

