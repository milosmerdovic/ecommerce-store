package com.ecommerce.service;

import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User Service interface
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for user business logic operations
 * 
 * Follows Interface Segregation Principle:
 * - Clients depend only on methods they use
 * 
 * Follows Dependency Inversion Principle:
 * - High-level modules depend on this abstraction
 */
public interface UserService {

    /**
     * Create a new user
     * 
     * @param user the user to create
     * @return the created user
     */
    User createUser(User user);

    /**
     * Find user by ID
     * 
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);

    /**
     * Find user by username
     * 
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     * 
     * @param email the email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Update user information
     * 
     * @param id the user ID
     * @param user the updated user information
     * @return the updated user
     */
    User updateUser(Long id, User user);

    /**
     * Delete user by ID
     * 
     * @param id the user ID
     */
    void deleteUser(Long id);

    /**
     * Get all users with pagination
     * 
     * @param pageable pagination information
     * @return Page of users
     */
    Page<User> getAllUsers(Pageable pageable);

    /**
     * Get users by role
     * 
     * @param role the role to search for
     * @return List of users with the specified role
     */
    List<User> getUsersByRole(User.UserRole role);

    /**
     * Get users by status
     * 
     * @param status the status to search for
     * @return List of users with the specified status
     */
    List<User> getUsersByStatus(User.UserStatus status);

    /**
     * Get active users
     * 
     * @return List of active users
     */
    List<User> getActiveUsers();

    /**
     * Get users by registration date range
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return List of users registered in the specified date range
     */
    List<User> getUsersByRegistrationDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get users by last login date
     * 
     * @param lastLoginDate the last login date
     * @return List of users who logged in after the specified date
     */
    List<User> getUsersByLastLoginAfter(LocalDateTime lastLoginDate);

    /**
     * Search users by name
     * 
     * @param name the name to search for
     * @return List of users whose name contains the search term
     */
    List<User> searchUsersByName(String name);

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
     * Update user status
     * 
     * @param id the user ID
     * @param status the new status
     * @return the updated user
     */
    User updateUserStatus(Long id, User.UserStatus status);

    /**
     * Update user role
     * 
     * @param id the user ID
     * @param role the new role
     * @return the updated user
     */
    User updateUserRole(Long id, User.UserRole role);

    /**
     * Update last login time
     * 
     * @param id the user ID
     * @return the updated user
     */
    User updateLastLogin(Long id);

    /**
     * Verify user email
     * 
     * @param id the user ID
     * @return the updated user
     */
    User verifyEmail(Long id);

    /**
     * Change user password
     * 
     * @param id the user ID
     * @param newPassword the new password
     * @return the updated user
     */
    User changePassword(Long id, String newPassword);

    /**
     * Get user count by role
     * 
     * @param role the role to count
     * @return number of users with the specified role
     */
    long countByRole(User.UserRole role);

    /**
     * Get user count by status
     * 
     * @param status the status to count
     * @return number of users with the specified status
     */
    long countByStatus(User.UserStatus status);

    /**
     * Get active user count
     * 
     * @return number of active users
     */
    long countActiveUsers();
}
