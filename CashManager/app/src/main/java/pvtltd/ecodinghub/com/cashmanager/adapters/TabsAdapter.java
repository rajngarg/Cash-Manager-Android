package pvtltd.ecodinghub.com.cashmanager.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    Fragment fragment1, fragment2;

    public TabsAdapter(FragmentManager fm, Fragment fragment1, Fragment fragment2) {
        super(fm);
        this.fragment1 = fragment1;
        this.fragment2 = fragment2;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return fragment1;
            case 1:
                return fragment2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
