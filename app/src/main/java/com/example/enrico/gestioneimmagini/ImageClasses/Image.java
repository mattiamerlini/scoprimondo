package com.example.enrico.gestioneimmagini.ImageClasses;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.util.*;
import android.content.Context;


public class Image extends ImageView {
    int imageChanges[] = new int[3];
	private Rectangle imageRect;
	PointF center;
	ImageTouchListener imageTouch;
	String drawable;
    Bitmap imageBitmap;
    final int BORDER_WIDTH = 5;
    public Image(Context context, int drawable)
    {
        this(context,null,0,drawable);
    }
    public Image(Context context, AttributeSet attrs,int drawable)
    {
        this(context, attrs, 0, drawable);
    }

    public Image(Context context, AttributeSet attrs, int defStyle,int drawable)
    {
        super(context, attrs, defStyle);
        init(drawable);
    }
    private void init(int drawable)
    {
        this.drawable= String.valueOf(drawable);
        setScaleType(ImageView.ScaleType.MATRIX);
        this.setDrawable(drawable);
        this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        imageRect= new Rectangle(this.getDrawable().getIntrinsicWidth(),this.getDrawable().getIntrinsicHeight());
        center=new PointF(this.getDrawable().getIntrinsicWidth()/2,this.getDrawable().getIntrinsicHeight()/2);
        imageBitmap = BitmapFactory.decodeResource(this.getResources(),drawable);
        this.setImageBitmap(imageBitmap);
        imageTouch=new ImageTouchListener(this, imageRect, center);
        this.setOnTouchListener(imageTouch);
    }
    public void setOnClickListener(OnClickListener listener)
    {
    	super.setOnClickListener(listener);
    }
    public void setDrawable(int drawable)
    {
    	this.setImageResource(drawable);
    }
    public boolean pointBelongToImage(MotionEvent ev)
    {
    	return imageTouch.pointBelongToImage(ev);
    }

    public void setEnabled(boolean flag)
    {
        if(flag)
            this.activeBorder(/*1*/);
        else
            this.deactiveBorder();

        super.setEnabled(flag);
    }

    private void activeBorder(/*float scale*/)
    {
        final int BORDER_COLOR = Color.RED;
        Bitmap borderBitmap = Bitmap.createBitmap(imageBitmap.getWidth() + 2 * BORDER_WIDTH,
                imageBitmap.getHeight() + 2 * BORDER_WIDTH,
                imageBitmap.getConfig());
        Canvas borderCanvas = new Canvas(borderBitmap);
        Paint borderPaint = new Paint();
        borderPaint.setColor(BORDER_COLOR);
        borderCanvas.drawRect(0, 0, borderBitmap.getWidth(), borderBitmap.getHeight(), borderPaint);
        borderPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        borderCanvas.drawBitmap(imageBitmap, BORDER_WIDTH, BORDER_WIDTH, borderPaint);
        this.setImageBitmap(borderBitmap);
        this.imageTouch.postTranslate(this,-BORDER_WIDTH, -BORDER_WIDTH);
    }
    private void deactiveBorder()
    {
        this.setImageBitmap(imageBitmap);
        this.imageTouch.postTranslate(this,BORDER_WIDTH, BORDER_WIDTH);
    }
    public void mirroring()
    {
        imageTouch.mirroringImage();
    }
    public String toString()
    {
        return this.drawable;
    }
}
