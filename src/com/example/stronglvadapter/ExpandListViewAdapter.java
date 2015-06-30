package com.example.stronglvadapter;

import java.util.HashMap;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * @author wd
 * ��չ���պϵ�ListViewAdapter
 * ������© ��
 * 		���������е�࣬ռ�����ݿ��ܱȽϴ󣬼��������ഴ�������Ƚ϶࣬�Ż�����
 * 		ż���ᷢ����������Ӧ�����Ż�����
 * 		���봫�� level �����������һ�ֳ�д�� �ƺ���ˬ������
 * 		����д�ĸ�ʽ�����ţ�ע��̫�࣬Ȼ������һĿ��Ȼ��
 * 		���ܻᷢ������¼���ͻ����δ���ԣ�
 * 		��Ȼ�о���
 * 		ż���ᷢ��չ�������ݲ���ʾ�����ö�����û������Ҳ�п����ǲ��Զ��࣬
 * 				�޸���ԭ��view��ͼ�Ĵ�С,д�������⣬ԭ��û�鵽��
 * 		�����޶������Զ������о���ˬ��û���ж϶��������Ƿ����
 */
public abstract class ExpandListViewAdapter extends BaseAdapter{
	
	private int level; // �㼶�� 0 ~ N
	
	public static int Level_First = 0;
	
	public static int Add_Level = 1;
	
	public static String LevelView = "Level_View";
	
	private Context mContext;
	
	// ָ��id --> ���id����չ���ر�    || ��ָ�� --> ���ÿ��view����չ���ر�
	public boolean pointIds; 
	
	// ���� ÿ��view��״̬   Integer --> position
	public HashMap<Integer,boolean[]> state = new HashMap<Integer,boolean[]>();
	
	// ���� ÿ��view ��ָ����id  Integer --> level
	private HashMap<Integer,int[]> ids ;
	
<<<<<<< HEAD
	//�㼶view �ĵ���¼�����
	public LevelViewOnClickListener mLevelViewOnClickListener;
	
	public void setLevelViewOnClickListener(LevelViewOnClickListener mLevelViewOnClickListener) {
		this.mLevelViewOnClickListener = mLevelViewOnClickListener;
	}
	
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
	public HashMap<Integer, int[]> getIds() {
		if(ids != null){
			return ids;
		} else {
			return null;
		}
	}

	public void setIds(HashMap<Integer, int[]> ids) {
		this.ids = ids;
		// �����õ�ǰ ids ,��ʾ��Ҫ�Լ�ѡ�� id ����չ���ر�
		pointIds = true;
	}

	public HashMap<Integer, boolean[]> getState() {
		return state;
	}
	
	public void setState(HashMap<Integer, boolean[]> state) {
		this.state = state;
	}

	public ExpandListViewAdapter(Context mContext){
		super();
		this.mContext = mContext;
	}
	
	public ExpandListViewAdapter(int level,Context mContext){
		this(mContext);
		this.level = level;
		this.mContext = mContext;
		accessLevel();
	}
	
<<<<<<< HEAD
	public void setOpenOrClose(boolean b){
		int count = getCount();
		for(int i=0; i<count; i++){
			for(int j=0; j< level; j++){
				boolean[] bs = new boolean[level];
				if(b){
					bs[j] = true;
				} else {
					bs[j] = false;
				}
				state.put(i, bs);
			}
		}
	}
	
	public void pointPositionOpenOrClose(boolean b,HashMap<Integer, boolean[]> map){
		setOpenOrClose(b);
		if(map == null){
			return ;
		}
		for (Integer posi : map.keySet()) {
			boolean[] bs = map.get(posi);
			int length = bs.length;
			boolean[] newBs = new boolean[level];
			
			if(length <= level){
				for(int i=0; i<length; i++){
					newBs[i] = bs[i];
				}
			} else {
				newBs = bs;
			}
			state.put(posi, newBs);
		}
	}
	
	
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
	@Override
	public int getCount() {
		return getLevelCount(Level_First);
	}
	
	public abstract int getLevelCount(int level);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, Object> tag = new HashMap<String, Object>();
		if(convertView == null){
			if(level == 0){
				convertView = getLevelView(Level_First,position,parent,tag);
			} else {
				convertView = createLevelView(position,parent,tag);
			} 
		} else {
			tag = (HashMap<String, Object>) convertView.getTag();
		}
		
		// ȡ��
		View[] v = (View[])tag.get(LevelView);
		
		// ��ȡ�����и���Ŀ��״̬����
		boolean[] bs = state.get(position);
		
		// ���û�У����½�
		if(bs == null){
			bs = new boolean[level];
			state.put(position, bs);
		}
		
		for(int level = 0; level < v.length; level++){
			// ����v������
			View currentView = ((LinearLayout) v[level]).getChildAt(0);
			if(currentView != null){
				fillLevelView(level,currentView,position,convertView,parent,tag);
			}
			
<<<<<<< HEAD
			//
			if(mLevelViewOnClickListener != null){
				mLevelViewOnClickListener.dealWithConvertView(currentView, level, bs[level]);
			}
			
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
			// ����v��״̬
			View nextView = ((LinearLayout) v[level]).getChildAt(1);
			if(nextView != null){
				if(bs[level] == true){
					nextView.setVisibility(View.VISIBLE);
				} else {
					nextView.setVisibility(View.GONE);
				}
			}
			
		}
		
		// ��ʱ�ü��� ��û���뵽�ð취��
		// ��� ��Ҫ������view �Լ� ����tag (tag ���� �㼶level �� �㼶View )
		HashMap<View, Tag> relationViews = new HashMap<View, Tag>();
		// �����Ҫ�����view
		View[] clickViews;
		if(pointIds){
			// ָ��ids , ѡ��id ���õ���¼�
			clickViews = new View[getSize(level, ids)];
			
			int itemPosi = 0; 
			
			for(int level = 0; level < v.length; level++){
				int[] is = ids.get(level);
				if(is == null){
					continue;
				}
				View contentView = v[level];
				int lenght = is.length;
				for(int i=0; i<lenght; i++){
					View itemView = contentView.findViewById(is[i]);
					
					Tag tg = new Tag();
					tg.contentView = contentView;
					tg.level = level;
					
					relationViews.put(itemView, tg);
					
					clickViews[itemPosi++] = itemView; 
				}
			}
			
		} else {
			// û��ָ��ids ,Ĭ�� ���õ�ǰitem �ĵ���¼� 
			
			clickViews = new View[level];
			
			int itemPosi = 0; 
			
			for(int level = 0; level < v.length; level++){
				View contentView = v[level];
				
				Tag tg = new Tag();
				tg.contentView = contentView;
				tg.level = level;
				relationViews.put(contentView, tg);
				
				clickViews[itemPosi++] = contentView; 
			}
		}
		
		ViewsClick viewsClick = new ViewsClick(position, relationViews);
		viewsClick.setOnClick(clickViews);
		
		return convertView;
	}
	
	private int getSize(int level,HashMap<Integer,int[]> ids){
		int size = 0;
		for(int i=0; i<level; i++ ){
			int[] js = ids.get(i);
			if(js != null){
				size += js.length;
			}
		}
		return size;
	}
	
	public abstract void fillLevelView(int level, View v,int position, View convertView, ViewGroup parent,HashMap<String,Object> tag);
	
	private View createLevelView(int position, ViewGroup parent,HashMap<String,Object> tag){
		
		// view �� tag
		if(tag == null){
			tag = new HashMap<String, Object>();
		}
		
		// ����ÿ�������ʽ
		View[] views = new View[level];
		
		// ����ÿ�������ʽ��״̬
<<<<<<< HEAD
		boolean[] bs = state.get(position);
		if(bs == null){
			bs = new boolean[level];
			// ��״̬���浽 ���� ��
			state.put(position, bs);
		} else {
			
		}
=======
		boolean[] booleans = new boolean[level];
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
		
		// ������һ�ݶ���ͼ
		View firstView = getLevelView(Level_First,position,parent,tag);
		
		// ����������ʽ��ͼ���� ��һ����ͼ��ӽ�ȥ
		LinearLayout baseStyleView = (LinearLayout) View.inflate(mContext, R.layout.base, null);
		baseStyleView.addView(firstView);
		
		// ����һ�������ʽ��������
		views[0] = baseStyleView;
		
		// �������㼶����ͼ
		for(int i=1; i< level; i++){
			
			// ��ȡ�㼶��ͼ
			View levelView = getLevelView(i,position,parent,tag);
			
			// ����������ʽ��ͼ
			LinearLayout baseView = (LinearLayout) View.inflate(mContext, R.layout.base, null);
			
			// ���㼶��ͼ ���� ������ͼ��
			baseView.addView(levelView);
			
			// ��ÿ�������ʽ��������
			views[i] = baseView;
			
			// ��������ȡ����һ�������ʽ
			View preView = views[i-1];
			
			// ���ò���ͼ�����ϲ���
			((LinearLayout) preView).addView(baseView);
			
		}
		
<<<<<<< HEAD
=======
		// ��״̬���浽 ���� ��
		state.put(position, booleans);

>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
		// ��views ���浽 ������
		tag.put(LevelView, views);
		
		// �������
		views = null;
		
		// ����
		baseStyleView.setTag(tag);
		
		return baseStyleView;
	}
	
	public abstract View getLevelView(int level,int position,ViewGroup parent,HashMap<String,Object> tag);
	
	public abstract Animator openViewAnim(View v);
	
	public abstract Animator closeViewAnim(View v);
	
	private void accessLevel(){
		if( level <  0){
			throw new IllegalArgumentException("һ�㶼û�У����ɻ�");
		}
	}
	
	class Tag {
		public View contentView;
		public int level;
	}
	
	public class ViewsClick implements OnClickListener {

		int position;
		boolean hasViewClick;
		HashMap<View,Tag> levelView ; // ��������view������
		View[] clickViews ;// ���clickView ������
		
		int preLevel = -1;
		
		private Animator openAnim; // �򿪶���
		private Animator closeAnim; // �رն��� 
		private boolean[] preOpen; // ������һ�β㼶view״̬ --> open
		private boolean[] preClose; // ������һ�β㼶view״̬ --> close
		
		public ViewsClick(int position,HashMap<View,Tag> levelView) {
			super();
			this.position = position;
			this.levelView = levelView;
		}
		
		public void setClickViews(View[] clickViews) {
			this.clickViews = clickViews;
		}

		// ������view���õ���¼�
		public void setOnClick(View[] clickViews){
			for (View view : clickViews) {
				if(view == null){
					// �Է���һ����һ�������id ���ٲ��������
					continue;
				}
				view.setOnClickListener(this);
			}
		}
		
<<<<<<< HEAD
		// �رն��� 
		public void closeAnim(){
			if(closeAnim != null){
				closeAnim.start();
			}
		}
		
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
		// ��ȡview �� contentView
		private View getContentView(View v){
			return levelView.get(v).contentView;
		}
		
		// ��ȡ��ǰview �� item �еĲ㼶
		private int getLevel(View v){
			if(v == null){
				return -1;
			} 
			Tag tag = levelView.get(v);
			if(tag == null){
				return -1;
			} else {
				return tag.level;
			}
		}

		@Override
		public void onClick(View v) {
			if(hasViewClick){
				// ��view������Ӧ����¼������������
				return ;
			} else {
				// ��ǰ��view������Ӧ����¼�����hasViewClick ��Ϊtrue
				hasViewClick = true;
				
				// ��ȡ��ǰitem �� ״̬ 
<<<<<<< HEAD
				boolean[] bs = getState().get(position);
=======
				final boolean[] bs = getState().get(position);
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
				
				// ��һ�θ� preOpen ���鸳ֵ
				if( preOpen == null ){
					// ��ȡ bs �ĳ���  --> Ϊ��ֵ
					int size = bs.length;
					preOpen = new boolean[size];
					for(int i=0; i<size; i++){
						preOpen[i] = bs[i];
					}
				}
				
				// ��һ�θ� preClose ���鸳ֵ
				if( preClose == null ){
					// ��ȡ bs �ĳ���  --> Ϊ��ֵ
					int size = bs.length;
					preClose = new boolean[size];
					for(int i=0; i<size; i++){
						preClose[i] = bs[i];
					}
				}
				
				// ��ȡcontentView 
				View contentView = getContentView(v);
				// ���contentView Ϊnull ����¼���ʾ������ϣ�һ�㲻�����
				if(contentView == null){
					hasViewClick = false;
					return ;
				}
				// ��ȡ ��Ҫ������view
				final View coreView = ((LinearLayout) contentView).getChildAt(1);
				
				// ��ȡ��ǰview �� item �еĲ㼶
				final int currentLevel = getLevel(v);
				if(currentLevel == -1){
					hasViewClick = false;
					return ;
				}
				
<<<<<<< HEAD
				// �����Ҫ�Ե�ǰview ���в���
				if(mLevelViewOnClickListener!=null){
					View firstView = ((LinearLayout) contentView).getChildAt(0);
					mLevelViewOnClickListener.onClick(firstView,currentLevel,bs[currentLevel]);
				}
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
				
				// �жϵ�ǰ�Ƿ���ڣ��������������һ����view���϶������ڣ�
				if(coreView != null){
					// ��ȡ��ǰview ��״̬ ����ʱֻ������ VISIBLE �� GONE ����״̬��
					if(coreView.getVisibility() == View.VISIBLE){
						// ��ǰview �� VISIBLE ״̬
						
						// ��ǰ�Ƿ��п��õ� closeAnim 
						// ��ǰclose ״̬�Ƿ�仯��   true û�仯  false �仯��
						boolean closeChange = Util.compareArr(bs, preClose);
						
						// �����ǰ��view �㼶�Ƿ�һ��
						boolean levelChange = (preLevel != currentLevel);
						
						// ��ǰû�п��õ� closeAnim || ��ǰitem ��״̬ �仯��
						if( closeAnim == null || levelChange || !closeChange ){
							// ���� closeAnim
							closeAnim = closeViewAnim(coreView);
							
							// ���¸�ֵpreLevel 
							preLevel = currentLevel;
							
							if(!closeChange){
								// ��ǰview ״̬�仯������preClose ��ֵ
								int size = bs.length;
								for(int i=0; i<size; i++){
									preClose[i] = bs[i];
								}
							}
							
							// �ٴ��жϵ�ǰ�Ƿ��� closeAnim 
							if(closeAnim == null){
								// û�л�ȡ�� closeAnim
								
								// GONE ����ǰview
								coreView.setVisibility(View.GONE);
								
<<<<<<< HEAD
								// ���ĵ�ǰitem ��״̬
								bs[currentLevel] = false;
								
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
								hasViewClick = false;
								// �˳��˴ε���¼�
								return ;
							} else {
								startCloseAnimtor(bs, coreView, currentLevel);
							}
						} else {
							startCloseAnimtor(bs, coreView, currentLevel);
						}
						
					} else {
						// ��ǰview �� GONE ״̬
						// ��ǰ�Ƿ��п��õ� openAnim 
						// ��ǰopen ״̬�Ƿ�仯��   true û�仯  false �仯��
						boolean openChange = Util.compareArr(bs, preOpen);
						
						// �����ǰ��view �㼶�Ƿ�һ��
						boolean levelChange = (preLevel != currentLevel);
						
						// ��ǰû�п��õ� closeAnim || ��ǰitem ��״̬ �仯��
						if( openAnim == null || levelChange || !openChange ){
							// ���¸�ֵpreLevel 
							preLevel = currentLevel;
							
							if( !openChange ){
								// ��ǰview ״̬�仯������preOpen ��ֵ
								int size = bs.length;
								for(int i=0; i<size; i++){
									preOpen[i] = bs[i];
								}
							}
							
							// ���� openAnim
							openAnim = openViewAnim(coreView);
							// �ٴ��жϵ�ǰ�Ƿ��� openAnim 
							if(openAnim == null){
								// û�л�ȡ�� openAnim
								
								// VISIBLE ����ǰview
								coreView.setVisibility(View.VISIBLE);
								
<<<<<<< HEAD
								// ���ĵ�ǰitem ��״̬
								bs[currentLevel] = true;
								
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
								hasViewClick = false;
								// �˳��˴ε���¼�
								return ;
							} else {
								startOpenAnimtor(bs, coreView, currentLevel);
							}
						} else {
							startOpenAnimtor(bs, coreView, currentLevel);
						}
						
					
					}
				} else {
					// ��ǰview�����ڣ�����Ҫ���в���
				}
			}
		}

		private void startOpenAnimtor(final boolean[] bs, final View coreView,
				final int currentLevel) {
			// ��ȡ�� openAnim
			
			// �ж� closeAnim �Ƿ���ڣ��Ƿ�����ִ�У� �ж� openAnim �Ƿ�����ִ��
			if( (closeAnim != null && closeAnim.isRunning()) || openAnim.isRunning() ){
				// closeAnim ����ִ�У� openAnim ����ִ��
				hasViewClick = false;
				return ;
			} else {
				// ���� openAnim ����
				openAnim.start();
				// ���� openAnim ����
				openAnim.addListener(new SimpleAnimatorListener(){
					@Override
					public void onAnimationEnd(Animator animation) {
						// openAnim ����ִ�н���
						
						// �õ�ǰview ��Ϊ VISIBLE
						coreView.setVisibility(View.VISIBLE);
						
						// ���ĵ�ǰitem ��״̬
						bs[currentLevel] = true;
						
						// openAnim ���н�����hasViewClick ��Ϊ false;
						hasViewClick = false;
						return ;
					}
				});
			}
		}

		private void startCloseAnimtor(final boolean[] bs, final View coreView,
				final int currentLevel) {
			// ��ȡ�� closeAnim
			
			// �ж� openAnim �Ƿ���ڣ��Ƿ�����ִ�У� �ж� closeAnim �Ƿ�����ִ��
			if( (openAnim != null && openAnim.isRunning()) || closeAnim.isRunning() ){
				// openAnim ����ִ�У� closeAnim ����ִ��
				
				hasViewClick = false;
				return ;
			} else {
				// ���� closeAnim ����
				closeAnim.start();
				// ���� closeAnim ����
				closeAnim.addListener(new SimpleAnimatorListener(){
					@Override
					public void onAnimationEnd(Animator animation) {
						// closeAnim ����ִ�н���
						
						// �õ�ǰview ��Ϊ GONE
						coreView.setVisibility(View.GONE);
						
						// ���ĵ�ǰitem ��״̬
						bs[currentLevel] = false;
						
						// closeAnim ���н�����hasViewClick ��Ϊ false;
						hasViewClick = false;
						return ;
					}
				});
			}
		}
		
	}
	
	public class SimpleAnimatorListener implements AnimatorListener {
		@Override
		public void onAnimationStart(Animator animation) {}

		@Override
		public void onAnimationEnd(Animator animation) {}

		@Override
		public void onAnimationCancel(Animator animation) {}

		@Override
		public void onAnimationRepeat(Animator animation) {}
	} 
	
<<<<<<< HEAD
	public interface LevelViewOnClickListener {
		void onClick(View v,int level,boolean isOpen);
		void dealWithConvertView(View v,int level,boolean isOpen);
	}
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
	
}
