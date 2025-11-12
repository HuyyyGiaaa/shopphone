package com.example.shopphone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.shopphone.model.CartItem;
import com.example.shopphone.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Helper: Convert Object to int (handle String, Number, null)
     */
    private int toInt(Object value, int defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /** ✅ Thêm sản phẩm vào giỏ hàng (có cả bộ nhớ) */
    @PostMapping("/add")
    public String addToCart(HttpSession session,
                            HttpServletRequest request,
                            @RequestParam int idhanghoa,
                            @RequestParam int idmau,
                            @RequestParam int idmay,
                            @RequestParam int idbonho, // ✅ Thêm bộ nhớ
                            @RequestParam(defaultValue = "1") int quantity) {

        String username = (String) session.getAttribute("username");

        // Nếu chưa đăng nhập → chuyển hướng đến login
        if (username == null) {
            String referer = request.getHeader("Referer");
            session.setAttribute("redirectAfterLogin",
                    referer != null ? referer : "/product/" + idhanghoa);
            return "redirect:/login?message=Bạn+phải+đăng+nhập+để+thêm+vào+giỏ+hàng";
        }

        // ✅ Thêm sản phẩm vào giỏ
        cartService.addToCart(username, idhanghoa, idmau, idmay, idbonho, quantity);

        // ✅ Cập nhật số lượng sản phẩm trong session
        session.setAttribute("cartCount", cartService.getCartCount(username));

        // ✅ Ghi nhớ trang trước
        String referer = request.getHeader("Referer");
        if (referer != null) session.setAttribute("lastPage", referer);

        return "redirect:/cart";
    }

    /**
     * API thêm sản phẩm dạng JSON (hỗ trợ client-side fetch/ajax).
     * POST /cart/addJson with JSON body { "idhanghoa":1, "idmau":2, "idmay":3, "idbonho":4, "quantity":1 }
     */
    @PostMapping(path = "/addJson", produces = "application/json")
    @ResponseBody
    public Object addToCartJson(HttpSession session, @RequestBody java.util.Map<String, Object> payload) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            System.out.println("❌ Unauthorized: no username in session");
            return java.util.Map.of("status", "error", "code", 401, "message", "Unauthorized");
        }

        try {
            int idhanghoa = toInt(payload.get("idhanghoa"), 0);
            int idmau = toInt(payload.get("idmau"), 0);
            int idmay = toInt(payload.get("idmay"), 0);
            int idbonho = toInt(payload.get("idbonho"), 0);
            int quantity = toInt(payload.get("quantity"), 1);

            System.out.println("📦 Request to add: idhanghoa=" + idhanghoa + ", idmau=" + idmau + ", idmay=" + idmay + ", idbonho=" + idbonho + ", qty=" + quantity);

            cartService.addToCart(username, idhanghoa, idmau, idmay, idbonho, quantity);
            int newCount = cartService.getCartCount(username);
            session.setAttribute("cartCount", newCount);

            System.out.println("✅ Successfully added! New cart count: " + newCount);
            return java.util.Map.of("status", "ok", "cartCount", newCount);
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
            return java.util.Map.of("status", "error", "message", e.getMessage(), "exception", e.getClass().getSimpleName());
        }
    }

    /** 🗑️ Xóa 1 sản phẩm trong giỏ */
    @PostMapping("/remove")
    public String removeFromCart(HttpSession session,
                                 @RequestParam int idhanghoa,
                                 @RequestParam int idmau,
                                 @RequestParam int idmay,
                                 @RequestParam int idbonho) { // ✅ thêm idbonho

        String username = (String) session.getAttribute("username");
        if (username != null) {
            cartService.removeFromCart(username, idhanghoa, idmau, idmay, idbonho);
            session.setAttribute("cartCount", cartService.getCartCount(username));
        }

        return "redirect:/cart";
    }

    /** 🛒 Xem giỏ hàng */
    @GetMapping
    public String viewCart(HttpSession session, Model model, HttpServletRequest request) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            session.setAttribute("redirectAfterLogin", "/cart");
            return "redirect:/login";
        }

        // Lưu trang trước khi vào giỏ
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/cart")) session.setAttribute("lastPage", referer);

        List<CartItem> cartItems = cartService.getCartItems(username);
        float totalPrice = cartService.getTotalPrice(username);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("username", username);

        return "cart";
    }

    /** 🗑️ API xóa sản phẩm dạng JSON */
    @PostMapping(path = "/removeJson", produces = "application/json")
    @ResponseBody
    public Object removeFromCartJson(HttpSession session, @RequestBody java.util.Map<String, Object> payload) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return java.util.Map.of("status", "error", "code", 401, "message", "Unauthorized");
        }

        try {
            // Convert to int (handle both Number and String)
            int idhanghoa = toInt(payload.get("idhanghoa"), 0);
            int idmau = toInt(payload.get("idmau"), 0);
            int idmay = toInt(payload.get("idmay"), 0);
            int idbonho = toInt(payload.get("idbonho"), 0);

            System.out.println("🗑️ Removing: idhanghoa=" + idhanghoa + ", idmau=" + idmau + ", idmay=" + idmay + ", idbonho=" + idbonho);
            cartService.removeFromCart(username, idhanghoa, idmau, idmay, idbonho);
            int newCount = cartService.getCartCount(username);
            session.setAttribute("cartCount", newCount);

            System.out.println("✅ Removed! New cart count: " + newCount);
            return java.util.Map.of("status", "ok", "cartCount", newCount);
        } catch (Exception e) {
            System.out.println("❌ Exception removing: " + e.getMessage());
            return java.util.Map.of("status", "error", "message", e.getMessage());
        }
    }

    /** 📝 API cập nhật số lượng sản phẩm */
    @PostMapping(path = "/update", produces = "application/json")
    @ResponseBody
    public Object updateQuantity(HttpSession session, @RequestBody java.util.Map<String, Object> payload) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return java.util.Map.of("status", "error", "code", 401, "message", "Unauthorized");
        }

        try {
            int idhanghoa = toInt(payload.get("idhanghoa"), 0);
            int idmau = toInt(payload.get("idmau"), 0);
            int idmay = toInt(payload.get("idmay"), 0);
            int idbonho = toInt(payload.get("idbonho"), 0);
            int quantity = toInt(payload.get("quantity"), 1);

            System.out.println("📝 Updating quantity: idhanghoa=" + idhanghoa + ", quantity=" + quantity);

            // Xóa item cũ
            cartService.removeFromCart(username, idhanghoa, idmau, idmay, idbonho);

            // Thêm lại với số lượng mới
            if (quantity > 0) {
                cartService.addToCart(username, idhanghoa, idmau, idmay, idbonho, quantity);
            }

            int newCount = cartService.getCartCount(username);
            session.setAttribute("cartCount", newCount);

            System.out.println("✅ Updated! New cart count: " + newCount);
            return java.util.Map.of("status", "ok", "cartCount", newCount);
        } catch (Exception e) {
            System.out.println("❌ Exception updating: " + e.getMessage());
            return java.util.Map.of("status", "error", "message", e.getMessage());
        }
    }

    /** 🧹 Xóa toàn bộ giỏ hàng */
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            cartService.clearCart(username);
            session.setAttribute("cartCount", 0);
        }
        return "redirect:/cart";
    }

    /** ↩️ Quay lại trang trước */
    @GetMapping("/back")
    public String backToPrevious(HttpSession session) {
        String lastPage = (String) session.getAttribute("lastPage");
        return lastPage != null ? "redirect:" + lastPage : "redirect:/";
    }
}
