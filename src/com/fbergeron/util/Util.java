/*
 * Copyright (C) 2008  Neo Wang (wang.liang.com@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


package com.fbergeron.util;

import android.content.Resources;
import android.graphics.drawable.Drawable;
import com.wangland.gsolitaire.*;

/** The class to hold images of cards
 * 
 * As I totally rewrite this class, I change the copyright claim to mine.
 * @author Neo Wang
 * @author <A HREF="http://gsolitaire.sourceforge.net">http://gsolitaire.sourceforge.net</A>
 * @version Version 1.0
 */
public class Util {
	final static int[] drawableIds = {
			R.drawable.ha, R.drawable.h2, R.drawable.h3, R.drawable.h4, R.drawable.h5, R.drawable.h6, R.drawable.h7,
			R.drawable.h8,R.drawable.h9,R.drawable.h10,R.drawable.hj, R.drawable.hq, R.drawable.hk,
			R.drawable.sa, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5, R.drawable.s6, R.drawable.s7,
			R.drawable.s8,R.drawable.s9,R.drawable.s10,R.drawable.sj, R.drawable.sq, R.drawable.sk,
			R.drawable.da, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6, R.drawable.d7,
			R.drawable.d8,R.drawable.d9,R.drawable.d10,R.drawable.dj, R.drawable.dq, R.drawable.dk,
			R.drawable.ca, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7,
			R.drawable.c8,R.drawable.c9,R.drawable.c10,R.drawable.cj, R.drawable.cq, R.drawable.ck};
	private static Resources _resources;
	public static void loadImages(Resources resources){
		_resources = resources;
	}
	public static Drawable getAndroid(){
		return _resources.getDrawable(R.drawable.android);
	}
	
	public static Drawable getImage(int index){
		return _resources.getDrawable(drawableIds[index]);
	}
}
