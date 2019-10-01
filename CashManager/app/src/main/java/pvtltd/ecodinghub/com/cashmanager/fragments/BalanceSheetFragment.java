package pvtltd.ecodinghub.com.cashmanager.fragments;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pvtltd.ecodinghub.com.cashmanager.MainActivity;
import pvtltd.ecodinghub.com.cashmanager.R;
import pvtltd.ecodinghub.com.cashmanager.adapters.TabsAdapter;
import pvtltd.ecodinghub.com.cashmanager.fragments.balance_sheet.LiabilitiesFragment;
import pvtltd.ecodinghub.com.cashmanager.fragments.balance_sheet.AssetFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceSheetFragment extends Fragment {


    public BalanceSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance_sheet, container, false);
        final ViewPager pager = view.findViewById(R.id.pager);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        TabsAdapter adapter = new TabsAdapter(getActivity().getSupportFragmentManager(), new AssetFragment(), new LiabilitiesFragment());
        pager.setAdapter(adapter);
        ((MainActivity) getActivity()).setTable("Assets");
        ((MainActivity) getActivity()).setCategory("an Asset");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        pager.setCurrentItem(0);
                        ((MainActivity) getActivity()).setTable("Assets");
                        ((MainActivity) getActivity()).setCategory("an Asset");
                        break;
                    case 1:
                        pager.setCurrentItem(1);
                        ((MainActivity) getActivity()).setTable("Liabilities");
                        ((MainActivity) getActivity()).setCategory("a Liability");
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
