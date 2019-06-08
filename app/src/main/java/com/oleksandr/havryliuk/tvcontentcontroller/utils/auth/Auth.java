package com.oleksandr.havryliuk.tvcontentcontroller.utils.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.Objects;

public class Auth {

    public final static int RC_SIGN_IN = 2;
    private static GoogleSignInClient googleSignInClient;

    public static void signIn(String email, String password, final Activity activity, final Intent intent) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //if login success
                    auth.getCurrentUser();
                    startMainActivity(activity, intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(activity, R.string.sign_in_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void signUp(String email, String password, final Activity activity, final Intent intent) {
        //register user with Firebase
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, R.string.sign_up_success, Toast.LENGTH_SHORT).show();
                    sendSignUpConfirm(auth.getCurrentUser(), activity);
                    startMainActivity(activity, intent);
                } else {
                    Toast.makeText(activity, R.string.sign_up_failed, Toast.LENGTH_SHORT).show();
                    //currentUser = null;
                }
            }
        });
    }

    private static void sendSignUpConfirm(FirebaseUser user, final Context context) {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, R.string.email_send_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.email_send_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void signOut() {
        if (googleSignInClient != null) {
            googleSignInClient.revokeAccess();
        }

        FirebaseAuth.getInstance().signOut();
    }

    public static void googleSignIn(Activity activity) {
        GoogleSignInClient googleSignInClient = getGoogleClient(activity);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static void sendRecoverCode(String email, final Context context) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, R.string.email_send_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.email_send_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static void firebaseAuthWithGoogle(GoogleSignInAccount acct,
                                               final Activity activity, final Intent intent) {
        //connecting to firebase
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(activity, R.string.google_auth_success, Toast.LENGTH_SHORT).show();
                            auth.getCurrentUser();
                            startMainActivity(activity, intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, R.string.google_auth_failed_firebase, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data,
                                        Activity activity, final Intent intent) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Auth.firebaseAuthWithGoogle(account, activity, intent);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(activity, R.string.google_auth_failed_google, Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    public static boolean isUserAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return false;
        }
        return true;
    }

    public static String getEmail(){
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    }

    public static GoogleSignInClient getGoogleClient(Context context) {
        if (googleSignInClient == null) {
            //Google
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(context, gso);
        }
        return googleSignInClient;
    }

    public static void startMainActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.finish();
    }
}
