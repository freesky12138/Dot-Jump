package lsnu.hyp.ddy.view;

import java.util.Random;

import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDInterstitialAd.InterstitialAdListener;

import lsnu.hyp.ddy.MainActivity;
import lsnu.hyp.ddy.CrizyActivity;
import lsnu.hyp.ddy.R;
import lsnu.hyp.ddy.viewdata.CrizyViewData;
import lsnu.hyp.ddy.viewdata.CrizyViewData;
import lsnu.hyp.ddy.viewdata.NamolViewData;

import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;

import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class CrizyView extends SurfaceView implements Runnable {
	// what's up
	// ps:像 Daydayup一样，你的up没有后退
	// ps:谁会告诉你游戏作者最终up值只有69呢
	// ps:对，本游戏程序猿的四级只考了250f
	// ps:可以尝试两个方向一起按哦~
	// ps:你也厌倦了彩色的世界么？
	// ps:我不告诉你可以从最左边穿越到最右边
	// ps:我不告诉你可以从最右边穿越到最左边
	// ps:废话
	// ps:还是废话
	// ps:你着急你就输了
	// top score: 5201314 up
	// ps:不会有人一直作死想看完ps吧~
	// p.s:你又跪了
	// 滚动的按钮
	private String vibratorString = Service.VIBRATOR_SERVICE;
	private Vibrator mVibrator01 = (Vibrator) CrizyActivity.application
			.getSystemService(vibratorString); // 初始化振动; //震动

	BDInterstitialAd appxInterstitialAd=new BDInterstitialAd(CrizyActivity.ctx,"28T2MbAvy0oggmHw5l4tGHu2" ,
			"28T2MbAvy0oggmHw5l4tGHu2");
	
	Random random = new Random();
	public static boolean closeOnclick = false;
	Thread gameloop = null;
	SurfaceHolder surface;
	volatile Boolean running = false;
	public static int speed = 20;
	// int WhiteRed=0;

	public static boolean isMidel = false;

	private int width;
	private int height;

	private int smallWidth;
	private int smallHeight;

	AssetManager assets = null;
	BitmapFactory.Options options = null;

	public CrizyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		appxInterstitialAd.setAdListener(new InterstitialAdListener() {
			
			@Override
			public void onAdvertisementViewWillStartNewIntent() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAdvertisementViewDidShow() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAdvertisementViewDidHide() {
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
		appxInterstitialAd.loadAd();
		
		width = CrizyActivity.screenSize.x;
		height = CrizyActivity.screenSize.y - CrizyActivity.screenSize.y / 6;
		smallWidth = width / CrizyViewData.vData.getxNum();
		smallHeight = height / CrizyViewData.vData.getyNum();

		surface = getHolder();
		assets = context.getAssets();
		options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				int action = event.getAction();

				if (action == MotionEvent.ACTION_DOWN) {

					if (!CrizyView.closeOnclick) {

						/*
						 * if (event.getX() <= width / 3) {
						 * CrizyViewData.vData.ChangePoint(3); } else
						 * if(v.equals(toDownButton)){
						 * ViewData.vData.ChangePoint(2); } else if
						 * (event.getX() <= (width / 3) * 2) {
						 * 
						 * CrizyViewData.vData.ChangePoint(1); } else {
						 * CrizyViewData.vData.ChangePoint(4); }
						 */
					} else {
						closeOnclick = false;
						if (CrizyViewData.vData.Hash[CrizyViewData.vData.stayPoint.y][CrizyViewData.vData.stayPoint.x] == 1) {
							CrizyViewData.vData.init();
							if(appxInterstitialAd.isLoaded())
							{
								appxInterstitialAd.showAd();
							}else {
								appxInterstitialAd.loadAd();
							}
						}
					}

				}


				return false;
			}
		});
	}

	public void pause() {
		running = false;
		while (running) {
			try {
				gameloop.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void resume() {
		running = true;
		gameloop = new Thread(this);
		gameloop.start();
	}

	Random r = new Random();
	boolean flag = true;
	Point p = new Point();
	int t = 0;

	private Bitmap bitmap_dot = BitmapFactory.decodeResource(getResources(),
			R.drawable.dot);
	private Bitmap bitmap_scorebg = BitmapFactory.decodeResource(
			getResources(), R.drawable.scorebg);
	private Bitmap bitmap_line_left = BitmapFactory.decodeResource(
			getResources(), R.drawable.cloudl);
	private Bitmap bitmap_line_middle = BitmapFactory.decodeResource(
			getResources(), R.drawable.cloudm);
	private Bitmap bitmap_line_right = BitmapFactory.decodeResource(
			getResources(), R.drawable.cloudr);
	private Bitmap bitmap_floor = BitmapFactory.decodeResource(getResources(),
			R.drawable.floor);

	private Bitmap bitmap_game_over = BitmapFactory.decodeResource(
			getResources(), R.drawable.gameover);
	private Bitmap bitmap_score = BitmapFactory.decodeResource(getResources(),
			R.drawable.score);
	private Bitmap bitmap_best = BitmapFactory.decodeResource(getResources(),
			R.drawable.best);
	private Bitmap bitmap_retry = BitmapFactory.decodeResource(getResources(),
			R.drawable.retry);

	private Bitmap bitmap_window = BitmapFactory.decodeResource(getResources(),
			R.drawable.window);

	private int starWindowFloor1;
	private int starWindowFloor2;
	private boolean isFirst = true;
	private double sf1, sf2;

	@Override
	public void run() {

		Paint paint = new Paint();
		while (running) {
			if (!surface.getSurface().isValid())
				continue;

			Canvas canvas = surface.lockCanvas();

			paint.setTypeface(MainActivity.typeFace);
			paint.setFakeBoldText(true);

			/*
			 * paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
			 * canvas.drawPaint(paint); paint.setXfermode(new
			 * PorterDuffXfermode(Mode.SRC));
			 */
			// drawLine(paint,canvas,CrizyViewData.vData.getyNum(),CrizyViewData.vData.getxNum());
			canvas.drawColor(Color.rgb(19, 168, 236));

		
			int windowFloor = 0;
			if (!isMidel) {
				windowFloor = 0;
			} else {
				windowFloor = (CrizyViewData.vData.floor - (CrizyViewData.vData
						.getyNum() / 2 - 1)) % 7;
			}

			if (windowFloor == 0 && isFirst) {
				isFirst = false;
				starWindowFloor1 = random.nextInt(width - width / 7);
				sf1 = ((double)( random.nextInt(5) + 12.0)/10.0);
			}
			if (windowFloor != 0) {
				isFirst = true;
			}

			Rect src_window = new Rect();// 图片
			Rect dst_window = new Rect();// 屏幕位置及尺寸
			// src 这个是表示绘画图片的大小
			src_window.left = 0; // 0,0
			src_window.top = 0;
			src_window.right = bitmap_window.getWidth();
			src_window.bottom = bitmap_window.getHeight();
			// 下面的 dst 是表示 绘画这个图片的位置

			dst_window.left = starWindowFloor1;
			dst_window.top = smallHeight * windowFloor;
			dst_window.right = (int) (starWindowFloor1 + ((double)bitmap_window.getWidth())/ sf1);
			dst_window.bottom = (int) (smallHeight * windowFloor
					+ ((double)bitmap_window.getHeight()) / sf1);


			canvas.drawBitmap(bitmap_window, src_window, dst_window, paint);

			paint.setColor(Color.WHITE);
			int weiyiNum = CrizyViewData.vData.floor; // 数字位移
			int weiyi = 0;
			while (weiyiNum > 0) {
				weiyiNum /= 10;
				weiyi++;
			}
			if (weiyi == 0)
				weiyi++;

			paint.setTextSize(height / 20);

			canvas.drawBitmap(bitmap_scorebg,
					width / 2 - (bitmap_scorebg.getWidth() / 2), 3, paint);
			canvas.drawText(String.valueOf(CrizyViewData.vData.floor), width
					/ 2 - (width / 40) * weiyi, (int) (height / 13), paint);

			
			// 画移动格子
			for (int i = 0; i < CrizyViewData.vData.getyNum(); i++) {

				// 设置左右不同颜色
				int color;
				if (CrizyViewData.vData.Hash[i][CrizyViewData.vData.getxNum() + 1] == 1) {
					color = Color.YELLOW;
				} else {
					color = Color.BLUE;
				}

				for (int j = 0; j < CrizyViewData.vData.getxNum(); j++) {

					if (CrizyViewData.vData.Hash[i][j] == 1) {
						/*
						 * paint.setColor(Color.WHITE); canvas.drawRect(j *
						 * smallWidth, height - i smallHeight, (j + 1) *
						 * smallWidth, height - (i + 1) * smallHeight, paint);
						 */} else {
						paint.setColor(color);

						Rect src = new Rect();// 图片
						Rect dst = new Rect();// 屏幕位置及尺寸
						// src 这个是表示绘画图片的大小
						src.left = 0; // 0,0
						src.top = 0;
						src.right = bitmap_line_middle.getWidth();
						src.bottom = bitmap_line_middle.getHeight();
						// 下面的 dst 是表示 绘画这个图片的位置
						dst.left = j * smallWidth;
						dst.top = height - (i + 1) * smallHeight - smallHeight
								/ 6;
						dst.right = (j + 1) * smallWidth;
						dst.bottom = height - (i + 1) * smallHeight;

						canvas.drawBitmap(bitmap_line_middle, src, dst, paint);

						int templ = (j - 1 + CrizyViewData.vData.getxNum())
								% CrizyViewData.vData.getxNum();
						if (CrizyViewData.vData.Hash[i][templ] == 1) {
							dst.right = (templ + 1) * smallWidth;
							dst.left = (templ + 1) * smallWidth
									- bitmap_line_left.getWidth();
							canvas.drawBitmap(bitmap_line_left, src, dst, paint);

						}
						int tempr = (j + 1 + CrizyViewData.vData.getxNum())
								% CrizyViewData.vData.getxNum();
						if (CrizyViewData.vData.Hash[i][tempr] == 1) {
							dst.right = (tempr) * smallWidth
									+ bitmap_line_left.getWidth();
							dst.left = (tempr) * smallWidth;
							canvas.drawBitmap(bitmap_line_right, src, dst,
									paint);

						}

						/*
						 * paint.setStrokeWidth((float) height/120);
						 * canvas.drawLine(j * smallWidth, height - i
						 * smallHeight-smallHeight/7, (j + 1) * smallWidth,
						 * height - (i) * smallHeight-smallHeight/7, paint);
						 */
					}

				}
			}

			canvas.drawBitmap(bitmap_dot, CrizyViewData.vData.stayPoint.x
					* smallWidth, height
					- (CrizyViewData.vData.stayPoint.y + 1) * smallHeight
					- smallHeight / 6 - bitmap_dot.getHeight(), paint);
			// height - (i + 1) * smallHeight - smallHeight / 6
			/*
			 * if(!closeOnclick) { paint.setColor(Color.GREEN);
			 * canvas.drawRect(ViewData.vData.stayPoint.x * smallWidth, height -
			 * (ViewData.vData.stayPoint.y) * smallHeight,
			 * (ViewData.vData.stayPoint.x + 1) * smallWidth, height -
			 * (ViewData.vData.stayPoint.y + 1) * smallHeight, paint);
			 * canvas.drawCircle(CrizyViewData.vData.stayPoint.x *
			 * smallWidth+smallWidth/2, height -
			 * (CrizyViewData.vData.stayPoint.y) * smallHeight-smallHeight/2,
			 * smallHeight/3, paint);
			 * 
			 * 
			 * }
			 *//*
				 * else { if(((t/3)%2==0)) paint.setColor(Color.RED); else {
				 * paint.setColor(Color.WHITE); }
				 * canvas.drawRect(CrizyViewData.vData.stayPoint.x * smallWidth,
				 * height - (CrizyViewData.vData.stayPoint.y) * smallHeight,
				 * (CrizyViewData.vData.stayPoint.x + 1) * smallWidth, height -
				 * (CrizyViewData.vData.stayPoint.y + 1) * smallHeight, paint);
				 * 
				 * WhiteRed++; }
				 */

			Rect src = new Rect();// 图片
			Rect dst = new Rect();// 屏幕位置及尺寸
			// src 这个是表示绘画图片的大小
			if (CrizyViewData.vData.floor <= 3) {

				src.left = 0; // 0,0
				src.top = 0;
				src.right = bitmap_floor.getWidth();
				src.bottom = bitmap_floor.getHeight();
				// 下面的 dst 是表示 绘画这个图片的位置
				dst.left = 0;
				dst.top = height - smallHeight - smallHeight / 6;
				dst.right = width;
				dst.bottom = height;

				canvas.drawBitmap(bitmap_floor, src, dst, paint);
			}

			if (CrizyViewData.vData.Hash[CrizyViewData.vData.stayPoint.y][CrizyViewData.vData.stayPoint.x] == 1) {
				// 得分 score : 17 up
				// top score: x up

				canvas.drawColor(Integer.valueOf(R.color.color_grey));

				if (closeOnclick == false) {
					CrizyActivity.CstarVoice(CrizyActivity.ctx, "diet");
					mVibrator01.vibrate(new long[] { 1, 100 }, -1); // 震动
				}

				closeOnclick = true;

				int heighScore = MainActivity.sharedata.getInt(
						"CRIZYHEIGHSCORE", 0);
				if (heighScore <= CrizyViewData.vData.floor) {
					heighScore = CrizyViewData.vData.floor;
					SharedPreferences.Editor savedata = MainActivity.sharedata
							.edit();
					savedata.putInt("CRIZYHEIGHSCORE",
							CrizyViewData.vData.floor);
					savedata.commit();
				}

				String scoreString = "score  :  %d up";

				String heighString = "top    :  %d up";

				scoreString = scoreString.format(scoreString,
						CrizyViewData.vData.floor);
				heighString = scoreString.format(heighString, heighScore);

				paint.setTextSize(height / 20);
				paint.setColor(Color.WHITE);

				// src 这个是表示绘画图片的大小
				src.left = 0; // 0,0
				src.top = 0;
				src.right = bitmap_game_over.getWidth();
				src.bottom = bitmap_game_over.getHeight();
				// 下面的 dst 是表示 绘画这个图片的位置
				dst.left = 0;
				dst.top = height / 4;
				dst.right = width;
				dst.bottom = height / 4 + bitmap_game_over.getHeight();

				canvas.drawBitmap(bitmap_game_over, src, dst, paint);

				/*
				 * canvas.drawBitmap(bitmap_score,width/4, height / 4+height/8,
				 * paint);
				 */
				canvas.drawText(scoreString, width / 4, height / 4 + height / 8
						+ bitmap_score.getHeight(), paint);

				/*
				 * canvas.drawBitmap(bitmap_best,width/4, height /
				 * 4+(height/11)*2, paint);
				 */
				canvas.drawText(heighString, width / 4, height / 4
						+ (height / 10) * 2 + bitmap_score.getHeight(), paint);

				canvas.drawBitmap(bitmap_retry,
						width / 2 - bitmap_retry.getWidth() / 2, height / 4
								+ (height / 8) * 3, paint);

				// canvas.drawText(ps[psNum], bitmapStarX+bitmapWidth/6,
				// bitmapStarY+(bitmapHeight/6)*5, paint);

				/*
				 * setOnClickListener(new OnClickListener() {
				 * 
				 * @Override public void onClick(View v) {
				 * 
				 * psNum=random.nextInt(ps.length); WhiteRed=0;
				 * closeOnclick=false;
				 * if(CrizyViewData.vData.Hash[CrizyViewData.
				 * vData.stayPoint.y][CrizyViewData.vData.stayPoint.x] == 1) {
				 * CrizyViewData.vData.init(); }
				 * 
				 * } });
				 */
			} else {
				if (t >= speed && closeOnclick == false) {
					CrizyViewData.vData.haveToChange();
					if (CrizyViewData.vData.floor != 0
							&& CrizyViewData.vData.floor % 20 == 0
							&& speed >= 10) {
						speed--;
					}
					t = 0;
				}
			}

			if (CrizyViewData.vData.stayPoint.y >= CrizyViewData.vData
					.getyNum() / 2 - 1) {
				CrizyViewData.vData.changeStar = 0;
				isMidel = true;
			}

			/*
			 * if(ViewData.vData.getyNum()-2==ViewData.vData.stayPoint.y) {
			 * closeOnclick=true;
			 * 
			 * if(t%5==0) { count++; ViewData.vData.changeDown(count); }
			 * if(count==9) { ViewData.vData.stayPoint.y=0; closeOnclick=false;
			 * } }
			 */

			surface.unlockCanvasAndPost(canvas);

			if (t > 20)
				t = 0;
			try {
				t++;
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/*
	 * private void drawLine(Paint paint,Canvas canvas, int getyNum, int
	 * getxNum) { paint.setColor(Color.GRAY); if(height/400>=1) {
	 * paint.setStrokeWidth((float) height/400); }else {
	 * paint.setStrokeWidth((float) 1); } for(int i=0;i<=getxNum;i++) {
	 * canvas.drawLine(i*smallWidth,height- 0, i*smallWidth,height-
	 * smallHeight*getyNum, paint); }
	 * 
	 * for(int i=0;i<=getyNum;i++) { canvas.drawLine(0, height- i*smallHeight,
	 * smallWidth*getxNum,height-i*smallHeight, paint); }
	 * 
	 * }
	 */

}
