package com.example.appa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appa.LinkLab;
import com.example.appa.R;
import com.example.appa.model.Link;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView mLinkRecyclerView;
    private LinkAdapter mAdapter;
    private boolean mListReverseByDate;
    private boolean mListSortByStatus;

    public HistoryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        mLinkRecyclerView = view.findViewById(R.id.link_recycler_view);
        mLinkRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class LinkHolder extends RecyclerView.ViewHolder {
        private TextView mLinkTextView;
        private Link mLink;

        public LinkHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_link, parent, false));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = mLink.getLink();
                    String status = mLink.getStatus();

                    try {
                        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.appb");
                        long date = new Date().getTime();
                        intent.putExtra("type", "history");
                        intent.putExtra("url", link);
                        intent.putExtra("status", status);
                        intent.putExtra("time", date);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mLinkTextView = itemView.findViewById(R.id.link_textView);
        }

        void bind(Link link) {
            mLink = link;
            mLinkTextView.setText(mLink.getLink());
            switch (mLink.getStatus()) {
                case "1":
                    mLinkTextView.setBackgroundColor(getResources().getColor(R.color.colorUploaded));
                    break;
                case "2":
                    mLinkTextView.setBackgroundColor(getResources().getColor(R.color.colorErrorUpload));
                    break;
                case "3":
                    mLinkTextView.setBackgroundColor(getResources().getColor(R.color.colorUnknown));
                    break;
                default:
                    break;
            }
        }
    }

    private class LinkAdapter extends RecyclerView.Adapter<LinkHolder> {
        private List<Link> mLinks;

        public LinkAdapter(List<Link> links) {
            mLinks = links;
        }

        @NonNull
        @Override
        public LinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LinkHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull LinkHolder holder, int position) {
            Link link = mLinks.get(position);
            holder.bind(link);
        }

        @Override
        public int getItemCount() {
            return mLinks.size();
        }

        public void setLinks(List<Link> links) {
            mLinks = links;
        }
    }

    private void updateUI() {
        LinkLab link = LinkLab.get(getActivity());
        List<Link> links = link.getLinks(null);

        if (mAdapter == null) {
            mAdapter = new LinkAdapter(links);
            mLinkRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setLinks(links);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_history, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.by_date:
                sortListByDate();
                return true;
            case R.id.by_status:
                sortListByStatus();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortListByDate() {
        LinkLab link = LinkLab.get(getActivity());

        if (!mListReverseByDate) {
            mListReverseByDate = true;
            List<Link> links = link.getLinks("_id DESC");
            mAdapter.setLinks(links);
        } else {
            mListReverseByDate = false;
            List<Link> links = link.getLinks("_id ASC");
            mAdapter.setLinks(links);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void sortListByStatus() {
        LinkLab link = LinkLab.get(getActivity());

        if (!mListSortByStatus) {
            mListSortByStatus = true;
            List<Link> links = link.getLinks("status DESC");
            mAdapter.setLinks(links);
        } else {
            mListSortByStatus = false;
            List<Link> links = link.getLinks("status ASC");
            mAdapter.setLinks(links);
        }
        mAdapter.notifyDataSetChanged();
    }
}
