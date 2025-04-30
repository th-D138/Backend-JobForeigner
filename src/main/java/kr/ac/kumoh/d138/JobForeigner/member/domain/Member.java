package kr.ac.kumoh.d138.JobForeigner.member.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.ac.kumoh.d138.JobForeigner.board.domain.Comment;
import kr.ac.kumoh.d138.JobForeigner.board.domain.Post;
import kr.ac.kumoh.d138.JobForeigner.global.base.BaseEntity;
import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
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
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() where id = ?")
@SQLRestriction(value = "deleted_at is NULL") // TODO: 7일 후 논리적 삭제된 사용자를 물리적으로 삭제하는 cron 작업이 필요
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MemberType type;

    @Column(name = "comany_id")
    private Long companyId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender", columnDefinition = "smallint", nullable = false)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "profile_url", nullable = false)
    private String profileImageUrl;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "address", nullable = false)),
            @AttributeOverride(name = "detailAddress", column = @Column(name = "detail_address", nullable = false)),
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode", nullable = false))
    })
    private Address address;

    @Builder
    public Member(
            @NonNull String name,
            @NonNull String username,
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
        this.username = username;
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

    @Override
    public String toString() {
        return "Member{" +
                "address=" + address +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", type=" + type +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
