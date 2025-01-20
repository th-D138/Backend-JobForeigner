package kr.ac.kumoh.d138.JobForeigner.advertisement.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image_url", nullable = false)
    private String image_url;

    @Column(name = "target_url", nullable = false)
    private String target_url;

    @Column(name = "click_count", nullable = false)
    private Integer click_count;

    @Column(name = "impression_count", nullable = false)
    private Integer impression_count;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime start_date;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime end_date;
}
