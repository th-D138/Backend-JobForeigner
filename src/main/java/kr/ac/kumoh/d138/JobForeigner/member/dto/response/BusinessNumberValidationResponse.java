package kr.ac.kumoh.d138.JobForeigner.member.dto.response;

import java.util.List;

/**
 * 사업자등록정보 진위확인을 위한 정보를 받아오기 위한 DTO 클래스입니다.<br>
 * 자세한 정보는 <a href="https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15081808">공공데이터포털 국세청_사업자등록정보 진위확인 및 상태조회 서비스</a>를 참조하세요.
 */
public record BusinessNumberValidationResponse(
    String status_code,
    int request_cnt,
    int valid_cnt,
    List<Data> data
) {
    public record Data(
            String b_no,
            String valid,
            String valid_msg,
            Parameters request_param,
            Status status
    ) {

    }

    public record Parameters(
        String b_no,
        String start_dt,
        String p_nm,
        String p_nm2,
        String b_nm,
        String corp_no,
        String b_sector,
        String b_type,
        String b_adr
    ) {

    }

    public record Status(
        String b_no,
        String b_stt,
        String b_stt_cd,
        String tax_type,
        String tax_type_cd,
        String end_dt,
        String utcc_yn,
        String tax_type_change_dt,
        String invoice_apply_dt,
        String rbf_tax_type,
        String rbf_tax_type_cd
    ) {

    }
}
