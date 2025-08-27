package com.ecommerce.repository;

import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User Repository interface
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for user data access operations
 * 
 * Follows Interface Segregation Principle:
 * - Clients depend only on methods they use
 * 
 * Follows Dependency Inversion Principle:
 * - High-level modules depend on this abstraction
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     * 
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     * 
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if username exists
     * 
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     * 
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find users by role
     * 
     * @param role the role to search for
     * @return List of users with the specified role
     */
    List<User> findByRole(User.UserRole role);

    /**
     * Find users by status
     * 
     * @param status the status to search for
     * @return List of users with the specified status
     */
    List<User> findByStatus(User.UserStatus status);

    /**
     * Find active users
     * 
     * @return List of active users
     */
    @Query("SELECT u FROM User u WHERE u.status = 'ACTIVE'")
    List<User> findActiveUsers();

    /**
     * Find users by registration date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return List of users registered in the specified date range
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findByRegistrationDateRange(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * Find users by last login date
     * 
     * @param lastLoginDate the last login date
     * @return List of users who logged in after the specified date
     */
    @Query("SELECT u FROM User u WHERE u.lastLogin > :lastLoginDate")
    List<User> findByLastLoginAfter(@Param("lastLoginDate") LocalDateTime lastLoginDate);

    /**
     * Find users with pagination
     * 
     * @param pageable pagination information
     * @return Page of users
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Find users by role with pagination
     * 
     * @param role the role to search for
     * @param pageable pagination information
     * @return Page of users with the specified role
     */
    Page<User> findByRole(User.UserRole role, Pageable pageable);

    /**
     * Find users by status with pagination
     * 
     * @param status the status to search for
     * @param pageable pagination information
     * @return Page of users with the specified status
     */
    Page<User> findByStatus(User.UserStatus status, Pageable pageable);

    /**
     * Search users by name (first name or last name)
     * 
     * @param name the name to search for
     * @return List of users whose first or last name contains the search term
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> searchByName(@Param("name") String name);

    /**
     * Count users by role
     * 
     * @param role the role to count
     * @return number of users with the specified role
     */
    long countByRole(User.UserRole role);

    /**
     * Count users by status
     * 
     * @param status the status to count
     * @return number of users with the specified status
     */
    long countByStatus(User.UserStatus status);

    /**
     * Count active users
     * 
     * @return number of active users
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.status = 'ACTIVE'")
    long countActiveUsers();
}
