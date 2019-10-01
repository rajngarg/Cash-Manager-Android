package pvtltd.ecodinghub.com.cashmanager;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import pvtltd.ecodinghub.com.cashmanager.helpers.DatabaseHelper;

public class InsertActivity extends AppCompatActivity {

    EditText name, amount, monthlyCashflow;
    String table, tableother;
    Intent intent;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.animate().scaleY(1).scaleX(1).setDuration(700).start();
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        this.setTitle("Enter Data");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        name = findViewById(R.id.nameEditText);
        amount = findViewById(R.id.amountEditText);
        monthlyCashflow = findViewById(R.id.monthlyCashflowEditText);
        intent = getIntent();
        if (intent.getStringExtra("table").equals("Income") || intent.getStringExtra("table").equals("Expenses")) {
            amount.setVisibility(View.INVISIBLE);
        }

    }

    public void insertDetails(View view) {
        if (intent.getStringExtra("table").equals("Assets") || intent.getStringExtra("table").equals("Liabilities")) {
            if (!name.getText().toString().equals("")
                    && !monthlyCashflow.getText().toString().equals("")
                    && !amount.getText().toString().equals("")) {
                if (intent.getStringExtra("table").equals("Assets")) {
                    table = "tableAssets";
                    tableother = "tableIncome";
                } else if (intent.getStringExtra("table").equals("Liabilities")) {
                    table = "tableLiabilities";
                    tableother = "tableExpenses";
                }

                new DatabaseHelper(getApplicationContext()).insertAmount(name.getText().toString()
                        , Integer.parseInt(amount.getText().toString())
                        , Integer.parseInt(monthlyCashflow.getText().toString())
                        , table);
                new DatabaseHelper(getApplicationContext()).insertAmount(name.getText().toString()
                        , Integer.parseInt(monthlyCashflow.getText().toString())
                        , tableother);
                finish();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Fill All Fields!", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            if (!name.getText().toString().equals("")
                    && !monthlyCashflow.getText().toString().equals("")) {
                if (intent.getStringExtra("table").equals("Income"))
                    table = "tableIncome";
                else
                    table = "tableExpenses";

                new DatabaseHelper(getApplicationContext()).insertAmount(name.getText().toString()
                        , Integer.parseInt(monthlyCashflow.getText().toString())
                        , table);
                finish();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Fill All Fields!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
