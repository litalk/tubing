package com.tubing.controller;

import com.tubing.common.StringUtils;
import com.tubing.dal.EntityPersister;
import com.tubing.dal.model.Account;
import com.tubing.dal.model.Session;
import com.tubing.logic.UserLogic;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/tubing")
public class UserController {

    @Autowired
    private UserLogic _logic;
    @Autowired
    private EntityPersister _persister;

    @RequestMapping(value = "login", method = RequestMethod.POST, headers = "Accept=application/json")
    public LoginResponse login(@RequestBody final String authCode) throws ServletException {

        Account account = null;
        account = _logic.login(getAuthCode(authCode));
        String sessionKey = UUID.randomUUID().toString();
        _persister.insert(new Session(sessionKey, account.getUserId()));

        return new LoginResponse(JwtHelper.build(account.getName(), sessionKey));
    }

    private static String getAuthCode(@RequestBody String json) throws ServletException {

        String ret;
        try {
            ret = new JSONObject(json).getString("auth_code");
        } catch (Exception ex) {
            throw new ServletException(
                    String.format("Invalid body: %s, should look like {auth_code: <authorization code>}", json));
        }

        return ret;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String session = null;
        try {
            session = JwtHelper.getSession(JwtHelper.getToken(request));
        } catch (Exception ex) {
            // todo no session found log
        }
        if (!StringUtils.isNullOrEmpty(session)) {
            try {
                _persister.delete(session);
            } catch (Exception ex) {
                // todo failed to delete session - error log
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @SuppressWarnings("unused")
    private static class LoginResponse {

        public String token;

        public LoginResponse(final String token) {

            this.token = token;
        }
    }
}