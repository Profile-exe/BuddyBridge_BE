package econo.buddybridge.auth.client.kakao;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.client.OAuthApiClient;
import econo.buddybridge.auth.client.kakao.feign.KakaoInfoFeignClient;
import econo.buddybridge.auth.client.kakao.feign.KakaoLoginFeignClient;
import econo.buddybridge.auth.dto.OAuthInfoResponse;
import econo.buddybridge.auth.dto.OAuthLoginParams;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private final KakaoLoginFeignClient kakaoLoginFeignClient;
    private final KakaoInfoFeignClient kakaoInfoFeignClient;

    private static final String GRANT_TYPE = "authorization_code";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Value("${oauth.kakao.url.api-url}")
    private String apiUrl;

    @Value("${oauth.kakao.url.redirect-url}")
    private String redirectUrl;

    @Value("${oauth.kakao.url.logout-redirect-url}")
    private String logoutRedirectUrl;

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getAccessToken(OAuthLoginParams params) {
        return kakaoLoginFeignClient.getToken(
                GRANT_TYPE, clientId, redirectUrl, params.getCode()
        ).getAccessToken();
    }

    @Override
    public OAuthInfoResponse getUserInfo(String accessToken) {
        return kakaoInfoFeignClient.getUserInfo(TOKEN_PREFIX + accessToken);
    }

    @Override
    public void logout() {
        kakaoLoginFeignClient.logout(clientId, logoutRedirectUrl);
    }
}
