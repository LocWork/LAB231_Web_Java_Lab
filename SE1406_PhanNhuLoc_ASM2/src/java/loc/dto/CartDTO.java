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

    private Map<String, ProductDTO> cart;

    public CartDTO() {
    }

    public CartDTO(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }

    public void add(ProductDTO dto) {
        if (cart == null) {
            cart = new HashMap<>();
        }
        if (this.cart.containsKey(dto.getProductID())) {
            int quantity = cart.get(dto.productID).getQuantity();
            dto.setQuantity(quantity);
        }
        cart.put(dto.productID, dto);
    }

    public void delete(String id) {
        if (cart == null) {
            return;
        }
        if (cart.containsKey(id)) {
            cart.remove(id);
        }
    }

    public void update(String id, ProductDTO dto) {
        if (cart != null) {
            if (cart.containsKey(id)) {
                cart.replace(id, dto);
            }
        }
    }

}
