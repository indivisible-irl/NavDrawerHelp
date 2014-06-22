package com.indivisible.navdrawertest;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Main
        extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks
{

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private String[] siteNames;
    private String[] siteAddresses;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        siteNames = getResources().getStringArray(R.array.site_names);
        siteAddresses = getResources().getStringArray(R.array.site_addresses);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        WebView myWebView = (WebView) findViewById(R.id.main_webview);
        String pageUrl = "http://www.nu.nl";
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(pageUrl);
        myWebView.setWebViewClient(new WebViewClient());

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        Log.d(TAG, "(onNavSelect) received index: " + position);
    }

    public void onSectionAttached(int number)
    {
        // we can keep using the list array's indexes
        mTitle = siteNames[number];
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!mNavigationDrawerFragment.isDrawerOpen())
        {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment
            extends Fragment
    {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int siteIndex)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, siteIndex);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment()
        {}

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity)
        {
            super.onAttach(activity);
            ((Main) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
