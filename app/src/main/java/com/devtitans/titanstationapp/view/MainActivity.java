package com.devtitans.titanstationapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.devtitans.titanstationapp.R;

import devtitans.adademanager.AdadeManager;

public class MainActivity extends AppCompatActivity {
    private AdadeManager manager;

    private SensorListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view_tag, SensorListFragment.class, null)
                    .commit();
        }

        viewModel = new ViewModelProvider(this).get(SensorListViewModel.class);
        //viewModel.initialize();
        viewModel.tryConnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                viewModel.refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem connectedMenu = menu.getItem(1);

        if(connectedMenu != null && viewModel.isConnected()){
            connectedMenu.setIcon(R.drawable.ic_outline_toggle_on_24);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}