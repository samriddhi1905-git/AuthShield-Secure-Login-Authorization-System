package authsystem.repository;

import authsystem.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoginHistoryRepository
        extends JpaRepository<LoginHistory, Long> {

    List<LoginHistory> findByEmail(String email);

    @Query("SELECT COUNT(l) FROM LoginHistory l")
    long totalLogins();

    @Query("SELECT COUNT(l) FROM LoginHistory l WHERE l.status='APPROVED'")
    long approvedLogins();

    @Query("SELECT COUNT(l) FROM LoginHistory l WHERE l.status='REJECTED'")
    long rejectedLogins();

    @Query("SELECT COUNT(l) FROM LoginHistory l WHERE l.status='PENDING'")
    long pendingLogins();

}