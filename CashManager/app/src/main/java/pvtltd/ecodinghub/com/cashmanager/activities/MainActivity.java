package pvtltd.ecodinghub.com.cashmanager.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import pvtltd.ecodinghub.com.cashmanager.R;
import pvtltd.ecodinghub.com.cashmanager.fragments.BalanceSheetFragment;
import pvtltd.ecodinghub.com.cashmanager.fragments.OverviewFragment;
import pvtltd.ecodinghub.com.cashmanager.fragments.StatementFragment;
import pvtltd.ecodinghub.com.cashmanager.helpers.DatabaseHelper;
import pvtltd.ecodinghub.com.cashmanager.helpers.SharedPreferenceHelper;

public class MainActivity extends AppCompatActivity {

    FragmentManager manager;
    FragmentTransaction transaction;
    FloatingActionButton fab;
    static String table = "", category = "";
    TextView salary, profession;
    int fragmentId = 0;
    Fragment fragment;

    @Override
    protected void onResume() {
        salary.setText(String.valueOf(SharedPreferenceHelper.getJobSalary(this)));
        profession.setText(SharedPreferenceHelper.getJobProfile(this));
        if (fragmentId == 0)
            fragment = new OverviewFragment();
        if (fragmentId == 1)
            fragment = new StatementFragment();
        if (fragmentId == 2)
            fragment = new BalanceSheetFragment();
        setFragment(fragment);
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        toolbar.setNavigationIcon(R.drawable.icon_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        toolbar.setOverflowIcon(getDrawable(R.drawable.icon_overflow_menu));
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_add_profession) {
                    startActivity(new Intent(getApplicationContext(), UserDetailsActivity.class));
                } else if (id == R.id.action_payday) {
                    itIsPayDay();
                } else if (id == R.id.overview) {
                    bottomNavigationView.setSelectedItemId(R.id.overview);
                } else if (id == R.id.balance_sheet) {
                    bottomNavigationView.setSelectedItemId(R.id.balance_sheet);
                } else if (id == R.id.income_statement) {
                    bottomNavigationView.setSelectedItemId(R.id.income_statement);
                } else if (id == R.id.action_instructions) {
                    startActivity(new Intent(MainActivity.this, InstructionsActivity.class));
                    //Snackbar.make(findViewById(android.R.id.content), "Under Development", Snackbar.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        View headerView = navigationView.getHeaderView(0);
        salary = headerView.findViewById(R.id.header_salary);
        profession = headerView.findViewById(R.id.header_profession);
        fab = findViewById(R.id.fab);
        fab.setScaleX(0);
        fab.setScaleY(0);
        setFragment(new OverviewFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.overview) {
                    fragment = new OverviewFragment();
                    fragmentId = 0;
                    fab.animate().setDuration(300).scaleY(0).scaleX(0).start();
                } else if (id == R.id.income_statement) {
                    fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                    fragmentId = 1;
                    table = "Income";
                    fragment = new StatementFragment();
                } else if (id == R.id.balance_sheet) {
                    fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                    table = "Expenses";
                    fragmentId = 2;
                    fragment = new BalanceSheetFragment();
                }
                setFragment(fragment);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
                intent.putExtra("table", table);
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            new DatabaseHelper(getApplicationContext()).resetAllPreferences();
            SharedPreferenceHelper.deleteData(getApplicationContext());
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTable(String table) {
        MainActivity.table = table;
    }

    public void setCategory(String category) {
        MainActivity.category = category;
    }

    public void setFragment(Fragment fragment) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    int income = 0, expenses = 0;

    public void itIsPayDay() {
        Cursor cursor = new DatabaseHelper(this).columnSum("tableIncome", "monthlyCashflow");
        cursor.moveToFirst();
        income = cursor.getInt(0);

        cursor = new DatabaseHelper(this).columnSum("tableExpenses", "monthlyCashflow");
        cursor.moveToFirst();
        expenses = cursor.getInt(0);

        new BottomSheet.Builder(MainActivity.this)
                .setTitle("Sure?")
                .setSheet(R.menu.ok_menu)
                .setListener(new BottomSheetListener() {
                    @Override
                    public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object o) {

                    }

                    @Override
                    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {
                        int id = menuItem.getItemId();
                        if (id == R.id.ok) {
                            SharedPreferenceHelper.setCash(getApplicationContext(),
                                    (income + SharedPreferenceHelper.getJobSalary(getApplicationContext())) - (expenses));
                            setFragment(new OverviewFragment());
                        } else if (id == R.id.no) {
                            bottomSheet.dismiss();
                        }
                    }

                    @Override
                    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object o, int i) {

                    }
                })
                .show();

    }

}

