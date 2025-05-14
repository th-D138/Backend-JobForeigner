package kr.ac.kumoh.d138.JobForeigner.member.client;

import io.netty.handler.codec.http.HttpScheme;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.BusinessNumberValidationRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.response.BusinessNumberValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class BusinessNumberValidationClient {
    @Value("${job-foreigner.all.open-api.validate-business-number.host}")
    private String apiHost;

    @Value("${job-foreigner.all.open-api.validate-business-number.path}")
    private String apiPath;

    @Value("${job-foreigner.all.open-api.validate-business-number.encoding-key}")
    private String apiKey;

    private final String SERVICE_KEY = "serviceKey"; // API 키 파라미터 지정
    private final String RETURN_TYPE = "returnType"; // 응답 형식 파라미터 지정
    private final String RETURN_TYPE_JSON = "JSON"; // 생략해도 되지만 JSON으로 응답을 받기 위함
    private final int ONLY_ONE_REQUEST = 1; // 요청을 하나만 보내었을 때인지 검증하기 위함
    private final String VALID_BUSINESS_NUMBER = "01"; // 유효한 사업자등록번호인지 확인하기 위함
    private final String CONTINUING_BUSINESS = "01"; // 계속사업자인지 확인하기 위함(폐업한 사용자가 아님을 확인하기 위함)

    private final WebClient.Builder webClientBuilder;

    public void validateBusinessNumber(BusinessNumberValidationRequest request) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE); // URI 인코딩 방지

        try {
            BusinessNumberValidationResponse apiResponse = webClientBuilder.uriBuilderFactory(factory).build().post()
                    .uri(UriComponentsBuilder.newInstance()
                            .scheme(HttpScheme.HTTPS.toString())
                            .host(apiHost)
                            .path(apiPath)
                            .queryParam(SERVICE_KEY, apiKey)
                            .queryParam(RETURN_TYPE, RETURN_TYPE_JSON)
                            .build(true) // URI 인코딩 방지
                            .toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(BusinessNumberValidationResponse.class)
                    .block();

                // 하나의 요청이 전송되었으며 사업자등록번호가 유효하고 계속사업자인 경우 검증 성공이며 그 외는 검증 실패 처리
                if (apiResponse.request_cnt() == ONLY_ONE_REQUEST
                        && apiResponse.data().getFirst().valid().equals(VALID_BUSINESS_NUMBER)
                        && apiResponse.data().getFirst().status().b_stt_cd().equals(CONTINUING_BUSINESS)) {
                    return;
                }
        } catch (WebClientResponseException ex) {
            log.error("오픈 API 서버와 통신에 실패했습니다. status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new BusinessException(ExceptionType.UNEXPECTED_SERVER_ERROR);
        }

        throw new BusinessException(ExceptionType.BUSINESS_NUMBER_INVALID);
    }
}
