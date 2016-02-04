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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Menu.Item;

/** Solitaire game on gPhone Android platform.
 * @author Neo Wang
 * @author <A HREF="http://gsolitaire.sourceforge.net">http://gsolitaire.sourceforge.net</A>
 * @version Version 1.0
 */
public class SolitaireActivity extends Activity {
    /** Called when the activity is first created. */
	private ViewDesk desk;
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        desk = new ViewDesk (this);
        this.setContentView(desk);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
        menu.add(0, Menu.FIRST, "New Game");
        return true;
	}
    public boolean onMenuItemSelected(int featureId, Item item) {
        switch(item.getId()) {
        case Menu.FIRST:
            desk.newGame();
            desk.invalidate();
            return true;
        }
        
        return super.onMenuItemSelected(featureId, item);
    }

}