package com.shopping.mall.service.impl;

import com.google.gson.Gson;
import com.mysql.cj.util.StringUtils;
import com.shopping.mall.dao.ProductMapper;
import com.shopping.mall.enums.ResponseEnum;
import com.shopping.mall.form.CartAddForm;
import com.shopping.mall.form.CartUpdateForm;
import com.shopping.mall.pojo.Cart;
import com.shopping.mall.pojo.Product;
import com.shopping.mall.service.ICartService;
import com.shopping.mall.vo.CartProductVo;
import com.shopping.mall.vo.CartVo;
import com.shopping.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.shopping.mall.enums.ProductEnum.ON_SALE;

@Service
public class CartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> addToCart(Integer uid, CartAddForm cartAddForm) {
        // add only one of each product every time
        final Integer QUANTITY = 1;

        // 1. check if the products are existed
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXISTED);
        }
        // 2. check if the products are on sale
        if (!ON_SALE.getCode().equals(product.getStatus())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE);
        }
        // 3. check if the products are available
        if (product.getStock() < 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_AVAILABLE);
        }

        //write to redis
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String productId = String.valueOf(product.getId());
        String value = opsForHash.get(redisKey, productId);

        Cart cart;
        if (StringUtils.isNullOrEmpty(value)) {
            cart = new Cart(product.getId(), QUANTITY, cartAddForm.getSelected());
        } else {
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + QUANTITY);
        }
        opsForHash.put(redisKey, productId, gson.toJson(cart));

        return getCartDetail(uid);
    }

    @Override
    public ResponseVo<CartVo> getCartDetail(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        for (Map.Entry<String, String> entry: entries.entrySet()) {
                Integer productId = Integer.valueOf(entry.getKey());
                Cart cart = gson.fromJson(entry.getValue(), Cart.class);

                //TODO: use mysql in
                Product product = productMapper.selectByPrimaryKey(productId);
                if (product != null) {
                    BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
                    CartProductVo cartProductVo = new CartProductVo(productId,
                            cart.getQuantity(), product.getName(), product.getSubtitle(), product.getMainImage(),
                            product.getPrice(), product.getStatus(), totalPrice,
                            product.getStock(), cart.getSelected());
                    cartProductVoList.add(cartProductVo);

                    if (!cart.getSelected()) {
                        selectAll = false;
                    }

                    if (cart.getSelected()){
                        cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                    }
                };
            cartTotalQuantity += cart.getQuantity();
        }

        cartVo.setSelectAll(selectAll);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> updateCart(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isNullOrEmpty(value)) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_IN_CART);
        }

        Cart cart = gson.fromJson(value, Cart.class);
        if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() > 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setSelected(cartUpdateForm.getSelected());
        }
        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));

        return getCartDetail(uid);
    }

    @Override
    public ResponseVo<CartVo> removeItemFromCart(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isNullOrEmpty(value)) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_IN_CART);
        }

        opsForHash.delete(redisKey, String.valueOf(productId));
        return getCartDetail(uid);
    }

    private List<Cart> generateListForCart (Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry: entries.entrySet()) {
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            cartList.add(cart);
        }
        return cartList;
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        List<Cart> cartList = generateListForCart(uid);
        for (Cart cart: cartList) {
            cart.setSelected(true);
            // redis high performance
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return getCartDetail(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        List<Cart> cartList = generateListForCart(uid);
        for (Cart cart: cartList) {
            cart.setSelected(false);
            // redis high performance
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return getCartDetail(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
//        Integer sum = generateListForCart(id).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        List<Cart> cartList = generateListForCart(uid);
        Integer sum = 0;
        for (Cart cart : cartList) {
            sum += cart.getQuantity();
        }
        return ResponseVo.success(sum);
    }
}
