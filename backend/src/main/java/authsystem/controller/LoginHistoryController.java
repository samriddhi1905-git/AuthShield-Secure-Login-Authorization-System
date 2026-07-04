package authsystem.controller;

import authsystem.entity.LoginHistory;
import authsystem.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginHistoryController {

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @GetMapping("/{email}")
    public List<LoginHistory> getHistory(@PathVariable String email) {

        return loginHistoryRepository.findByEmail(email);

    }

}