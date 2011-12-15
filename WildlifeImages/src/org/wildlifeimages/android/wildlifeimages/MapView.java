package org.wildlifeimages.android.wildlifeimages;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 *
 */
class MapView extends SurfaceView implements SurfaceHolder.Callback {

	private final int gridCountX = 8;
	private final int gridCountY = 8;
	
    /** Pointer to the text view to display "Paused.." etc. */
    private TextView mStatusText;
    private TextView mButton;
    private MapView mMapView;
    
    private int mCurrentWidth;
    private int mCurrentHeight;
    
    private int mCurrentGridWidth;
    private int mCurrentGridHeight;
    
    private GestureDetector gestures;
    
    private int selectedGridX = 0;
    private int selectedGridY = 0;
    
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        mMapView = this;
        
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        gestures = new GestureDetector(context, new GestureListener(this));
        
        this.setBackgroundDrawable( context.getResources().getDrawable(
                R.drawable.facilitymap) );

        setFocusable(true); // make sure we get key events
    }
    
    @Override  
    public boolean onTouchEvent(MotionEvent event) { 
        return gestures.onTouchEvent(event);  
    }  
    
    private class GestureListener implements GestureDetector.OnGestureListener, 
    		GestureDetector.OnDoubleTapListener {  
		MapView view;  
		public GestureListener(MapView view) {  
		    this.view = view;  
		}
		
		//@Override
		public boolean onDoubleTap(MotionEvent e) {
			return false;
		}
		//@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}
		//@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			mCurrentGridWidth = mCurrentWidth/gridCountX;
			mCurrentGridHeight = mCurrentHeight/gridCountY;
			
			selectedGridX = (int)e.getX()/mCurrentGridWidth;
			selectedGridY = (int)e.getY()/mCurrentGridHeight;

			mMapView.invalidate();
			return true;
		}
		//@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
		//@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
		//@Override
		public void onLongPress(MotionEvent e) {
		}
		//@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}
		//@Override
		public void onShowPress(MotionEvent e) {
		}
		//@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}  
	} 

    @Override
    public void onDraw(Canvas canvas){
    	super.onDraw(canvas);
    	Paint p = new Paint();
    	p.setARGB(127, 0, 0, 255);
    	Rect r = new Rect(selectedGridX*mCurrentGridWidth, selectedGridY*mCurrentGridHeight, (selectedGridX+1)*mCurrentGridWidth, (selectedGridY+1)*mCurrentGridHeight);
    	canvas.drawRect(r, p);
    	
    	for(int i=0; i<gridCountX; i++){
    		canvas.drawLine(i*mCurrentGridWidth, 0, i*mCurrentGridWidth, mCurrentHeight, p);
    	}
    	for(int i=0; i<gridCountY; i++){
    		canvas.drawLine(0, i*mCurrentGridHeight, mCurrentWidth, i*mCurrentGridHeight, p);
    	}
    }
    
    /**
     * Standard override to get key-press events.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return false;
    }

    /**
     * Standard override for key-up. We actually care about these, so we can
     * turn off the engine or stop rotating.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
        return false;
    }

    /**
     * Standard window-focus override. Notice focus lost so we can pause on
     * focus lost. e.g. user switches to take a call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
    }

    /**
     * Installs a pointer to the text view used for messages.
     */
    public void setTextView(TextView textView) {
        mStatusText = textView;
    }

    /**
     * Installs a pointer to the text view used for messages.
     */
    public void setButton(Button button) {
        mButton = button;
    }
    
    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    	mCurrentWidth = width;
    	mCurrentHeight = height;
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
