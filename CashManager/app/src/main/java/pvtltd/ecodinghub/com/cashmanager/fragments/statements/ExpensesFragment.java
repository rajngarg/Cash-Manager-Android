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
public class ExpensesFragment extends Fragment {


    public ExpensesFragment() {
        // Required empty public constructor
    }

    SectionedRecyclerViewAdapter viewAdapter;
    RecyclerView recyclerView;

    @Override
    public void onResume() {
        viewAdapter.removeAllSections();
        viewAdapter.addSection(new StatementAdapter(getContext(), "tableExpenses", ExpensesFragment.this));
        recyclerView.setAdapter(viewAdapter);
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_expenses, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        viewAdapter = new SectionedRecyclerViewAdapter();
        viewAdapter.removeAllSections();
        viewAdapter.addSection(new StatementAdapter(getContext(), "tableExpenses", ExpensesFragment.this));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(viewAdapter);
        return root;
    }
}