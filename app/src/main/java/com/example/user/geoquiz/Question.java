package com.example.user.geoquiz;

/**
 * Created by USER on 17-06-2016.
 */
public class Question {
    private int mResId;
    private boolean mAnswerTrue;
    private boolean mIsCheated;
    public Question(int textResId,boolean ansTrue,boolean cheated)
    {
        mResId=textResId;
        mAnswerTrue=ansTrue;
        mIsCheated=cheated;
    }

    public boolean isCheated() {
        return mIsCheated;
    }

    public void setCheated(boolean cheated) {
        mIsCheated = cheated;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int resId) {
        mResId = resId;
    }
}
