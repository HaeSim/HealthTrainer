package info.androidhive.HealthTrainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.HashMap;

import info.androidhive.HealthTrainer.R;
import info.androidhive.HealthTrainer.helper.SQLiteHandler;
import info.androidhive.HealthTrainer.helper.SessionManager;

public class ProfileActivity extends AppCompatActivity {
    ImageView iv;
    TextView pName;

    private SQLiteHandler db;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        iv = (ImageView)findViewById(R.id.iv);
        pName = (TextView)findViewById(R.id.pName);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        pName.setText(name);
        Glide.with(this).load(R.drawable.ic_launcher).bitmapTransform(new CenterCrop(this)).into(iv);
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
