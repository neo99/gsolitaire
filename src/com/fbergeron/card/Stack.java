/*
 * Copyright (C) 2008  Neo Wang (wang.liang.com@gmail.com)
 * 
 * Copyright (C) 2002  Fr�d�ric Bergeron (fbergeron@users.sourceforge.net)
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

import java.util.Enumeration;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.fbergeron.util.Util;

/** A stack of cards.
 * 
 * @author Neo Wang
 * @author <A HREF="http://gsolitaire.sourceforge.net">http://gsolitaire.sourceforge.net</A>
 * @version Version 1.1, change to use Android views API
 * 
 * @author Frederic Bergeron
 * @author <A HREF="http://javasol.sourceforge.net">http://javasol.sourceforge.net</A>
 * @version Version 1.0
 */
public class Stack {

    public static final int SPREAD_NONE     = 0;
    public static final int SPREAD_NORTH    = 1;
    public static final int SPREAD_EAST     = 2;
    public static final int SPREAD_SOUTH    = 3;
    public static final int SPREAD_WEST     = 4;

    /**
     * Creates an empty stack of cards.
     */
    public  Stack() {
        cards = new Vector();
        setLocation( 0, 0 );
    }

    /**
     * @return <CODE>true</CODE>, if the stack is empty.
     * <CODE>false</CODE> otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * @return the card on top of the stack without poping it out.
     */
    public Card top() {
        if( cards.size() == 0 )
            return null;
        else
            return (Card)( cards.elementAt( cards.size() - 1 ) );
    }

    /**
     * @param index An index into the stack.
     * @return the card at the specified index.
     */
    public Card elementAt( int index ) {
        return (Card)(cards.elementAt( index ));
    }

    /**
     * Pushes a card on the stack.
     * @param c Card to be pushed.
     */
    public void push( Card c ) {
    	cards.addElement( c );
        c.setLocation( nextCardLocation );
        switch( spreadDirection ) {
            case SPREAD_NORTH : nextCardLocation.y -= spreadingDelta; break;
            case SPREAD_EAST : nextCardLocation.x += spreadingDelta; break;
            case SPREAD_SOUTH : nextCardLocation.y += spreadingDelta; break;
            case SPREAD_WEST : nextCardLocation.x -= spreadingDelta; break;
        }
    }

    /**
     * Pushes a stack of cards on the stack.
     * @param sc Stack of cards to be pushed.
     */
    public void push( Stack sc ) {
        for ( ; !sc.isEmpty(); ) {
           push( (Card) sc.pop() );
        }
    }

    /**
     * Pops a card from the stack.
     * @return The card on top of the stack.
     */
    public Card pop() {
        Card c = top();
        cards.removeElement( c );
        switch( spreadDirection ) {
            case SPREAD_NORTH : nextCardLocation.y += spreadingDelta; break;
            case SPREAD_EAST : nextCardLocation.x -= spreadingDelta; break;
            case SPREAD_SOUTH : nextCardLocation.y -= spreadingDelta; break;
            case SPREAD_WEST : nextCardLocation.x += spreadingDelta; break;
        }
        return c;
    }

    /**
     * Pops several cards from the stack into a returned stack.
     * @param n Number of cards to be popped.
     * @return A stack containing the cards popped.
     */
    public Stack pop( int n ) {
        Stack s = new Stack();

        for( int i = n; i > 0 && !isEmpty() ; i-- )
            s.push( pop() );
        s.setSpreadingDelta( spreadingDelta );
        s.setSpreadingDirection( spreadDirection );

        return s;
    }

    /**
     * Pops several cards from the stack into
     * a returned stack until the specified card is reached.
     * @param c Last card to be popped.
     * @return A stack containing the cards popped.
     */
    public Stack pop( Card c ) {
        Stack s = new Stack();

        for( ; !top().equals( c ) && !isEmpty(); ) {
            s.push( pop() );
        }
        //We also take the card c
        if( !isEmpty() )
            s.push( pop() );

        s.setSpreadingDelta( spreadingDelta );
        s.setSpreadingDirection( spreadDirection );

        return s;
    }

    /**
     * @return The number of cards contained on the stack.
     */
    public int cardCount() {
       return cards.size();
    }

    /**
     * @param c Card to check.
     * @return <CODE>true</CODE> if the stack contains
     * the card.  <CODE>false</CODE> otherwise.
     */
    public boolean contains( Card c ) {
        return cards.contains( c );
    }

    /**
     * @param c Card to check.
     * @return <CODE>true</CODE> if the stack contains
     * the card.  <CODE>false</CODE> otherwise.
     */
    public boolean contains( Point p ) {
        Rect rect = null;
        switch( spreadDirection ) {
            case SPREAD_NONE :
                rect = new Rect( location.x, location.y, location.x + Card.DEFAULT_WIDTH, location.y + Card.DEFAULT_HEIGHT );
                break;
            case SPREAD_NORTH :
                int height = Card.DEFAULT_HEIGHT + (cards.size()-1) * spreadingDelta;
                rect = new Rect( location.x - height, location.y, location.x, location.y + Card.DEFAULT_WIDTH );
//                rect = new Rect( location.x - height, location.y, height, Card.DEFAULT_WIDTH );
                break;
            case SPREAD_EAST :
                rect = new Rect( location.x, location.y, location.x + Card.DEFAULT_WIDTH + (cards.size()-1) * spreadingDelta, location.y + Card.DEFAULT_HEIGHT );
//                rect = new Rect( location.x, location.y, Card.DEFAULT_WIDTH + (cards.size()-1) * spreadingDelta, Card.DEFAULT_HEIGHT );
                break;
            case SPREAD_SOUTH :
                rect = new Rect( location.x, location.y, location.x + Card.DEFAULT_WIDTH, location.y + Card.DEFAULT_HEIGHT + (cards.size()-1) * spreadingDelta );
//                rect = new Rect( location.x, location.y, Card.DEFAULT_WIDTH, Card.DEFAULT_HEIGHT + (cards.size()-1) * spreadingDelta );
                break;
            case SPREAD_WEST :
                int width = Card.DEFAULT_WIDTH + (cards.size()-1) * spreadingDelta;
                rect = new Rect( location.x - width, location.y, location.x + width, location.y + Card.DEFAULT_HEIGHT );
//                rect = new Rect( location.x - width, location.y, width, Card.DEFAULT_HEIGHT );
                break;
        }
        return( rect.contains( p.x, p.y ) );
    }

    /**
     * Should be overridden by subclasses of stack.
     * For a normal stack, it always return <CODE>true</CODE>.
     * @return <CODE>true</CODE>, if it's ok to push a card on the stack.
     * <CODE>false</CODE> otherwise.
     */
    public  boolean isValid( Card c ) {
        return( true );
    }

    /**
     * Should be overridden by subclasses of stack.
     * For a normal stack, it always return <CODE>true</CODE>.
     * @return <CODE>true</CODE>, if it's ok to push a card on the stack.
     * <CODE>false</CODE> otherwise.
     */
    public  boolean isValid( Stack c ) {
        return( true );
    }

    /**
     * @return the constants corresponding to the spreading
     * direction of the stack of cards.
     */
    public int getSpreadingDirection() {
        return spreadDirection;
    }

    /**
     * @param sd Constant corresponding to the spreading
     * direction of the stack of cards.
     */
    public void setSpreadingDirection( int sd ) {
        spreadDirection = sd;
    }

    /**
     * @return the delta value in pixels corresponding to
     * space between each card spread.
     */
    public int getSpreadingDelta() {
        return spreadingDelta;
    }

    /**
     * @param delta the delta value in pixels corresponding to
     * space between each card spread.
     */
    public void setSpreadingDelta( int delta ) {
        spreadingDelta = delta;
    }

    public void draw(Canvas canvas) {
        if( isEmpty() ) {
            Point loc = getLocation();
            Paint paint = new Paint();
//            paint.setColor(Color.DKGRAY);
//            canvas.drawRect(loc.x, loc.y, loc.x + Card.DEFAULT_WIDTH, loc.y + Card.DEFAULT_HEIGHT, paint);
            paint.setColor(Color.rgb(0, 102, 0));
            canvas.drawRect(loc.x, loc.y, loc.x + Card.DEFAULT_WIDTH, loc.y + Card.DEFAULT_HEIGHT, paint);
//            g.setColor( Color.darkGray );
//            g.fillRect( loc.x, loc.y, Card.DEFAULT_WIDTH, Card.DEFAULT_HEIGHT );
//            g.setColor( Color.black );
//            g.drawRect( loc.x, loc.y, Card.DEFAULT_WIDTH, Card.DEFAULT_HEIGHT );
        }
        else
            for( Enumeration e = cards.elements(); e.hasMoreElements(); ) {
                Card c = (Card)(e.nextElement());
                c.draw(canvas);
//                c.paint( g );
            }
    }

    /**
     * @param p Point clicked.
     * @return The card corresponding to the clicked point p.
     */
    public Card getClickedCard( Point p ) {
        boolean cardFound = false;
        Card c = null;
        for( int i = cards.size() - 1; !cardFound && i >= 0; i-- ) {
            c = (Card)(cards.elementAt( i ));
            cardFound = c.contains( p );
        }
        return( c );
    }

    /**
     * Reverses the cards contained in the stack.
     */
    public void reverse() {
       Vector v = new Vector();

       for( ; !isEmpty(); )
          v.addElement( pop() );

        cards = v;
    }

    /**
     * Sets the location of the stack.
     * @param x X-coord.
     * @param y Y-coord.
     */
    public void setLocation( int x, int y ) {
        location = new Point( x, y );
        if( cards != null ) {
            for( Enumeration e = cards.elements(); e.hasMoreElements(); ) {
                Card c = (Card)(e.nextElement());
                c.setLocation( x, y );
                switch( spreadDirection ) {
                    case SPREAD_NORTH : y -= spreadingDelta; break;
                    case SPREAD_EAST : x += spreadingDelta; break;
                    case SPREAD_SOUTH : y += spreadingDelta; break;
                    case SPREAD_WEST : x -= spreadingDelta; break;
                }
            }
        }
        setNextCardLocation( x, y );
    }

    /**
     * @return the point corresponding to the location of the stack.
     */
    public Point getLocation() {
        return location;
    }

    public String toString() {
        return cards.toString();
    }

    private void setNextCardLocation( int x, int y ) {
        nextCardLocation = new Point( x, y );
    }

    private Point getNextCardLocation() {
        return nextCardLocation;
    }

    private   	Vector  	cards;
    private   	Point   	location;
    private   	Point   	nextCardLocation;
    private   	int     	spreadDirection;
    private   	int     	spreadingDelta;
}


