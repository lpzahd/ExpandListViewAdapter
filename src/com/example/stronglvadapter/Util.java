package com.example.stronglvadapter;

import android.util.Log;

public class Util {
	
	public static boolean compareArr(boolean[] b1,boolean[] b2){
		// �Ƚϳ���
		if(b1.length != b2.length){
			return false;
		}
		
		// �Ƚ�ȫ��Ԫ��
		for(int i=0; i<b1.length; i++){
			if(b1[i] != b2[i]){
				Log.i("hit", i +"... "+ (b1[i])+" : "+(b2[i]));
				return false;
			}
		}
		
		return true;
	}
	
}
