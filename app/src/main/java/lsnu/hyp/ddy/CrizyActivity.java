package lsnu.hyp.ddy;


import lsnu.hyp.ddy.view.CrizyView;
import lsnu.hyp.ddy.viewdata.CrizyViewData;
import lsnu.hyp.ddy.voice.PlayVoice;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class CrizyActivity extends Activity implements OnClickListener {

	
	CrizyView drawView;
	public static Point screenSize;
	DisplayMetrics metrics = new DisplayMetrics();
	public static Activity ctx;
	public static SharedPreferences sharedata ;
	
	private ImageButton toLeftButton;
	private ImageButton toRightButton;
	private ImageButton toUpButton;
	// private Button toDownButton;

	public static Application application;
	public Point scrS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ctx=this;
		application=getApplication();
		sharedata=ctx.getSharedPreferences("data", 0);
		setContentView(R.layout.crizy_activity);
		
				
		
		getWindowManager().getDefaultDisplay().getMetrics(metrics);// 获得屏幕大小

		screenSize = new Point(metrics.widthPixels, metrics.heightPixels);

		RelativeLayout myRelative = (RelativeLayout) findViewById(R.id.drawview);

		toLeftButton = (ImageButton) findViewById(R.id.toLeft);
		toRightButton = (ImageButton) findViewById(R.id.toRight);
		toUpButton = (ImageButton) findViewById(R.id.toUp);
		// toDownButton=(Button) findViewById(R.id.toDown);
		toLeftButton.setOnClickListener(this);
		toRightButton.setOnClickListener(this);
		toUpButton.setOnClickListener(this);
		// toDownButton.setOnClickListener(this);

		// 获得Relative的大小
		LayoutParams linearParams = myRelative.getLayoutParams();

		linearParams.height = screenSize.y - screenSize.y / 6;
		myRelative.setLayoutParams(linearParams);

		drawView = new CrizyView(this);
		
	/*	drawView.setZOrderOnTop(true);// 设置画布 背景透明
		drawView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	*/	
		myRelative.addView(drawView);
		
		if(CrizyView.closeOnclick==false)
		{
			CrizyViewData.vData.OnclickButtonChange();
			toUpButton.setBackgroundResource(image[CrizyViewData.vData.buttonNum[0]]);
			toLeftButton.setBackgroundResource(image[CrizyViewData.vData.buttonNum[1]]);
			toRightButton.setBackgroundResource(image[CrizyViewData.vData.buttonNum[2]]);
		}

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
		CrizyViewData.vData.init();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	//String string[]={"无","上","下","左","右"};
	int[] image={R.drawable.iconm,R.drawable.iconm,R.drawable.iconm,R.drawable.iconl,R.drawable.iconr};
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (!CrizyView.closeOnclick) {
			
			this.CstarVoice(this, "movie");
			if (v.equals(toUpButton)) {
				CrizyViewData.vData.ChangePoint(CrizyViewData.vData.buttonNum[0]);
			}/*
			 * else if(v.equals(toDownButton)){ ViewData.vData.ChangePoint(2); }
			 */else if (v.equals(toLeftButton)) {
				CrizyViewData.vData.ChangePoint(CrizyViewData.vData.buttonNum[1]);
			} else {
				CrizyViewData.vData.ChangePoint(CrizyViewData.vData.buttonNum[2]);
			}	
			if(CrizyView.closeOnclick==false)
			{
				CrizyViewData.vData.OnclickButtonChange();
				toUpButton.setBackgroundResource(image[CrizyViewData.vData.buttonNum[0]]);
				toLeftButton.setBackgroundResource(image[CrizyViewData.vData.buttonNum[1]]);
				toRightButton.setBackgroundResource(image[CrizyViewData.vData.buttonNum[2]]);
			}
			
		}
	}

	public static void CstarVoice(final Context context,final String string){
		new Thread(){
			public void run(){
				PlayVoice playVoice=new PlayVoice(context,string);
				
			}
		}.start();
	}
}
