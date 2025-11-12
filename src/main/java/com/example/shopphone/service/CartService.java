package com.example.shopphone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.shopphone.model.CTHangHoa;
import com.example.shopphone.model.CTHangHoaId;
import com.example.shopphone.model.CartItem;
import com.example.shopphone.repository.CTHangHoaRepository;
import com.example.shopphone.repository.CartRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

    @Autowired
    private CTHangHoaRepository ctHangHoaRepository;

    @Autowired
    private CartRepository cartRepository;

    /**
     * Lấy Session hiện tại
     */
    private HttpSession getSession() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            return attrs.getRequest().getSession();
        }
        return null;
    }

    /**
     * Lấy giỏ hàng từ Session (hoặc tạo mới nếu chưa có)
     */
    private List<CartItem> getSessionCart(String username) {
        HttpSession session = getSession();
        if (session == null) {
            System.out.println("⚠️ No session available");
            return new ArrayList<>();
        }

        String cartKey = "cart_" + username;
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute(cartKey);

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(cartKey, cart);
            System.out.println("📦 Created new cart for user: " + username);
        }
        return cart;
    }

    /**
     * Lấy tất cả sản phẩm trong giỏ của user (từ Session)
     */
    public List<CartItem> getCartItems(String username) {
        return getSessionCart(username);
    }

    /**
     * 💾 Lưu giỏ hàng từ Session vào Database (khi logout)
     */
    public void saveCartToDatabase(String username) {
        try {
            List<CartItem> sessionCart = getSessionCart(username);
            if (sessionCart != null && !sessionCart.isEmpty()) {
                // Xóa giỏ cũ trong DB
                cartRepository.deleteByUsername(username);
                // Lưu giỏ từ session vào DB
                cartRepository.saveAll(sessionCart);
                System.out.println("✅ Saved " + sessionCart.size() + " items to database for user: " + username);
            }
        } catch (Exception e) {
            System.out.println("❌ Error saving cart to database: " + e.getMessage());
        }
    }

    /**
     * 📥 Load giỏ hàng từ Database vào Session (khi login)
     */
    public void loadCartFromDatabase(String username) {
        try {
            List<CartItem> dbCart = cartRepository.findByUsername(username);
            if (dbCart != null && !dbCart.isEmpty()) {
                HttpSession session = getSession();
                if (session != null) {
                    String cartKey = "cart_" + username;
                    session.setAttribute(cartKey, dbCart);
                    System.out.println("✅ Loaded " + dbCart.size() + " items from database for user: " + username);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error loading cart from database: " + e.getMessage());
        }
    }

    /**
     * Thêm sản phẩm vào giỏ
     */
    public void addToCart(String username, int idhanghoa, int idmau, int idmay, int idbonho, int quantity) {
        try {
            List<CartItem> cart = getSessionCart(username);

            // Ép int sang Long để phù hợp với CTHangHoaId
            CTHangHoaId ctId = new CTHangHoaId(
                    Long.valueOf(idhanghoa),
                    Long.valueOf(idmau),
                    Long.valueOf(idmay),
                    Long.valueOf(idbonho)
            );

            CTHangHoa ct = ctHangHoaRepository.findById(ctId).orElse(null);
            if (ct == null) {
                System.out.println("⚠️ CTHangHoa not found for: mahh=" + idhanghoa + ", idmau=" + idmau + ", idmay=" + idmay + ", idbonho=" + idbonho);
                return;
            }

            // Kiểm tra đã có trong giỏ chưa
            CartItem existing = cart.stream()
                    .filter(i -> i.getIdhanghoa() == idhanghoa
                            && i.getIdmau() == idmau
                            && i.getIdmay() == idmay
                            && i.getIdbonho() == idbonho)
                    .findFirst()
                    .orElse(null);

            if (existing != null) {
                existing.setSoluong(existing.getSoluong() + quantity);
                System.out.println("✅ Updated quantity for product: " + idhanghoa);
            } else {
                CartItem newItem = new CartItem();
                newItem.setUsername(username);
                newItem.setIdhanghoa(idhanghoa);
                newItem.setIdmau(idmau);
                newItem.setIdmay(idmay);
                newItem.setIdbonho(idbonho);

                // Lấy thông tin từ quan hệ (với null-safe getter)
                String tenHH = "Không rõ";
                String mauSac = "Không rõ";
                String tenMay = "Không rõ";
                String boNho = "-";

                try {
                    if (ct.getHangHoa() != null) tenHH = ct.getHangHoa().getTenhh();
                    if (ct.getMauSac() != null) mauSac = ct.getMauSac().getTenMau();
                    if (ct.getDongMay() != null) tenMay = ct.getDongMay().getTenmay();
                    if (ct.getBoNho() != null) boNho = ct.getBoNho().getDungluong();
                } catch (Exception e) {
                    System.out.println("⚠️ Error fetching lazy relations: " + e.getMessage());
                }

                newItem.setTenHangHoa(tenHH);
                newItem.setMauSac(mauSac);
                newItem.setTenMay(tenMay);
                newItem.setBoNho(boNho);
                newItem.setSoluong(quantity);
                Float price = ct.getDongia();
                newItem.setDongia(price != null ? price : 0f);

                cart.add(newItem);
                System.out.println("✅ Added new product to cart: " + idhanghoa);
            }
        } catch (Exception e) {
            System.out.println("❌ Error adding to cart: " + e.getMessage());
        }
    }

    /**
     * Xóa sản phẩm khỏi giỏ (từ Session)
     */
    public void removeFromCart(String username, int idhanghoa, int idmau, int idmay, int idbonho) {
        try {
            List<CartItem> cart = getSessionCart(username);
            CartItem toRemove = cart.stream()
                    .filter(i -> i.getIdhanghoa() == idhanghoa
                            && i.getIdmau() == idmau
                            && i.getIdmay() == idmay
                            && i.getIdbonho() == idbonho)
                    .findFirst()
                    .orElse(null);

            if (toRemove != null) {
                cart.remove(toRemove);
                System.out.println("✅ Removed from cart: idhanghoa=" + idhanghoa);
            } else {
                System.out.println("⚠️ Product not found in cart: idhanghoa=" + idhanghoa);
            }
        } catch (Exception e) {
            System.out.println("❌ Error removing from cart: " + e.getMessage());
        }
    }

    /**
     * Tính tổng tiền giỏ hàng
     */
    public float getTotalPrice(String username) {
        List<CartItem> items = getSessionCart(username);
        return (float) items.stream()
                .mapToDouble(i -> i.getDongia() * i.getSoluong())
                .sum();
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    public void clearCart(String username) {
        try {
            List<CartItem> cart = getSessionCart(username);
            cart.clear();
            System.out.println("✅ Cleared cart for user: " + username);
        } catch (Exception e) {
            System.out.println("❌ Error clearing cart: " + e.getMessage());
        }
    }

    /**
     * Lấy tổng số sản phẩm trong giỏ
     */
    public int getCartCount(String username) {
        List<CartItem> items = getSessionCart(username);
        int count = 0;
        for (CartItem item : items) {
            count += item.getSoluong();
        }
        return count;
    }
}
