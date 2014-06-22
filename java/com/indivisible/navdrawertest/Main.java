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

    // we need a class level references to some objects to be able to modify the
    //   target address outside of onCreate()
    private WebView myWebView;
    private ActionBar actionBar;

    // keep the pair of String arrays of site names and addresses
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

        Log.v(TAG, "names: " + siteNames.length);
        Log.v(TAG, "addresses: " + siteAddresses.length);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        myWebView = (WebView) findViewById(R.id.main_webview);
        String pageUrl = "http://www.nu.nl";
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(pageUrl);
        myWebView.setWebViewClient(new WebViewClient());

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int siteIndex)
    {
        Log.d(TAG, "(onNavSelect) received index: " + siteIndex);
        Log.d(TAG, "(onNavSelect) Loading page: " + siteNames[siteIndex]);
        mTitle = siteNames[siteIndex];
        if (actionBar == null)
        {
            restoreActionBar();
        }
        else
        {
            actionBar.setTitle(mTitle);
        }
        myWebView.loadUrl(siteAddresses[siteIndex]);
    }

    public void onSectionAttached(int siteIndex)
    {
        mTitle = siteNames[siteIndex];
        actionBar.setTitle(mTitle);
    }

    public void restoreActionBar()
    {
        actionBar = getSupportActionBar();
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
        private static final String ARG_SELECTED_SITE_INDEX = "selected_site_index";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int siteIndex)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SELECTED_SITE_INDEX, siteIndex);
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
            ((Main) activity)
                    .onSectionAttached(getArguments().getInt(ARG_SELECTED_SITE_INDEX));
        }
    }

}
