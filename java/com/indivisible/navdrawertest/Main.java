package com.indivisible.navdrawertest;


import android.app.Activity;
import android.app.ProgressDialog;
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
    private ProgressDialog progressDialog;

    // keep the pair of String arrays of site names and addresses
    private String[] siteNames;
    private String[] siteAddresses;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // grab the needed website arrays
        siteNames = getResources().getStringArray(R.array.site_names);
        siteAddresses = getResources().getStringArray(R.array.site_addresses);

        // set up WebView. initial page load comes from NavDrawerFragment attach
        myWebView = (WebView) findViewById(R.id.main_webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient()
            {

                @Override
                public void onPageFinished(WebView view, String url)
                {
                    // when a page has finished loading dismiss any progress dialog
                    if (progressDialog != null && progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }
            });

        // Set up the drawer.
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int siteIndex)
    {
        // user selected page load
        Log.d(TAG, "(onNavSelect) received index: " + siteIndex);
        loadWebPage(siteIndex);
    }

    public void onSectionAttached(int siteIndex)
    {
        // initial page load. not user selected.
        loadWebPage(siteIndex);
    }

    public void restoreActionBar()
    {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void loadWebPage(int siteIndex)
    {
        // lets show a progress indicator instead of a blank screen
        if (progressDialog == null)
        {
            initProgressDialog();
        }
        progressDialog.show();

        // load the page
        Log.d(TAG, "(loadWebPage) Loading page: " + siteNames[siteIndex] + "("
                + siteAddresses[siteIndex] + ")");
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

        // progressDialog gets dismissed above in WebViewclient declaration
    }

    private void initProgressDialog()
    {
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.page_load_progress_message));
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
            // Here is where you can define the default page you want loaded
            //  or if you want to save/restore the last page viewed etc.
            ((Main) activity)
                    .onSectionAttached(getArguments().getInt(ARG_SELECTED_SITE_INDEX));
        }
    }

}
