package com.example.user.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    boolean mAnswerIsTrue;
    private static final String EXTRA_ANSWER_IS_TRUE="com.example.user.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN="xom.example.user.geoquiz.answer_shown";
    private static final String KEY_INDEX="index";
    private boolean mCheated=false;
    Button mShowAnswerButton;
    TextView mAnswerTextView;
    public void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent i=new Intent();
        i.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,i);
    }
    public static boolean wasAnswerShown(Intent result)
    {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_INDEX,mCheated);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            mCheated=savedInstanceState.getBoolean(KEY_INDEX,true);
            if(mCheated)
            {
                setAnswerShownResult(true);
            }
        }
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mShowAnswerButton=(Button) findViewById(R.id.show_answer_button);
        mAnswerTextView=(TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue)
                {
                    mAnswerTextView.setText(R.string.true_button);
                    mCheated=true;

                }
                else
                {
                    mAnswerTextView.setText(R.string.false_button);
                    mCheated=true;
                }
                setAnswerShownResult(true);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }
                else
                {
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
