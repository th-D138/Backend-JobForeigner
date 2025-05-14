package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ProfileImageRequest(
        MultipartFile image
) {
}
