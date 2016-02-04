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
package com.wangland.gsolitaire;

import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/** View of card. 
 * 
 * @deprecated I was going to use this view to represent each card that can response to click event
 * 			but I just find out this is not workable.
 * @author Neo Wang
 * @author <A HREF="http://gsolitaire.sourceforge.net">http://gsolitaire.sourceforge.net</A>
 * @version Version 1.0
 */
public class ViewCard extends View {

	public ViewCard(Context context, AttributeSet attrs, Map inflateParams) {
        super(context, attrs, inflateParams);
	}

	public ViewCard(Context context) {
        super(context);

//		super.setBackground(Color.GREEN);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
//		Dialog d = new Dialog(this.getContext());
//		d.setTitle("Hi Mystique");
//      TextView tv = new TextView(this.getContext());
//      tv.setText("Hello\nMystique");
//      
//      this.set
//      d.setContentView(tv);
//		d.show();
		System.out.println("hiiiiiiiiiiii");
		this.setBackground(this.getResources().getDrawable(R.drawable.icon));
		this.refreshDrawableState();
		
		return super.onKeyDown(keyCode, event);
	}

}
