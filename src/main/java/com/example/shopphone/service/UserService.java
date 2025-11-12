    package com.example.shopphone.service;

    import com.example.shopphone.model.User;
    import com.example.shopphone.repository.UserRepository;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class    UserService {

        @Autowired
        private UserRepository userRepository;

        /** --- Lấy user theo username/email/phone --- */
        public User getByUsername(String username) {
            return userRepository.findByUsername(username); // trả về User hoặc null
        }

        public User getByEmail(String email) {
            return userRepository.findByEmail(email); // trả về User hoặc null
        }

        public User getByPhone(String phone) {
            return userRepository.findByPhone(phone); // trả về User hoặc null
        }

        /** --- Lấy username hiện tại từ session --- */
        public String getCurrentUsername(HttpSession session) {
            return (String) session.getAttribute("username");
        }
        // Hàm lấy thông tin người dùng theo ID
        public User getById(Long id) {
            return userRepository.findById(id).orElse(null);
        }


        /** --- Lấy user hiện tại từ session --- */
        public User getCurrentUser(HttpSession session) {
            String username = getCurrentUsername(session);
            if (username != null) {
                return getByUsername(username);
            }
            return null;
        }

        /** --- Lưu hoặc cập nhật user (update) --- */
        public User save(User user) {
            // Nếu update, giữ nguyên avatar đã có, không tự set
            return userRepository.save(user);
        }

        /** --- Đăng ký user mới (set avatar mặc định nếu chưa có) --- */
        public User register(User user) {
            if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
                user.setAvatar("avatardefault.png"); // avatar mặc định
            }
            return userRepository.save(user);
        }

        /** --- Kiểm tra login: username/email/phone + password --- */
        public User authenticate(String account, String password) {
            User user = getByUsername(account);
            if (user == null) user = getByEmail(account);
            if (user == null) user = getByPhone(account);

            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        }


        /** --- Đăng xuất --- */
        public void logout(HttpSession session) {
            session.invalidate();
        }

    }
