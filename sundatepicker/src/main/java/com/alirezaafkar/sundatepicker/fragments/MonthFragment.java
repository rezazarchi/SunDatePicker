package com.alirezaafkar.sundatepicker.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alirezaafkar.sundatepicker.R;
import com.alirezaafkar.sundatepicker.adapters.MonthAdapter;
import com.alirezaafkar.sundatepicker.interfaces.DateInterface;
import com.alirezaafkar.sundatepicker.utils.TextAndFontUtility;

import java.util.Locale;

import static android.support.v7.widget.RecyclerView.RecycledViewPool;

/**
 * Created by Alireza Afkar on 2/5/16 AD.
 */
public class MonthFragment extends Fragment implements View.OnClickListener {
    private TextView mTitle;
    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private DateInterface mCallback;
    private Typeface typeface;

    public static MonthFragment newInstance(DateInterface callback, Typeface typeface) {
        MonthFragment fragment = new MonthFragment();
        fragment.mCallback = callback;
        fragment.typeface = typeface;
        return fragment;
    }

    public MonthFragment() {
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext())
                .inflate(R.layout.fragment_month, container, false);
    }

    @Override
    public void onClick(View view) {
        int currentItem = mPager.getCurrentItem();
        if (view.getId() == R.id.next) {
            if (++currentItem < mAdapter.getCount()) {
                mPager.setCurrentItem(currentItem, true);
            }
        } else if (view.getId() == R.id.before) {
            if (--currentItem >= 0) {
                mPager.setCurrentItem(currentItem, true);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextAndFontUtility.setFonts(view, typeface);
        mPager = view.findViewById(R.id.pager);
        mTitle = view.findViewById(R.id.title);
        view.findViewById(R.id.next).setOnClickListener(this);
        view.findViewById(R.id.before).setOnClickListener(this);
        initPager(mCallback.getYear(), mCallback.getMonth() - 1);
    }

    private void initPager(final int year, int chosenMonth) {
        mAdapter = new PagerAdapter(year);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int month) {
                super.onPageSelected(month);
                mTitle.setText(TextAndFontUtility.toPersianNumber(String.format(Locale.US, "%s %d",
                        mAdapter.getPageTitle(month),
                        year)));
            }
        });
        mPager.setCurrentItem(chosenMonth);
        if (chosenMonth == 0) {
            mTitle.setText(TextAndFontUtility.toPersianNumber(String.format(Locale.US, "%s %d",
                    mAdapter.getPageTitle(0),
                    year)));
        }
    }

    private class PagerAdapter extends android.support.v4.view.PagerAdapter implements View.OnClickListener {
        private int mCurrentYear;
        private RecycledViewPool viewPool = new RecycledViewPool();

        PagerAdapter(int year) {
            mCurrentYear = year;
        }

        @Override
        public int getCount() {
            return mCallback.getMonths().length;
        }

        public int getYear() {
            return mCurrentYear;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCallback.getMonths()[position];
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int month) {
            View view = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.layout_recycler_view, container, false);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setRecycledViewPool(viewPool);
            MonthAdapter adapter = new MonthAdapter(mCallback, this, month, mCurrentYear);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onClick(View view) {
            notifyDataSetChanged();
        }
    }
}
