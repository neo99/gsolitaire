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

import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.fbergeron.card.ClassicCard;
import com.fbergeron.card.ClassicDeck;
import com.fbergeron.card.Stack;
import com.fbergeron.solitaire.SequentialStack;
import com.fbergeron.solitaire.SolitaireStack;
import com.fbergeron.util.Util;

/** The main and the only view in the whole program, representing the desk/table where you draw cards.
 * @author Neo Wang
 * @author <A HREF="http://gsolitaire.sourceforge.net">http://gsolitaire.sourceforge.net</A>
 * @version Version 1.0
 */
public class ViewDesk extends View {

	public ViewDesk(Context context) {
        super(context);
        
		setBackgroundColor(Color.rgb(51, 153, 51));
		
		//request focus
		setFocusable(true);
		
		Util.loadImages(this.getResources());
		
		newGame();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
//		loadImages();
        repaint(canvas);
	}
	

    protected   Stack               currStack;
    protected   ClassicDeck         deck;
    protected   Stack               revealedCards;
    protected   SolitaireStack[]    solStack;
    protected   SequentialStack[]   seqStack;
    /** Number of sequential stacks. */
    public static final int SEQ_STACK_CNT = 4;

    /** Number of solitaire stacks. */
    public static final int SOL_STACK_CNT = 7;

    /** Number of cards freed from the deck when requested. */
    public static final int FREED_CARDS_CNT = 3;

    public static final int VERTICAL_GAP = 5, HORIZON_GAP = 5, SPREADING_DELTA = 20;
    public static final Point DECK_POS              = new Point( 5, 5 );
    public static final Point REVEALED_CARDS_POS    = new Point( DECK_POS.x + ClassicCard.DEFAULT_WIDTH + HORIZON_GAP, 5 );
    public static final Point SEQ_STACK_POS         = new Point( DECK_POS.x + (ClassicCard.DEFAULT_WIDTH + VERTICAL_GAP)*3, DECK_POS.y );
//    public static final Point SEQ_STACK_POS         = new Point( REVEALED_CARDS_POS.x + ClassicCard.DEFAULT_WIDTH + 92, DECK_POS.y );
    public static final Point SOL_STACK_POS         = new Point( DECK_POS.x, SEQ_STACK_POS.y + ClassicCard.DEFAULT_HEIGHT + HORIZON_GAP );
    public static final Point CURR_STACK_POS        = new Point( DECK_POS.x + (ClassicCard.DEFAULT_WIDTH + VERTICAL_GAP)*3, 
    															 DECK_POS.y + ClassicCard.DEFAULT_HEIGHT + HORIZON_GAP + SPREADING_DELTA * 13 + HORIZON_GAP);

    public void newGame() {
        deck = new ClassicDeck();
        deck.shuffle();
        deck.setLocation( DECK_POS.x, DECK_POS.y );

        revealedCards = new Stack();
        revealedCards.setLocation( REVEALED_CARDS_POS.x, REVEALED_CARDS_POS.y );

        seqStack = new SequentialStack[ SEQ_STACK_CNT ];
        for ( int i = 0; i < SEQ_STACK_CNT; i++ ) {
            seqStack[ i ] = new SequentialStack();
            seqStack[ i ].setLocation( SEQ_STACK_POS.x + i * (ClassicCard.DEFAULT_WIDTH + 5), SEQ_STACK_POS.y );
        }

        solStack = new SolitaireStack[ SOL_STACK_CNT ];
        for ( int i = 0; i < SOL_STACK_CNT; i++ ) {
            solStack[ i ] = new SolitaireStack();
            solStack[ i ].setSpreadingDirection( Stack.SPREAD_SOUTH );
            solStack[ i ].setSpreadingDelta( SPREADING_DELTA );
            solStack[ i ].setLocation( SOL_STACK_POS.x + i * (ClassicCard.DEFAULT_WIDTH + 5), SOL_STACK_POS.y );
        }

        currStack = new Stack();
        currStack.setSpreadingDirection( Stack.SPREAD_SOUTH );
        currStack.setSpreadingDelta( SPREADING_DELTA );

        distributeCards();
    }
    private void repaint(Canvas canvas){
//            //Create offscreen
//            Dimension dim = this.getSize();
//            if( offscreen == null ) {
//                offscreen = this.createImage( dim.width, dim.height );
//                offscreenGr = offscreen.getGraphics();
//            }
//
//            //Draw background
//            offscreenGr.setColor( TABLE_COLOR );
//            offscreenGr.fillRect( 0, 0, dim.width, dim.height );
//
//            //Draw background image
//            //offscreenGr.drawImage( backgroundImage, 0, 0, dim.width, dim.height, this );
//
//            //Draw deck
        Paint paint = new Paint();
            if( deck != null )
                if( deck.isEmpty() ) {
                    Point loc = deck.getLocation();
//                    paint.setColor(Color.DKGRAY);
//                    canvas.drawRect(loc.x, loc.y, loc.x + ClassicCard.DEFAULT_WIDTH, loc.y + ClassicCard.DEFAULT_HEIGHT, paint);
                    paint.setColor(Color.rgb(0, 102, 0));
                    canvas.drawRect(loc.x, loc.y, loc.x + ClassicCard.DEFAULT_WIDTH, loc.y + ClassicCard.DEFAULT_HEIGHT, paint);
//                    offscreenGr.setColor( Color.darkGray );
//                    offscreenGr.fillRect( loc.x, loc.y, ClassicCard.DEFAULT_WIDTH, ClassicCard.DEFAULT_HEIGHT );
//                    offscreenGr.setColor( Color.black );
//                    offscreenGr.drawRect( loc.x, loc.y, ClassicCard.DEFAULT_WIDTH, ClassicCard.DEFAULT_HEIGHT );
                }
                else
                    deck.top().draw(canvas);

            //Draw revealedCards
            if( revealedCards != null && !revealedCards.isEmpty() )
                revealedCards.top().draw(canvas);

            //Draw sequential stacks
            if( seqStack != null )
                for( int i = 0; i < SEQ_STACK_CNT; i++ )
                    seqStack[ i ].draw(canvas);

            //Draw solitaire stacks
            if( solStack != null )
                for( int i = 0; i < SOL_STACK_CNT; i++ )
                    solStack[ i ].draw(canvas);

            //Draw current stack
            if( currStack != null && !currStack.isEmpty())
                currStack.draw(canvas);

            //You win
            if (cong != null){
            	paint.setColor(Color.BLACK);
            	canvas.drawText(cong, CURR_STACK_POS.x - 10, CURR_STACK_POS.y, paint);
            	cong = null;
            }
    }
    /**
     * Plays a stack of cards from a stack to another stack.
     * @param curr Current stack of cards to be played.
     * @param src Stack where the card comes from.
     * @param dst Stack where the card is played.
     */
    public void play( Stack curr, Stack src, Stack dst ) {
        if( curr != null )
            curr.reverse();
        if( dst != null && dst.isValid( curr ) ) {
            for( ; !curr.isEmpty(); )
                dst.push( curr.pop() );
            if( !src.isEmpty() && src.top().isFaceDown() ) {
                ClassicCard topCard = ((ClassicCard)src.top());
                topCard.turnFaceUp();
            }
            if( isGameWon() )
                congratulate();
        }
        else {
            for( ; !curr.isEmpty(); )
                src.push( curr.pop() );
        }
    }
    /**
     * @return <CODE>true</CODE>, if the game is won.
     * <CODE>false</CODE> otherwise.
     */
    private boolean isGameWon() {
        boolean gameWon = deck.isEmpty() && revealedCards.isEmpty();
        if( gameWon )
            for( int i = 0; i < SOL_STACK_CNT && gameWon; i++ )
                gameWon = gameWon && solStack[ i ].isEmpty();
        return( gameWon );
    }

    /**
     * Shows a frame congratulating the player.
     */
    private String cong = null;
    private void congratulate(){
    	cong = "YOU WIN";
    }


    /** Each time a new game begins, we have to distribute the
     * cards in colums.  The number of columns is equal to SOL_STACK_CNT.
     */
    private void distributeCards() {
        for( int i = 0; i < SOL_STACK_CNT; i++ ) {
            ClassicCard c = ((ClassicCard)deck.pop());
            c.turnFaceUp();
            solStack[ i ].push( c );
            for( int j = i+1; j < SOL_STACK_CNT; j++ )
                solStack[ j ].push( deck.pop() );
        }
    }

    private   Stack   curr;
    private   Stack   src;
    private   Stack   dst;
//    private Date oldDate = new Date();
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) return true;
		//I don't know why it just trigger onTouchEvent three times every time I click on the screen
		//so, now I change the program don't response to continuous quick touches (if they are)
		//maybe I coded somewhere wrong that triggers 3 times events.
//		Date now = new Date();
//		if (now.getTime() - oldDate.getTime() > 2000){
//			oldDate = now;
//		} else {
//			return true;
//		}
//		
		//if current stack is null, populate current stack with the one(s) clicked, and place current stack to the bottom.
		//otherwise, place current stack on selected stack, then clean current stack
        Point p = new Point ((int)event.getX(), (int)event.getY());
		if (curr == null){
	        ClassicCard c = null;
	
	        if( deck.contains( p ) ){
	            getNewCards();
	        } else {
                if( !revealedCards.isEmpty() && revealedCards.top().contains( p ) ) {
                    src = revealedCards;
                    c = ((ClassicCard)src.top());
                } else {
                    for( int i = 0; i < SOL_STACK_CNT && src == null; i++ ) {
                        if( !solStack[ i ].isEmpty() && solStack[ i ].contains( p ) ) {
                            src = solStack[ i ];
                            c = ((ClassicCard)src.getClickedCard( p ));
                        }
                    }
                    for( int i = 0; i < SEQ_STACK_CNT && src == null; i++ ) {
                        if( !seqStack[ i ].isEmpty() && seqStack[ i ].contains( p ) ) {
                            src = seqStack[ i ];
                            c = ((ClassicCard)src.top());
                        }
                    }
                }

                //We don't allow to drag hidden cards
                if( c != null && c.isFaceDown() ) {
                    src = null;
                    c = null;
                }
                if( src != null && c != null ) {
//                    Point loc = c.getLocation();
//                    translation = new Point( p.x - loc.x, p.y - loc.y );
                    currStack = src.pop( c );
                    currStack.reverse();
                    curr = currStack;
                    currStack.setLocation(CURR_STACK_POS.x, CURR_STACK_POS.y);
                }
	        }
		} else {
            for( int i = 0; i < SOL_STACK_CNT && dst == null; i++ ) {
                if( solStack[ i ].contains( p ) )
                    dst = solStack[ i ];
            }
            for( int i = 0; i < SEQ_STACK_CNT && dst == null; i++ ) {
                if( seqStack[ i ].contains( p ) )
                    dst = seqStack[ i ];
            }
            if( curr != null && src != null )
                play( curr, src, dst );
            curr = src = dst = null;
		}
		
		this.invalidate();
		
		return true;
	}

    public void getNewCards() {
        //First, restore the deck if it's empty.
        if( deck.isEmpty() ) {
            for( ; !revealedCards.isEmpty(); ) {
                ClassicCard c = ((ClassicCard)revealedCards.pop());
                c.turnFaceDown();
                deck.push( c );
            }
        }

        for( int i = 0; !deck.isEmpty() && i < FREED_CARDS_CNT; i++ ) {
            ClassicCard c = ((ClassicCard)deck.pop());
            c.turnFaceUp();
            revealedCards.push( c );
        }

    }


}
