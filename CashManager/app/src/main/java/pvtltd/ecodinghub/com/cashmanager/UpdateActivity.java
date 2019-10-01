package pvtltd.ecodinghub.com.cashmanager;

import android.database.Cursor;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import pvtltd.ecodinghub.com.cashmanager.helpers.DatabaseHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText name, amount, monthlyCashflow;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.animate().scaleY(1).scaleX(1).setDuration(700).start();
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.setTitle("Update Data");
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
        if (getIntent().getStringExtra("tableName").equals("tableIncome")
                || getIntent().getStringExtra("tableName").equals("tableExpenses")) {
            amount.setVisibility(View.INVISIBLE);
        }
        setData();
    }

    private void setData() {
        Cursor cursor = new DatabaseHelper(this).getItem(getIntent().getStringExtra("tableName")
                , String.valueOf(getIntent().getIntExtra("position", 0)));
        cursor.moveToFirst();
        name.setText(cursor.getString(1));
        monthlyCashflow.setText(String.valueOf(cursor.getInt(2)));
        if (getIntent().getStringExtra("tableName").equals("tableIncome")
                || getIntent().getStringExtra("tableName").equals("tableExpenses")) {

        } else {
            amount.setText(String.valueOf(cursor.getInt(3)));
        }
    }

    public void updateDetails(View view) {

        if (getIntent().getStringExtra("tableName").equals("tableIncome")
                || getIntent().getStringExtra("tableName").equals("tableExpenses")) {
            if (!name.getText().toString().equals("")
                    && !monthlyCashflow.getText().toString().equals("")) {
                new DatabaseHelper(getApplicationContext()).updateItem(name.getText().toString()
                        , Integer.parseInt(monthlyCashflow.getText().toString())
                        , getIntent().getStringExtra("tableName")
                        , String.valueOf(getIntent().getIntExtra("position", 0)));
                finish();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Fill All Fields!", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            if (!name.getText().toString().equals("")
                    && !monthlyCashflow.getText().toString().equals("")
                    && !amount.getText().toString().equals("")) {
                //for updating table assets and table liabilities
                new DatabaseHelper(getApplicationContext()).updateItemLarge(name.getText().toString()
                        , Integer.parseInt(amount.getText().toString())
                        , getIntent().getStringExtra("tableName")
                        , String.valueOf(getIntent().getIntExtra("position", 0))
                        , Integer.parseInt(monthlyCashflow.getText().toString()));
/*                //for updating table income and expenses
                new DatabaseHelper(getApplicationContext()).updateItem(name.getText().toString()
                        , Integer.parseInt(monthlyCashflow.getText().toString())
                        , getIntent().getStringExtra("tableName")
                        , String.valueOf(getIntent().getIntExtra("position", 0)));*/
                finish();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Fill All Fields!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}