package com.tubing.logic.google;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.tubing.TubingApplication;
import com.tubing.rest.RestClientImpl;
import com.tubing.rest.RestClientResponse;
import com.tubing.rest.RestRequest;

public class YouTubeBuilder {

    public static YouTube build(String userId, String authCode) {

        YouTube ret;
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        //if (isValid(transport, jsonFactory, authCode)) {
        ret = build(authCode, transport, jsonFactory);
        //}

        return ret;
    }

    private static String getAccessToken(String authCode) {

        String ret;
        try {
            final Form form = new Form();
            form.param("code", authCode);
            form.param("client_id", "470859838725-ghrbl1kg0ttueqdosoodf06fohfjlbhr.apps.googleusercontent.com");
            form.param("client_secret", "1zeE1eG9bsIfD8xYYfabEPIC");
            form.param("redirect_uri", "");
            form.param("grant_type", "authorization_code");
            RestRequest request = new RestRequest("https://accounts.google.com/o/oauth2/token", form, MediaType.APPLICATION_FORM_URLENCODED);
            RestClientResponse<String> response = RestClientImpl.getInstance().post(
                    request);
            ret = new JSONObject(response.getEntity()).getString("access_token");
            //String responseBody = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException("Failed to exchange auth code for access and refresh token", e);
        }

        return ret;
    }

//    public static boolean isValid(NetHttpTransport transport, JacksonFactory jsonFactory, String BEARER_TOKEN) {
//
//        boolean ret = true;
//        try {
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(
//                    // Http Transport is needed to fetch Google's latest public key
//                    new ApacheHttpTransport(), jsonFactory);
//            GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, BEARER_TOKEN);
//            if (idToken == null) {
//                System.out.println("Token cannot be parsed");
//                ret = false;
//            }
//
//            System.out.println("Token details:");
//            System.out.println(idToken.getPayload().toPrettyString());
//
//            // Verify valid token, signed by google.com, intended for a third party.
//            if (!verifier.verify(idToken)) {
//                //|| !idToken.verifyAudience(Collections.singletonList(GOOGLE_API_CLIENT_EMAIL_ADDRESS))
//                //|| !idToken.getPayload().getAuthorizedParty().equals(GMAIL_ISSUEE)) {
//                System.out.println("Invalid token");
//                ret = false;
//            }
//
//            // Token originates from Google and is targeted to a specific client.
//            System.out.println("The token is valid");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        return ret;
//    }

//    private static boolean isValid(NetHttpTransport transport, JacksonFactory jsonFactory, String accessToken) {
//
//        boolean ret = false;
//        try {
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//                    .setAudience(Arrays.asList("470859838725-ghrbl1kg0ttueqdosoodf06fohfjlbhr.apps.googleusercontent.com"))
//                    .build();
//            GoogleIdToken idToken = verifier.verify(accessToken);
//            if(idToken != null) {
//                GoogleIdToken.Payload payload = idToken.getPayload();
//                System.out.println("User ID: " + payload.getSubject());
//                System.out.println("getHostedDomain: " + payload.getHostedDomain());
//                System.out.println("getAuthorizedParty: " + payload.getAuthorizedParty());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ret;
//    }

    private static YouTube build(String authCode, NetHttpTransport transport, JacksonFactory jsonFactory) {

        GoogleCredential credential = new GoogleCredential().setAccessToken(getAccessToken(authCode));

        return new YouTube.Builder(transport, jsonFactory, credential).
                setApplicationName(TubingApplication.NAME).
                build();
    }
}
