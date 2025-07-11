package kr.ac.kumoh.d138.JobForeigner.board.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "published", nullable = false)
    private Boolean published;

    @ManyToOne
    @JoinColumn(name = "board_id")  // 외래 키 컬럼명
    private Board board;

    @Builder
    public Post(String title, String content, Member member, Category category, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean published) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.published = published;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
