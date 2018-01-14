package com.zhy.circleview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.zhy.circleview.R;
import com.zhy.circleview.utils.L;

/**
 * Created by zhy on 2018/1/14.
 * 圆形镂空遮罩层
 */

public class FrameLayoutWithHole extends FrameLayout {

    private Bitmap mEraserBitmap;
    private Canvas mEraserCanvas;
    private Paint mEraser;
    private float mDensity;
    private Context mContext;

    private float mRadius;
    private int mBackgroundColor;
    private float mRx;//默认在中心位置
    private float mRy;

    public FrameLayoutWithHole(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public FrameLayoutWithHole(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FrameLayoutWithHole);
        mBackgroundColor = ta.getColor(R.styleable.FrameLayoutWithHole_background_color, -1);
        mRadius = ta.getFloat(R.styleable.FrameLayoutWithHole_hole_radius, 0);
        mRx = ta.getFloat(R.styleable.FrameLayoutWithHole_radius_x, 0);
        mRy = ta.getFloat(R.styleable.FrameLayoutWithHole_radius_y, 0);
        init(null, 0);
        ta.recycle();
    }

    public FrameLayoutWithHole(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FrameLayoutWithHole(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FrameLayoutWithHole(Context context, int backgroundColor, int radius
            , int rx, int ry) {//半径位置
        this(context);

        mBackgroundColor = backgroundColor;
        this.mRadius = radius;
        this.mRx = rx;
        this.mRy = ry;
        init(null, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        setWillNotDraw(false);
        mDensity = mContext.getResources().getDisplayMetrics().density;

        Point size = new Point();
        size.x = mContext.getResources().getDisplayMetrics().widthPixels;
        size.y = mContext.getResources().getDisplayMetrics().heightPixels;

        mRx = mRx * mDensity;
        mRy = mRy * mDensity;

        mRx = mRx != 0 ? mRx : size.x / 2;
        mRy = mRy != 0 ? mRy : size.y / 2;

        mRadius = mRadius != 0 ? mRadius : 150;

        mRadius = mRadius * mDensity;
        L.d("mRadius", "mRadius: " + mRadius);

        mBackgroundColor = mBackgroundColor != -1 ? mBackgroundColor : Color.parseColor("#000000");

        mEraserBitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        mEraserCanvas = new Canvas(mEraserBitmap);


        mEraser = new Paint();
        mEraser.setColor(0xFFFFFFFF);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);

        L.d("tourguide", "getHeight: " + size.y);
        L.d("tourguide", "getWidth: " + size.x);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEraserBitmap.eraseColor(Color.TRANSPARENT);
        mEraserCanvas.drawColor(mBackgroundColor);

        mEraserCanvas.drawCircle(
                mRx,
                mRy,
                mRadius, mEraser);

        canvas.drawBitmap(mEraserBitmap, 0, 0, null);

    }
}
