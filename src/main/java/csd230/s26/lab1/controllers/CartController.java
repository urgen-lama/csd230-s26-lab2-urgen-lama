package csd230.s26.lab1.controllers;

import csd230.s26.lab1.entities.CartEntity;
import csd230.s26.lab1.entities.OrderEntity;
import csd230.s26.lab1.entities.ProductEntity;
import csd230.s26.lab1.entities.PublicationEntity;
import csd230.s26.lab1.repositories.CartRepository;
import csd230.s26.lab1.repositories.OrderRepository;
import csd230.s26.lab1.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository,
                          OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // 1. VIEW CART
    @GetMapping
    public String viewCart(Model model) {
        Long defaultCartId = 1L;
        CartEntity cart = cartRepository.findById(defaultCartId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setId(defaultCartId);
                    return cartRepository.save(newCart);
                });
        model.addAttribute("cart", cart);
        return "cartDetails";
    }

    // 2. ADD TO CART
    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        Long defaultCartId = 1L;
        CartEntity cart = cartRepository.findById(defaultCartId).orElse(null);
        ProductEntity product = productRepository.findById(productId).orElse(null);

        if (cart != null && product != null) {
            cart.addProduct(product);
            cartRepository.save(cart);
        }
        return "redirect:/books";
    }

    // 3. REMOVE FROM CART
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        Long defaultCartId = 1L;
        CartEntity cart = cartRepository.findById(defaultCartId).orElse(null);
        ProductEntity product = productRepository.findById(productId).orElse(null);

        if (cart != null && product != null) {
            cart.getProducts().remove(product);
            cartRepository.save(cart);
        }
        return "redirect:/cart";
    }

    // 4. CHECKOUT
    @PostMapping("/checkout")
    @Transactional
    public String checkout() {
        CartEntity cart = cartRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("No cart found with ID 1"));

        OrderEntity order = new OrderEntity();
        double total = 0.0;

        List<ProductEntity> snapshot = new ArrayList<>(cart.getProducts());

        for (ProductEntity product : snapshot) {
            total += product.getPrice();

            if (product instanceof PublicationEntity publication) {
                publication.setCopies(publication.getCopies() - 1);
                productRepository.save(publication);
            }

            order.getProducts().add(product);
        }

        order.setTotalAmount(total);

        cart.getProducts().clear();
        cartRepository.save(cart);

        OrderEntity saved = orderRepository.save(order);
        return "redirect:/cart/order/" + saved.getId();
    }

    // 5. ORDER CONFIRMATION
    @GetMapping("/order/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        model.addAttribute("order", order);
        return "orderDetails";
    }
}