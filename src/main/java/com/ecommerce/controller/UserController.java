package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User Controller for REST API endpoints
 * 
 * Follows Single Responsibility Principle:
 * - Only responsible for handling HTTP requests for user operations
 * 
 * Follows Open/Closed Principle:
 * - Can be extended without modifying existing code
 * 
 * Follows Dependency Inversion Principle:
 * - Depends on UserService abstraction
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for managing users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user
     */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(@Parameter(description = "User ID") @PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get user by username
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Retrieves a user by their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserByUsername(@Parameter(description = "Username") @PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get user by email
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieves a user by their email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserByEmail(@Parameter(description = "Email") @PathVariable String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update user information
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<User> updateUser(@Parameter(description = "User ID") @PathVariable Long id,
                                         @Valid @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Soft deletes a user by setting status to DELETED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@Parameter(description = "User ID") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all users with pagination
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users with pagination and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<User>> getAllUsers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = userService.getAllUsers(pageable);
        
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by role
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieves all users with the specified role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    public ResponseEntity<List<User>> getUsersByRole(@Parameter(description = "User role") @PathVariable User.UserRole role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get users by status", description = "Retrieves all users with the specified status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    public ResponseEntity<List<User>> getUsersByStatus(@Parameter(description = "User status") @PathVariable User.UserStatus status) {
        List<User> users = userService.getUsersByStatus(status);
        return ResponseEntity.ok(users);
    }

    /**
     * Get active users
     */
    @GetMapping("/active")
    @Operation(summary = "Get active users", description = "Retrieves all active users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Active users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    public ResponseEntity<List<User>> getActiveUsers() {
        List<User> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Search users by name
     */
    @GetMapping("/search")
    @Operation(summary = "Search users by name", description = "Searches for users whose name contains the search term")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users found successfully",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    public ResponseEntity<List<User>> searchUsersByName(@Parameter(description = "Search term") @RequestParam String name) {
        List<User> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }

    /**
     * Update user status
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update user status", description = "Updates the status of a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User status updated successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateUserStatus(@Parameter(description = "User ID") @PathVariable Long id,
                                               @Parameter(description = "New status") @RequestParam User.UserStatus status) {
        User updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Update user role
     */
    @PatchMapping("/{id}/role")
    @Operation(summary = "Update user role", description = "Updates the role of a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User role updated successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateUserRole(@Parameter(description = "User ID") @PathVariable Long id,
                                             @Parameter(description = "New role") @RequestParam User.UserRole role) {
        User updatedUser = userService.updateUserRole(id, role);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Verify user email
     */
    @PatchMapping("/{id}/verify-email")
    @Operation(summary = "Verify user email", description = "Marks a user's email as verified")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email verified successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> verifyEmail(@Parameter(description = "User ID") @PathVariable Long id) {
        User updatedUser = userService.verifyEmail(id);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Change user password
     */
    @PatchMapping("/{id}/change-password")
    @Operation(summary = "Change user password", description = "Changes the password of a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password changed successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid password")
    })
    public ResponseEntity<User> changePassword(@Parameter(description = "User ID") @PathVariable Long id,
                                             @Parameter(description = "New password") @RequestParam String newPassword) {
        User updatedUser = userService.changePassword(id, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Get user statistics
     */
    @GetMapping("/stats")
    @Operation(summary = "Get user statistics", description = "Retrieves various user statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    })
    public ResponseEntity<UserStats> getUserStats() {
        long totalUsers = userService.countByStatus(User.UserStatus.ACTIVE) + 
                         userService.countByStatus(User.UserStatus.INACTIVE) + 
                         userService.countByStatus(User.UserStatus.SUSPENDED);
        long activeUsers = userService.countActiveUsers();
        long customers = userService.countByRole(User.UserRole.CUSTOMER);
        long admins = userService.countByRole(User.UserRole.ADMIN);
        
        UserStats stats = new UserStats(totalUsers, activeUsers, customers, admins);
        return ResponseEntity.ok(stats);
    }

    /**
     * User statistics DTO
     */
    public static class UserStats {
        private final long totalUsers;
        private final long activeUsers;
        private final long customers;
        private final long admins;

        public UserStats(long totalUsers, long activeUsers, long customers, long admins) {
            this.totalUsers = totalUsers;
            this.activeUsers = activeUsers;
            this.customers = customers;
            this.admins = admins;
        }

        // Getters
        public long getTotalUsers() { return totalUsers; }
        public long getActiveUsers() { return activeUsers; }
        public long getCustomers() { return customers; }
        public long getAdmins() { return admins; }
    }
}
