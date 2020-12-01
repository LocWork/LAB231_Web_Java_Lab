/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loc.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hi
 */
public class CartDTO {

    private Map<String, RoomCartDTO> cart;

    public CartDTO() {
    }

    public CartDTO(Map<String, RoomCartDTO> cart) {
        this.cart = cart;
    }

    public Map<String, RoomCartDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, RoomCartDTO> cart) {
        this.cart = cart;
    }

    public void add(RoomCartDTO dto) {
        if (cart == null) {
            cart = new HashMap<>();
        }
        if (this.cart.containsKey(dto.getRoomID())) {
            int quantity = 1;
            dto.setAmount(quantity);
        }
        cart.put(dto.getRoomID(), dto);
    }

    public void delete(String id) {
        if (cart == null) {
            return;
        }
        if (cart.containsKey(id)) {
            cart.remove(id);
        }
    }

    public void update(String id, RoomCartDTO dto) {
        if (cart != null) {
            if (cart.containsKey(id)) {
                cart.replace(id, dto);
            }
        }
    }
}
