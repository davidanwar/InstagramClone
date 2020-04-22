package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdaptor extends FragmentPagerAdapter {

    public TabAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int tabPosition) {
        switch (tabPosition){
            case 0 :
                ProfileTab profileTab = new ProfileTab();
                return profileTab; // tidak perlu di break karena otomatis direturn

            case 1 :
                return new UserTab();
                // alternatif pemanggilan object tab
//                UserTab userTab = new UserTab();
//                return userTab;
            case 2 :
                return new SharePictureTab();
            default : return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Profile";
            case 1:
                return "User";
            case 2:
                return "Share Picture";
            default : return null; // harus dimasukan ini untuk mengembalikan nilai
        }
    }
}
