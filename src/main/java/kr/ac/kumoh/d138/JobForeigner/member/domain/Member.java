package kr.ac.kumoh.d138.JobForeigner.member.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.board.domain.Comment;
import kr.ac.kumoh.d138.JobForeigner.board.domain.Post;
import kr.ac.kumoh.d138.JobForeigner.global.base.BaseEntity;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.MemberProfileRequest;
import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE member_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MemberType type;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender", columnDefinition = "char(1)", nullable = false)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "profile_url", nullable = false)
    private String profileImageUrl;

    @Column(name = "verified", nullable = false)
    private Boolean isVerified = true;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "address", nullable = false)),
            @AttributeOverride(name = "detailAddress", column = @Column(name = "detail_address")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode", nullable = false))
    })
    private Address address;

    @Builder
    public Member(
            @NonNull String name,
            @NonNull String password,
            @NonNull MemberType type,
            Long companyId,
            String countryCode,
            @NonNull String phoneNumber,
            @NonNull String email,
            @NonNull Gender gender,
            @NonNull LocalDate birthDate,
            @NonNull String profileImageUrl,
            @NonNull Address address
    ) {
        this.name = name;
        this.password = password;
        this.type = type;
        this.companyId = companyId;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profileImageUrl = profileImageUrl;
        this.address = address;
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy="member", fetch=FetchType.LAZY)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Resume> resumes = new ArrayList<>();
  
    public void changeEmail(String email) {
        this.email = email;
    }

    public void setVerified() {
        this.isVerified = true;
    }

    public void unsetVerified() {
        this.isVerified = false;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void updateMemberProfile(MemberProfileRequest request) {
        this.phoneNumber = request.phoneNumber();
        this.email = request.email();
        this.address = new Address(
                request.address(),
                request.detailAddress(),
                request.zipcode()
        );
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
