package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private final String UPLOAD_DIR = "";

//    @Transactional
//    public void updateMemberProfile(Long memberId, MemberProfileRequest request) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
//        member.updateMemberProfile(request);
//    }
//
//    @Transactional
//    public void updateProfileImage(ProfileImageRequest request, Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
//        String imageUrl = this.storeImage(request.image());
//        member.updateProfileImage(imageUrl);
//    }

    public String storeImage(MultipartFile file) {
        try {
            // 이미지의 확장자 추출
            String ext = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(f.lastIndexOf(".")))
                    .orElse("");

            String savedName = UUID.randomUUID() + ext;
            Path savePath = Paths.get(UPLOAD_DIR, savedName);
            Files.createDirectories(savePath.getParent());
            file.transferTo(savePath.toFile());

            return "/images/" + savedName;
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패", e);
        }
    }
}
