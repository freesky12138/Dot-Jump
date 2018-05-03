package lsnu.hyp.ddy;



import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDBannerAd.BannerAdListener;

import lsnu.hyp.ddy.R.layout;
import lsnu.hyp.ddy.viewdata.CrizyViewData;
import lsnu.hyp.ddy.viewdata.NamolViewData;
import lsnu.hyp.ddy.voice.PlayVoice;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;



public class MainActivity extends Activity {

	/**
	 * @param args
	 * @author hyp
	 * 
	 */
	public static SharedPreferences sharedata ;
	public static Typeface typeFace ;
	
	BDBannerAd bannerAd;
	RelativeLayout appxBannerLayout;
	
	RelativeLayout mRelativeLayout;

	private Button mNamolModeButton;
	private Button mCrizyModeButton;
	private Button mQuitButton;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharedata=this.getSharedPreferences("data", 0);
		typeFace=Typeface.createFromAsset(getAssets(),"fonts/apple.ttf");
		
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		
		bannerAd=new BDBannerAd(this, "28T2MbAvy0oggmHw5l4tGHu2", "M5x5EWALye4zSmjqd9yLguFc");
		bannerAd.setAdListener(new BannerAdListener() {
			
			@Override
			public void onAdvertisementViewWillStartNewIntent() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAdvertisementViewDidShow() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAdvertisementViewDidClick() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAdvertisementDataDidLoadSuccess() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAdvertisementDataDidLoadFailure() {
				// TODO Auto-generated method stub
				
			}
		});
		mRelativeLayout=(RelativeLayout) findViewById(R.id.adcontainer);
		mRelativeLayout.addView(bannerAd);
		
		 mNamolModeButton = (Button) findViewById(R.id.namol_mode);
		mCrizyModeButton = (Button) findViewById(R.id.crizy_mode);
		mQuitButton=(Button)findViewById(R.id.quit);

		mNamolModeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						NamolActivity.class);
				NamolViewData.vData.init();
				startActivity(intent);
			}
		});

		mCrizyModeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int heighScore = sharedata.getInt("DAYDAYUPHEIGHSCORE", 0);
				
				if(heighScore>=100)
				{
					CrizyViewData.vData.init();
					Intent intent = new Intent(MainActivity.this,
							CrizyActivity.class);

					startActivity(intent);
				}else {
					Toast.makeText(MainActivity.this, "普通模式Up值达到100可解锁疯狂模式", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		mQuitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
	}
	
	public static void MstarVoice(final Context context,final String string){
		new Thread(){
			public void run(){
				PlayVoice playVoice=new PlayVoice(context,string);
				
			}
		}.start();
	}
}
