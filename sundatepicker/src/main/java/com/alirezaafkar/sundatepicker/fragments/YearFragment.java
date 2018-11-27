package com.alirezaafkar.sundatepicker.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alirezaafkar.sundatepicker.interfaces.DateInterface;
import com.alirezaafkar.sundatepicker.R;
import com.alirezaafkar.sundatepicker.adapters.YearAdapter;
import com.alirezaafkar.sundatepicker.utils.TextAndFontUtility;

/**
 * Created by Alireza Afkar on 2/5/16 AD.
 */
public class YearFragment extends Fragment {
    private DateInterface mCallback;
    private Typeface typeface;

    public static YearFragment newInstance(DateInterface callback, Typeface typeface) {
        YearFragment fragment = new YearFragment();
        fragment.mCallback = callback;
        fragment.typeface = typeface;
        return fragment;
    }

    public YearFragment() {
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_recycler_view, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextAndFontUtility.setFonts(view, typeface);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        YearAdapter adapter = new YearAdapter(mCallback, getYears());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(adapter.getSelectedYear());
    }

    private int[] getYears() {
        int maxYear = mCallback.getDateItem().getMaxDate().getIranianYear();
        int minYear = mCallback.getDateItem().getMinDate().getIranianYear();
        int[] years = new int[(maxYear - minYear) + 1];

        int counter = 0;
        for (int i = minYear; i <= maxYear; i++) {
            years[counter++] = i;
        }
        return years;
    }
}
