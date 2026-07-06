package authsystem.controller;

import authsystem.dto.ApprovalStatusResponse;
import authsystem.dto.DashboardStats;
import authsystem.dto.LoginRequest;
import authsystem.dto.RegisterRequest;
import authsystem.repository.LoginHistoryRepository;
import authsystem.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    // ================= REGISTER =================

    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        return authService.login(request, httpRequest);
    }

    // ================= APPROVE LOGIN =================

    @GetMapping("/approve/{token}")
    public String approveLogin(@PathVariable String token) {

        return authService.approveLogin(token);
    }

    // ================= REJECT LOGIN =================

    @GetMapping("/reject/{token}")
    public String rejectLogin(@PathVariable String token) {

        return authService.rejectLogin(token);
    }

    // ================= APPROVAL STATUS =================

    @GetMapping("/status/{token}")
    public ApprovalStatusResponse checkStatus(
            @PathVariable String token) {

        return authService.checkApprovalStatus(token);
    }

    // ================= DASHBOARD STATS =================

    @GetMapping("/dashboard/stats")
    public DashboardStats getDashboardStats() {

        return new DashboardStats(
                loginHistoryRepository.totalLogins(),
                loginHistoryRepository.approvedLogins(),
                loginHistoryRepository.rejectedLogins(),
                loginHistoryRepository.pendingLogins()
        );
    }

}