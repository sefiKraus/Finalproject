package com.sefy.finalproject.Cart;

import com.sefy.finalproject.CustomMessageEvent;
import com.sefy.finalproject.Model.CartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Vector;
/**
 * Created by sefy1 on 25/08/2017.
 */

public class CartListService {

    private Vector<CartItem> cartItemVector;
    private static final CartListService instance = new CartListService();

    private CartListService(){
        EventBus.getDefault().register(this);
        cartItemVector = new Vector<>();
    }

    public static CartListService getInstance() {

        return instance;
    }

    /**
     * Adding new item to cart if the item already exists it will increase it's quantity
     * @param cartItem
     */
    public void addToCart(CartItem cartItem){

        if(getInstance().cartItemVector.size() ==0){
            cartItemVector.add(cartItem);

        }
        else{
            CartItem tempItem = null;
            for(int i = 0 ; i< getInstance().cartItemVector.size() ; i++) {
                if(getInstance().cartItemVector.get(i).getItem().getId().equals(cartItem.getItem().getId())){
                    tempItem =getInstance().cartItemVector.get(i);
                    break;
                }
            }
            if(tempItem == null){
                getInstance().cartItemVector.add(cartItem);
            }
            else{
                tempItem.setQuantity(tempItem.getQuantity() + 1);
            }

        }


    }

    /**
     *  Remove cart item
     * @param cartItem
     */
    public void removeFromCart(CartItem cartItem){
        for(int i = 0 ; i< getInstance().cartItemVector.size() ; i++){
            CartItem tempItem = getInstance().cartItemVector.get(i);
            if(tempItem.getItem().getBrandName().equals(cartItem.getItem().getBrandName())
                    && tempItem.getItem().getName().equals(cartItem.getItem().getName())){

                getInstance().cartItemVector.remove(i);

            }
        }

    }
    public Vector<CartItem> getCartItemVector() {
        return cartItemVector;
    }


    /**
     * subscribed to CustomMessageEvent and will take care of adding new cartItem to shopping cart
     * @param event
     */
    @Subscribe
    public void onMessageEvent(CustomMessageEvent event) {
        this.addToCart(event.getCartItem());
    };


}
