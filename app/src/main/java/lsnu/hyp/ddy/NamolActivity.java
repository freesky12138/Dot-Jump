package lsnu.hyp.ddy;

import lsnu.hyp.ddy.view.NamolView;
import lsnu.hyp.ddy.viewdata.NamolViewData;
import lsnu.hyp.ddy.voice.PlayVoice;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;


public class NamolActivity extends Activity{

	NamolView drawView;
	public static Point screenSize;
	DisplayMetrics metrics = new DisplayMetrics();
	public static Context ctx;
	
	
	public static Application application;
	public Point scrS;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ctx=this;
		application=getApplication();
		setContentView(R.layout.namol_activity);

		getWindowManager().getDefaultDisplay().getMetrics(metrics);// 获得屏幕大小

		screenSize = new Point(metrics.widthPixels, metrics.heightPixels);

		RelativeLayout myRelative = (RelativeLayout) findViewById(R.id.drawview);

		

		// 获得Relative的大小
		LayoutParams linearParams = myRelative.getLayoutParams();

		linearParams.height = screenSize.y;
		myRelative.setLayoutParams(linearParams);

		drawView = new NamolView(this);
		
	/*drawView.setZOrderOnTop(true);// 设置画布 背景透明
		drawView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	*/
		myRelative.addView(drawView);
		
		
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		drawView.resume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		drawView.pause();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy(); 
		NamolViewData.vData.init();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

	public static void NstarVoice(final Context context,final String string){
		new Thread(){
			public void run(){
				PlayVoice playVoice=new PlayVoice(context,string);				
			}
		}.start();
	}


}
