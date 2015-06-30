package com.example.stronglvadapter;

import java.util.HashMap;
import com.example.stronglvadapter.ExpandListViewAdapter.LevelViewOnClickListener;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView lv;
	private TestAdapter testAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv);
		
		testAdapter = new TestAdapter(3,this);
		
		HashMap<Integer, boolean[]> ids = new HashMap<Integer, boolean[]>();
		ids.put(0, new boolean[]{true,false,true});
		ids.put(1, new boolean[]{true,true});
	//	ids.put(2, new boolean[]{false});
		testAdapter.pointPositionOpenOrClose(false, ids);
		
	//	testAdapter.setOpenOrClose(false);
	//	testAdapter.setIds(ids);
		testAdapter.setLevelViewOnClickListener(new LevelViewOnClickListener() {
			
			@Override
			public void onClick(View v, int level, boolean isOpen) {
				// TODO Auto-generated method stub
				switch (level) {
				case 0:
					if(!isOpen){
						Log.i("hit", "±‰¿∂");
						v.setBackgroundColor(Color.BLUE);
					} else {
						Log.i("hit", "±‰∫Ï");
						v.setBackgroundColor(Color.RED);
					}
					break;

				default:
					break;
				}
				
			}
			@Override
			public void dealWithConvertView(View v, int level, boolean isOpen) {
				switch (level) {
				case 0:
					if(isOpen){
						Log.i("hit", "¿∂");
						v.setBackgroundColor(Color.BLUE);
					} else {
						Log.i("hit", "∫Ï");
						v.setBackgroundColor(Color.RED);
					}
					break;

				default:
					break;
				}
			}

		});
		lv.setAdapter(testAdapter);
		
		lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	class TestAdapter extends ExpandListViewAdapter{
		
		public TestAdapter(int level, Context mContext) {
			super(level, mContext);
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public int getLevelCount(int level) {
			return 15;
		}

		
		@Override
		public View getLevelView(int level,int position, ViewGroup parent,HashMap<String,Object> tag) {
			switch (level) {
			case 0:
				final View inflate = View.inflate(MainActivity.this, R.layout.tes1t, null);
				TextView tv = (TextView) inflate.findViewById(R.id.tv);
				tv.setText("count : "+position);
			/*	inflate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(haha){
							inflate.setBackgroundColor(Color.BLUE);
						} else {
							inflate.setBackgroundColor(Color.RED);
						}
						haha = !haha;
					}
				});*/
				return inflate;
			case 1:
				return View.inflate(MainActivity.this, R.layout.tes2t, null);
			case 2:
				return View.inflate(MainActivity.this, R.layout.tes3t, null);
			default:
				break;
			}
			throw new NullPointerException("√ª ”Õº∏„ƒ„√√");
		}
		
		@Override
		public void fillLevelView(int level, View v, int position, View convertView,
				ViewGroup parent,HashMap<String,Object> tag) {
			switch (level) {
			case 0:
				TextView tv = (TextView) v.findViewById(R.id.tv);
				tv.setText("cont £∫" + position);
				
				Button l0_btn = (Button) v.findViewById(R.id.l0_btn);
				l0_btn.setText("cont £∫" + position);
				break;
			case 1:
				Button l1_btn = (Button) v.findViewById(R.id.btn);
				l1_btn.setText("btn : " + position);
				break;
			case 2:
			
				break;

			default:
				break;
			}
		}
		

		@Override
		public Animator openViewAnim(final View v) {
			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			v.measure(w, h);
			
			ObjectAnimator objectAnimator = new ObjectAnimator();
			objectAnimator.setTarget(v);
			objectAnimator.setPropertyName("alpha");
			objectAnimator.setFloatValues(0,1);
			objectAnimator.setDuration(1000);
			objectAnimator.addUpdateListener(new AnimatorUpdateListener() {
				int measuredHeight = v.getMeasuredHeight();
				@Override
				public void onAnimationUpdate(ValueAnimator va) {
					long currentPlayTime = va.getCurrentPlayTime();
					long duration = va.getDuration();
					if (v.getVisibility() != View.VISIBLE) {
						v.setVisibility(View.VISIBLE);
					}
					if (currentPlayTime >= duration) {
						v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					//	v.invalidate();
					} else {
						if(measuredHeight > 10){
							v.getLayoutParams().height = (int) (measuredHeight * currentPlayTime / duration);
							v.requestLayout();
						} else {
							v.getLayoutParams().height += 2;
							v.requestLayout();
						}
					}
				}
			});
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.playTogether(
	              //  ObjectAnimator.ofFloat(v, "translationY",-300,0).setDuration(1000),
	                objectAnimator

	        );
			return animatorSet;
		}

		@Override
		public Animator closeViewAnim(final View v) {
			final int initialHeight = v.getMeasuredHeight();
			
			AnimatorSet animatorSet = new AnimatorSet();
			ObjectAnimator objectAnimator = new ObjectAnimator();
			objectAnimator.setTarget(v);
			objectAnimator.setPropertyName("alpha");
			objectAnimator.setFloatValues(1,0);
			objectAnimator.setDuration(1000);
			objectAnimator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator va) {
					// TODO Auto-generated method stub
					long currentPlayTime = va.getCurrentPlayTime();
					long duration = va.getDuration();
					if (currentPlayTime >= duration) {
						if(v.getLayoutParams().height != 0){
						/*	v.getLayoutParams().height = 0;
							v.requestLayout();*/
							
							v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
						}
						if (v.getVisibility() != View.GONE) {
							v.setVisibility(View.GONE);
						}
					} else {
						v.getLayoutParams().height = initialHeight
								- (int) (initialHeight * currentPlayTime / duration);
						v.requestLayout();
					}
					
				}
			});
			animatorSet.playTogether(objectAnimator);
		
			return animatorSet;
		}

	}

}
