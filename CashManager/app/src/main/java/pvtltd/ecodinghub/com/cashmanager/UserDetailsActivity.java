package pvtltd.ecodinghub.com.cashmanager;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.Objects;

import pvtltd.ecodinghub.com.cashmanager.helpers.SharedPreferenceHelper;


public class UserDetailsActivity extends AppCompatActivity {
    EditText profile, salary;
    FloatingActionButton fab;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.animate().scaleY(1).scaleX(1).setDuration(700).start();
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        fab = findViewById(R.id.fab);

        this.setTitle("Enter Details");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        profile = findViewById(R.id.profile);
        salary = findViewById(R.id.salary);
    }

    public void setUserDetails(View view) {
        if (!profile.getText().toString().equals("") && !salary.getText().toString().equals("")) {
            SharedPreferenceHelper.deleteData(getApplicationContext());
            SharedPreferenceHelper.setJobProfile(getApplicationContext(), profile.getText().toString());
            SharedPreferenceHelper.setJobSalary(getApplicationContext(), Integer.parseInt(salary.getText().toString()));
            finish();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Complete All Fields!", Snackbar.LENGTH_SHORT).show();
        }
    }
}
