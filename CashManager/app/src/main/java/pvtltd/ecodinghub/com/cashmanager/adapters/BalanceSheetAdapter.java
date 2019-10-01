package pvtltd.ecodinghub.com.cashmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import pvtltd.ecodinghub.com.cashmanager.R;
import pvtltd.ecodinghub.com.cashmanager.UpdateActivity;
import pvtltd.ecodinghub.com.cashmanager.helpers.DatabaseHelper;

public class BalanceSheetAdapter extends StatelessSection implements BottomSheetListener {
    /**
     * Create a stateless Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */

    Context context;
    String tableName;
    Fragment fragment;
    Cursor cursor;
    int pos = 0;


    public BalanceSheetAdapter(Context context, String tableName, Fragment fragment) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.largelistlayout)
                .footerResourceId(R.layout.footer_view)
                .build()
        );
        this.context = context;
        this.fragment = fragment;
        this.tableName = tableName;
        cursor = new DatabaseHelper(context).viewAmount(tableName);
    }


    @Override
    public int getContentItemsTotal() {
        if (cursor.getCount() != 0) {
            return cursor.getCount();
        } else return 0;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (cursor.getCount() != 0) {
            cursor.moveToPosition(position);
            itemViewHolder.serialNo.setText(String.valueOf(cursor.getInt(0)));
            itemViewHolder.name.setText(String.valueOf(cursor.getString(1)));
            itemViewHolder.monthlyCashflow.setText(String.valueOf(cursor.getInt(2)));
            itemViewHolder.amount.setText(String.valueOf(cursor.getInt(3)));
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    pos = position;
                    new BottomSheet.Builder(context)
                            .setSheet(R.menu.action_menu)
                            .setTitle("Choose Action")
                            .setListener(BalanceSheetAdapter.this)
                            .show();
                    return true;
                }
            });
        }
    }

    //for footer
    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        if (getContentItemsTotal() == 0)
            footerViewHolder.footerText.setText("Tap + to add");
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

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            footerText = itemView.findViewById(R.id.footer_item);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, amount, name, monthlyCashflow;

        public ItemViewHolder(View itemView) {
            super(itemView);
            serialNo = itemView.findViewById(R.id.serialno);
            name = itemView.findViewById(R.id.element);
            amount = itemView.findViewById(R.id.amountText);
            monthlyCashflow = itemView.findViewById(R.id.monthlyCashflow);
        }
    }
}
