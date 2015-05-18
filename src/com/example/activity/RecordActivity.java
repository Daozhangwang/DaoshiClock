package com.example.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.example.daoshiclock.R;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class RecordActivity extends Activity {
	public Button record;
	public Button playlast;
	public Button delete;
	public Button save;
	public SeekBar playbar;
	public Handler mHandler;
	public TextView Timershow;
	public Timer CountTimer = null;
	public TimerTask task = null;
	private String Clockpath;
	private String musicpath;
	public SimpleDateFormat nowtime;
	public Date nowDate;
	private static int delay = 1000;
	private static int period = 100;
	public int i = 1;
	private double count_double;
	public LinearLayout firstlayout;
	public MediaRecorder Recorder;
	public MediaPlayer recordplayer;
	public Runnable updateThread;
	public Handler seekhandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_record);

		initview();
		initevent();
		inittimer();

	}

	private void initview() {
		record = (Button) findViewById(R.id.record_start);
		Timershow = (TextView) findViewById(R.id.time_count);
		playlast = (Button) findViewById(R.id.playlast);
		playbar = (SeekBar) findViewById(R.id.playseekbar);
		save = (Button) findViewById(R.id.save);
		delete = (Button) findViewById(R.id.delete);
		Clockpath = Environment.getExternalStorageDirectory().getAbsolutePath();
		Clockpath += "/Clock";
		firstlayout = (LinearLayout) this.findViewById(R.id.firstlayout);

	}

	public void sendMessage(int id) {
		if (mHandler != null) {
			Message message = Message.obtain(mHandler, id);
			mHandler.sendMessage(message);
		}
	}

	public void stopTimer() {
		if (CountTimer != null) {
			CountTimer.cancel();
			CountTimer = null;
		}

		if (task != null) {
			task.cancel();
			task = null;
		}

	}

	public void startTimer() {
		count_double = 0;
		if (CountTimer == null) {
			CountTimer = new Timer();
		}

		if (task == null) {
			task = new TimerTask() {
				@Override
				public void run() {

					sendMessage(0);
					count_double = count_double + 0.1;
				}
			};
		}

		if (CountTimer != null && task != null)
			CountTimer.schedule(task, delay, period);

	}

	private void inittimer() {
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Timershow.setText(String.format("%.2f", count_double));
					break;
				default:
					break;
				}
			}
		};

	}

	private void initevent() {

		record.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					nowtime = new SimpleDateFormat("MMddHHmmss");
					nowDate = new Date(System.currentTimeMillis());// 获取当前时间
					String nowtime_s = nowtime.format(nowDate);
					musicpath = Clockpath + "/" + nowtime_s;
					Recorder = new MediaRecorder();
					Recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					Recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					Recorder.setOutputFile(musicpath);

					Recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

					try {
						Recorder.prepare();
					} catch (IOException e) {
						Log.e("Test", "prepare() failed");
					}
					Recorder.start();
					startTimer();
					Log.i("nowtime", "down " + musicpath);
					record.setText("正在录音");
					break;
				}

				case MotionEvent.ACTION_UP: {
					Recorder.stop();
					Recorder.release();
					Log.i("touch", "no");
					stopTimer();
					record.setText("开始录音");
					playbar.setVisibility(View.VISIBLE);
					playlast.setVisibility(View.VISIBLE);
					save.setVisibility(View.VISIBLE);
					delete.setVisibility(View.VISIBLE);
					initnewevent(musicpath);
					record.setEnabled(false);

					break;
				}

				default:
					break;
				}
				return true;
			}
		});

	}

	protected void initnewevent(String musicpath) {
		final String path = musicpath;
		recordplayer = new MediaPlayer();
		final File musicfile = new File(path);

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playbar.setVisibility(View.GONE);
				playlast.setVisibility(View.GONE);
				save.setVisibility(View.GONE);
				delete.setVisibility(View.GONE);
				record.setEnabled(true);
				if (musicfile.delete()) {
					Toast.makeText(RecordActivity.this, "删除成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(RecordActivity.this, "删除失败",
							Toast.LENGTH_LONG).show();
				}

			}

		});
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText inputName = new EditText(RecordActivity.this);
				inputName.setFocusable(true);

				AlertDialog.Builder builder = new Builder(RecordActivity.this);
				builder.setView(inputName);
				builder.setTitle("将其重命名为：");

				builder.setPositiveButton(

				"确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						String newName = inputName.getText().toString();

						if (!newName.equals("")) {
							File newfile = new File(Environment
									.getExternalStorageDirectory()
									.getAbsolutePath()
									+ "/Clock/" + newName + ".3gp");

							musicfile.renameTo(newfile);
							Log.i("newname", Environment
									.getExternalStorageDirectory()
									.getAbsolutePath()
									+ "/Clock/" + newName + ".3gp");
							Toast.makeText(RecordActivity.this, "保存成功",
									Toast.LENGTH_LONG).show();

						} else {
							Toast.makeText(RecordActivity.this, "按默认时间保存",
									Toast.LENGTH_LONG).show();

						}

					}
				});
				builder.show();

				playbar.setVisibility(View.GONE);
				playlast.setVisibility(View.GONE);
				save.setVisibility(View.GONE);
				delete.setVisibility(View.GONE);
				record.setEnabled(true);

			}

		});
		seekhandler = new Handler();

		updateThread = new Runnable() {
			public void run() {
				// 获得歌曲现在播放位置并设置成播放进度条的值
				playbar.setProgress(recordplayer.getCurrentPosition());
				// 每次延迟100毫秒再启动线程
				seekhandler.postDelayed(updateThread, 100);
			}
		};
		playlast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					recordplayer.reset();
					recordplayer.setDataSource(path);
					recordplayer.prepare();
					recordplayer.start();
					seekhandler.post(updateThread);
					// playlast.setText("stop");
					playbar.setMax(recordplayer.getDuration());
					Log.i("play", "cool");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		playbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// fromUser判断是用户改变的滑块的值
				if (fromUser == true) {
					recordplayer.seekTo(progress);
				}
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

		});

	}

}
