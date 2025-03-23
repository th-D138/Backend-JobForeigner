package kr.ac.kumoh.d138.JobForeigner.member.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.board.domain.Comment;
import kr.ac.kumoh.d138.JobForeigner.board.domain.Post;
import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MemberType type;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "address", nullable = false)),
            @AttributeOverride(name = "detailAddress", column = @Column(name = "detail_address", nullable = false)),
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode", nullable = false))
    })
    private Address address;

    public Member(String name, String username, String password, MemberType type, String countryCode, String phoneNumber, String email, Gender gender, LocalDate birthDate, Address address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy="member", fetch=FetchType.LAZY)
    private List<Rating> ratings = new ArrayList<>();
}
