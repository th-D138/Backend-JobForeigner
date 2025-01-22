package kr.ac.kumoh.d138.JobForeigner.rating;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rating {
    @Id @GeneratedValue
    @Column(name="rating_id",nullable = false)
    private Long id;

    @Column(name="salary_satisfaction",nullable = false)
    private Float salarySatisfaction;

    @Column(name = "work_difficulty",nullable = false)
    private Float workDifficulty;

    @Column(name = "work_atmosphere",nullable = false)
    private Float workAtmosphere;

    @Column(name = "accessibility",nullable = false)
    private Float accessibility;

    @Column(name = "culture_support",nullable = false)
    private Float cultureSupport;

    @Column(name = "comment",nullable = false)
    private String comment;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "approve",nullable = false)
    private boolean approve=false;
}
