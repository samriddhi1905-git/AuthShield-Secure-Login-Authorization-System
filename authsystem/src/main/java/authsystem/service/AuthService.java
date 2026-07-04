package authsystem.service;

import authsystem.entity.LoginHistory;
import authsystem.repository.LoginHistoryRepository;
import authsystem.dto.ApprovalStatusResponse;
import authsystem.dto.LoginRequest;
import authsystem.dto.RegisterRequest;
import authsystem.entity.User;
import authsystem.repository.UserRepository;
import authsystem.repository.LoginRequestRepository;
import authsystem.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import authsystem.dto.ApprovalStatusResponse;

@Service
public class AuthService {

    @Autowired
private LocationService locationService;

    @Autowired
private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // ================= REGISTER =================

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // ================= LOGIN =================

    public String login(
            LoginRequest request,
            HttpServletRequest httpRequest) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return "User not found";
        }

        boolean isPasswordCorrect =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!isPasswordCorrect) {
            return "Invalid Credentials";
        }

        // Create approval token
        String approvalToken = UUID.randomUUID().toString();

        authsystem.entity.LoginRequest loginRequest =
                new authsystem.entity.LoginRequest();

        loginRequest.setEmail(user.getEmail());
        loginRequest.setToken(approvalToken);
        loginRequest.setCreatedAt(LocalDateTime.now());

        loginRequest.setExpiresAt(
                LocalDateTime.now().plusMinutes(10)
        );

        loginRequest.setApproved(false);

        loginRequestRepository.save(loginRequest);

        // Collect login details
        String ipAddress = httpRequest.getRemoteAddr();

        String browser =
                httpRequest.getHeader("User-Agent");

       /*  String loginTime =
                LocalDateTime.now().toString();*/

                DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(
                "dd MMM yyyy, hh:mm a"
        );

String loginTime =
        LocalDateTime.now().format(formatter);

                String location =
        locationService.getLocation(ipAddress);




                LoginHistory history = new LoginHistory();

history.setEmail(user.getEmail());
history.setIpAddress(ipAddress);
history.setBrowser(browser);
history.setStatus("PENDING");
history.setLoginTime(LocalDateTime.now());

loginHistoryRepository.save(history);





        // Send Email
        try {
           emailService.sendApprovalEmail(
        user.getEmail(),
        approvalToken,
        ipAddress,
        browser,
        loginTime,
        location
);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email";
        }

        return "Approval email sent. Check your inbox.";

    }

    // ================= APPROVE LOGIN =================

 public String approveLogin(String token) {

    authsystem.entity.LoginRequest loginRequest =
            loginRequestRepository.findByToken(token)
                    .orElse(null);

    if (loginRequest == null) {
        return "Invalid Token";
    }

    if (loginRequest.getExpiresAt()
            .isBefore(LocalDateTime.now())) {
        return "Approval Link Expired";
    }

    if (loginRequest.isApproved()) {
        return """
        <html>
        <head>
        <title>Already Approved</title>
        <style>
        body{
            font-family:Arial,sans-serif;
            background:#0f172a;
            color:white;
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            margin:0;
        }
        .card{
            background:#1e293b;
            padding:40px;
            border-radius:15px;
            text-align:center;
            box-shadow:0 0 20px rgba(0,0,0,.4);
        }
        h1{
            color:#facc15;
        }
        </style>
        </head>
        <body>
            <div class="card">
                <h1>Already Approved</h1>
                <p>This login request has already been approved.</p>
            </div>
        </body>
        </html>
        """;
    }

    // Mark approved
    loginRequest.setApproved(true);

    // Generate JWT
    String jwt = jwtUtil.generateToken(loginRequest.getEmail());

    // Save JWT
    loginRequest.setJwtToken(jwt);

    // Save request
    loginRequestRepository.save(loginRequest);

    // Save login history
    LoginHistory history = new LoginHistory();

    history.setEmail(loginRequest.getEmail());
    history.setIpAddress("127.0.0.1");
    history.setBrowser("Approved via Email");
    history.setStatus("APPROVED");
    history.setLoginTime(LocalDateTime.now());

    loginHistoryRepository.save(history);

    // Success Page
    return """
    <html>

    <head>

        <meta http-equiv="refresh" content="2;url=http://localhost:5173/dashboard">

        <title>Login Approved</title>

        <style>

        body{
            font-family:Arial,sans-serif;
            background:#0f172a;
            color:white;
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            margin:0;
        }

        .card{
            background:#1e293b;
            padding:40px;
            border-radius:15px;
            text-align:center;
            box-shadow:0 0 20px rgba(0,0,0,.4);
        }

        h1{
            color:#22c55e;
        }

        p{
            color:#cbd5e1;
        }

        </style>

    </head>

    <body>

        <div class="card">

            <h1>✅ Login Approved</h1>

            <p>Redirecting to Dashboard...</p>

        </div>

    </body>

    </html>
    """;
}

    // ================= REJECT LOGIN =================

    public String rejectLogin(String token) {

        authsystem.entity.LoginRequest loginRequest =
                loginRequestRepository.findByToken(token)
                        .orElse(null);

        if (loginRequest == null) {
            return "Invalid Token";
        }






        LoginHistory history = new LoginHistory();

history.setEmail(loginRequest.getEmail());
history.setIpAddress("127.0.0.1");
history.setBrowser("Rejected via Email");
history.setStatus("REJECTED");
history.setLoginTime(LocalDateTime.now());

loginHistoryRepository.save(history);






        loginRequestRepository.delete(loginRequest);

        return "Login Rejected Successfully";
    }




    public ApprovalStatusResponse checkApprovalStatus(String token) {

    authsystem.entity.LoginRequest loginRequest =
            loginRequestRepository.findByToken(token)
                    .orElse(null);

    if (loginRequest == null) {
        return new ApprovalStatusResponse(false, null);
    }

    if (!loginRequest.isApproved()) {
        return new ApprovalStatusResponse(false, null);
    }

    return new ApprovalStatusResponse(
            true,
            loginRequest.getJwtToken()
    );
}



}