package vn.iotstar.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
    private String username;

    @Column(name = "email", unique = true, nullable = false, columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100)")
    private String password; // đã hash BCrypt

    @Column(name = "fullname", columnDefinition = "NVARCHAR(100) NULL")
    private String fullname;

    @Column(name = "avatar", columnDefinition = "NVARCHAR(500) NULL")
    private String avatar;

    @Column(name = "roleid")
    private int roleid = 5; // 1: admin, 2: manager, 5: user thường (giống bài 1)

    @Column(name = "phone", unique = true, columnDefinition = "VARCHAR(20) NULL")
    private String phone;

    @Column(name = "createddate")
    private LocalDateTime createdDate;

    @Column(name = "active")
    private boolean active;

    @Column(name = "otp", columnDefinition = "VARCHAR(10) NULL")
    private String otp;

    @Column(name = "otpExpiry")
    private LocalDateTime otpExpiry;

    @OneToMany(mappedBy = "user")
    private List<Video> videos;

    public User() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public int getRoleid() { return roleid; }
    public void setRoleid(int roleid) { this.roleid = roleid; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
    public LocalDateTime getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(LocalDateTime otpExpiry) { this.otpExpiry = otpExpiry; }
    public List<Video> getVideos() { return videos; }
    public void setVideos(List<Video> videos) { this.videos = videos; }
}
