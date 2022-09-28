package com.devtitans.titanstationapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.devtitans.titanstationapp.R;
import com.devtitans.titanstationapp.model.Sensor;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class SensorListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private SensorListViewModel viewModel;
    private ShimmerFrameLayout shimmerFrameLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SensorListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SensorListViewModel.class);
        shimmerFrameLayout.stopShimmer();
        viewModel.isLoading.observe(requireActivity(), this::isLoading);
        viewModel.sensorsLD.observe(requireActivity(), sensors -> recyclerView.setAdapter(new SensorRecyclerViewAdapter(sensors)));
        viewModel.refresh();
    }

    private void isLoading(Boolean isLoading){
        if(isLoading){
            shimmerFrameLayout.startShimmer();
        }else{
            shimmerFrameLayout.stopShimmer();
        }
    }


    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        return view;
    }
}