package com.example.appa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appa.R;

import java.util.Date;

public class TestFragment extends Fragment {

    private EditText mLinkEditText;
    private Button mOkButton;

    public TestFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        mLinkEditText = view.findViewById(R.id.link_editText);

        mOkButton = view.findViewById(R.id.ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.example.appb");
                    long date = new Date().getTime();
                    intent.putExtra("type", "ok");
                    intent.putExtra("url", mLinkEditText.getText().toString());
                    intent.putExtra("time", date);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}