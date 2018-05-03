package lsnu.hyp.ddy.voice;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * 
 * @author FreeSky
 * @version 1.0
 * @date 2015.03.04
 * 
 */
public class PlayVoice {
	
	public static int t=0;

	 SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); 
	
	public PlayVoice(Context c,String nameString){
		

		
		AssetManager assetManager = c.getAssets(); 
		try {
			nameString =URLEncoder.encode(nameString, "UTF-8");
			
			AssetFileDescriptor fileDescriptor = assetManager
			        .openFd("voice/"+nameString+".wav");	
			
			
			 
			
			soundPool.load(fileDescriptor.getFileDescriptor(),
					fileDescriptor.getStartOffset(),  
	                fileDescriptor.getLength(),1 );
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			soundPool.play(1, 1, 1, 0, 0, 1);  
			
			final Timer	timer = new Timer(true);
			
			TimerTask task = new TimerTask(){  
			      public void run() {  
			    	  soundPool.release();
			    	 
			    t++;
			    if(t==1){
			    	timer.cancel();
			    	t=0;
			    }
			   }  
			};
			
		    
			 timer.schedule(task,1000, 1000); //延时1000ms后执行，1000ms执行一次
			 //timer.cancel(); //退出计时器
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
