package edu.txstate.mjg.ppm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.txstate.mjg.ppm.core.User;
import edu.txstate.mjg.ppm.fragments.ProcessListFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String[] mTestContent;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> navDrawerAdapter;

    public static User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initNavDrawer();

        setSupportActionBar(toolbar);

        //TODO: User should keep track of followed processes
        startFragment();
    }

    void startFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        ProcessListFragment newFragment = new ProcessListFragment();

        newFragment.setArguments(getIntent().getExtras());
        //newFragment.show(fragmentManager, "dialog");
        //if (mIsLargeLayout) {
       // newFragment.show(getFragmentManager(), "dialog");
        //   } else {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    void initViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTestContent = getResources().getStringArray(R.array.navigation_drawer_values);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
    }
    void initNavDrawer() {
        navDrawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTestContent);
        mDrawerList.setAdapter(navDrawerAdapter);

        //        TODO: Should this be abstracted away into it's own class?
//              Is this same drawer going to be used in multiple activities?
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Switch between fragments/do other actions
            }

        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle("Title");
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle()
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
