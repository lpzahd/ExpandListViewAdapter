package com.example.stronglvadapter;

<<<<<<< HEAD
=======
import android.util.Log;

>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
public class Util {
	
	public static boolean compareArr(boolean[] b1,boolean[] b2){
		// 比较长度
		if(b1.length != b2.length){
			return false;
		}
		
		// 比较全部元素
		for(int i=0; i<b1.length; i++){
			if(b1[i] != b2[i]){
<<<<<<< HEAD
=======
				Log.i("hit", i +"... "+ (b1[i])+" : "+(b2[i]));
>>>>>>> 3b6d8831990d984105b434a8f702d393c5c55f9d
				return false;
			}
		}
		
		return true;
	}
	
}
