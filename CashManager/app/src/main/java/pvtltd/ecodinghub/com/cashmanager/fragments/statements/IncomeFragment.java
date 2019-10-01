package pvtltd.ecodinghub.com.cashmanager.fragments.statements;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import pvtltd.ecodinghub.com.cashmanager.R;
import pvtltd.ecodinghub.com.cashmanager.adapters.StatementAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {


    public IncomeFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    SectionedRecyclerViewAdapter viewAdapter;

    @Override
    public void onResume() {
        viewAdapter.removeAllSections();
        viewAdapter.addSection(new StatementAdapter(getContext(), "tableIncome", IncomeFragment.this));
        recyclerView.setAdapter(viewAdapter);
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_income, container, false);
        viewAdapter = new SectionedRecyclerViewAdapter();
        viewAdapter.removeAllSections();
        recyclerView = root.findViewById(R.id.recyclerView);
        viewAdapter.addSection(new StatementAdapter(getContext(), "tableIncome", IncomeFragment.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(viewAdapter);
        return root;
    }
}