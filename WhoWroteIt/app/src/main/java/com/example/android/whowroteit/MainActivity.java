package com.example.android.whowroteit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private TextView mdescription;
    private ScrollView mscroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookInput = findViewById(R.id.book_input);
        mTitleText = findViewById(R.id.title_text);
        mAuthorText = findViewById(R.id.authorText);
        mdescription = findViewById(R.id.description);
        mscroll = findViewById(R.id.scroll);
    }

    public void searchBooks(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        String queryString = mBookInput.getText().toString();
        new FetchBook(mTitleText, mAuthorText, mdescription).execute(queryString);

        mscroll.setVisibility(View.VISIBLE);
    }
}
