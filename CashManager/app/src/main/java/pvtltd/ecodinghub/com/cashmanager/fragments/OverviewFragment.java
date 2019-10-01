package pvtltd.ecodinghub.com.cashmanager.fragments;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import pvtltd.ecodinghub.com.cashmanager.R;
import pvtltd.ecodinghub.com.cashmanager.helpers.DatabaseHelper;
import pvtltd.ecodinghub.com.cashmanager.helpers.SharedPreferenceHelper;


/**
 * A simple {@link Fragment} subclass.
 */


public class OverviewFragment extends Fragment {
    DatabaseHelper mydb;
    TextView expenseText, passiveIncomeText, totalIncome, totalExpenses, cashInHand, payDayText;
    Button getButton, paidButton;
    int expenses = 1, income, passiveIncome, liabilities, salary = 0;
    ProgressBar seek;

    @Override
    public void onResume() {
        setValue();
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        seek = rootView.findViewById(R.id.seekBarLayout);
        expenseText = rootView.findViewById(R.id.expenseView);
        passiveIncomeText = rootView.findViewById(R.id.passiveIncomeView);
        totalIncome = rootView.findViewById(R.id.totalIncome);
        getButton = rootView.findViewById(R.id.getCash);
        paidButton = rootView.findViewById(R.id.paidCash);
        totalExpenses = rootView.findViewById(R.id.totalExpenses);
        payDayText = rootView.findViewById(R.id.payText);
        cashInHand = rootView.findViewById(R.id.cashInHand);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View builderView = inflater.inflate(R.layout.input_cash, null);
                final EditText amount = builderView.findViewById(R.id.amountEditText);
                builder.setView(builderView)
                        .setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!amount.getText().toString().equals("")) {
                            SharedPreferenceHelper.addIntoCash(getContext(), Integer.parseInt(amount.getText().toString()));
                            setValue();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        paidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View builderView = inflater.inflate(R.layout.input_cash, null);
                final EditText amount = builderView.findViewById(R.id.amountEditText);
                builder.setView(builderView)
                        .setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!amount.getText().toString().equals("")) {
                            SharedPreferenceHelper.subtractFromCash(getContext(), Integer.parseInt(amount.getText().toString()));
                            setValue();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        setValue();
        return rootView;
    }

    public void setValue() {
        try {
            salary = SharedPreferenceHelper.getJobSalary(getContext());
            cashInHand.setText(String.valueOf(SharedPreferenceHelper.getCash(getContext())));
            mydb = new DatabaseHelper(getContext());
            mydb.getReadableDatabase();

            Cursor cursor = mydb.columnSum("tableIncome", "monthlyCashflow");
            cursor.moveToFirst();
            income = cursor.getInt(0);

            cursor = mydb.columnSum("tableExpenses", "monthlyCashflow");
            cursor.moveToFirst();
            expenses = cursor.getInt(0);

            cursor = mydb.columnSum("tableLiabilities", "monthlyCashflow");
            cursor.moveToFirst();
            liabilities = cursor.getInt(0);

            cursor = mydb.columnSum("tableAssets", "monthlyCashflow");
            cursor.moveToFirst();
            passiveIncome = cursor.getInt(0);

            expenseText.setText("Total Expenses: " + String.valueOf(expenses));
            passiveIncomeText.setText("Passive Income: " + String.valueOf(passiveIncome));
            totalIncome.setText(String.valueOf(income + salary));
            totalExpenses.setText(String.valueOf("-" + expenses));
            payDayText.setText(String.valueOf((income + salary) - (expenses)));
            int percentage = (passiveIncome * 100) / (expenses);
            seek.setMax(100);
            seek.setProgress(percentage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
