package pvtltd.ecodinghub.com.cashmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import pvtltd.ecodinghub.com.cashmanager.R;
import pvtltd.ecodinghub.com.cashmanager.UpdateActivity;
import pvtltd.ecodinghub.com.cashmanager.helpers.DatabaseHelper;

public class LargeTableAdapter extends RecyclerView.Adapter<LargeTableAdapter.CustomViewHolder> implements BottomSheetListener {

    private Cursor cursor;
    Context context;
    String tableName;
    int pos;
    Fragment fragment;


    public LargeTableAdapter(String tableName, Context context, Fragment income) {
        cursor = new DatabaseHelper(context).viewAmount(tableName);
        this.context = context;
        this.tableName = tableName;
        fragment = income;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.largelistlayout, parent, false);
        return new CustomViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        if (cursor.getCount() != 0) {
            cursor.moveToPosition(position);
            holder.serialNo.setText(String.valueOf(cursor.getInt(0)));
            holder.name.setText(String.valueOf(cursor.getString(1)));
            holder.amount.setText(String.valueOf(cursor.getInt(2)));
            holder.monthlyCashflow.setText(String.valueOf(cursor.getInt(3)));
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    pos = position;
                    new BottomSheet.Builder(context)
                            .setSheet(R.menu.action_menu)
                            .setTitle("Choose Action")
                            .setListener(LargeTableAdapter.this)
                            .show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (cursor.getCount() != 0) {
            return cursor.getCount();
        } else return 0;
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet, @Nullable Object o) {

    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem, @Nullable Object o) {
        switch (menuItem.getItemId()) {

            case R.id.edit:
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("position", pos + 1);
                intent.putExtra("tableName", tableName);
                context.startActivity(intent);
                break;
            case R.id.delete:
                DatabaseHelper helper = new DatabaseHelper(context);
                helper.deleteItem(tableName, pos + 1);
                fragment.onResume();
                break;
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @Nullable Object o, int i) {
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, amount, name, monthlyCashflow;

        public CustomViewHolder(View itemView) {
            super(itemView);
            serialNo = itemView.findViewById(R.id.serialno);
            name = itemView.findViewById(R.id.element);
            amount = itemView.findViewById(R.id.amountText);
            monthlyCashflow = itemView.findViewById(R.id.monthlyCashflow);
        }
    }
}
