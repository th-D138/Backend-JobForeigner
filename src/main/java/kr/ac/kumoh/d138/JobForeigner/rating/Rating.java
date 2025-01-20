package kr.ac.kumoh.d138.JobForeigner.rating;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rating {
    @Id @GeneratedValue
    @Column(name="rating_id",nullable = false)
    private Long id;

    @Column(name="salary_satisfaction",nullable = false)
    private float salarySatisfaction;

    @Column(name="work_difficulty",nullable = false)
    private float workDifficulty;

    @Column(name="work_atmosphere",nullable = false)
    private float workAtmosphere;

    @Column(name="accessibility",nullable = false)
    private float accessibility;

    @Column(name="culture_support",nullable = false)
    private float cultureSupport;

    @Column(name="comment",nullable = false)
    private String comment;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="approve",nullable = false)
    private boolean approve=false;
}
