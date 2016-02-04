/*
 * Copyright (C) 2008  Neo Wang (wang.liang.com@gmail.com)
 * 
 * Copyright (C) 2002  Frédéric Bergeron (fbergeron@users.sourceforge.net)
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

package com.fbergeron.card;

import android.graphics.*;
import android.graphics.drawable.*;

import java.io.*;
import java.util.*;

import com.fbergeron.util.*;

/** A classic game card.
 * 
 * @author Neo Wang
 * @author <A HREF="http://gsolitaire.sourceforge.net">http://gsolitaire.sourceforge.net</A>
 * @version Version 1.1, change to use Android views API
 * 
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public class ClassicCard extends Card {

    public static final String  STRING_HIDDEN  = "X";

    public static final int   CARD_COLOR = Color.BLUE;

    /**
     * Creates a card.
     *
     * @param value The value of the card.
     * @param suit  The suit of the card.
     */
    public ClassicCard( Value value, Suit suit, Drawable img ) {
    	super();
        _suit = suit;
        _value = value;
        _img = img;
		StringBuffer imgName = new StringBuffer( _suit.toString() );
		imgName.append( "/" ).append( _value.toString() );
		_imgName = imgName.toString();
        turnFaceDown();
    }

//    public void setImageObserver( ImageObserver imgObserver ) {
//    	_imgObserver = imgObserver;
//    }

    /**
     * @return Color of the card.
     * May be either <CODE>Color.red</CODE> or <CODE>Color.black</CODE>.
     */
    public int getColor() {
        return ( _suit == Suit.SPADE || _suit == Suit.CLUB ) ? Color.BLACK : Color.RED;
    }

    /**
     * @return Value of the card.  May be from 1 to 13.
     */
    public Value getValue() {
        return _value;
    }

    /**
     * @return Suit of the card.  May be one of these values
     * <CODE>HEART</CODE>, <CODE>SPADE</CODE>, <CODE>DIAMOND</CODE> or <CODE>CLUB</CODE>,
     */
    public Suit getSuit() {
        return _suit;
    }

    public boolean equals(Object obj) {
        return
            isFaceDown() == ( (ClassicCard)obj ).isFaceDown() &&
            _suit == ( (ClassicCard)obj )._suit &&
            _value == ( (ClassicCard)obj )._value;
    }

    public String toString() {
        StringBuffer strBufTemp = new StringBuffer();
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        strBufTemp.append( _value.toString() );
        strBufTemp.append( _suit.toString() );
        if( isFaceDown() )
            strBufTemp.append( STRING_HIDDEN );
        return strBufTemp.toString();
    }

    public void draw( Canvas canvas ) {
        Point location = getLocation();

        Paint paint = new Paint();

        //Background
        if( isFaceDown() ) {
        	Drawable android = Util.getAndroid();
        	android.setBounds(location.x, location.y, location.x + this.getSize().width, location.y + this.getSize().height);
        	android.draw(canvas);
        	
//        	paint.setColor(CARD_COLOR);
//        	canvas.drawRect(location.x, location.y, location.x + this.getSize().width, location.y + this.getSize().height, paint);
        	
//        	canvas.drawRect(location.x, location.y, getSize().width - 1, getSize().height - 1, paint);
//            g.setColor( CARD_COLOR );
//            g.fillRect( location.x, location.y, getSize().width - 1, getSize().height - 1 );
        }
//        else {
////        	canvas.drawColor(Color.WHITE);
//        	paint.setColor(Color.WHITE);
//        	canvas.drawRect(location.x, location.y, getSize().width - 1, getSize().height - 1, paint);
////            g.setColor( Color.white );
////            g.fillRect( location.x, location.y, getSize().width - 1, getSize().height - 1 );
//        }

        //Frame
//    	paint.setColor(Color.BLACK);
//    	canvas.drawRect(location.x, location.y, getSize().width - 1, getSize().height - 1, paint);
//        g.setColor( Color.black );
//        g.drawRect( location.x, location.y, getSize().width - 1, getSize().height - 1 );

        //Card
        if ( !isFaceDown() ) {
//			Drawable img = (Drawable)images.get( _imgName );
			if( _img != null /*&& _imgObserver != null */ ){
//				_img.setBounds(location.x + 3, location.y + 3, location.x + 3 + this.getSize().width, location.y + 3 + this.getSize().height);
				_img.setBounds(location.x, location.y, location.x + this.getSize().width, location.y + this.getSize().height);
				_img.draw(canvas);
//				g.drawImage( img, location.x + 3, location.y + 3, _imgObserver );
			}
			
//			Image img = (Image)images.get( _imgName );
//			if( img != null && _imgObserver != null )
//				g.drawImage( img, location.x + 3, location.y + 3, _imgObserver );

//            //Round edges
//            paint.setColor( Color.BLACK);
//            canvas.drawLine( location.x, location.y, location.x + getSize().width - 1, location.y, paint );
//            canvas.drawLine( location.x, location.y + getSize().height - 1, location.x + getSize().width - 1, location.y + getSize().height - 1, paint );
//            canvas.drawLine( location.x, location.y, location.x, location.y + getSize().height - 1, paint );
//            canvas.drawLine( location.x + getSize().width - 1, location.y, location.x + getSize().width - 1, location.y + getSize().height - 1, paint );
//            canvas.drawLine( location.x + 1, location.y + 1, location.x + 1, location.y + 1, paint );
//            canvas.drawLine( location.x + getSize().width - 2, location.y + 1, location.x + getSize().width - 2, location.y + 1, paint );
//            canvas.drawLine( location.x + 1, location.y + getSize().height - 2, location.x + 1, location.y + getSize().height - 2, paint );
//            canvas.drawLine( location.x + getSize().width - 2, location.y + getSize().height - 2, location.x + getSize().width - 2, location.y + getSize().height - 2, paint );
//            g.setColor( Color.black );
//            g.drawLine( location.x, location.y, location.x + getSize().width - 1, location.y );
//            g.drawLine( location.x, location.y + getSize().height - 1, location.x + getSize().width - 1, location.y + getSize().height - 1 );
//            g.drawLine( location.x, location.y, location.x, location.y + getSize().height - 1 );
//            g.drawLine( location.x + getSize().width - 1, location.y, location.x + getSize().width - 1, location.y + getSize().height - 1 );
//			g.drawLine( location.x + 1, location.y + 1, location.x + 1, location.y + 1 );
//			g.drawLine( location.x + getSize().width - 2, location.y + 1, location.x + getSize().width - 2, location.y + 1 );
//			g.drawLine( location.x + 1, location.y + getSize().height - 2, location.x + 1, location.y + getSize().height - 2 );
//			g.drawLine( location.x + getSize().width - 2, location.y + getSize().height - 2, location.x + getSize().width - 2, location.y + getSize().height - 2 );
        }
        //Round edges
        paint.setColor( Color.BLACK);
        canvas.drawLine( location.x, location.y, location.x + getSize().width - 1, location.y, paint );
        canvas.drawLine( location.x, location.y + getSize().height - 1, location.x + getSize().width - 1, location.y + getSize().height - 1, paint );
        canvas.drawLine( location.x, location.y, location.x, location.y + getSize().height - 1, paint );
        canvas.drawLine( location.x + getSize().width - 1, location.y, location.x + getSize().width - 1, location.y + getSize().height - 1, paint );
        canvas.drawLine( location.x + 1, location.y + 1, location.x + 1, location.y + 1, paint );
        canvas.drawLine( location.x + getSize().width - 2, location.y + 1, location.x + getSize().width - 2, location.y + 1, paint );
        canvas.drawLine( location.x + 1, location.y + getSize().height - 2, location.x + 1, location.y + getSize().height - 2, paint );
        canvas.drawLine( location.x + getSize().width - 2, location.y + getSize().height - 2, location.x + getSize().width - 2, location.y + getSize().height - 2, paint );
    }

//    public static Hashtable<String, Drawable>    images = new Hashtable<String, Drawable>();
    private Drawable _img;
//    static private MediaTracker tracker = new MediaTracker( new Button() );

    //Preloading of the card images.
//    static {
//    	for( int i = 0; i < Suit.suits.length; i++ ) {
//    		for( int j = 0; j < Value.values.length; j++ ) {
//    			StringBuffer imgFilename = new StringBuffer( Suit.suits[ i ].toString() );
//    			imgFilename.append( "/" ).append( Value.values[ j ].toString() );
//    			String imgName = imgFilename.toString();
//    			imgFilename.append( ".png" );
//			    Image img = Util.getImageResourceFile( imgFilename.toString(), ClassicCard.class );
//			    tracker.addImage( img, 0 );
//	            images.put( imgName, img );
//    		}
//    	}
//		try {
//			tracker.waitForID( 0 );
//		}
//		catch( InterruptedException e ) {
//			// Ignore the interruption.
//		}
//    }

    private Suit        	_suit;
    private Value       	_value;
    private String			_imgName;

//    private ImageObserver	_imgObserver;
//    private Image       	_img;
}
