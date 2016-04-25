package com.tubing.logic;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.tubing.dal.EntityPersister;
import com.tubing.dal.model.Account;
import com.tubing.logic.google.SecretsContainer;
import com.tubing.rest.RestClientImpl;
import com.tubing.rest.RestClientResponse;
import com.tubing.rest.RestRequest;

@Component
public class UserLogic {

    @Autowired
    private EntityPersister _persister;

    public Account login(String authCode) {

        Account ret = null;
        try {
            TokenContainer token = getToken(authCode);
            GoogleIdToken idToken = validateAccessToken(token.getAccessToken());
            if (idToken != null) {
                ret = updateAccount(token, idToken);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return ret;
    }

    private Account updateAccount(TokenContainer token, GoogleIdToken idToken) {

        GoogleIdToken.Payload payload = idToken.getPayload();
        String userId = payload.getSubject();
        Account ret = new Account(
                token.getAccessToken(),
                token.getRefreshToken(),
                payload.getEmail(),
                userId,
                (String) payload.get("name"),
                (String) payload.get("given_name"),
                (String) payload.get("family_name"),
                (String) payload.get("picture"),
                (String) payload.get("locale"));
        _persister.insert(ret);
        // todo: add login history entity to db

        return ret;
    }

    private TokenContainer getToken(String authCode) {

        TokenContainer ret;
        try {
            final Form form = new Form();
            form.param("code", authCode);
            form.param("client_id", SecretsContainer.get().getDetails().getClientId());
            form.param("client_secret", SecretsContainer.get().getDetails().getClientSecret());
            form.param("redirect_uri", "");
            form.param("grant_type", "authorization_code");
            RestRequest request = new RestRequest("https://accounts.google.com/o/oauth2/token", form, MediaType.APPLICATION_FORM_URLENCODED);
            RestClientResponse<String> response = RestClientImpl.getInstance().post(
                    request);
            JSONObject json = new JSONObject(response.getEntity());
            ret = new TokenContainer(json.getString("access_token"), json.getString("refresh_token"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to exchange auth code for access and refresh token", e);
        }

        return ret;
    }

    private static GoogleIdToken validateAccessToken(String idToken) throws GeneralSecurityException, IOException {

        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jsonFactory)
                .setAudience(Arrays.asList(SecretsContainer.get().getDetails().getClientId()))
                // If you retrieved the token on Android using the Play Services 8.3 API or newer, set
                // the issuer to "https://accounts.google.com". Otherwise, set the issuer to
                // "accounts.google.com". If you need to verify tokens from multiple sources, build
                // a GoogleIdTokenVerifier for each issuer and try them both.
                .setIssuer("https://accounts.google.com")
                .build();
        GoogleIdToken ret = verifier.verify(idToken);
        if (ret == null) {
            throw new RuntimeException("Token cannot be parsed");
        }

        return ret;
    }

    private static class TokenContainer {

        private String _accessToken;
        private String _refreshToken;

        public TokenContainer(String accessToken, String refreshToken) {

            _accessToken = accessToken;
            _refreshToken = refreshToken;
        }

        public String getAccessToken() {

            return _accessToken;
        }

        public String getRefreshToken() {

            return _refreshToken;
        }
    }
}
