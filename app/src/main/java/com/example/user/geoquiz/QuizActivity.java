package com.example.user.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG="QuizActivity";
    private static final String KEY_INDEX="index";
    private static final String EXTRA_ANSWER_IS_TRUE="com.example.user.geoquiz.answer_is_true";
    private static final String KEY_CHEAT="is_cheated";
    private static final int REQUEST_CODE_CHEAT=0;
    private boolean mIsCheater;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex=0;
    private  Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_oceans,true,false),
            new Question(R.string.question_mideast,false,false),
            new Question(R.string.question_africa,false,false),
            new Question(R.string.question_americas,true,false),
            new Question(R.string.question_asia,true,false)
    };
    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
        Intent i=new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }
    private void updateQuestion()
    {
        int question=mQuestionBank[mCurrentIndex].getResId();
        mQuestionTextView.setText(question);
    }
    private void updateQuestionPrevious()
    {
        int question=mQuestionBank[mCurrentIndex].getResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userEnteredTrue)
    {
        boolean correctAnswer=mQuestionBank[mCurrentIndex].isAnswerTrue();
        if(mIsCheater|mQuestionBank[mCurrentIndex].isCheated())
        {
            Toast.makeText(QuizActivity.this,R.string.judgement_toast,Toast.LENGTH_SHORT).show();
            return;
        }
        if(userEnteredTrue==correctAnswer)
        {
            Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mCurrentIndex=(mCurrentIndex+1) % mQuestionBank.length;
            updateQuestion();
        }
        else
        {
            Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX);
            mIsCheater=savedInstanceState.getBoolean(KEY_CHEAT);
        }
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        mTrueButton=(Button) findViewById(R.id.true_button);
        mFalseButton=(Button) findViewById(R.id.false_button);
        mNextButton=(Button) findViewById(R.id.next_button);
        mPreviousButton=(Button) findViewById(R.id.previous_button);
        mCheatButton=(Button) findViewById(R.id.cheat_button);
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                checkAnswer(false);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mCurrentIndex=(mCurrentIndex+1) % mQuestionBank.length;
                mIsCheater=false;
                updateQuestion();
            }
        });
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                    mIsCheater = false;
                }
                else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    mIsCheater = false;
                }
                updateQuestionPrevious();
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=newIntent(QuizActivity.this,mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState() called");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEAT,mIsCheater);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(resultCode!= Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode==REQUEST_CODE_CHEAT)
        {
            if(data==null)
                return;
            else
            {
                mIsCheater=CheatActivity.wasAnswerShown(data);
                if(mIsCheater)
                {
                    mQuestionBank[mCurrentIndex].setCheated(true);
                }
            }
        }
    }
}
