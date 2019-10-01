package pvtltd.ecodinghub.com.cashmanager.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import pvtltd.ecodinghub.com.cashmanager.MainActivity;
import pvtltd.ecodinghub.com.cashmanager.R;
import pvtltd.ecodinghub.com.cashmanager.adapters.TabsAdapter;
import pvtltd.ecodinghub.com.cashmanager.fragments.statements.ExpensesFragment;
import pvtltd.ecodinghub.com.cashmanager.fragments.statements.IncomeFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatementFragment extends Fragment {


    public StatementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statement, container, false);
        final ViewPager pager = view.findViewById(R.id.pager);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        TabsAdapter adapter = new TabsAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new IncomeFragment(), new ExpensesFragment());
        pager.setAdapter(adapter);
        final MainActivity mainActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setTable("Income");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        pager.setCurrentItem(0);
                        mainActivity.setTable("Income");
                        break;
                    case 1:
                        pager.setCurrentItem(1);
                        mainActivity.setTable("Expenses");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

}
