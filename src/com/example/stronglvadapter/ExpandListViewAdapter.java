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
 * 可展开闭合的ListViewAdapter
 * 尚有遗漏 ：
 * 		动画可能有点多，占用内容可能比较大，集合数组类创建数量比较多，优化不足
 * 		偶尔会发生卡顿现象，应该是优化不足
 * 		必须传入 level 层次数量，万一手抽写错 似乎不爽的样子
 * 		代码写的格式不优雅（注释太多，然而难以一目了然）
 * 		可能会发生点击事件冲突（尚未测试）
 * 		居然有警告
 * 		偶尔会发生展开的内容不显示（不用动画到没发生，也有可能是测试动类，
 * 				修改了原来view视图的大小,写的有问题，原因没查到）
 * 		动画限定了属性动画，感觉不爽。没有判断动画参数是否合理
 */
public abstract class ExpandListViewAdapter extends BaseAdapter{
	
	private int level; // 层级数 0 ~ N
	
	public static int Level_First = 0;
	
	public static int Add_Level = 1;
	
	public static String LevelView = "Level_View";
	
	private Context mContext;
	
	// 指定id --> 点击id进行展开关闭    || 不指定 --> 点击每层view进行展开关闭
	public boolean pointIds; 
	
	// 保存 每层view的状态   Integer --> position
	public HashMap<Integer,boolean[]> state = new HashMap<Integer,boolean[]>();
	
	// 保存 每层view 中指定的id  Integer --> level
	private HashMap<Integer,int[]> ids ;
	
<<<<<<< HEAD
	//层级view 的点击事件监听
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
		// 当设置当前 ids ,表示需要自己选定 id 进行展开关闭
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
		
		// 取包
		View[] v = (View[])tag.get(LevelView);
		
		// 获取集合中该条目的状态集合
		boolean[] bs = state.get(position);
		
		// 如果没有，则新建
		if(bs == null){
			bs = new boolean[level];
			state.put(position, bs);
		}
		
		for(int level = 0; level < v.length; level++){
			// 更新v的内容
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
			// 设置v的状态
			View nextView = ((LinearLayout) v[level]).getChildAt(1);
			if(nextView != null){
				if(bs[level] == true){
					nextView.setVisibility(View.VISIBLE);
				} else {
					nextView.setVisibility(View.GONE);
				}
			}
			
		}
		
		// 暂时用集合 （没有想到好办法）
		// 存放 需要操作的view 以及 他的tag (tag 包含 层级level 和 层级View )
		HashMap<View, Tag> relationViews = new HashMap<View, Tag>();
		// 存放需要点击的view
		View[] clickViews;
		if(pointIds){
			// 指定ids , 选定id 设置点击事件
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
			// 没有指定ids ,默认 设置当前item 的点击事件 
			
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
		
		// view 的 tag
		if(tag == null){
			tag = new HashMap<String, Object>();
		}
		
		// 保存每层基础样式
		View[] views = new View[level];
		
		// 保存每层基础样式的状态
<<<<<<< HEAD
		boolean[] bs = state.get(position);
		if(bs == null){
			bs = new boolean[level];
			// 将状态保存到 集合 中
			state.put(position, bs);
		} else {
			
		}
=======
		boolean[] booleans = new boolean[level];
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
		
		// 创建第一梯队视图
		View firstView = getLevelView(Level_First,position,parent,tag);
		
		// 创建基础样式视图，将 第一层视图添加进去
		LinearLayout baseStyleView = (LinearLayout) View.inflate(mContext, R.layout.base, null);
		baseStyleView.addView(firstView);
		
		// 将第一层基础样式加入数组
		views[0] = baseStyleView;
		
		// 填充各个层级的视图
		for(int i=1; i< level; i++){
			
			// 获取层级视图
			View levelView = getLevelView(i,position,parent,tag);
			
			// 创建基础样式视图
			LinearLayout baseView = (LinearLayout) View.inflate(mContext, R.layout.base, null);
			
			// 将层级视图 加入 基础视图中
			baseView.addView(levelView);
			
			// 将每层基础样式加入数组
			views[i] = baseView;
			
			// 从数组中取出上一层基础样式
			View preView = views[i-1];
			
			// 将该层视图加入上层中
			((LinearLayout) preView).addView(baseView);
			
		}
		
<<<<<<< HEAD
=======
		// 将状态保存到 集合 中
		state.put(position, booleans);

>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
		// 将views 保存到 集合中
		tag.put(LevelView, views);
		
		// 清空数组
		views = null;
		
		// 背包
		baseStyleView.setTag(tag);
		
		return baseStyleView;
	}
	
	public abstract View getLevelView(int level,int position,ViewGroup parent,HashMap<String,Object> tag);
	
	public abstract Animator openViewAnim(View v);
	
	public abstract Animator closeViewAnim(View v);
	
	private void accessLevel(){
		if( level <  0){
			throw new IllegalArgumentException("一层都没有，你搞飞机");
		}
	}
	
	class Tag {
		public View contentView;
		public int level;
	}
	
	public class ViewsClick implements OnClickListener {

		int position;
		boolean hasViewClick;
		HashMap<View,Tag> levelView ; // 保存所有view的数组
		View[] clickViews ;// 点击clickView 的数组
		
		int preLevel = -1;
		
		private Animator openAnim; // 打开动画
		private Animator closeAnim; // 关闭动画 
		private boolean[] preOpen; // 保存上一次层级view状态 --> open
		private boolean[] preClose; // 保存上一次层级view状态 --> close
		
		public ViewsClick(int position,HashMap<View,Tag> levelView) {
			super();
			this.position = position;
			this.levelView = levelView;
		}
		
		public void setClickViews(View[] clickViews) {
			this.clickViews = clickViews;
		}

		// 给所有view设置点击事件
		public void setOnClick(View[] clickViews){
			for (View view : clickViews) {
				if(view == null){
					// 以防万一，万一输入错误id 至少不会崩溃了
					continue;
				}
				view.setOnClickListener(this);
			}
		}
		
<<<<<<< HEAD
		// 关闭动画 
		public void closeAnim(){
			if(closeAnim != null){
				closeAnim.start();
			}
		}
		
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
		// 获取view 的 contentView
		private View getContentView(View v){
			return levelView.get(v).contentView;
		}
		
		// 获取当前view 在 item 中的层级
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
				// 有view正在响应点击事件，不允许操作
				return ;
			} else {
				// 当前有view正在响应点击事件，将hasViewClick 置为true
				hasViewClick = true;
				
				// 获取当前item 的 状态 
<<<<<<< HEAD
				boolean[] bs = getState().get(position);
=======
				final boolean[] bs = getState().get(position);
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
				
				// 第一次给 preOpen 数组赋值
				if( preOpen == null ){
					// 获取 bs 的长度  --> 为赋值
					int size = bs.length;
					preOpen = new boolean[size];
					for(int i=0; i<size; i++){
						preOpen[i] = bs[i];
					}
				}
				
				// 第一次给 preClose 数组赋值
				if( preClose == null ){
					// 获取 bs 的长度  --> 为赋值
					int size = bs.length;
					preClose = new boolean[size];
					for(int i=0; i<size; i++){
						preClose[i] = bs[i];
					}
				}
				
				// 获取contentView 
				View contentView = getContentView(v);
				// 如果contentView 为null 点击事件表示处理完毕，一般不会出现
				if(contentView == null){
					hasViewClick = false;
					return ;
				}
				// 获取 需要操作的view
				final View coreView = ((LinearLayout) contentView).getChildAt(1);
				
				// 获取当前view 在 item 中的层级
				final int currentLevel = getLevel(v);
				if(currentLevel == -1){
					hasViewClick = false;
					return ;
				}
				
<<<<<<< HEAD
				// 如果需要对当前view 进行操作
				if(mLevelViewOnClickListener!=null){
					View firstView = ((LinearLayout) contentView).getChildAt(0);
					mLevelViewOnClickListener.onClick(firstView,currentLevel,bs[currentLevel]);
				}
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
				
				// 判断当前是否存在（如果点击的是最后一级的view，肯定不存在）
				if(coreView != null){
					// 获取当前view 的状态 （暂时只打算用 VISIBLE 和 GONE 两种状态）
					if(coreView.getVisibility() == View.VISIBLE){
						// 当前view 是 VISIBLE 状态
						
						// 当前是否有可用的 closeAnim 
						// 当前close 状态是否变化过   true 没变化  false 变化过
						boolean closeChange = Util.compareArr(bs, preClose);
						
						// 点击当前的view 层级是否一样
						boolean levelChange = (preLevel != currentLevel);
						
						// 当前没有可用的 closeAnim || 当前item 的状态 变化了
						if( closeAnim == null || levelChange || !closeChange ){
							// 创建 closeAnim
							closeAnim = closeViewAnim(coreView);
							
							// 重新赋值preLevel 
							preLevel = currentLevel;
							
							if(!closeChange){
								// 当前view 状态变化过，给preClose 赋值
								int size = bs.length;
								for(int i=0; i<size; i++){
									preClose[i] = bs[i];
								}
							}
							
							// 再次判断当前是否有 closeAnim 
							if(closeAnim == null){
								// 没有获取到 closeAnim
								
								// GONE 到当前view
								coreView.setVisibility(View.GONE);
								
<<<<<<< HEAD
								// 更改当前item 的状态
								bs[currentLevel] = false;
								
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
								hasViewClick = false;
								// 退出此次点击事件
								return ;
							} else {
								startCloseAnimtor(bs, coreView, currentLevel);
							}
						} else {
							startCloseAnimtor(bs, coreView, currentLevel);
						}
						
					} else {
						// 当前view 是 GONE 状态
						// 当前是否有可用的 openAnim 
						// 当前open 状态是否变化过   true 没变化  false 变化过
						boolean openChange = Util.compareArr(bs, preOpen);
						
						// 点击当前的view 层级是否一样
						boolean levelChange = (preLevel != currentLevel);
						
						// 当前没有可用的 closeAnim || 当前item 的状态 变化了
						if( openAnim == null || levelChange || !openChange ){
							// 重新赋值preLevel 
							preLevel = currentLevel;
							
							if( !openChange ){
								// 当前view 状态变化过，给preOpen 赋值
								int size = bs.length;
								for(int i=0; i<size; i++){
									preOpen[i] = bs[i];
								}
							}
							
							// 创建 openAnim
							openAnim = openViewAnim(coreView);
							// 再次判断当前是否有 openAnim 
							if(openAnim == null){
								// 没有获取到 openAnim
								
								// VISIBLE 到当前view
								coreView.setVisibility(View.VISIBLE);
								
<<<<<<< HEAD
								// 更改当前item 的状态
								bs[currentLevel] = true;
								
=======
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
								hasViewClick = false;
								// 退出此次点击事件
								return ;
							} else {
								startOpenAnimtor(bs, coreView, currentLevel);
							}
						} else {
							startOpenAnimtor(bs, coreView, currentLevel);
						}
						
					
					}
				} else {
					// 当前view不存在，不需要进行操作
				}
			}
		}

		private void startOpenAnimtor(final boolean[] bs, final View coreView,
				final int currentLevel) {
			// 获取到 openAnim
			
			// 判断 closeAnim 是否存在，是否正在执行； 判断 openAnim 是否正在执行
			if( (closeAnim != null && closeAnim.isRunning()) || openAnim.isRunning() ){
				// closeAnim 正在执行， openAnim 正在执行
				hasViewClick = false;
				return ;
			} else {
				// 开启 openAnim 动画
				openAnim.start();
				// 挂在 openAnim 监听
				openAnim.addListener(new SimpleAnimatorListener(){
					@Override
					public void onAnimationEnd(Animator animation) {
						// openAnim 动画执行结束
						
						// 让当前view 置为 VISIBLE
						coreView.setVisibility(View.VISIBLE);
						
						// 更改当前item 的状态
						bs[currentLevel] = true;
						
						// openAnim 运行结束，hasViewClick 置为 false;
						hasViewClick = false;
						return ;
					}
				});
			}
		}

		private void startCloseAnimtor(final boolean[] bs, final View coreView,
				final int currentLevel) {
			// 获取到 closeAnim
			
			// 判断 openAnim 是否存在，是否正在执行； 判断 closeAnim 是否正在执行
			if( (openAnim != null && openAnim.isRunning()) || closeAnim.isRunning() ){
				// openAnim 正在执行， closeAnim 正在执行
				
				hasViewClick = false;
				return ;
			} else {
				// 开启 closeAnim 动画
				closeAnim.start();
				// 挂在 closeAnim 监听
				closeAnim.addListener(new SimpleAnimatorListener(){
					@Override
					public void onAnimationEnd(Animator animation) {
						// closeAnim 动画执行结束
						
						// 让当前view 置为 GONE
						coreView.setVisibility(View.GONE);
						
						// 更改当前item 的状态
						bs[currentLevel] = false;
						
						// closeAnim 运行结束，hasViewClick 置为 false;
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
