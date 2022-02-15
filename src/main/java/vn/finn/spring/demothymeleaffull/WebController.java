package vn.finn.spring.demothymeleaffull;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {
    @GetMapping("/profile")
    public String profile(Model model){
        // Tạo ra thông tin
        List<Info> profile = new ArrayList<>();
        profile.add(new Info("fullname", "Nguyen Van Sam"));
        profile.add(new Info("nickname", "finn"));
        profile.add(new Info("gmail", "nguyensam576@gmail.com"));
        profile.add(new Info("facebook", "https://www.facebook.com/fin.nn777/"));
        profile.add(new Info("website", "https://www.facebook.com/fin.nn777/"));

        // Đưa thông tin vào Model
        model.addAttribute("finn", profile);

        // TRả về template profile.html
        return "profile";
    }
}
