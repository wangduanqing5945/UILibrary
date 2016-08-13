package com.sevenheaven.uilibrarysample;

import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.sevenheaven.uilibrary.drawables.GroupedAvatarDrawable;
import com.sevenheaven.uilibrary.drawables.progressive.ProgressiveDrawable;
import com.sevenheaven.uilibrary.drawables.progressive.providers.AppStoreStyleProgressProvider;
import com.sevenheaven.uilibrary.drawables.progressive.providers.PathProgressProvider;

public class MainActivity extends AppCompatActivity {

    ProgressiveDrawable progressiveDrawable;

    ProgressiveDrawable.DrawContentProvider pathProgressProvider;
    ProgressiveDrawable.DrawContentProvider appStoreProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new View(this);

        Paint paint = new Paint();
        paint.setTextSize(100);

        Path animatePath = new Path();
        String text = "Android N";
        paint.getTextPath(text, 0, text.length(), 0, paint.getTextSize(), animatePath);
        PathProgressProvider.PathDesc animateDesc = new PathProgressProvider.PathDesc(animatePath, Gravity.CENTER, true, false, false);

        RectF animatePathBounds = new RectF();
        animatePath.computeBounds(animatePathBounds, false);

        Path progressPath = new Path();
        progressPath.addCircle(100, 100, animatePathBounds.width() / 2 + 50, Path.Direction.CW);
        progressPath.addCircle(100, 100, animatePathBounds.width() / 2 + 80, Path.Direction.CW);
        PathProgressProvider.PathDesc pathDesc = new PathProgressProvider.PathDesc(progressPath, Gravity.CENTER, true, true, false);

        pathProgressProvider = new PathProgressProvider(pathDesc, animateDesc){
            @Override
            protected void updateProgressPaint(Paint paint){
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setColor(0xFF44AEFF);
            }

            @Override
            protected void updateAnimationPaint(Paint paint){
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                paint.setColor(0xFF44AEFF);
            }
        };

        appStoreProvider = new AppStoreStyleProgressProvider(0x9944AEFF);

        progressiveDrawable = new ProgressiveDrawable(pathProgressProvider);

        GroupedAvatarDrawable avatarDrawable = new GroupedAvatarDrawable();
        avatarDrawable.setAvatars(BitmapFactory.decodeResource(getResources(), R.drawable.test0),
                BitmapFactory.decodeResource(getResources(), R.drawable.test1),
                BitmapFactory.decodeResource(getResources(), R.drawable.test2),
                BitmapFactory.decodeResource(getResources(), R.drawable.test0),
                BitmapFactory.decodeResource(getResources(), R.drawable.test1),
                BitmapFactory.decodeResource(getResources(), R.drawable.test2));

        view.setBackground(avatarDrawable);

        setContentView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                progressiveDrawable.setProgress(0);
                break;
            case MotionEvent.ACTION_MOVE:
                float progress = event.getX() / (float) getResources().getDisplayMetrics().widthPixels;

                progressiveDrawable.setProgress(progress);
                break;
            case MotionEvent.ACTION_UP:
                progressiveDrawable.setProgress(ProgressiveDrawable.PROGRESS_IDLE);
                break;
        }

        return true;
    }
}
