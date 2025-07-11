package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 사업자등록정보 진위확인을 위한 정보를 전달하기 위한 DTO 클래스입니다.<br>
 * 자세한 정보는 <a href="https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15081808">공공데이터포털 국세청_사업자등록정보 진위확인 및 상태조회 서비스</a>를 참조하세요.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusinessNumberValidationRequest {
    private List<BusinessInfo> businesses;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BusinessInfo {
        @Schema(description = "사업자등록번호", example = "5138302865")
        @NotBlank(message = "사업자등록번호는 필수 항목입니다.")
        @Pattern(regexp = "^[0-9]{10}$", message = "사업자등록번호는 숫자로만 이루어진 10자리여야 합니다.")
        private String b_no;

        @Schema(description = "개업일자", example = "19780601")
        @NotBlank(message = "개업일자는 필수 항목입니다.")
        @Pattern(regexp = "^[0-9]{8}$", message = "날짜 형식은 숫자로만 이루어진 8자리여야 합니다.")
        private String start_dt;

        @Schema(description = "대표자 성명", example = "곽호상")
        @NotBlank(message = "대표자 성명은 필수 항목입니다.")
        private String p_nm;

        // 필수 항목이 아닌 선택 항목은 빈 문자열을 전달
        private String p_nm2 = "";
        private String b_nm = "";
        private String corp_no = "";
        private String b_sector = "";
        private String b_type = "";
        private String b_adr = "";
    }

    public int size() {
        return businesses.size();
    }
}
